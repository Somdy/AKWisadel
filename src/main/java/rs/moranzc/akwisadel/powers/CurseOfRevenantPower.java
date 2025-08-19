package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rs.moranzc.akwisadel.actions.common.DamageCardsAction;
import rs.moranzc.akwisadel.base.EWPowerBase;

public class CurseOfRevenantPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(CurseOfRevenantPower.class.getSimpleName());
    
    public CurseOfRevenantPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "curse_of_revenant", PowerType.DEBUFF, owner);
        setValues(AbstractDungeon.player, amount);
        preloadString(() -> mkstring(desc[0], this.owner.name, this.amount));
        updateDescription();
    }

    @Override
    public void onDeath() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && owner.currentHealth <= 0) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractDungeon.getMonsters().monsters.stream()
                            .filter(m -> m != null && !m.isDeadOrEscaped() && m.hasPower(GiftPower.POWER_ID))
                            .forEach(m -> {
                                AbstractPower p = m.getPower(GiftPower.POWER_ID);
                                if (p instanceof GiftPower) {
                                    addToTop(new ApplyPowerAction(m, source, new GiftPower(m, p.amount * CurseOfRevenantPower.this.amount)));
                                }
                            });
                }
            });
        }
    }
}