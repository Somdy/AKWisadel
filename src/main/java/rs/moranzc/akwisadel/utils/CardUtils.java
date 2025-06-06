package rs.moranzc.akwisadel.utils;

import com.megacrit.cardcrawl.cards.AbstractCard;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

public class CardUtils {

    public static boolean IsPart(AbstractCard card) {
        return card instanceof IPartCard;
    }
}