package rs.moranzc.akwisadel.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rs.moranzc.akwisadel.actions.utility.DamageCardsOnBombAction;
import rs.moranzc.akwisadel.actions.utility.HandCardSelectActionBuilder;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;
import rs.moranzc.akwisadel.localization.I18nManager;
import rs.moranzc.akwisadel.powers.AdvancedExtendedPower;
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
        AbstractPower po = p.getPower(AdvancedExtendedPower.POWER_ID);
        if (po instanceof AdvancedExtendedPower) {
            manually = po.amount > 0;
        }
        if (manually) {
            AbstractDungeon.actionManager.addToBottom(new HandCardSelectActionBuilder(bomb.slot, I18nManager.MT("DamageCardAction"))
                    .anyNumber(true).canPickZero(false).postSelected(cards -> {
                        // execute the bomb and rearrange the actions so UseCardAction would be the latest one
                        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
                            @Override
                            public void update() {
                                isDone = true;
                                applySlotsOnBomb(bomb, p, t, cards);
                                bomb.lastCardsToDamage.clear();
                                bomb.onUse(p, t, cards);
                                addToBot(new DamageCardsOnBombAction(bomb, p, t, cards));
                                List<AbstractGameAction> uca = new ArrayList<>();
                                AbstractDungeon.actionManager.actions.stream().filter(a -> a instanceof UseCardAction).forEach(uca::add);
                                AbstractDungeon.actionManager.actions.removeAll(uca);
                                AbstractDungeon.actionManager.actions.addAll(uca);
                            }
                        });
                    }));
            return new ArrayList<>();
        } else {
            List<AbstractCard> cardsToDamage = new ArrayList<>();
            AutoCollectCardsForBomb(cardsToDamage, bomb, p);
            applySlotsOnBomb(bomb, p, t, cardsToDamage);
            bomb.lastCardsToDamage.clear();
            bomb.onUse(p, t, cardsToDamage);
            return cardsToDamage;
        }
    }
    
    private static void applySlotsOnBomb(EWBombCardBase card, AbstractPlayer p, AbstractCreature t, List<AbstractCard> slots) {
        int numberOfSlots = slots.size();
        boolean change250816 = false;
        if (numberOfSlots > 0 && change250816) {
            int obd = card.baseDamage;
            int increment = numberOfSlots * DAMAGE_INCREMENT_PER_SLOT;
            card.increaseDamageForCombat(increment);
            Kazdel.logger.info("Bomb {} damaging {} cards and increasing damage from {} to {}, increment: {}", 
                    card, numberOfSlots, obd, card.baseDamage, increment);
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
        tempHand.remove(bomb);
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
        Kazdel.logger.info("Bomb {}:{} consumed [ {} ]", bomb.cardID, bomb, sbr.substring(0, sbr.length() - 1));
    }
}
