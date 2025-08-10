package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCallbackAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.GiftOfRevenantPower;

public class GiftOfRevenant extends EWCardBase {
    public static final String ID = MakeID(GiftOfRevenant.class.getSimpleName());
    
    public GiftOfRevenant() {
        super(ID, "ew/GiftOfRevenant.png", 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setDamage(10);
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageCallbackAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY)
                .whenFatal(c -> addToTop(new ApplyPowerAction(s, s, new GiftOfRevenantPower(s, 1)))));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(4);
    }
}