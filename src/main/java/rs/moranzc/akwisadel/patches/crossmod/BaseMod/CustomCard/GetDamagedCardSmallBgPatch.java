package rs.moranzc.akwisadel.patches.crossmod.BaseMod.CustomCard;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import rs.moranzc.akwisadel.utils.CardUtils;
import rs.moranzc.akwisadel.utils.TexMgr;

@SpirePatch(clz = CustomCard.class, method = "getBackgroundSmallTexture")
public class GetDamagedCardSmallBgPatch {
    @SpirePrefixPatch
    public static SpireReturn<Texture> PrefixGet(CustomCard _inst) {
        if (CardUtils.IsDamaged(_inst)) {
            return SpireReturn.Return(TexMgr.CARD_BG_SMALL_DAMAGED);
        }
        return SpireReturn.Continue();
    }
}
