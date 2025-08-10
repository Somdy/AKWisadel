package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.OmegaFormPower;
import rs.moranzc.akwisadel.powers.PensionPower;

public class OmegaForm extends EWCardBase {
    public static final String ID = MakeID(OmegaForm.class.getSimpleName());
    
    public OmegaForm() {
        super(ID, "ew/OmegaForm.png", 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        isEthereal = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new OmegaFormPower(s)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
    }
}