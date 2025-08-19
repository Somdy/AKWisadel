package rs.moranzc.akwisadel.cards.wisadel;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.DustAndSmoke;
import rs.moranzc.akwisadel.cards.wisadel.derived.MicroBomb;

public class BombsAndSmoke extends EWCardBase {
    public static final String ID = MakeID(BombsAndSmoke.class.getSimpleName());
    
    public BombsAndSmoke() {
        super(ID, "ew/BombsAndSmoke.png", 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setMagic(1);
        MultiCardPreview.add(this, new MicroBomb(), new DustAndSmoke());
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new MakeTempCardInHandAction(new MicroBomb(), 1));
        addToBot(new MakeTempCardInHandAction(new DustAndSmoke(), magicNumber));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}
