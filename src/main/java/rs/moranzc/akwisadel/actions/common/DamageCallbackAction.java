package rs.moranzc.akwisadel.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.function.Consumer;

public class DamageCallbackAction extends AbstractGameAction {
    private final DamageInfo info;
    private Consumer<AbstractCreature> whenFatalAction;
    private Consumer<AbstractCreature> callback;

    public DamageCallbackAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        setValues(target, info.owner);
        this.info = info;
        this.attackEffect = effect;
        whenFatalAction = t -> {};
        callback = t -> {};
        actionType = ActionType.DAMAGE;
        startDuration = duration = Settings.ACTION_DUR_XFAST;
    }

    public DamageCallbackAction whenFatal(Consumer<AbstractCreature> action) {
        whenFatalAction = action;
        return this;
    }
    
    public DamageCallbackAction callback(Consumer<AbstractCreature> action) {
        callback = action;
        return this;
    }

    @Override
    public void update() {
        if (target == null || info == null || target.isDeadOrEscaped()) {
            isDone = true;
            return;
        }
        if (duration == startDuration) {
            if (info.type != DamageInfo.DamageType.THORNS && info.owner != null && (info.owner.isDying || info.owner.halfDead)) {
                isDone = true;
                return;
            }
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect, false));
        }
        tickDuration();
        if (isDone) {
            target.damage(info);
            if (whenFatalAction != null && (target.isDying || target.currentHealth <= 0)
                    && !target.halfDead && !target.hasPower("Minion")) {
                whenFatalAction.accept(target);
            }
            if (callback != null) {
                callback.accept(target);
            }
        }
    }
}
