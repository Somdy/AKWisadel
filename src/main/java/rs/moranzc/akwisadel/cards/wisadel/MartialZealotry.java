package rs.moranzc.akwisadel.cards.wisadel;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import rs.moranzc.akwisadel.actions.common.DamageCardsAction;
import rs.moranzc.akwisadel.base.EWCardBase;

import java.util.stream.Collectors;

@AutoAdd.Ignore
public class MartialZealotry extends EWCardBase {
    public static final String ID = MakeID(MartialZealotry.class.getSimpleName());
    
    public MartialZealotry() {
        super(ID, "ew/MartialZealotry.png", 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2);
        isInnate = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new StrengthPower(s, magicNumber)));
        addToBot(new DamageCardsAction(s, s.hand.group.stream().filter(c -> c.type != CardType.ATTACK).collect(Collectors.toList())));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}
