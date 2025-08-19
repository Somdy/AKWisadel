package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import rs.moranzc.akwisadel.actions.common.ApplyPowerToEnemiesAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;
import rs.moranzc.akwisadel.powers.GiftPower;

public class ExtendedMag extends EWCardBase implements IPartCard {
    public static final String ID = MakeID(ExtendedMag.class.getSimpleName());
    
    public ExtendedMag() {
        super(ID, "ew/ExtendedMag.png", 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(4);
        setMagic(1);
        setExtraMagic(4);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainBlockAction(s, block));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(2);
        upgradeExtraMagic(2);
    }

    @Override
    public void onAppliedOnBomb(EWBombCardBase card, AbstractPlayer s, AbstractCreature t, float slotEfficiency) {
        addToBot(new GainBlockAction(s, extraMagic));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                card.increaseSlotsForCombat(magicNumber);
            }
        });
    }
}
