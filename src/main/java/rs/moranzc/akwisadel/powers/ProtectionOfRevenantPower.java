package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.ApplyPowerToEnemiesAction;
import rs.moranzc.akwisadel.base.EWPowerBase;

public class ProtectionOfRevenantPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(ProtectionOfRevenantPower.class.getSimpleName());
    
    public ProtectionOfRevenantPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "protection_of_revenant", PowerType.DEBUFF, owner);
        setValues(owner, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.owner != owner && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            addToBot(new ApplyPowerToEnemiesAction(owner, m -> new GiftPower(m, amount)));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }
}