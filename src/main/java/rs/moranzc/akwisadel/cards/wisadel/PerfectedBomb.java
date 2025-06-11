package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akwisadel.base.EWBombCardBase;

import java.util.List;

public class PerfectedBomb extends EWBombCardBase {
    public static final String ID = MakeID(PerfectedBomb.class.getSimpleName());
    
    public PerfectedBomb() {
        super(ID, "ew/PerfectedBomb.png", 2, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        setDamage(6);
        setMagic(6);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t, List<AbstractCard> cardsToDamage) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(3);
    }

    @Override
    public void applyPowers() {
        int real = baseDamage;
        int bombs = countBombs(cpr().drawPile);
        bombs += countBombs(cpr().hand);
        bombs += countBombs(cpr().discardPile);
        baseDamage += bombs * magicNumber;
        super.applyPowers();
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int real = baseDamage;
        int bombs = countBombs(cpr().drawPile);
        bombs += countBombs(cpr().hand);
        bombs += countBombs(cpr().discardPile);
        baseDamage += bombs * magicNumber;
        super.calculateCardDamage(mo);
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    private int countBombs(CardGroup cg) {
        if (cg.isEmpty())
            return 0;
        return cg.group.stream().filter(c -> c instanceof EWBombCardBase).mapToInt(c -> 1).sum();
    }
}