package rs.moranzc.akwisadel.patches.com.megacrit.cardcrawl.characters.AbstractPlayer;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;
import rs.moranzc.akwisadel.characters.CharWisadel;

@SpirePatch(clz = AbstractPlayer.class, method = "damage")
public class RevenantDamagePatch {
    @SpireInsertPatch(locator = Locator.class, localvars = {"damageAmount"})
    public static void Insert(AbstractPlayer _inst, DamageInfo info, @ByRef int[] damageAmount) {
        int damage = damageAmount[0];
        if (_inst instanceof CharWisadel && damage > 0) {
            CharWisadel ew = ((CharWisadel) _inst);
            damageAmount[0] = ew.blockDamageByRevenant(info, damage);
        }
    }
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.FieldAccessMatcher matcher = new Matcher.FieldAccessMatcher(DamageInfo.class, "owner");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
