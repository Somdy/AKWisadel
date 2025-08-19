package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import rs.moranzc.akwisadel.base.EWCardBase;

public class WarJunkie extends EWCardBase {
    public static final String ID = MakeID(WarJunkie.class.getSimpleName());
    
    public WarJunkie() {
        super(ID, "ew/WarJunkie.png", 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
        setExtraMagic(1);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new StrengthPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeExtraMagic(1);
    }

    @Override
    public void applyPowers() {
        int real = baseMagicNumber;
        baseMagicNumber += (int) AbstractDungeon.getMonsters().monsters.stream().filter(m -> m != null && !m.isDeadOrEscaped()).count() * extraMagic;
        super.applyPowers();
        magicNumber = baseMagicNumber;
        baseMagicNumber = real;
        isMagicNumberModified = magicNumber != baseMagicNumber;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int real = baseMagicNumber;
        baseMagicNumber += (int) AbstractDungeon.getMonsters().monsters.stream().filter(m -> m != null && !m.isDeadOrEscaped()).count() * extraMagic;
        super.calculateCardDamage(mo);
        magicNumber = baseMagicNumber;
        baseMagicNumber = real;
        isMagicNumberModified = magicNumber != baseMagicNumber;
    }
}