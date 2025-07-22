package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCardsInHandAction;
import rs.moranzc.akwisadel.base.EWCardBase;

public class NecessaryTransaction extends EWCardBase {
    public static final String ID = MakeID(NecessaryTransaction.class.getSimpleName());
    
    public NecessaryTransaction() {
        super(ID, "ew/NecessaryTransaction.png", 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setExtraMagic(2);
        setMagic(3);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainEnergyAction(extraMagic));
        addToBot(new DamageCardsInHandAction(magicNumber, false));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(-1);
    }
}
