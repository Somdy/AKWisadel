package rs.moranzc.akwisadel.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.localization.I18nManager;
import rs.moranzc.akwisadel.utils.CardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HandCardSelectActionBuilder extends AbstractGameAction {
    private boolean anyNumber;
    private boolean canPickZero;
    private Predicate<AbstractCard> matcher = c -> true;
    private BiFunction<AbstractCard, Integer, Boolean> manipulator = (c, i) -> true;
    private Consumer<List<AbstractCard>> postSelectedAction = null;
    private String msg;
    private boolean firstFramed;
    private ArrayList<AbstractCard> cardsToReturn;
    private final List<AbstractCard> cardSelected = new ArrayList<>();
    
    public HandCardSelectActionBuilder(int amount, String msg, Predicate<AbstractCard> matcher) {
        this.amount = amount;
        if (matcher != null)
            this.matcher = matcher;
        this.msg = msg;
        firstFramed = false;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.CARD_MANIPULATION;
    }

    public HandCardSelectActionBuilder(int amount, String msg) {
        this(amount, msg, c -> true);
    }
    
    public HandCardSelectActionBuilder anyNumber(boolean anyNumber) {
        this.anyNumber = anyNumber;
        return this;
    }
    
    public HandCardSelectActionBuilder canPickZero(boolean canPickZero) {
        this.canPickZero = canPickZero;
        return this;
    }

    public HandCardSelectActionBuilder msg(String msg) {
        this.msg = msg;
        return this;
    }

    public HandCardSelectActionBuilder manipulate(BiFunction<AbstractCard, Integer, Boolean> action) {
        if (action != null) {
            manipulator = action;
        }
        return this;
    }
    
    public HandCardSelectActionBuilder postSelected(Consumer<List<AbstractCard>> action) {
        postSelectedAction = action;
        return this;
    }
    
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!firstFramed) {
            firstFramed = true;
            if (p.hand.isEmpty() || amount <= 0) {
                isDone = true;
                return;
            }
            cardsToReturn = p.hand.group;
            ArrayList<AbstractCard> matched = (ArrayList<AbstractCard>) p.hand.group.stream().filter(matcher).collect(Collectors.toList());
            amount = Math.min(amount, matched.size());
            if (amount <= 0) {
                isDone = true;
                return;
            }
            p.hand.group = matched;
            AbstractDungeon.handCardSelectScreen.open(msg, amount, anyNumber, canPickZero);
        }
        if (firstFramed && !AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
            for (int i = 0; i < AbstractDungeon.handCardSelectScreen.selectedCards.group.size(); i++) {
                AbstractCard c = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(i);
                cardSelected.add(c);
                if (!manipulator.apply(c, i)) {
                    cardsToReturn.remove(c);
                }
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            p.hand.group = cardsToReturn;
            p.hand.refreshHandLayout();
        }
        tickDuration();
        if (isDone) {
            if (postSelectedAction != null) {
                postSelectedAction.accept(cardSelected);
            }
        }
    }
}
