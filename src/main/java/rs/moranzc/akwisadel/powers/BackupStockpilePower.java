package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.SupplementaryCharge;

public class BackupStockpilePower extends EWPowerBase {
    public static final String POWER_ID = MakeID(BackupStockpilePower.class.getSimpleName());
    
    public BackupStockpilePower(AbstractCreature owner, int amount) {
        super(POWER_ID, "backup_stockpile", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (owner.isPlayer) {
            flash();
            addToBot(new MakeTempCardInHandAction(new SupplementaryCharge(), amount));
        }
    }
}