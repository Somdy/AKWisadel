package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.MicroBomb;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

public class Stupor extends EWCardBase implements IPartCard {
    public static final String ID = MakeID(Stupor.class.getSimpleName());
    
    public Stupor() {
        super(ID, "ew/Stupor.png", 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setMagic(2);
        setExtraMagic(2);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DrawCardAction(s, magicNumber));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
        upgradeExtraMagic(1);
    }

    @Override
    public void onAppliedOnBomb(EWBombCardBase card, AbstractPlayer s, AbstractCreature t, float slotEfficiency) {
        addToBot(new DrawCardAction(s, extraMagic));
    }
}
