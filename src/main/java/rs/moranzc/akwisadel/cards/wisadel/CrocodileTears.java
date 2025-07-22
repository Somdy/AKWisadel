package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.DarkEmbracePower;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.CrocodileTearsPower;

public class CrocodileTears extends EWCardBase {
    public static final String ID = MakeID(CrocodileTears.class.getSimpleName());
    
    public CrocodileTears() {
        super(ID, "ew/CrocodileTears.png", 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new CrocodileTearsPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}