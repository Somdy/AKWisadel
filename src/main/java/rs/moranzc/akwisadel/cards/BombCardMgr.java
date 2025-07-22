package rs.moranzc.akwisadel.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;
import rs.moranzc.akwisadel.utils.CardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

public class BombCardMgr {
    public static final int DAMAGE_INCREMENT_PER_SLOT = 2;
    public static final WeakHashMap<EWBombCardBase, List<AbstractCard>> CARDS_TO_DAMAGE_MAP = new WeakHashMap<>();
    
    public static List<AbstractCard> ProvokeBombCard(EWBombCardBase bomb, AbstractPlayer p, AbstractCreature t) {
        if (p.hand.isEmpty() || bomb.slot <= 0)
            return new ArrayList<>();
        boolean manually = false;
        if (manually) {
            //TODO: Select manually
            return new ArrayList<>();
        } else {
            List<AbstractCard> cardsToDamage = new ArrayList<>();
            AutoCollectCardsForBomb(cardsToDamage, bomb, p);
            applySlotsOnBomb(bomb, p, t, cardsToDamage);
            bomb.onUse(p, t, cardsToDamage);
            return cardsToDamage;
        }
    }
    
    private static void applySlotsOnBomb(EWBombCardBase card, AbstractPlayer p, AbstractCreature t, List<AbstractCard> slots) {
        int numberOfSlots = slots.size();
        if (numberOfSlots > 0) {
            card.increaseDamageForCombat(numberOfSlots * DAMAGE_INCREMENT_PER_SLOT);
        }
        applyPartsOnBomb(card, p, t, slots);
    }
    
    private static void applyPartsOnBomb(EWBombCardBase card, AbstractPlayer p, AbstractCreature t, List<AbstractCard> parts) {
        parts.stream().filter(c -> c instanceof IPartCard)
                .map(c -> (IPartCard) c)
                .forEach(c -> c.onAppliedOnBomb(card, p, t, card.slotEfficiency));
    }
    
    public static void AutoCollectCardsForBomb(List<AbstractCard> cardsToDamage, EWBombCardBase bomb, AbstractPlayer p) {
        if (p.hand.isEmpty() || bomb.slot <= 0) {
            return;
        }
        List<AbstractCard> tempHand = new ArrayList<>(p.hand.group);
        if (tempHand.size() <= bomb.slot) {
            cardsToDamage.addAll(tempHand);
        } else {
            List<AbstractCard> parts = tempHand.stream().filter(CardUtils::IsPart).collect(Collectors.toList());
            if (parts.size() > bomb.slot) {
                parts.subList(bomb.slot, parts.size()).clear();
            }
            cardsToDamage.addAll(parts);
            tempHand.removeAll(parts);
            int left = bomb.slot - cardsToDamage.size();
            if (left > 0 && left < tempHand.size()) {
                cardsToDamage.addAll(tempHand.subList(0, left));
            }
        }
    }
    
    public static void LogMetric(EWBombCardBase bomb, List<AbstractCard> cardsToDamage) {
        StringBuilder sbr = new StringBuilder();
        cardsToDamage.forEach(c -> sbr.append(String.format("%s:%s, ", c.cardID, c.name)));
        Kazdel.logger.info("Bomb {}:{} consumed [ {} ]", bomb.cardID, bomb.name, sbr.substring(0, sbr.length() - 1));
    }
}
