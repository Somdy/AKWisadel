package rs.moranzc.akwisadel.core;

import basemod.AutoAdd;
import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.dynvars.ExtraMagicVariable;
import rs.moranzc.akwisadel.cards.dynvars.SlotVariable;
import rs.moranzc.akwisadel.cards.wisadel.Strike_EW;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

import java.util.*;
import java.util.function.Predicate;

public final class CardMst {
    
    private static final Map<String, EWCardBase> card_map = new HashMap<>();
    private static final Set<EWCardBase> parts = new HashSet<>();
    
    public static void Initialize() {
        new AutoAdd(Kazdel.MOD_ID)
                .packageFilter(Strike_EW.class)
                .any(EWCardBase.class, (i, c) -> addCard(c));
        Kazdel.logger.info("{} cards added!", card_map.values().size());
        BaseMod.addDynamicVariable(new SlotVariable());
        BaseMod.addDynamicVariable(new ExtraMagicVariable());
    }
    
    private static void addCard(EWCardBase card) {
        card_map.put(card.cardID, card);
        if (card instanceof IPartCard)
            parts.add(card);
        BaseMod.addCard(card.makeCopy());
    }
    
    public static AbstractCard GetRandomPart(Predicate<EWCardBase> matcher) {
        return parts.stream().parallel().filter(matcher).findAny().map(c -> (AbstractCard) c).orElse(new Madness());
    }

    public static AbstractCard GetRandomPart() {
        return GetRandomPart(c -> true);
    }
    
    public static AbstractCard GetRandomCard(Predicate<EWCardBase> matcher) {
        return card_map.values().stream().parallel().filter(matcher).findAny().map(c -> (AbstractCard) c).orElse(new Madness());
    }
}
