package rs.moranzc.akwisadel.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GridCardSelectActionBuilder extends AbstractGameAction {
    private String msg;
    private Predicate<AbstractCard> matcher = c -> true;
    private CardGroup cg;
    private BiConsumer<CardGroup, AbstractCard> manipulator = (g, c) -> {};
    private Consumer<List<AbstractCard>> postFunc;
    private boolean anyNumber;
    private boolean canCancel;
    private boolean firstFramed = false;
    
    public GridCardSelectActionBuilder(int amount, String msg, CardGroup from, Predicate<AbstractCard> matcher) {
        this.amount = amount;
        this.msg = msg;
        this.cg = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        appendCG(from, false);
        if (matcher != null)
            this.matcher = matcher;
        actionType = ActionType.CARD_MANIPULATION;
    }
    
    public GridCardSelectActionBuilder manipulate(BiConsumer<CardGroup, AbstractCard> action) {
        if (action != null) {
            manipulator = action;
        }
        return this;
    }
    
    public GridCardSelectActionBuilder forAllSelected(Consumer<List<AbstractCard>> action) {
        postFunc = action;
        return this;
    }
    
    public GridCardSelectActionBuilder appendCards(CardGroup from, boolean order) {
        appendCG(from, order);
        return this;
    }
    
    public GridCardSelectActionBuilder anyNumber(boolean anyNumber) {
        this.anyNumber = anyNumber;
        return this;
    }
    
    public GridCardSelectActionBuilder canCancel(boolean canCancel) {
        this.canCancel = canCancel;
        return this;
    }
    
    @Override
    public void update() {
        if (amount <= 0 || cg.isEmpty()) {
            isDone = true;
            return;
        }
        if (!firstFramed) {
            firstFramed = true;
            cg.group.removeIf(c -> !matcher.test(c));
            amount = Math.min(cg.size(), amount);
            if (amount <= 0) {
                isDone = true;
                return;
            }
            if (anyNumber) {
                AbstractDungeon.gridSelectScreen.open(cg, amount, true, msg);
            } else {
                AbstractDungeon.gridSelectScreen.open(cg, amount, msg, false, false, canCancel, false);
            }
        }
        if (firstFramed && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                manipulator.accept(cg, c);
            }
            if (postFunc != null) {
                List<AbstractCard> theSelected = new ArrayList<>(AbstractDungeon.gridSelectScreen.selectedCards);
                postFunc.accept(theSelected);
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            cg.clear();
            isDone = true;
        }
    }
    
    private void appendCG(CardGroup from, boolean order) {
        if (from != null && !from.isEmpty()) {
            for (AbstractCard c : from.group) {
                if (c.isGlowing)
                    c.stopGlowing();
                if (order) {
                    cg.addToBottom(c);
                } else {
                    cg.addToRandomSpot(c);
                }
            }
        }
    }
}