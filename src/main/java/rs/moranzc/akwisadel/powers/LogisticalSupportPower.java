package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.core.CardMst;

public class LogisticalSupportPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(LogisticalSupportPower.class.getSimpleName());
    
    public LogisticalSupportPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "logistical_support", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (amount > 0 && owner != null && owner.isPlayer && !owner.isDeadOrEscaped()) {
            flash();
            for (int i = 0; i < amount; i++) {
                AbstractCard c = CardMst.GetUnrestrictedRandomPart();
                addToBot(new MakeTempCardInHandAction(c, 1));
            }
        }
    }
}