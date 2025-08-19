package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.common.DamageCardsAction;
import rs.moranzc.akwisadel.base.EWPowerBase;

public class FurnacePower extends EWPowerBase {
    public static final String POWER_ID = MakeID(FurnacePower.class.getSimpleName());
    
    public FurnacePower(AbstractCreature owner) {
        super(POWER_ID, "furnace", PowerType.DEBUFF, owner);
        setValues(owner, -1);
        preloadString(() -> mkstring(desc[0]));
        updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        flashWithoutSound();
        addToBot(new DamageCardsAction(AbstractDungeon.player, card));
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}