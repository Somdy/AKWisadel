package rs.moranzc.akwisadel.cards.wisadel;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DrawMatchingCardsAction;
import rs.moranzc.akwisadel.actions.common.MendCardsInHandAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnModifierAppliedCard;
import rs.moranzc.akwisadel.utils.CardUtils;

public class MASH extends EWCardBase implements ICallOnModifierAppliedCard {
    public static final String ID = MakeID(MASH.class.getSimpleName());
    
    public MASH() {
        super(ID, "ew/MASH.png", 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3);
        setExtraMagic(3);
        selfRetain = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new MendCardsInHandAction(extraMagic, false));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
        upgradeExtraMagic(1);
    }

    @Override
    public void onModifierApplied(AbstractCardModifier mod) {
        if (DamagedCardModifier.ID.equals(mod.identifier(this))) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    CardUtils.MendCard(MASH.this);
                    addToTop(new DrawMatchingCardsAction(1, CardUtils::IsDamaged));
                }
            });
        }
    }
}
