package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.utils.DamageUtils;

public class GiftPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(GiftPower.class.getSimpleName());
    private boolean ignited;
    
    public GiftPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "gift", PowerType.DEBUFF, owner);
        setValues(null, amount);
        preloadString(() -> mkstring(desc[0], ((int) calculateDamageBoost()) * 100, calculateExplodingDamage()));
        updateDescription();
        ignited = false;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float damageBoost = calculateDamageBoost();
        damage = damageBoost * (1.0F - damageBoost);
        return super.atDamageGive(damage, type);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (DamageUtils.IsDamageInfoFromCard(info, c -> c instanceof EWBombCardBase) && !ignited) {
            ignited = true;
            flash();
            addToTop(new DamageAction(owner, new DamageInfo(info.owner, calculateExplodingDamage(), DamageInfo.DamageType.THORNS), 
                    AbstractGameAction.AttackEffect.FIRE));
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
        return super.onAttacked(info, damageAmount);
    }
    
    private int calculateExplodingDamage() {
        return amount * 2;
    }

    private float calculateDamageBoost() {
        return amount * 0.03F;
    }
}