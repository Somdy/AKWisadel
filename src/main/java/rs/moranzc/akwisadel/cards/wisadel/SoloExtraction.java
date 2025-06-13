package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.core.Kazdel;

public class SoloExtraction extends EWCardBase {
    public static final String ID = MakeID(SoloExtraction.class.getSimpleName());
    
    public SoloExtraction() {
        super(ID, "ew/SoloExtraction.png", 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setDamage(6);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(t, s, new WeakPower(t, magicNumber, false)));
        addToBot(new ApplyPowerAction(s, s, new EnergizedPower(s, 1)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }

    @Override
    public boolean freeToPlay() {
        if (Kazdel.OutOfCombat())
            return false;
        return super.freeToPlay() || noOtherCardsPlayed();
    }
    
    private boolean noOtherCardsPlayed() {
        return AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty();
    }
}