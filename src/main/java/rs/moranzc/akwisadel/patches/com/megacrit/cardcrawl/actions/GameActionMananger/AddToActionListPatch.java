package rs.moranzc.akwisadel.patches.com.megacrit.cardcrawl.actions.GameActionMananger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;

public class AddToActionListPatch {
    @Deprecated
    @SpirePatches({
            @SpirePatch(clz = GameActionManager.class, method = "addToBottom"),
            @SpirePatch(clz = GameActionManager.class, method = "addToTop"),
            @SpirePatch(clz = GameActionManager.class, method = "addToTurnStart")
    })
    public static class PreCheckAction {
        public static void PreCheck(GameActionManager _inst, AbstractGameAction action) {
            
        }
    }
}
