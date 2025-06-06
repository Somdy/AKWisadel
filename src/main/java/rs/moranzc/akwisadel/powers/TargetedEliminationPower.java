package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.utils.DamageUtils;

public class TargetedEliminationPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(TargetedEliminationPower.class.getSimpleName());
    
    public TargetedEliminationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "targeted_elimination", PowerType.DEBUFF, owner);
        setValues(null, amount);
        preloadString(() -> mkstring(desc[0], ((int) calculateDamageBoost()) * 100));
        updateDescription();
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType, AbstractCard card) {
        if (card instanceof EWBombCardBase) {
            float damageBoost = calculateDamageBoost();
            damage = damage * (1.0F + damageBoost);
        }
        return super.atDamageReceive(damage, damageType, card);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (DamageUtils.IsDamageInfoFromCard(info, c -> c instanceof EWBombCardBase)) {
            flashWithoutSound();
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
        return super.onAttacked(info, damageAmount);
    }

    private float calculateDamageBoost() {
        return ((int) Math.pow(2, amount)) - 1;
    }
}