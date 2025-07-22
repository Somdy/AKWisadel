package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akwisadel.base.EWBombCardBase;

import java.util.List;

public class AN602 extends EWBombCardBase {
    public static final String ID = MakeID(AN602.class.getSimpleName());
    private boolean bombThemAll;
    
    public AN602() {
        super(ID, "ew/AN602.png", 3, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setDamage(36);
        exhaust = true;
        bombThemAll = false;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t, List<AbstractCard> cardsToDamage) {
        if (bombThemAll) {
            addToBot(new DamageAllEnemiesAction(s, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        } else {
            addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(9);
    }

    @Override
    public void applyPowers() {
        bombThemAll = cpr().hand.group.stream().noneMatch(c -> c != this);
        target = bombThemAll ? CardTarget.ALL_ENEMY : CardTarget.ENEMY;
        isMultiDamage = bombThemAll;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        bombThemAll = cpr().hand.group.stream().noneMatch(c -> c != this);
        target = bombThemAll ? CardTarget.ALL_ENEMY : CardTarget.ENEMY;
        isMultiDamage = bombThemAll;
        super.calculateCardDamage(mo);
    }
}