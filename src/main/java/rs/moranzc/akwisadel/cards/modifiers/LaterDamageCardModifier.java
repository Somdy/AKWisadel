package rs.moranzc.akwisadel.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import rs.moranzc.akwisadel.actions.common.DamageCardsAction;
import rs.moranzc.akwisadel.base.EWCardModifierBase;
import rs.moranzc.akwisadel.localization.I18nManager;
import rs.moranzc.akwisadel.utils.CardUtils;

public class LaterDamageCardModifier extends EWCardModifierBase {
    public static final String ID = MakeID(LaterDamageCardModifier.class.getSimpleName());
    
    public LaterDamageCardModifier() {
        super(ID, true, false, null, null);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new LaterDamageCardModifier();
    }

    @SpirePatch(clz = UseCardAction.class, method = "update")
    public static class DamageCardLaterPatch {
        @SpirePostfixPatch
        public static void Postfix(UseCardAction _inst, AbstractCard ___targetCard) {
            if (CardModifierManager.hasModifier(___targetCard, LaterDamageCardModifier.ID)) {
                CardModifierManager.removeModifiersById(___targetCard, LaterDamageCardModifier.ID, true);
                CardUtils.DamageCard(___targetCard);
            }
        }
    }
}