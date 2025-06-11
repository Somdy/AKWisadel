package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class MarchOfTheDead extends EWCardBase {
    public static final String ID = MakeID(MarchOfTheDead.class.getSimpleName());
    
    public MarchOfTheDead() {
        super(ID, "ew/MarchOfTheDead.png", 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setDamage(6);
        setMagic(1);
        isMultiDamage = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAllEnemiesAction(s, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }

    @Override
    public void applyPowers() {
        int real = baseDamage;
        int damageds = countDamagedOnes(cpr().drawPile);
        damageds += countDamagedOnes(cpr().hand);
        damageds += countDamagedOnes(cpr().discardPile);
        damageds += countDamagedOnes(cpr().exhaustPile);
        baseDamage += damageds;
        super.applyPowers();
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int real = baseDamage;
        int damageds = countDamagedOnes(cpr().drawPile);
        damageds += countDamagedOnes(cpr().hand);
        damageds += countDamagedOnes(cpr().discardPile);
        damageds += countDamagedOnes(cpr().exhaustPile);
        baseDamage += damageds;
        super.calculateCardDamage(mo);
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }
    
    private int countDamagedOnes(CardGroup cg) {
        if (cg.isEmpty())
            return 0;
        return cg.group.stream().filter(CardUtils::IsDamaged).mapToInt(c -> 1).sum();
    }
}