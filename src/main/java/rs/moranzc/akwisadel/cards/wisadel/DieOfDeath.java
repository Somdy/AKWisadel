package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.SummonRevenantAction;
import rs.moranzc.akwisadel.actions.utility.GridCardSelectActionBuilder;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.powers.DieOfDeathPower;
import rs.moranzc.akwisadel.utils.CardUtils;

public class DieOfDeath extends EWCardBase {
    public static final String ID = MakeID(DieOfDeath.class.getSimpleName());
    
    public DieOfDeath() {
        super(ID, "ew/DieOfDeath.png", 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3);
        exhaust = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GridCardSelectActionBuilder(magicNumber, String.format(strings.customs.get("Msg"), magicNumber), s.drawPile,
                CardUtils::IsDamaged).appendCards(s.discardPile, false)
                .manipulate((cg, c) -> {
                    if (s.drawPile.contains(c))
                        s.drawPile.moveToHand(c);
                    if (s.discardPile.contains(c))
                        s.discardPile.moveToHand(c);
                })
                .forAllSelected(cards -> addToTop(new ApplyPowerAction(s, s, new DieOfDeathPower(s, cards)))));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}
