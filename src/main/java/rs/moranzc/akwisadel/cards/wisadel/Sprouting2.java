package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.SproutingPower;

public class Sprouting2 extends EWCardBase {
    public static final String ID = MakeID(Sprouting2.class.getSimpleName());
    
    public Sprouting2() {
        super(ID, "ew/Sprouting.png", 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(2);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new SproutingPower(s)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBaseCost(1);
    }
}
