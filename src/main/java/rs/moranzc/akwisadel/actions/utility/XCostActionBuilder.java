package rs.moranzc.akwisadel.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.function.Consumer;
import java.util.function.Function;

public class XCostActionBuilder extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int energyOnUse;
    private Function<Integer, Integer> timesSupplier;
    private Consumer<Integer> action;
    
    public XCostActionBuilder(boolean freeToPlayOnce, int energyOnUse) {
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;
        timesSupplier = x -> x;
        action = i -> {};
    }
    
    public XCostActionBuilder effectTimes(Function<Integer, Integer> timesSupplier) {
        this.timesSupplier = timesSupplier;
        return this;
    }
    
    public XCostActionBuilder act(Consumer<Integer> action) {
        this.action = action;
        return this;
    }
    
    @Override
    public void update() {
        isDone = true;
        int effectTimes = energyOnUse;
        if (timesSupplier != null) {
            effectTimes = timesSupplier.apply(effectTimes);
        }
        if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) {
            effectTimes += 2;
            AbstractDungeon.player.getRelic(ChemicalX.ID).flash();
        }
        if (action != null) {
            action.accept(effectTimes);
        }
        if (!freeToPlayOnce) {
            AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
        }
    }
}