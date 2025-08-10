package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.AdvancedExtendedPower;
import rs.moranzc.akwisadel.powers.LogisticalSupportPower;

public class LogisticalSupport extends EWCardBase {
    public static final String ID = MakeID(LogisticalSupport.class.getSimpleName());
    
    public LogisticalSupport() {
        super(ID, "ew/LogisticalSupport.png", 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new LogisticalSupportPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}