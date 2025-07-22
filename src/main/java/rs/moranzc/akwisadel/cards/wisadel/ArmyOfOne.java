package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.Revenant;

public class ArmyOfOne extends EWCardBase {
    public static final String ID = MakeID(ArmyOfOne.class.getSimpleName());
    
    public ArmyOfOne() {
        super(ID, "ew/ArmyOfOne.png", 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setDamage(8);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractDungeon.getMonsters().monsters.stream().filter(m -> m != null && !m.isDeadOrEscaped())
                        .forEach(m -> {
                            calculateCardDamage(m);
                            if (upgraded) {
                                addToTop(new SummonRevenantAction(1, Revenant::takeMove));
                            }
                            addToTop(new DamageAction(m, new DamageInfo(s, damage, damageTypeForTurn), AttackEffect.BLUNT_LIGHT));
                        });
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
    }
}