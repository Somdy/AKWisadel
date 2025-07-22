package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.utility.AutoMoveCardsToHandAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnOtherCardsDamagedCard;

public class ToothlessRage extends EWCardBase implements ICallOnOtherCardsDamagedCard {
    public static final String ID = MakeID(ToothlessRage.class.getSimpleName());
    
    public ToothlessRage() {
        super(ID, "ew/ToothlessRage.png", 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(4);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(2);
    }

    @Override
    public void onOtherCardDamaged(AbstractCard cardDamaged) {
        addToBot(new AutoMoveCardsToHandAction(false, this));
    }
}