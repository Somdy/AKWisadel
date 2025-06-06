package rs.moranzc.akwisadel.patches.com.megacrit.cardcrawl.cards.DamageInfo;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

import java.util.Map;

@SpirePatch(clz = DamageInfo.class, method = SpirePatch.CLASS)
public class DamageInfoFieldPatch {
    public static SpireField<AbstractCard> cardFrom = new SpireField<>(() -> null);
}
