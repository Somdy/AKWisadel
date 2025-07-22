package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.powers.CrocodileTearsPower;
import rs.moranzc.akwisadel.powers.OrderOfRevenantPower;

public class OrderOfRevenant extends EWCardBase {
    public static final String ID = MakeID(OrderOfRevenant.class.getSimpleName());
    
    public OrderOfRevenant() {
        super(ID, "ew/OrderOfRevenant.png", 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        if (upgraded && s instanceof CharWisadel) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    ((CharWisadel) s).letRevenantsTakeMove();
                }
            });
        }
        addToBot(new ApplyPowerAction(s, s, new OrderOfRevenantPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
    }
}