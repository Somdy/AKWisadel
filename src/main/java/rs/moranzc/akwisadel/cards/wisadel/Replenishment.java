package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class Replenishment extends EWCardBase {
    public static final String ID = MakeID(Replenishment.class.getSimpleName());
    
    public Replenishment() {
        super(ID, "ew/Replenishment.png", 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        setMagic(3);
        exhaust = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                s.hand.group.stream().filter(CardUtils::IsPart)
                        .forEach(c -> {
                            addToTop(new GainEnergyAction(1));
                            addToTop(new DrawCardAction(s, 1));
                            addToTop(new ExhaustSpecificCardAction(c, s.hand));
                        });
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
        selfRetain = true;
    }
}
