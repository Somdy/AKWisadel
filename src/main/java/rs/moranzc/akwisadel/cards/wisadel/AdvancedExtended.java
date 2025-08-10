package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.AdvancedExtendedPower;

public class AdvancedExtended extends EWCardBase {
    public static final String ID = MakeID(AdvancedExtended.class.getSimpleName());
    
    public AdvancedExtended() {
        super(ID, "ew/AdvancedExtended.png", 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new AdvancedExtendedPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBaseCost(1);
    }
}