package rs.moranzc.akwisadel.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.utils.CardUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DamageCardsAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final List<AbstractCard> cardsToDamage;
    
    public DamageCardsAction(AbstractPlayer p, List<AbstractCard> cardsToDamage) {
        this.p = p;
        this.cardsToDamage = new ArrayList<>(cardsToDamage);
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }

    public DamageCardsAction(AbstractPlayer p, AbstractCard... cardsToDamage) {
        this.p = p;
        this.cardsToDamage = new ArrayList<>(Arrays.asList(cardsToDamage));
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
    }
    
    @Override
    public void update() {
        if (cardsToDamage.isEmpty() || AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
            return;
        }
        if (duration == startDuration) {
            cardsToDamage.stream().filter(Objects::nonNull).forEach(c -> {
                // Damaged ones go to Exhaust pile and rest
//                if (CardUtils.IsDamaged(c)) {
//                    p.hand.moveToExhaustPile(c);
//                } else {
//                    CardUtils.DamageCard(c);
//                }
                CardUtils.DamageCard(c, p.hand);
                c.superFlash();
            });
            cardsToDamage.clear();
            p.hand.applyPowers();
            CardCrawlGame.dungeon.checkForPactAchievement();
        }
        tickDuration();
    }
}
