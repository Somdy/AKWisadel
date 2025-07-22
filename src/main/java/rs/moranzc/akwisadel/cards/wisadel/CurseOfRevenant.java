package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.CurseOfRevenantPower;
import rs.moranzc.akwisadel.powers.GiftPower;

public class CurseOfRevenant extends EWCardBase {
    public static final String ID = MakeID(CurseOfRevenant.class.getSimpleName());
    
    public CurseOfRevenant() {
        super(ID, "ew/CurseOfRevenant.png", 1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        setMagic(6);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(t, s, new GiftPower(t, magicNumber)));
        addToBot(new ApplyPowerAction(t, s, new CurseOfRevenantPower(t, 1)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(3);
    }
}
