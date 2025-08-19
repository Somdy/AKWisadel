package rs.moranzc.akwisadel.utils;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnOtherCardsDamagedCard;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;
import rs.moranzc.akwisadel.interfaces.powers.ICallOnCardsDamagedPower;
import rs.moranzc.akwisadel.interfaces.powers.ICallOnCardsMendedPower;

import java.util.function.Predicate;

public class CardUtils {
    public static final int BLOCK_PER_MEND = 2;

    public static void DamageCard(AbstractCard card) {
        DamageCard(card, null);
    }

    public static void DamageCard(AbstractCard card, CardGroup srcCG) {
        if (card == null)
            return;
        if (!IsDamaged(card)) {
            CardModifierManager.addModifier(card, new DamagedCardModifier());
        } else {
            // Exhausts those damaged
            if (srcCG == null) {
                srcCG = LocateCardGroupWhereExists(card);
            }
            if (srcCG == null) {
                if (AbstractDungeon.player.exhaustPile.contains(card)) {
                    Kazdel.logger.info("Card {} is already exhausted", card);
                } else {
                    Kazdel.logger.warn("Unable to find the card group where the card {} exists", card);
                    srcCG = AbstractDungeon.player.hand;
                }
            }
            if (srcCG != null) {
                srcCG.moveToExhaustPile(card);
            }
        }
        CharWisadel.CARDS_DAMAGED_THIS_TURN.add(card);
        CardGroup cg = GetAllUnexhaustedCardsInCombat();
        cg.group.stream().filter(c -> c instanceof ICallOnOtherCardsDamagedCard && c != card)
                .map(c -> (ICallOnOtherCardsDamagedCard) c)
                .forEach(c -> c.onOtherCardDamaged(card));
        AbstractDungeon.player.powers.stream().filter(p -> p instanceof ICallOnCardsDamagedPower)
                .map(p -> (ICallOnCardsDamagedPower) p)
                .forEach(p -> p.onCardDamaged(card));
    }
    
    public static void MendCard(AbstractCard card) {
        if (card != null && IsDamaged(card)) {
            CardModifierManager.removeModifiersById(card, DamagedCardModifier.ID, true);
            AbstractDungeon.player.powers.stream().filter(p -> p instanceof ICallOnCardsMendedPower)
                    .map(p -> (ICallOnCardsMendedPower) p)
                    .forEach(p -> p.onCardMended(card));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, BLOCK_PER_MEND));
            CharWisadel.CARDS_MENDED_THIS_COMBAT.add(card);
        }
    }

    public static boolean IsPart(AbstractCard card) {
        return card instanceof IPartCard;
    }
    
    public static boolean IsDamaged(AbstractCard card) {
        return CardModifierManager.hasModifier(card, DamagedCardModifier.ID);
    }

    public static CardGroup GetAllUnexhaustedCardsInCombat() {
        return GetAllUnexhaustedCardsInCombat(c -> true);
    }

    public static CardGroup GetAllUnexhaustedCardsInCombat(Predicate<AbstractCard> matcher) {
        CardGroup cg = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractPlayer p = AbstractDungeon.player;
        cg.group.addAll(p.drawPile.group);
        cg.group.addAll(p.hand.group);
        cg.group.addAll(p.discardPile.group);
        cg.group.removeIf(c -> !matcher.test(c));
        return cg;
    }
    
    public static CardGroup LocateCardGroupWhereExists(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.drawPile.contains(card))
            return p.drawPile;
        if (p.hand.contains(card))
            return p.hand;
        if (p.discardPile.contains(card))
            return p.discardPile;
        if (p.exhaustPile.contains(card))
            return p.exhaustPile;
        return null;
    }
}