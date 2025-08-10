package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.Ammo;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.core.CardMst;

public class ExplosiveDawnPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(ExplosiveDawnPower.class.getSimpleName());
    
    public ExplosiveDawnPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "explosive_dawn", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (amount > 0 && owner != null && owner.isPlayer && !owner.isDeadOrEscaped()) {
            flash();
            addToBot(new MakeTempCardInHandAction(new Ammo(), 1));
        }
    }

    @Override
    public void onInitialApplication() {
        if (owner instanceof CharWisadel) {
            CharWisadel ew = ((CharWisadel) owner);
            ew.state.setAnimation(0, "Skill_3_Begin", false);
            ew.state.addAnimation(0, "Skill_3_Idle", true, 0.0F);
        }
    }

    @Override
    public void onRemove() {
        if (owner instanceof CharWisadel) {
            CharWisadel ew = ((CharWisadel) owner);
            ew.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }
}