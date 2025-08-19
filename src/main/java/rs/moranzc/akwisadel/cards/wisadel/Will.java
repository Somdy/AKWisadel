package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.SproutingPower;
import rs.moranzc.akwisadel.powers.WillPower;

public class Will extends EWCardBase {
    public static final String ID = MakeID(Will.class.getSimpleName());
    
    public Will() {
        super(ID, "ew/Will.png", 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(1);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new SummonRevenantAction(magicNumber));
        addToBot(new ApplyPowerAction(s, s, new WillPower(s)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}
