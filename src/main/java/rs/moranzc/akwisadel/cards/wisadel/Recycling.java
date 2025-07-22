package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCardsInHandAction;
import rs.moranzc.akwisadel.actions.common.MendCardsInHandAction;
import rs.moranzc.akwisadel.base.EWCardBase;

public class Recycling extends EWCardBase {
    public static final String ID = MakeID(Recycling.class.getSimpleName());
    
    public Recycling() {
        super(ID, "ew/Recycling.png", 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setBlock(7);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageCardsInHandAction(1, !upgraded).canPickZero(false).anyNumber(false));
        addToBot(new MendCardsInHandAction(1, !upgraded).canPickZero(true).anyNumber(true));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(2);
    }
}
