package rs.moranzc.akwisadel.patches.crossmods.basemod.CardModifierManager;

import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnModifierAppliedCard;

@SpirePatch(cls = "basemod.helpers.CardModifierManager", method = "addModifier", optional = true)
public class AddModifierPatch {
    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(AbstractCard card, AbstractCardModifier mod) {
        if (card instanceof ICallOnModifierAppliedCard && mod != null) {
            ((ICallOnModifierAppliedCard) card).onModifierApplied(mod);
        }
    }
    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher.MethodCallMatcher matcher = new Matcher.MethodCallMatcher(AbstractCardModifier.class, "onInitialApplication");
            return LineFinder.findInOrder(ctBehavior, matcher);
        }
    }
}
