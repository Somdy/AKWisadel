package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.Revenant;

import java.util.List;

public class FirstRevenant extends EWCardBase {
    public static final String ID = MakeID(FirstRevenant.class.getSimpleName());
    
    public FirstRevenant() {
        super(ID, "ew/FirstRevenant.png", 1, CardType.SKILL, CardRarity.BASIC, CardTarget.NONE);
        setMagic(2);
        exhaust = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new SummonRevenantAction(1, r -> r.takeMove(magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBaseCost(0);
    }
}