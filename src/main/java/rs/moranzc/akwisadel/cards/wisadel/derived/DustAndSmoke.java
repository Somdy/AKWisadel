package rs.moranzc.akwisadel.cards.wisadel.derived;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

@NoCompendium
public class DustAndSmoke extends EWCardBase implements IPartCard {
    public static final String ID = MakeID(DustAndSmoke.class.getSimpleName());
    
    public DustAndSmoke() {
        super(ID, "ew/DustAndSmoke.png", 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        setBlock(3);
        setMagic(4);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainBlockAction(s, s, block));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(1);
        upgradeMagicNumber(1);
    }

    @Override
    public void onAppliedOnBomb(EWBombCardBase card, AbstractPlayer s, AbstractCreature t, float slotEfficiency) {
        addToBot(new GainBlockAction(s, s, MathUtils.floor(magicNumber * slotEfficiency) ));
    }
}