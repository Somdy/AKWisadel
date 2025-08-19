package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class BloodstainedHewer extends EWCardBase {
    public static final String ID = MakeID(BloodstainedHewer.class.getSimpleName());
    
    public BloodstainedHewer() {
        super(ID, "ew/BloodstainedHewer.png", 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setDamage(7);
        setMagic(2);
        isMultiDamage = true;
        selfRetain = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAllEnemiesAction(s, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(3);
    }

    @Override
    public void applyPowers() {
        int real = baseDamage;
        if (CardUtils.IsDamaged(this)) {
            baseDamage *= magicNumber;
        }
        super.applyPowers();
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int real = baseDamage;
        if (CardUtils.IsDamaged(this)) {
            baseDamage *= magicNumber;
        }
        super.calculateCardDamage(mo);
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (CardUtils.IsDamaged(this)) {
            glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}