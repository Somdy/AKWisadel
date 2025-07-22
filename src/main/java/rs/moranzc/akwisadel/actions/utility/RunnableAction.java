package rs.moranzc.akwisadel.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class RunnableAction extends AbstractGameAction {
    private final Runnable runnable;

    public RunnableAction(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void update() {
        isDone = true;
        if (runnable != null)
            runnable.run();
    }
}
