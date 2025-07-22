package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCardsAction;
import rs.moranzc.akwisadel.actions.common.DrawMatchingCardsAction;
import rs.moranzc.akwisadel.actions.common.PlayTopCardsCallbackAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class SecondCall extends EWCardBase {
    public static final String ID = MakeID(SecondCall.class.getSimpleName());
    
    public SecondCall() {
        super(ID, "ew/SecondCall.png", 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setBlock(8);
        setMagic(1);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainBlockAction(s, s, block));
        addToBot(new DrawMatchingCardsAction(magicNumber, CardUtils::IsDamaged)
                .followUp(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        DrawCardAction.drawnCards.forEach(CardUtils::MendCard);
                    }
                }));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }
}
