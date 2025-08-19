package rs.moranzc.akwisadel.cards.wisadel;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DrawMatchingCardsAction;
import rs.moranzc.akwisadel.actions.common.MendCardsInHandAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnModifierAppliedCard;
import rs.moranzc.akwisadel.utils.CardUtils;

public class MASH2 extends EWCardBase implements ICallOnModifierAppliedCard {
    public static final String ID = MakeID(MASH2.class.getSimpleName());
    
    public MASH2() {
        super(ID, "ew/MASH.png", 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3);
        setExtraMagic(3);
        setBlock(14);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new MendCardsInHandAction(extraMagic, false).postSelected(cards -> {
            if (cards.size() >= 3) {
                addToTop(new GainBlockAction(s, block));
            }
        }));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(4);
    }

    @Override
    public void onModifierApplied(AbstractCardModifier mod) {
        if (DamagedCardModifier.ID.equals(mod.identifier(this))) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    CardUtils.MendCard(MASH2.this);
                    addToTop(new DrawMatchingCardsAction(1, CardUtils::IsDamaged));
                }
            });
        }
    }
}
