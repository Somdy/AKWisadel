package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.LordOfRevenantPower;

public class LordOfRevenant extends EWCardBase {
    public static final String ID = MakeID(LordOfRevenant.class.getSimpleName());
    
    public LordOfRevenant() {
        super(ID, "ew/LordOfRevenant.png", 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(3);
        isEthereal = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new LordOfRevenantPower(s)));
        addToBot(new SummonRevenantAction(magicNumber));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
        isEthereal = false;
    }
}