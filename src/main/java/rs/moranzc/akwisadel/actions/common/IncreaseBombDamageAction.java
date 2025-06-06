package rs.moranzc.akwisadel.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;

public class IncreaseBombDamageAction extends AbstractGameAction {
    private final EWBombCardBase bomb;
    
    public IncreaseBombDamageAction(EWBombCardBase bomb, int amount) {
        this.bomb = bomb;
        this.amount = amount;
        actionType = ActionType.CARD_MANIPULATION;
    }
    
    @Override
    public void update() {
        isDone = true;
        if (bomb != null) {
            bomb.increaseDamageForCombat(amount);
        }
    }
}
