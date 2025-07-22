package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.PensionPower;

public class Pension extends EWCardBase {
    public static final String ID = MakeID(Pension.class.getSimpleName());
    
    public Pension() {
        super(ID, "ew/Pension.png", 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new PensionPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}