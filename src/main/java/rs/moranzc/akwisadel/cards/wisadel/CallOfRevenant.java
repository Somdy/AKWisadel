package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.base.EWCardBase;

public class CallOfRevenant extends EWCardBase {
    public static final String ID = MakeID(CallOfRevenant.class.getSimpleName());
    
    public CallOfRevenant() {
        super(ID, "ew/CallOfRevenant.png", 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new SummonRevenantAction(1));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        isEthereal = false;
    }
}
