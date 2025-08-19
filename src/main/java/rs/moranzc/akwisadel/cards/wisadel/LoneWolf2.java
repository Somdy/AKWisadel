package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import rs.moranzc.akwisadel.base.EWCardBase;

public class LoneWolf2 extends EWCardBase {
    public static final String ID = MakeID(LoneWolf2.class.getSimpleName());

    public LoneWolf2() {
        super(ID, "ew/LoneWolf.png", 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(14);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainBlockAction(s, block));
        addToBot(new ApplyPowerAction(s, s, new StrengthPower(s, -magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(4);
    }
}