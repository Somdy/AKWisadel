package rs.moranzc.akwisadel.cards.wisadel;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DrawMatchingCardsAction;
import rs.moranzc.akwisadel.actions.common.IncreaseBombDamageAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;
import rs.moranzc.akwisadel.utils.CardUtils;

public class LordOfBombing extends EWCardBase implements IPartCard {
    public static final String ID = MakeID(LordOfBombing.class.getSimpleName());
    
    public LordOfBombing() {
        super(ID, "ew/LordOfBombing.png", 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        setMagic(2);
        setExtraMagic(2);
        exhaust = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DrawMatchingCardsAction(magicNumber, c -> c instanceof EWBombCardBase));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
        upgradeExtraMagic(1);
    }

    @Override
    public void onAppliedOnBomb(EWBombCardBase card, AbstractPlayer s, AbstractCreature t, float slotEfficiency) {
        addToBot(new IncreaseBombDamageAction(card, MathUtils.floor(extraMagic * slotEfficiency), true));
    }
}
