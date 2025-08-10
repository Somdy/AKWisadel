package rs.moranzc.akcharlib.patches;

import basemod.CustomCharacterSelectScreen;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import javassist.CtBehavior;
import rs.moranzc.akcharlib.core.CLOSURE;

public class AKCharSkinPatches {
    @SpirePatch(clz = CustomCharacterSelectScreen.class, method = "initialize")
    public static class CharacterSelectScreenInitializePatch {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen _inst) {
            CLOSURE.SkinPanel.initialize(_inst);
        }
    }
    
    @SpirePatch(clz = CharacterSelectScreen.class, method = "open")
    public static class CharacterSelectScreenOpenPath {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen _inst) {
            CLOSURE.SkinPanel.reset();
        }
    }

    @SpirePatch(clz = CharacterOption.class, method = "update")
    public static class CharacterOptionUpdatePatch {
        @SpirePostfixPatch
        public static void Postfix(CharacterOption _inst, float ___infoX) {
            if (_inst.selected && CLOSURE.SkinPanel.hasSkins(_inst)) 
                CLOSURE.SkinPanel.update(_inst, ___infoX);
        }
    }

    @SpirePatch(clz = CharacterOption.class, method = "renderInfo")
    public static class CharacterOptionRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(CharacterOption _inst, SpriteBatch sb) {
            if (_inst.selected && CLOSURE.SkinPanel.hasSkins(_inst)) 
                CLOSURE.SkinPanel.render(sb);
        }
        
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CharacterOption _inst) {
            if (_inst.selected && CLOSURE.SkinPanel.hasSkins(_inst)) {
                ReflectionHacks.setPrivate(_inst, CharacterOption.class, "infoY",
                        (Settings.HEIGHT / 2.0F) - 90.0F * Settings.scale);
            }
        }
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher matcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "draw");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}