package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.utility.HandCardSelectActionBuilder;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class OrderOfRevenantPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(OrderOfRevenantPower.class.getSimpleName());
    
    public OrderOfRevenantPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "order_of_revenant", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }
}