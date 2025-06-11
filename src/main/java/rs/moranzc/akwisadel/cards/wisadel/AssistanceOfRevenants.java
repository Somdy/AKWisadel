package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;

public class AssistanceOfRevenants extends EWCardBase {
    public static final String ID = MakeID(AssistanceOfRevenants.class.getSimpleName());
    
    public AssistanceOfRevenants() {
        super(ID, "ew/AssistanceOfRevenants.png", 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(8);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (s instanceof CharWisadel) {
                    CharWisadel ew = ((CharWisadel) s);
                    addToTop(new DrawCardAction(ew.countRevenants()));
                }
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(3);
    }
}