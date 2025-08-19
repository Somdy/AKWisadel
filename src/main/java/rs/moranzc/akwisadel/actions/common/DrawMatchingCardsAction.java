package rs.moranzc.akwisadel.actions.common;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NoDrawPower;

import java.util.function.Predicate;

public class DrawMatchingCardsAction extends AbstractGameAction {
    private Predicate<AbstractCard> matcher;
    private ShellAction followUpAction;
    private boolean shuffleCheck;
    private boolean sorted;
    private boolean discardIncluded;
    private boolean clearHistory;

    public DrawMatchingCardsAction(int amount, Predicate<AbstractCard> matcher) {
        this(amount, true, matcher);
    }

    public DrawMatchingCardsAction(int amount, boolean clearHistory, Predicate<AbstractCard> matcher) {
        this.amount = amount;
        this.matcher = matcher;
        discardIncluded = true;
        shuffleCheck = false;
        sorted = false;
        this.clearHistory = clearHistory;
        followUpAction = new ShellAction(null);
    }
    
    public DrawMatchingCardsAction followUp(AbstractGameAction followUpAction) {
        this.followUpAction = new ShellAction(followUpAction);
        return this;
    }

    public DrawMatchingCardsAction discardIncluded(boolean discardIncluded) {
        this.discardIncluded = discardIncluded;
        return this;
    }

    @Override
    public void update() {
        if (clearHistory) {
            DrawCardAction.drawnCards.clear();
            clearHistory = false;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(NoDrawPower.POWER_ID)) {
            p.getPower(NoDrawPower.POWER_ID).flash();
            endWithFollowUpAction();
            return;
        }
        if (amount <= 0 || matcher == null) {
            endWithFollowUpAction();
            return;
        }
        int drawSize = countSpecificCards(p.drawPile, matcher);
        int discardSize = discardIncluded ? countSpecificCards(p.discardPile, matcher) : 0;
        if (!SoulGroup.isActive()) {
            if (drawSize + discardSize == 0) {
                endWithFollowUpAction();
                return;
            }
            if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                p.createHandIsFullDialog();
                endWithFollowUpAction();
                return;
            }
            if (!shuffleCheck) {
                if (amount + p.hand.size() > BaseMod.MAX_HAND_SIZE) {
                    int delta = BaseMod.MAX_HAND_SIZE - (amount + p.hand.size());
                    amount += delta;
                }
                if (amount > drawSize + discardSize) {
                    amount = drawSize + discardSize;
                }
                if (amount > drawSize) {
                    addToTop(new DrawMatchingCardsAction(amount, false, matcher)
                            .followUp(followUpAction.action)
                            .discardIncluded(discardIncluded));
                    addToTop(new EmptyDeckShuffleAction());
                    isDone = true;
                    return;
                }
                shuffleCheck = true;
            }
            if (amount > 0) {
                if (!sorted) {
                    moveMatchingCardToTop(amount);
                    sorted = true;
                }
                if (!p.drawPile.isEmpty()) {
                    addToTop(new DrawCardAction(amount, followUpAction, clearHistory));
                }
            }
            isDone = true;
        }
    }
    
    private void endWithFollowUpAction() {
        isDone = true;
        if (followUpAction.action != null) {
            addToTop(followUpAction);
        }
    }
    
    private int countSpecificCards(CardGroup group, Predicate<AbstractCard> matcher) {
        if (group.isEmpty()) return 0;
        int count = 0;
        for (AbstractCard card : group.group) {
            if (matcher.test(card))
                count++;
        }
        return count;
    }

    private void moveMatchingCardToTop(int times) {
        int count = times;
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = p.drawPile.size() - 1; i >= 0; i--) {
            if (count <= 0) break;
            AbstractCard c = p.drawPile.group.get(i);
            if (matcher.test(c)) {
                count--;
                continue;
            }
            for (int j = i - 1; j >= 0; j--) {
                if (count <= 0) break;
                AbstractCard nc = p.drawPile.group.get(j);
                if (!matcher.test(nc)) continue;
                p.drawPile.group.remove(j);
                p.drawPile.group.add(i--, nc);
                count--;
            }
            break;
        }
    }
    
    private static class ShellAction extends AbstractGameAction {
        private final AbstractGameAction action;

        private ShellAction(AbstractGameAction action) {
            if (action instanceof ShellAction)
                this.action = ((ShellAction) action).action;
            else
                this.action = action;
        }

        @Override
        public void update() {
            if (action == null) {
                isDone = true;
                return;
            }
            action.update();
            isDone = action.isDone;
        }
    }
}
