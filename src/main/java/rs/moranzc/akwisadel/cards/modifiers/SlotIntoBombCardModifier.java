package rs.moranzc.akwisadel.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import rs.moranzc.akwisadel.base.EWCardModifierBase;

public class SlotIntoBombCardModifier extends EWCardModifierBase {
    public static final String ID = MakeID(SlotIntoBombCardModifier.class.getSimpleName());
    
    public SlotIntoBombCardModifier() {
        super(ID, true, true);
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new SlotIntoBombCardModifier();
    }
}
