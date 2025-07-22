package rs.moranzc.akwisadel.cards.wisadel;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.common.DamageCardsInHandAction;
import rs.moranzc.akwisadel.actions.common.MendCardsAction;
import rs.moranzc.akwisadel.actions.common.MendCardsInHandAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnModifierAppliedCard;
import rs.moranzc.akwisadel.utils.CardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CombatMedic extends EWCardBase implements ICallOnModifierAppliedCard {
    public static final String ID = MakeID(CombatMedic.class.getSimpleName());
    
    public CombatMedic() {
        super(ID, "ew/CombatMedic.png", 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setBlock(7);
        setMagic(2);
        selfRetain = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageCardsInHandAction(1, !upgraded).canPickZero(false).anyNumber(false));
        addToBot(new MendCardsInHandAction(1, !upgraded).canPickZero(true).anyNumber(true));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }

    @Override
    public void onModifierApplied(AbstractCardModifier mod) {
        if (DamagedCardModifier.ID.equals(mod.identifier(this))) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    List<AbstractCard> tmp = cpr().hand.group.stream()
                            .filter(c -> c != CombatMedic.this && CardUtils.IsDamaged(c))
                            .collect(Collectors.toList());
                    AbstractCard anotherCardToMend = null;
                    if (!tmp.isEmpty()) {
                        anotherCardToMend = tmp.get(AbstractDungeon.cardRandomRng.random(tmp.size() - 1));
                    }
                    addToTop(new MendCardsAction(CombatMedic.this, anotherCardToMend));
                }
            });
        }
    }
}
