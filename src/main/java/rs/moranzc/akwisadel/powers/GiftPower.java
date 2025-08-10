package rs.moranzc.akwisadel.powers;

import com.badlogic.gdx.math.MathUtils;
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
        preloadString(() -> mkstring(desc[0], calculateDamageBoost() * 100.0F, calculateExplodingDamage(2.0F)));
        updateDescription();
        ignited = false;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float damageBoost = calculateDamageBoost();
        damage = damage * (1.0F - damageBoost);
        return super.atDamageGive(damage, type);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (DamageUtils.IsDamageInfoFromCard(info, c -> c instanceof EWBombCardBase) && !ignited) {
            ignite(info.owner, 2.0F);
        }
        return super.onAttacked(info, damageAmount);
    }
    
    public void ignite(AbstractCreature s, float times) {
        ignited = true;
        flash();
        addToTop(new DamageAction(owner, new DamageInfo(s, calculateExplodingDamage(times), DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.FIRE));
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }
    
    private int calculateExplodingDamage(float times) {
        return MathUtils.floor(amount * times);
    }

    private float calculateDamageBoost() {
        return amount * 0.03F;
    }
}