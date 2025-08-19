package rs.moranzc.akwisadel.cards.wisadel;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.utils.CardUtils;

@AutoAdd.Ignore
public class LoneWolf extends EWCardBase {
    public static final String ID = MakeID(LoneWolf.class.getSimpleName());
    private boolean lonewolf;
    
    public LoneWolf() {
        super(ID, "ew/LoneWolf.png", 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(10);
        setMagic(3);
        lonewolf = false;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), lonewolf ? AbstractGameAction.AttackEffect.BLUNT_HEAVY
                : AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(4);
    }

    @Override
    public void applyPowers() {
        int real = baseDamage;
        lonewolf = cpr().hand.contains(this) && !CardUtils.IsDamaged(this)
                && cpr().hand.group.stream().allMatch(c -> (c != this && CardUtils.IsDamaged(c)) || (c == this && !CardUtils.IsDamaged(c) ));
        if (lonewolf) {
            baseDamage *= magicNumber;
        }
        super.applyPowers();
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int real = baseDamage;
        lonewolf = cpr().hand.contains(this) && !CardUtils.IsDamaged(this)
                && cpr().hand.group.stream().allMatch(c -> (c != this && CardUtils.IsDamaged(c)) || (c == this && !CardUtils.IsDamaged(c) ));
        if (lonewolf) {
            baseDamage *= magicNumber;
        }
        super.calculateCardDamage(mo);
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (lonewolf) {
            glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}