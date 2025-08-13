package rs.moranzc.akwisadel.relics;

import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.base.EWRelicBase;

public class StarterRelicEW extends EWRelicBase {
    public static final String ID = MakeID(StarterRelicEW.class.getSimpleName());
    
    public StarterRelicEW() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new SummonRevenantAction(1));
    }
}