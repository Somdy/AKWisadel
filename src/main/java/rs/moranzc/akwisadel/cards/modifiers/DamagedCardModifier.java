package rs.moranzc.akwisadel.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import rs.moranzc.akwisadel.base.EWCardModifierBase;

public class DamagedCardModifier extends EWCardModifierBase {
    
    protected DamagedCardModifier(String id, boolean unique, boolean inherent, String tipTile, String tipDesc) {
        super(id, unique, inherent, tipTile, tipDesc);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return null;
    }
}