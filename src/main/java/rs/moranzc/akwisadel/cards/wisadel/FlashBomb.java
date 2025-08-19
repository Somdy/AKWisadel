package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.WeakPower;
import rs.moranzc.akwisadel.actions.common.ApplyPowerToEnemiesAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

import java.util.List;

public class FlashBomb extends EWBombCardBase {
    public static final String ID = MakeID(FlashBomb.class.getSimpleName());
    
    public FlashBomb() {
        super(ID, "ew/FlashBomb.png", 1, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(8);
        setMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t, List<AbstractCard> cardsToDamage) {
        addToBot(new DamageAction(t, createBombInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int count = (int) cardsToDamage.stream().filter(c -> c instanceof IPartCard).count();
                addToTop(new ApplyPowerToEnemiesAction(s, m -> new WeakPower(m, count, false)));
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeSlot(1);
    }
}
