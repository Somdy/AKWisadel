package rs.moranzc.akwisadel.cards.wisadel;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.Ammo;
import rs.moranzc.akwisadel.powers.BladeOfRevenantPower;
import rs.moranzc.akwisadel.powers.ExplosiveDawnPower;

@AutoAdd.Ignore
public class BladeOfRevenant extends EWCardBase {
    public static final String ID = MakeID(BladeOfRevenant.class.getSimpleName());
    
    public BladeOfRevenant() {
        super(ID, "ew/BladeOfRevenant.png", 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new BladeOfRevenantPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBaseCost(1);
    }
}