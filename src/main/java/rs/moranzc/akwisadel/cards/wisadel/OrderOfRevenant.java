package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.OrderOfRevenantPower;

public class OrderOfRevenant extends EWCardBase {
    public static final String ID = MakeID(OrderOfRevenant.class.getSimpleName());
    
    public OrderOfRevenant() {
        super(ID, "ew/OrderOfRevenant.png", 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
        setExtraMagic(2);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new SummonRevenantAction(1));
        addToBot(new ApplyPowerAction(s, s, new OrderOfRevenantPower(s, magicNumber, extraMagic)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
        upgradeExtraMagic(1);
    }
}