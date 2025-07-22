package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCardsInHandAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.ProtectionOfRevenantPower;

public class ProtectionOfRevenant extends EWCardBase {
    public static final String ID = MakeID(ProtectionOfRevenant.class.getSimpleName());
    
    public ProtectionOfRevenant() {
        super(ID, "ew/ProtectionOfRevenant.png", 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(12);
        setMagic(2);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainBlockAction(s, block));
        addToBot(new ApplyPowerAction(s, s, new ProtectionOfRevenantPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(4);
        upgradeMagicNumber(1);
    }
}
