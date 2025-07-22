package rs.moranzc.akwisadel.cards.dynvars;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.core.Kazdel;

public class ExtraMagicVariable extends DynamicVariable {
    public static final String KEY = Kazdel.MakeID("eM");
    @Override
    public String key() {
        return KEY;
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof EWCardBase)
            return ((EWCardBase) card).isExtraMagicModified;
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof EWCardBase)
            return ((EWCardBase) card).extraMagic;
        return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof EWCardBase)
            return ((EWCardBase) card).baseExtraMagic;
        return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof EWCardBase)
            return ((EWCardBase) card).upgradedExtraMagic;
        return false;
    }
}
