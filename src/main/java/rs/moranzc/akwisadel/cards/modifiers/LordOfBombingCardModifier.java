package rs.moranzc.akwisadel.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akwisadel.base.EWCardModifierBase;

public class LordOfBombingCardModifier extends EWCardModifierBase implements CustomSavable<Integer> {
    public static final String ID = MakeID(LordOfBombingCardModifier.class.getSimpleName());
    public int damageIncrement;
    
    public LordOfBombingCardModifier(int damageIncrement) {
        super(ID, true, false, null, null);
        this.damageIncrement = damageIncrement;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        damage += damageIncrement;
        return super.modifyBaseDamage(damage, type, card, target);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new LordOfBombingCardModifier(damageIncrement);
    }

    @Override
    public Integer onSave() {
        return damageIncrement;
    }

    @Override
    public void onLoad(Integer v) {
        if (v != null) {
            damageIncrement = v;
        }
    }
}