package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.common.ApplyPowerToEnemiesAction;
import rs.moranzc.akwisadel.base.EWPowerBase;

public class FogOfRevenantPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(FogOfRevenantPower.class.getSimpleName());
    
    public FogOfRevenantPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "fog_of_revenant", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (owner.isPlayer) {
            flash();
            addToBot(new ApplyPowerToEnemiesAction(owner, m -> new GiftPower(m, amount)));
        }
    }
}