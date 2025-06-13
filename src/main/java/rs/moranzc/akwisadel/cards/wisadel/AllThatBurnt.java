package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.core.Kazdel;

public class AllThatBurnt extends EWCardBase {
    public static final String ID = MakeID(AllThatBurnt.class.getSimpleName());
    private int times;
    
    public AllThatBurnt() {
        super(ID, "ew/AllThatBurnt.png", 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(6);
        times = 0;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        for (int i = 0; i < times; i++) {
            addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(2);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        times = CharWisadel.CARDS_DAMAGED_THIS_TURN.size();
        if (times > 0) {
            updateDescription(String.format(strings.desc + strings.customs.getOrDefault("Extd", ""), damage, times));
        } else {
            updateDescription(strings.desc);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        times = CharWisadel.CARDS_DAMAGED_THIS_TURN.size();
        if (times > 0) {
            updateDescription(String.format(strings.desc + strings.customs.getOrDefault("Extd", ""), damage, times));
        } else {
            updateDescription(strings.desc);
        }
    }
}