package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCardsInHandAction;
import rs.moranzc.akwisadel.actions.common.DrawMatchingCardsAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class BaggageAbandonment extends EWCardBase {
    public static final String ID = MakeID(BaggageAbandonment.class.getSimpleName());
    
    public BaggageAbandonment() {
        super(ID, "ew/BaggageAbandonment.png", 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageCardsInHandAction(1, false));
        addToBot(new DrawMatchingCardsAction(magicNumber, c -> !CardUtils.IsDamaged(c)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}
