package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import rs.moranzc.akwisadel.actions.common.ApplyPowerToEnemiesAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.powers.GiftPower;

public class FuryOfRevenant extends EWCardBase {
    public static final String ID = MakeID(FuryOfRevenant.class.getSimpleName());
    
    public FuryOfRevenant() {
        super(ID, "ew/FuryOfRevenant.png", 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3);
        exhaust = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int powerAmount = magicNumber;
                if (upgraded && s instanceof CharWisadel) {
                    powerAmount += ((CharWisadel) s).countRevenants();
                }
                int finalPowerAmount = powerAmount;
                addToTop(new ApplyPowerToEnemiesAction(s, m -> new VulnerablePower(m, finalPowerAmount, false)));
                addToTop(new ApplyPowerToEnemiesAction(s, m -> new GiftPower(m, finalPowerAmount)));
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
    }
}
