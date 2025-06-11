package rs.moranzc.akwisadel.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.NoDrawPower;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DrawCardFromDiscardPileAction extends AbstractGameAction {
    private final Predicate<AbstractCard> filter;
    private final AbstractGameAction followingAction;

    public DrawCardFromDiscardPileAction(int amount, AbstractGameAction action, Predicate<AbstractCard> filter) {
        this.amount = amount;
        this.followingAction = action;
        this.filter = filter;
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    public DrawCardFromDiscardPileAction(int amount, AbstractGameAction action) {
        this(amount, action, c -> true);
    }

    public DrawCardFromDiscardPileAction(int amount, Predicate<AbstractCard> filter) {
        this(amount, null, filter);
    }

    @Override
    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(NoDrawPower.POWER_ID) || p.discardPile.isEmpty() || p.discardPile.group.stream().noneMatch(filter) || amount <= 0)
            return;
        List<AbstractCard> matched = p.discardPile.group.stream().filter(filter).collect(Collectors.toList());
        while (matched.size() > amount) {
            matched.remove(matched.size() - 1);
        }
        for (AbstractCard c : matched) {
            p.discardPile.moveToDeck(c, false);
        }
        addToTop(new DrawCardAction(amount, followingAction));
    }
}
