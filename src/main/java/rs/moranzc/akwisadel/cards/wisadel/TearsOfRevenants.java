package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.unique.TearsOfRevenantsAction;
import rs.moranzc.akwisadel.base.EWCardBase;

public class TearsOfRevenants extends EWCardBase {
    public static final String ID = MakeID(TearsOfRevenants.class.getSimpleName());
    
    public TearsOfRevenants() {
        super(ID, "ew/TearsOfRevenants.png", 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        setDamage(7);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new TearsOfRevenantsAction(this, s, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(3);
    }
}