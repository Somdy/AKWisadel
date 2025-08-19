package rs.moranzc.akwisadel.cards.wisadel;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;

public class MarchOfTheDead2 extends EWCardBase {
    public static final String ID = MakeID(MarchOfTheDead2.class.getSimpleName());
    
    public MarchOfTheDead2() {
        super(ID, "ew/MarchOfTheDead.png", 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(20);
        CardModifierManager.addModifier(this, new DamagedCardModifier());
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainBlockAction(s, block));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(8);
    }
}