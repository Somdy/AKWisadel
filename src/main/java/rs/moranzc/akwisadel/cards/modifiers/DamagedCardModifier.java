package rs.moranzc.akwisadel.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import rs.moranzc.akwisadel.base.EWCardModifierBase;
import rs.moranzc.akwisadel.localization.I18nManager;

public class DamagedCardModifier extends EWCardModifierBase {
    public static final String ID = MakeID(DamagedCardModifier.class.getSimpleName());
    public static final I18nManager.LocaleTip TIP = I18nManager.GetTip("DamagedCardMod");
    
    public DamagedCardModifier() {
        super(ID, true, false, TIP.title, TIP.desc());
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DamagedCardModifier();
    }
}