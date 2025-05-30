package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;

public class Strike_EW extends EWCardBase {
    public static final String ID = MakeID(Strike_EW.class.getSimpleName());
    
    public Strike_EW() {
        super(ID, "ew/Strike.png", 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        setDamage(6);
        addTags(CardTags.STRIKE, CardTags.STARTER_STRIKE);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(3);
    }
}
