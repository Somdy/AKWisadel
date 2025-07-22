package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCallbackAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.GiftPower;

public class ThePresent extends EWCardBase {
    public static final String ID = MakeID(ThePresent.class.getSimpleName());
    
    public ThePresent() {
        super(ID, "ew/ThePresent.png", 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setDamage(6);
        setMagic(4);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageCallbackAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT)
                .callback(c -> c.powers.stream().filter(p -> GiftPower.POWER_ID.equals(p.ID))
                        .map(p -> (GiftPower) p)
                        .forEach(p -> p.ignite(s, magicNumber))));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}