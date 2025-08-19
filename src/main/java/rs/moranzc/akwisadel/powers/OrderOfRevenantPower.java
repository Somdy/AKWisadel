package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.utility.HandCardSelectActionBuilder;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.utils.CardUtils;

public class OrderOfRevenantPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(OrderOfRevenantPower.class.getSimpleName());
    
    public OrderOfRevenantPower(AbstractCreature owner, int damage, int maxHp) {
        super(POWER_ID, "order_of_revenant", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, damage, maxHp);
        preloadString(() -> mkstring(desc[0], this.amount, this.extraAmt));
        updateDescription();
    }

    @Override
    public void stackExtraAmount(int stackAmount) {
        super.stackExtraAmount(stackAmount);
        if (cpr() instanceof CharWisadel) {
            CharWisadel ew = ((CharWisadel) cpr());
            ew.forEachLiveRevenant(r -> r.modifyMaxHp(stackAmount));
        }
    }

    @Override
    public void onInitialApplication() {
        if (cpr() instanceof CharWisadel) {
            CharWisadel ew = ((CharWisadel) cpr());
            ew.forEachLiveRevenant(r -> r.modifyMaxHp(extraAmt));
        }
    }

    @Override
    public void onRemove() {
        if (cpr() instanceof CharWisadel) {
            CharWisadel ew = ((CharWisadel) cpr());
            ew.forEachLiveRevenant(r -> r.modifyMaxHp(-extraAmt));
        }
    }
}