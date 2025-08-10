package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.base.EWPowerBase;

public class BladeOfRevenantPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(BladeOfRevenantPower.class.getSimpleName());
    
    public BladeOfRevenantPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "blade_of_revenant", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != owner && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            addToTop(new ApplyPowerAction(target, owner, new GiftPower(target, amount)));
        }
    }
}