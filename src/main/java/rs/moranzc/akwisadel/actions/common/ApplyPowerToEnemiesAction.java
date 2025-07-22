package rs.moranzc.akwisadel.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ApplyPowerToEnemiesAction extends AbstractGameAction {
    private final Function<AbstractMonster, AbstractPower> pwrBuilder;
    private List<AbstractMonster> targets;

    public ApplyPowerToEnemiesAction(AbstractCreature source, Function<AbstractMonster, AbstractPower> pwrBuilder) {
        this.source = source;
        this.pwrBuilder = pwrBuilder;
        targets = AbstractDungeon.getMonsters().monsters.stream().filter(m -> m != null && !m.isDeadOrEscaped()).collect(Collectors.toList());
        actionType = ActionType.POWER;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        isDone = true;
        if (pwrBuilder == null || targets == null || targets.isEmpty()) {
            return;
        }
        targets.forEach(m -> {
            AbstractPower p = pwrBuilder.apply(m);
            if (p != null) {
                addToTop(new ApplyPowerAction(m, source, p));
            }
        });
    }
}
