package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.DustAndSmoke;
import rs.moranzc.akwisadel.powers.TargetedEliminationPower;

public class TargetedElimination extends EWCardBase {
    public static final String ID = MakeID(TargetedElimination.class.getSimpleName());
    
    public TargetedElimination() {
        super(ID, "ew/TargetedElimination.png", 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(8);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(t, s, new TargetedEliminationPower(t, 1)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(4);
    }
}