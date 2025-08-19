package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;

import java.util.List;

public class MayhemBomb extends EWBombCardBase {
    public static final String ID = MakeID(MayhemBomb.class.getSimpleName());
    
    public MayhemBomb() {
        super(ID, "ew/MayhemBomb.png", 2, 10, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setDamage(7);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t, List<AbstractCard> cardsToDamage) {
        addToBot(new DamageAction(t, createBombInfo(s, damage * cardsToDamage.size(), damageTypeForTurn), 
                AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(3);
    }
}