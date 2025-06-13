package rs.moranzc.akwisadel.cards.wisadel;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnModifierAppliedCard;
import rs.moranzc.akwisadel.utils.CardUtils;

public class TemperedResolve extends EWCardBase implements ICallOnModifierAppliedCard {
    public static final String ID = MakeID(TemperedResolve.class.getSimpleName());
    private int timesDamaged;
    
    public TemperedResolve() {
        super(ID, "ew/TemperedResolve.png", 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(8);
        setMagic(5);
        timesDamaged = 0;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(3);
    }

    @Override
    public void applyPowers() {
        int real = baseDamage;
        baseDamage += timesDamaged * magicNumber;
        super.applyPowers();
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int real = baseDamage;
        baseDamage += timesDamaged * magicNumber;
        super.calculateCardDamage(mo);
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        if (card instanceof TemperedResolve) {
            ((TemperedResolve) card).timesDamaged = timesDamaged;
        }
        return card;
    }

    @Override
    public void onModifierApplied(AbstractCardModifier mod) {
        if (DamagedCardModifier.ID.equals(mod.identifier(this))) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    baseDamage += magicNumber;
                    isDamageModified = baseDamage != damage;
                    applyPowers();
                    CardModifierManager.removeSpecificModifier(TemperedResolve.this, mod, true);
                }
            });
        }
    }
}