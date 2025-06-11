package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.SupplementaryCharge;

public class Overcharge extends EWCardBase {
    public static final String ID = MakeID(Overcharge.class.getSimpleName());
    
    public Overcharge() {
        super(ID, "ew/Overcharge.png", 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(4);
        setMagic(1);
        cardsToPreview = new SupplementaryCharge();
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}