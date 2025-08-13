package rs.moranzc.akwisadel.patches.com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import rs.moranzc.akwisadel.utils.CardUtils;
import rs.moranzc.akwisadel.utils.TexMgr;

public class RenderDamagedMarkPatch {
    @SpirePatch(clz = AbstractCard.class, method = "renderCardBg")
    public static class RenderTempMarkPatch {
        private static TextureAtlas.AtlasRegion img = null;
        @SpirePostfixPatch
        public static void Postfix(AbstractCard _inst, SpriteBatch sb, float x, float y) {
            if (CardUtils.IsDamaged(_inst)) {
                if (img == null) {
                    img = new TextureAtlas.AtlasRegion(TexMgr.LoadTex("AKWisadelAssets/images/cards/ui/damaged_mark_temp.png"), 
                            0, 0, 512, 512);
                }
                ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, 
                        TextureAtlas.AtlasRegion.class, float.class, float.class)
                        .invoke(_inst, sb, Color.WHITE.cpy(), img, x, y);
            }
        }
    }
}
