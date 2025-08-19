package rs.moranzc.akwisadel.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rs.moranzc.akwisadel.cards.wisadel.TearsOfRevenants;
import rs.moranzc.akwisadel.powers.GiftPower;

import java.util.function.Function;

public class TearsOfRevenantsAction extends AbstractGameAction {
    private final TearsOfRevenants tor;
    private final boolean timesTBD;
    private Function<AbstractMonster, AbstractPower> powerToApply;

    public TearsOfRevenantsAction(TearsOfRevenants tor, AbstractCreature source, AttackEffect effect) {
        this.tor = tor;
        setValues(null, source, -1);
        timesTBD = true;
        attackEffect = effect;
        actionType = ActionType.DAMAGE;
    }

    private TearsOfRevenantsAction(int times, TearsOfRevenants tor, AbstractCreature source, AttackEffect effect) {
        this.tor = tor;
        setValues(null, source, times);
        timesTBD = false;
        attackEffect = effect;
        actionType = ActionType.DAMAGE;
    }
    
    public TearsOfRevenantsAction applyPower(Function<AbstractMonster, AbstractPower> powerToApply) {
        this.powerToApply = powerToApply;
        return this;
    }

    @Override
    public void update() {
        isDone = true;
        if (timesTBD) {
            amount = AbstractDungeon.getMonsters().monsters.stream()
                    .filter(m -> m != null && !m.isDeadOrEscaped())
                    .mapToInt(m -> 1).sum();
        }
        if (amount <= 0 || tor == null)
            return;
        amount--;
        if (amount > 0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            addToTop(new TearsOfRevenantsAction(amount, tor, source, attackEffect));
        }
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
        if (m != null) {
            tor.applyPowers();
            tor.calculateCardDamage(m);
            AbstractPower p = powerToApply != null ? powerToApply.apply(m) : new GiftPower(m, tor.magicNumber);
            addToTop(new ApplyPowerAction(m, source, p));
            addToTop(new DamageAction(m, new DamageInfo(source, tor.damage, tor.damageTypeForTurn), attackEffect));
        }
    }
}