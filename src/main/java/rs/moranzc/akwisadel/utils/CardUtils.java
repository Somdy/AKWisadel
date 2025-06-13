package rs.moranzc.akwisadel.utils;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

public class CardUtils {
    
    
    public static void DamageCard(AbstractCard card) {
        if (!IsDamaged(card)) {
            CardModifierManager.addModifier(card, new DamagedCardModifier());
            CharWisadel.CARDS_DAMAGED_THIS_TURN.add(card);
        }
    }

    public static boolean IsPart(AbstractCard card) {
        return card instanceof IPartCard;
    }
    
    public static boolean IsDamaged(AbstractCard card) {
        return CardModifierManager.hasModifier(card, DamagedCardModifier.ID);
    }
}