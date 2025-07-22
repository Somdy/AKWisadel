package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.actions.utility.XCostActionBuilder;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;

public class DanceOfRevenant extends EWCardBase {
    public static final String ID = MakeID(DanceOfRevenant.class.getSimpleName());
    private boolean firstUsed;
    
    public DanceOfRevenant() {
        super(ID, "ew/DanceOfRevenant.png", -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        if (!firstUsed) {
            firstUsed = true;
            addToBot(new SummonRevenantAction(1));
        }
        addToBot(new XCostActionBuilder(freeToPlayOnce, energyOnUse)
                .effectTimes(x -> upgraded ? x + 1 : x)
                .act(x -> {
                    if (s instanceof CharWisadel) {
                        CharWisadel ew = ((CharWisadel) s);
                        ew.modifyRevenantMoveTimes(x);
                    }
                }));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        if (c instanceof DanceOfRevenant) {
            ((DanceOfRevenant) c).firstUsed = firstUsed;
        }
        return c;
    }
}
