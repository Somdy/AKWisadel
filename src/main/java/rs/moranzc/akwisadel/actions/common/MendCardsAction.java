package rs.moranzc.akwisadel.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import rs.moranzc.akwisadel.utils.CardUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MendCardsAction extends AbstractGameAction {
    private final List<AbstractCard> cardsToMend;

    public MendCardsAction(List<AbstractCard> cardsToMend) {
        this.cardsToMend = new ArrayList<>(cardsToMend);
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }
    
    public MendCardsAction(AbstractCard... cardsToMend) {
        this.cardsToMend = new ArrayList<>(Arrays.asList(cardsToMend));
    }

    @Override
    public void update() {
        isDone = true;
        cardsToMend.stream().filter(Objects::nonNull).forEach(c -> {
            c.superFlash();
            CardUtils.MendCard(c);
        });
    }
}
