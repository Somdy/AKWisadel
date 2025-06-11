package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.DustAndSmoke;

public class SmokeGrenade extends EWCardBase {
    public static final String ID = MakeID(SmokeGrenade.class.getSimpleName());
    
    public SmokeGrenade() {
        super(ID, "ew/SmokeGrenade.png", 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        setMagic(2);
        cardsToPreview = new DustAndSmoke();
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new MakeTempCardInHandAction(cardsToPreview.makeCopy(), magicNumber));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}