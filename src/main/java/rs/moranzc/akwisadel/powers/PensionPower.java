package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.interfaces.powers.ICallOnCardsDamagedPower;

public class PensionPower extends EWPowerBase implements ICallOnCardsDamagedPower {
    public static final String POWER_ID = MakeID(PensionPower.class.getSimpleName());
    
    public PensionPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "pension", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }

    @Override
    public void onCardDamaged(AbstractCard cardDamaged) {
        flash();
        addToBot(new GainBlockAction(owner, amount, true));
    }
}