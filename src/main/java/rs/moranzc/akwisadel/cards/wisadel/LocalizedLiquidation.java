package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.LocalizedLiquidationPower;

public class LocalizedLiquidation extends EWCardBase {
    public static final String ID = MakeID(LocalizedLiquidation.class.getSimpleName());
    
    public LocalizedLiquidation() {
        super(ID, "ew/LocalizedLiquidation.png", 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(8);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(t, s, new LocalizedLiquidationPower(t, 1)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(4);
    }
}