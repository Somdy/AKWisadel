package rs.moranzc.akwisadel.actions.common;

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
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DamageCardsInHandAction extends AbstractGameAction {
    private final boolean random;
    private boolean anyNumber;
    private boolean canPickZero;
    private Predicate<AbstractCard> matcher;
    private String msg;
    private boolean firstFramed;
    private ArrayList<AbstractCard> cardsToReturn;
    
    public DamageCardsInHandAction(int amount, boolean random) {
        this.amount = amount;
        this.random = random;
        anyNumber = !random;
        canPickZero = anyNumber;
        matcher = c -> true;
        msg = I18nManager.MT("DamageCardAction");
        firstFramed = false;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.CARD_MANIPULATION;
    }

    public DamageCardsInHandAction(int amount) {
        this(amount, true);
    }
    
    public DamageCardsInHandAction anyNumber(boolean anyNumber) {
        this.anyNumber = anyNumber;
        return this;
    }
    
    public DamageCardsInHandAction canPickZero(boolean canPickZero) {
        this.canPickZero = canPickZero;
        return this;
    }

    public DamageCardsInHandAction msg(String msg) {
        this.msg = msg;
        return this;
    }

    public DamageCardsInHandAction filter(Predicate<AbstractCard> filter) {
        if (filter != null) {
            matcher = matcher.and(filter);
        }
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
                Kazdel.logger.info("No matching card to damage");
                isDone = true;
                return;
            }
            if (random) {
                for (int i = 0; i < amount; i++) {
                    AbstractCard c = matched.remove(AbstractDungeon.cardRandomRng.random(matched.size() - 1));
                    c.superFlash();
                    CardUtils.DamageCard(c);
                }
                isDone = true;
                return;
            } else {
                p.hand.group = matched;
                AbstractDungeon.handCardSelectScreen.open(msg, amount, anyNumber, canPickZero);
            }
        }
        if (firstFramed && !AbstractDungeon.handCardSelectScreen.selectedCards.isEmpty()) {
            List<AbstractCard> cardsToDamage = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                c.superFlash();
                cardsToDamage.add(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            p.hand.group = cardsToReturn;
            p.hand.refreshHandLayout();
            addToTop(new DamageCardsAction(p, cardsToDamage));
        }
        tickDuration();
    }
}
