package rs.moranzc.akwisadel.patches.com.megacrit.cardcrawl.actions.common.ApplyPowerAction;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.core.Kazdel;

import java.lang.reflect.Field;

public class StackExtraPowerAmountFix {
    @SpirePatch(clz = ApplyPowerAction.class, method = "update")
    public static class ApplyPowerActionStackPowerFix {
        @SpireInsertPatch(rloc = 78, localvars = {"p"})
        public static void Insert(ApplyPowerAction _inst, AbstractPower p) {
            AbstractPower powerToApply = null;
            try {
                Field power = ApplyPowerAction.class.getDeclaredField("powerToApply");
                power.setAccessible(true);
                powerToApply = (AbstractPower) power.get(_inst);
            } catch (Exception e) {
                Kazdel.logger.info("Failed to catch powerToApply, which wise mod are you playing with???");
            }
            if (powerToApply != null) {
                if (p.ID.equals(powerToApply.ID) && !p.ID.equals("Night Terror")
                        && p instanceof EWPowerBase && powerToApply instanceof EWPowerBase) {
                    ((EWPowerBase) p).stackExtraAmount(((EWPowerBase) powerToApply).extraAmt);
                    p.updateDescription();
                }
            }
        }
    }
    @SpirePatch(clz = AbstractCreature.class, method = "addPower")
    public static class CreatureAddPowerStackFix {
        @SpireInsertPatch(rloc = 10, localvars = {"hasBuffAlready"})
        public static void Insert(AbstractCreature _inst, AbstractPower powerToApply, @ByRef boolean[] hasBuffAlready) {
            for (AbstractPower p : _inst.powers) {
                if (p.ID.equals(powerToApply.ID) && p instanceof EWPowerBase && powerToApply instanceof EWPowerBase) {
                    ((EWPowerBase) p).stackExtraAmount(((EWPowerBase) powerToApply).extraAmt);
                    p.updateDescription();
                    if (!hasBuffAlready[0])
                        hasBuffAlready[0] = true;
                }
            }
        }
    }
}