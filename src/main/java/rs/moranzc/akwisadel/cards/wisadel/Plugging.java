package rs.moranzc.akwisadel.cards.wisadel;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCardsAction;
import rs.moranzc.akwisadel.actions.common.PlayTopCardsCallbackAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.modifiers.LaterDamageCardModifier;

public class Plugging extends EWCardBase {
    public static final String ID = MakeID(Plugging.class.getSimpleName());
    
    public Plugging() {
        super(ID, "ew/Plugging.png", 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new PlayTopCardsCallbackAction(1, false)
                .callback(c -> CardModifierManager.addModifier(c, new LaterDamageCardModifier())));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBaseCost(0);
    }
}
