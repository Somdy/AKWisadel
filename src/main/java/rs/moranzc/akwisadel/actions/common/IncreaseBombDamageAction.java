package rs.moranzc.akwisadel.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.base.EWBombCardBase;

public class IncreaseBombDamageAction extends AbstractGameAction {
    private final EWBombCardBase bomb;
    private boolean permanent;
    
    public IncreaseBombDamageAction(EWBombCardBase bomb, int amount) {
        this(bomb, amount, false);
    }

    public IncreaseBombDamageAction(EWBombCardBase bomb, int amount, boolean permanent) {
        this.bomb = bomb;
        this.amount = amount;
        this.permanent = permanent;
        actionType = ActionType.CARD_MANIPULATION;
    }
    
    @Override
    public void update() {
        isDone = true;
        if (bomb != null) {
            bomb.increaseDamageForCombat(amount);
            if (permanent) {
                AbstractDungeon.player.masterDeck.group.stream().filter(c -> c.uuid == bomb.uuid && c instanceof EWBombCardBase)
                        .map(c -> (EWBombCardBase) c)
                        .forEach(c -> c.increaseDamageForRun(amount));
            }
        }
    }
}
