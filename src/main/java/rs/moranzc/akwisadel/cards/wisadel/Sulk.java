package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.SulkPower;

public class Sulk extends EWCardBase {
    public static final String ID = MakeID(Sulk.class.getSimpleName());
    
    public Sulk() {
        super(ID, "ew/Sulk.png", 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new ApplyPowerAction(s, s, new SulkPower(s)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}
