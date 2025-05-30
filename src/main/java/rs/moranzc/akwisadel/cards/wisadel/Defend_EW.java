package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;

public class Defend_EW extends EWCardBase {
    public static final String ID = MakeID(Defend_EW.class.getSimpleName());
    
    public Defend_EW() {
        super(ID, "ew/Defend.png", 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        setBlock(5);
        addTags(CardTags.STARTER_DEFEND);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainBlockAction(s, s, block));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(3);
    }
}
