package rs.moranzc.akwisadel.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoMoveCardsToHandAction extends AbstractGameAction {
    private final boolean includingExhausted;
    private final List<AbstractCard> cardsToMove = new ArrayList<>();
    private CardGroup sourceCG;
    
    public AutoMoveCardsToHandAction(boolean includingExhausted, AbstractCard... cardsToMove) {
        sourceCG = null;
        this.includingExhausted = includingExhausted;
        if (cardsToMove != null && cardsToMove.length > 0) {
            this.cardsToMove.addAll(Arrays.asList(cardsToMove));
        }
        actionType = ActionType.CARD_MANIPULATION;
    }

    public AutoMoveCardsToHandAction(CardGroup sourceCG, AbstractCard... cardsToMove) {
        this.sourceCG = sourceCG;
        includingExhausted = sourceCG == AbstractDungeon.player.exhaustPile;
        if (cardsToMove != null && cardsToMove.length > 0) {
            this.cardsToMove.addAll(Arrays.asList(cardsToMove));
        }
        actionType = ActionType.CARD_MANIPULATION;
    }
    
    @Override
    public void update() {
        isDone = true;
        if (cardsToMove.isEmpty())
            return;
        for (AbstractCard c : cardsToMove) {
            if (AbstractDungeon.player.hand.contains(c))
                continue;
            if (sourceCG == null) {
                sourceCG = findCardSourceCG(c);
            }
            if (sourceCG == null) 
                continue;
            sourceCG.moveToHand(c);
        }
    }
    
    private CardGroup findCardSourceCG(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.drawPile.contains(card))
            return p.drawPile;
        if (p.discardPile.contains(card))
            return p.discardPile;
        if (includingExhausted && p.exhaustPile.contains(card))
            return p.exhaustPile;
        return null;
    }
}