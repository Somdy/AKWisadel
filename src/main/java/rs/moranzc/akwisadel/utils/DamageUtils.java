package rs.moranzc.akwisadel.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import rs.moranzc.akwisadel.patches.com.megacrit.cardcrawl.cards.DamageInfo.DamageInfoFieldPatch;

import java.util.function.Predicate;

public class DamageUtils {
    
    public static boolean IsDamageInfoFromCard(DamageInfo info, Predicate<AbstractCard> cardFilter) {
        if (info == null || cardFilter == null)
            return false;
        AbstractCard cardFrom = DamageInfoFieldPatch.cardFrom.get(info);
        return cardFrom != null && cardFilter.test(cardFrom);
    }
    
    public static void AddCardFromToDamageInfo(DamageInfo info, AbstractCard cardFrom) {
        if (info == null || cardFrom == null)
            return;
        DamageInfoFieldPatch.cardFrom.set(info, cardFrom);
    }
}
