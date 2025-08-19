package rs.moranzc.akwisadel.core;

import basemod.AutoAdd;
import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.dynvars.ExtraMagicVariable;
import rs.moranzc.akwisadel.cards.dynvars.SlotVariable;
import rs.moranzc.akwisadel.cards.wisadel.LordOfBombing;
import rs.moranzc.akwisadel.cards.wisadel.Strike_EW;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class CardMst {
    
    private static final Map<String, EWCardBase> card_map = new HashMap<>();
    private static final Set<EWCardBase> parts = new HashSet<>();
    private static final Set<String> special_parts = new HashSet<>();
    
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
        UnlockTracker.unlockCard(card.cardID);
        BaseMod.addCard(card.makeCopy());
    }
    
    public static boolean IsRestrictedPart(AbstractCard card) {
        return card instanceof LordOfBombing || special_parts.stream().anyMatch(s -> s.equals(card.cardID));
    }
    
    public static AbstractCard GetRandomPart(Predicate<EWCardBase> matcher, Random rng) {
        List<AbstractCard> cards = parts.stream().filter(matcher).collect(Collectors.toList());
        if (cards.isEmpty())
            return new Madness();
        if (rng != null) {
            return cards.get(rng.random(cards.size() - 1)).makeCopy();
        }
        return cards.get(MathUtils.random(cards.size() - 1)).makeCopy();
    }

    public static AbstractCard GetUnrestrictedRandomPart() {
        return GetRandomPart(c -> !IsRestrictedPart(c), AbstractDungeon.cardRandomRng);
    }
    
    public static AbstractCard GetRandomCard(Predicate<EWCardBase> matcher, Random rng) {
        List<AbstractCard> cards = card_map.values().stream().filter(matcher).collect(Collectors.toList());
        if (cards.isEmpty())
            return new Madness();
        if (rng != null) {
            return cards.get(rng.random(cards.size() - 1)).makeCopy();
        }
        return cards.get(MathUtils.random(cards.size() - 1)).makeCopy();
    }
    
    public static AbstractCard GetRandomCard(Predicate<EWCardBase> matcher) {
        return GetRandomCard(matcher, AbstractDungeon.cardRandomRng);
    }
}
