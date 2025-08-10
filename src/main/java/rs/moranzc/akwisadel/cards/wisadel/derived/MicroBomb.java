package rs.moranzc.akwisadel.cards.wisadel.derived;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.core.CardMst;

import java.util.List;

public class MicroBomb extends EWBombCardBase {
    public static final String ID = MakeID(MicroBomb.class.getSimpleName());
    
    public MicroBomb() {
        super(ID, "ew/MicroBomb.png", 0, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        setDamage(4);
        exhaust = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t, List<AbstractCard> cardsToDamage) {
        addToBot(new DamageAction(t, createBombInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(2);
    }
}