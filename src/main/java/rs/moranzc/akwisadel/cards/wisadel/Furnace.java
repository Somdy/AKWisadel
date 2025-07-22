package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.utility.GridCardSelectActionBuilder;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.FurnacePower;
import rs.moranzc.akwisadel.utils.CardUtils;

public class Furnace extends EWCardBase {
    public static final String ID = MakeID(Furnace.class.getSimpleName());
    
    public Furnace() {
        super(ID, "ew/Furnace.png", 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        setMagic(2);
        setExtraMagic(3);
        exhaust = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new DrawCardAction(extraMagic));
        addToBot(new ApplyPowerAction(s, s, new FurnacePower(s)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
        upgradeExtraMagic(2);
    }
}
