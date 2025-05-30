package rs.moranzc.akwisadel.cards.dynvars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.core.Kazdel;

public class SlotVariable extends DynamicVariable {
    public static final String KEY = Kazdel.MakeID("slot");
    @Override
    public String key() {
        return KEY;
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof EWBombCardBase)
            return ((EWBombCardBase) card).isSlotModified;
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof EWBombCardBase)
            return ((EWBombCardBase) card).slot;
        return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof EWBombCardBase)
            return ((EWBombCardBase) card).baseSlot;
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof EWBombCardBase)
            return ((EWBombCardBase) card).upgradedSlot;
        return false;
    }
}
