package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;

import java.util.List;

public class HomemadeBomb extends EWBombCardBase {
    public static final String ID = MakeID(HomemadeBomb.class.getSimpleName());
    
    public HomemadeBomb() {
        super(ID, "ew/HomemadeBomb.png", 1, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        setDamage(10);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t, List<AbstractCard> cardsToDamage) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(4);
    }
}