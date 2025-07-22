package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.utility.HandCardSelectActionBuilder;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class CrocodileTearsPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(CrocodileTearsPower.class.getSimpleName());
    
    public CrocodileTearsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "crocodile_tears", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && owner.isPlayer) {
            addToBot(new HandCardSelectActionBuilder(amount, desc[1]).anyNumber(true).canPickZero(true)
                    .manipulate((c, i) -> {
                        if (!c.isEthereal)
                            c.retain = true;
                        CardUtils.MendCard(c);
                        return true;
                    }));
        }
    }
}