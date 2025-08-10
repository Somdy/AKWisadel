package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.base.EWPowerBase;

public class AdvancedExtendedPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(AdvancedExtendedPower.class.getSimpleName());
    
    public AdvancedExtendedPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "advanced_extended", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }
}