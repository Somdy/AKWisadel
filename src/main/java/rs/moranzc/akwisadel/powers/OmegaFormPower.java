package rs.moranzc.akwisadel.powers;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import rs.moranzc.akwisadel.actions.utility.DamageCardsOnBombAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.interfaces.cards.IOmegaFormAffectable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OmegaFormPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(OmegaFormPower.class.getSimpleName());
    private final List<SingleTCard> singleTCards = new ArrayList<>();
    
    public OmegaFormPower(AbstractCreature owner) {
        super(POWER_ID, "omega_form", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, -1);
        preloadString(() -> desc[0]);
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        singleTCards.stream().filter(t -> t.identifies(card, action.target))
                .forEach(t -> {
                    Kazdel.logger.info("Omega executing single target card {}", t.c);
                    flash();
                    AbstractDungeon.getMonsters().monsters.stream().filter(m -> m != null && m != t.ot)
                            .forEach(m -> {
                                AbstractCard c = t.c;
                                if (c instanceof EWBombCardBase) {
                                    ((EWBombCardBase) c).onUseRepeatedByOmega(AbstractDungeon.player, m);
                                } else {
                                    c.use(AbstractDungeon.player, m);
                                }
                            });
                });
        singleTCards.removeIf(t -> t.identifies(card, action.target));
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if (AbstractDungeon.getMonsters().monsters.stream().filter(m -> m != null && !m.isDeadOrEscaped()).count() == 1) {
            damage *= 2;
        }
        return super.atDamageFinalGive(damage, type);
    }

    private static class SingleTCard {
        AbstractCard c;
        AbstractMonster ot;
        
        private SingleTCard(AbstractCard c, AbstractMonster ot) {
            this.c = c;
            this.ot = ot;
        }
        
        private boolean identifies(AbstractCard c, AbstractCreature ot) {
            return this.c == c && this.ot == ot;
        }
    }
    
    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class UseCardPatch {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if ("use".equals(m.getMethodName()) && AbstractCard.class.getName().equals(m.getClassName())) {
                        m.replace("{" + UseCardPatch.class.getName() + ".MarkPreCardUse($0, $$);" +
                                "$_=$proceed($$);" + UseCardPatch.class.getName() + ".MarkAfterCardUsed($0, $$);}");
                    }
                }
            };
        }
        
        public static void MarkPreCardUse(AbstractCard c, AbstractPlayer p, AbstractMonster m) {
            if (c.type != AbstractCard.CardType.ATTACK || !(p instanceof CharWisadel))
                return;
            CharWisadel ew = ((CharWisadel) p);
            AbstractPower po = ew.getPower(OmegaFormPower.POWER_ID);
            if (!(po instanceof OmegaFormPower))
                return;
            OmegaFormPower omega = ((OmegaFormPower) po);
            if ( (m != null && (c.target == AbstractCard.CardTarget.ENEMY || c.target == AbstractCard.CardTarget.SELF_AND_ENEMY)) 
                    || c instanceof IOmegaFormAffectable) {
                if (c instanceof IOmegaFormAffectable) {
                    ((IOmegaFormAffectable) c).affectByOmegaFormPreUse(omega);
                    omega.flash();
                    Kazdel.logger.info("Omega affecting affectable card {}", c);
                } else if (omega.singleTCards.stream().noneMatch(o -> o.identifies(c, m))) {
                    omega.singleTCards.add(new SingleTCard(c, m));
                    Kazdel.logger.info("Single target card {} on target {} added to omega execution list", c, m);
                }
            }
        }
        
        public static void MarkAfterCardUsed(AbstractCard c, AbstractPlayer p, AbstractMonster m) {
            if (c.type != AbstractCard.CardType.ATTACK || !(p instanceof CharWisadel))
                return;
            CharWisadel ew = ((CharWisadel) p);
            AbstractPower po = ew.getPower(OmegaFormPower.POWER_ID);
            if (!(po instanceof OmegaFormPower))
                return;
            OmegaFormPower omega = ((OmegaFormPower) po);
            if (m == null && (c.target == AbstractCard.CardTarget.ALL_ENEMY || c.target == AbstractCard.CardTarget.ALL) && ! (c instanceof IOmegaFormAffectable)) {
                Kazdel.logger.info("Omega affecting random attack card {}", c);
                if (AbstractDungeon.actionManager.actions.stream().anyMatch(a -> a instanceof AttackDamageRandomEnemyAction)) {
                    omega.flash();
                    AbstractDungeon.actionManager.actions.stream().filter(a -> a instanceof AttackDamageRandomEnemyAction)
                            .mapToInt(a -> AbstractDungeon.actionManager.actions.indexOf(a))
                            .forEach(i -> AbstractDungeon.actionManager.actions.set(i, new DamageAllEnemiesAction(p, c.baseDamage,
                                    c.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY)));
                }
            }
        }
    }
}