package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.BackupStockpilePower;
import rs.moranzc.akwisadel.powers.PensionPower;

public class BackupStockpile extends EWCardBase {
    public static final String ID = MakeID(BackupStockpile.class.getSimpleName());
    
    public BackupStockpile() {
        super(ID, "ew/BackupStockpile.png", 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new BackupStockpilePower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
    }
}