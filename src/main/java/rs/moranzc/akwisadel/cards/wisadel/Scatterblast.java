package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class Scatterblast extends EWCardBase {
    public static final String ID = MakeID(Scatterblast.class.getSimpleName());
    
    public Scatterblast() {
        super(ID, "ew/Scatterblast.png", 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setDamage(2);
        setMagic(5);
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        for(int i = 0; i < magicNumber; ++i) {
            addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}