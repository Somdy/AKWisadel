package rs.moranzc.akwisadel.actions.utility;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

import java.util.ArrayList;
import java.util.List;

public class DamageCardsInHandAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final List<AbstractCard> cardsToDamage;
    
    public DamageCardsInHandAction(AbstractPlayer p, AbstractCreature t, List<AbstractCard> cardsToDamage) {
        this.p = p;
        setValues(t, p);
        this.cardsToDamage = new ArrayList<>(cardsToDamage);
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
                // Damaged ones go to Exhaust pile and rest
                if (CardModifierManager.hasModifier(c, DamagedCardModifier.ID)) {
                    p.hand.moveToExhaustPile(c);
                } else {
                    CardModifierManager.addModifier(c, new DamagedCardModifier());
                }
            });
            cardsToDamage.clear();
            p.hand.applyPowers();
            CardCrawlGame.dungeon.checkForPactAchievement();
        }
        tickDuration();
    }
}
