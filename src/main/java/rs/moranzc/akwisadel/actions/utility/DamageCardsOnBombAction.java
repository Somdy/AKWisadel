package rs.moranzc.akwisadel.actions.utility;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.cards.BombCardMgr;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.cards.modifiers.SlotIntoBombCardModifier;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;
import rs.moranzc.akwisadel.utils.CardUtils;

import java.util.ArrayList;
import java.util.List;

public class DamageCardsOnBombAction extends AbstractGameAction {
    private final EWBombCardBase bomb;
    private final AbstractPlayer p;
    private final List<AbstractCard> cardsToDamage;
    
    public DamageCardsOnBombAction(EWBombCardBase bomb, AbstractPlayer p, AbstractCreature t, List<AbstractCard> cardsToDamage) {
        this.bomb = bomb;
        this.p = p;
        setValues(t, p);
        this.cardsToDamage = new ArrayList<>(cardsToDamage);
        this.cardsToDamage.forEach(c -> CardModifierManager.addModifier(c, new SlotIntoBombCardModifier()));
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }
    
    @Override
    public void update() {
        if (cardsToDamage.isEmpty() || AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
            return;
        }
        if (duration == startDuration) {
            cardsToDamage.forEach(c -> {
                CardModifierManager.removeModifiersById(c, SlotIntoBombCardModifier.ID, true);
                // Parts and Damaged ones go to Exhaust pile and rest
                if (c instanceof IPartCard || CardUtils.IsDamaged(c)) {
                    p.hand.moveToExhaustPile(c);
                } else {
                    CardUtils.DamageCard(c);
                    p.hand.moveToDiscardPile(c);
                }
            });
            BombCardMgr.LogMetric(bomb, cardsToDamage);
            cardsToDamage.clear();
            p.hand.applyPowers();
            CardCrawlGame.dungeon.checkForPactAchievement();
        }
        tickDuration();
    }
}
