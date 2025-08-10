package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCardsAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;

import java.util.List;
import java.util.stream.Collectors;

public class ScorchedEarth extends EWCardBase {
    public static final String ID = MakeID(ScorchedEarth.class.getSimpleName());
    
    public ScorchedEarth() {
        super(ID, "ew/DieOfDeath.png", 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(5);
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        return super.canPlay(card);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                List<AbstractCard> cards = s.hand.group.stream().filter(c -> !(c instanceof EWBombCardBase)).collect(Collectors.toList());
                if (!cards.isEmpty()) {
                    int blocks = cards.size() * block;
                    addToTop(new GainBlockAction(s, blocks));
                    addToTop(new DamageCardsAction(s, cards));
                }
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(2);
    }
}
