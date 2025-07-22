package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.actions.utility.RunnableAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;

public class BreathOfRevenant extends EWCardBase {
    public static final String ID = MakeID(BreathOfRevenant.class.getSimpleName());
    
    public BreathOfRevenant() {
        super(ID, "ew/BreathOfRevenant.png", 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        setDamage(0);
        isMultiDamage = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new SummonRevenantAction(1));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (s instanceof CharWisadel) {
                    CharWisadel ew = ((CharWisadel) s);
                    int d = ew.countRevenantsTotalHP();
                    int real = baseDamage;
                    baseDamage += d;
                    applyPowers();
                    baseDamage = real;
                    addToTop(new DamageAllEnemiesAction(ew, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                }
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(4);
    }
}