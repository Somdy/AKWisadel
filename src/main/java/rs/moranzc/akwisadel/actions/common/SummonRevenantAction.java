package rs.moranzc.akwisadel.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.characters.Revenant;

import java.util.List;
import java.util.function.Consumer;

public class SummonRevenantAction extends AbstractGameAction {
    private Consumer<Revenant> postAction;
    
    public SummonRevenantAction(int amount, Consumer<Revenant> postAction) {
        this.amount = amount;
        this.postAction = postAction;
    }
    
    public SummonRevenantAction(int amount) {
        this(amount, r -> {});
    }
    
    @Override
    public void update() {
        isDone = true;
        AbstractPlayer p = AbstractDungeon.player;
        if (!(p instanceof CharWisadel) || amount <= 0)
            return;
        CharWisadel ew = ((CharWisadel) p);
        List<Revenant> summoned = ew.summonRevenants(amount);
        summoned.forEach(r -> postAction.accept(r));
    }
}