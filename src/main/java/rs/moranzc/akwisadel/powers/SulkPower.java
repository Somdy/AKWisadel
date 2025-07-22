package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWPowerBase;

public class SulkPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(SulkPower.class.getSimpleName());
    
    public SulkPower(AbstractCreature owner) {
        super(POWER_ID, "sulk", PowerType.DEBUFF, owner);
        setValues(null, -1);
        preloadString(() -> mkstring(desc[0]));
        updateDescription();
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        if (owner.isPlayer) {
            return card.type == AbstractCard.CardType.ATTACK;
        }
        return super.canPlayCard(card);
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}