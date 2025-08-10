package rs.moranzc.akwisadel.cards.wisadel;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DrawMatchingCardsAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.core.CardMst;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnModifierAppliedCard;
import rs.moranzc.akwisadel.utils.CardUtils;

public class Station extends EWCardBase implements ICallOnModifierAppliedCard {
    public static final String ID = MakeID(Station.class.getSimpleName());
    
    public Station() {
        super(ID, "ew/Station.png", 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3);
        selfRetain = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (int i = 0; i < magicNumber; i++) {
                    AbstractCard c = CardMst.GetRandomPart();
                    addToTop(new MakeTempCardInHandAction(c, 1));
                }
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }

    @Override
    public void onModifierApplied(AbstractCardModifier mod) {
        if (DamagedCardModifier.ID.equals(mod.identifier(this))) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    CardUtils.MendCard(Station.this);
                    addToTop(new MakeTempCardInHandAction(CardMst.GetRandomPart(), 1));
                }
            });
        }
    }
}
