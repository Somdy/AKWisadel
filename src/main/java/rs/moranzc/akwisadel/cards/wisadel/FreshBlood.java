package rs.moranzc.akwisadel.cards.wisadel;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.IncreaseBombDamageAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;
import rs.moranzc.akwisadel.powers.TargetedEliminationPower;

public class FreshBlood extends EWCardBase implements IPartCard {
    public static final String ID = MakeID(FreshBlood.class.getSimpleName());
    
    public FreshBlood() {
        super(ID, "ew/FreshBlood.png", 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(6);
        setMagic(6);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new MakeTempCardInDiscardAction(makeCopy(), 1));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }

    @Override
    public void onAppliedOnBomb(EWBombCardBase card, AbstractPlayer s, AbstractCreature t, float slotEfficiency) {
        addToBot(new IncreaseBombDamageAction(card, MathUtils.floor(magicNumber * slotEfficiency) ));
    }
}