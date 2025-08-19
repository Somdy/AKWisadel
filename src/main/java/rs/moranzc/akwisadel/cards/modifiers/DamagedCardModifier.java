package rs.moranzc.akwisadel.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import rs.moranzc.akwisadel.base.EWCardModifierBase;
import rs.moranzc.akwisadel.localization.I18nManager;
import rs.moranzc.akwisadel.utils.CardUtils;

public class DamagedCardModifier extends EWCardModifierBase {
    public static final String ID = MakeID(DamagedCardModifier.class.getSimpleName());
    
    public DamagedCardModifier() {
        super(ID, true, false, I18nManager.MT("DamagedCardMod.title"), I18nManager.MT("DamagedCardMod.desc"));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DamagedCardModifier();
    }
    
    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class UseDamagedCardPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if ("exhaustCard".equals(f.getFieldName())) {
                        f.replace("$_=$proceed($$) || " + CardUtils.class.getName() + ".IsDamaged(targetCard);");
                    }
                }
            };
        }
    }
}