package rs.moranzc.akwisadel.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.function.Consumer;

public class PlayTopCardsCallbackAction extends AbstractGameAction {
    private Consumer<AbstractCard> callback;
    private boolean exhaustCard;
    
    public PlayTopCardsCallbackAction(int amount, boolean exhaustCard) {
        this.amount = amount;
        this.exhaustCard = exhaustCard;
        callback = c -> {};
        target = AbstractDungeon.getMonsters().getRandomMonster(true);
        actionType = ActionType.CARD_MANIPULATION;
    }
    
    public PlayTopCardsCallbackAction callback(Consumer<AbstractCard> action) {
        callback = action;
        return this;
    }
    
    public PlayTopCardsCallbackAction target(AbstractCreature t) {
        target = t;
        return this;
    }
    
    @Override
    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;
        int drawSize = p.drawPile.size();
        int discardSize = p.discardPile.size();
        if (drawSize + discardSize <= 0)
            return;
        if (drawSize <= 0) {
            addToTop(new PlayTopCardsCallbackAction(amount, exhaustCard).target(target).callback(callback));
            addToTop(new EmptyDeckShuffleAction());
            return;
        }
        AbstractCard card = p.drawPile.getTopCard();
        if (card == null)
            return;
        p.drawPile.group.remove(card);
        AbstractDungeon.getCurrRoom().souls.remove(card);
        p.limbo.group.add(card);
        card.exhaustOnUseOnce = exhaustCard;
        card.current_y = -200.0F * Settings.scale;
        card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
        card.target_y = Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.lighten(false);
        card.drawScale = 0.12F;
        card.targetDrawScale = 0.75F;
        card.applyPowers();
        if (target != null && target instanceof AbstractMonster) {
            card.calculateCardDamage((AbstractMonster) target);
        }
        addToTop(new NewQueueCardAction(card, target, false, true));
        addToTop(new UnlimboAction(card));
        addToTop(new WaitAction(Settings.ACTION_DUR_XFAST));
        if (callback != null)
            callback.accept(card);
    }
}
