package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.Ammo;
import rs.moranzc.akwisadel.powers.ExplosiveDawnPower;
import rs.moranzc.akwisadel.powers.LordOfRevenantPower;

public class ExplosiveDawn extends EWCardBase {
    public static final String ID = MakeID(ExplosiveDawn.class.getSimpleName());
    
    public ExplosiveDawn() {
        super(ID, "ew/ExplosiveDawn.png", 4, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(1);
        cardsToPreview = new Ammo();
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new ExplosiveDawnPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        selfRetain = true;
    }
}