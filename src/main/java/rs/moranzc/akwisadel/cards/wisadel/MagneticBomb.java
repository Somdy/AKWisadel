package rs.moranzc.akwisadel.cards.wisadel;

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

public class MagneticBomb extends EWBombCardBase {
    public static final String ID = MakeID(MagneticBomb.class.getSimpleName());
    
    public MagneticBomb() {
        super(ID, "ew/MagneticBomb.png", 2, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(18);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t, List<AbstractCard> cardsToDamage) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(6);
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new MakeTempCardInHandAction(CardMst.GetRandomPart(), 1));
    }
}