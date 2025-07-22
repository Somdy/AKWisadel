package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.MicroBomb;

public class Batching extends EWCardBase {
    public static final String ID = MakeID(Batching.class.getSimpleName());
    
    public Batching() {
        super(ID, "ew/Batching.png", 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setMagic(2);
        cardsToPreview = new MicroBomb();
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new MakeTempCardInHandAction(cardsToPreview.makeCopy(), magicNumber));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}
