package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.DarkEmbracePower;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.FogOfRevenantPower;

public class EmbraceOfOldFriends extends EWCardBase {
    public static final String ID = MakeID(EmbraceOfOldFriends.class.getSimpleName());
    
    public EmbraceOfOldFriends() {
        super(ID, "ew/EmbraceOfOldFriends.png", 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new DarkEmbracePower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBaseCost(1);
    }
}