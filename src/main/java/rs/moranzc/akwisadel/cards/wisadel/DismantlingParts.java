package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.utility.GridCardSelectActionBuilder;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.SupplementaryCharge;
import rs.moranzc.akwisadel.powers.DieOfDeathPower;
import rs.moranzc.akwisadel.utils.CardUtils;

public class DismantlingParts extends EWCardBase {
    public static final String ID = MakeID(DismantlingParts.class.getSimpleName());
    
    public DismantlingParts() {
        super(ID, "ew/DismantlingParts.png", 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3);
        cardsToPreview = new SupplementaryCharge();
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GridCardSelectActionBuilder(magicNumber, String.format(strings.customs.get("Msg"), magicNumber), s.drawPile,
                c -> true).manipulate((cg, c) -> {
                    if (cpr().drawPile.contains(c)) {
                        CardUtils.DamageCard(c);
                        addToTop(new AbstractGameAction() {
                            @Override
                            public void update() {
                                isDone = true;
                                if (cpr().drawPile.contains(c))
                                    cpr().drawPile.moveToDiscardPile(c);
                            }
                        });
                    }
                })
                .forAllSelected(cards -> addToTop(new MakeTempCardInHandAction(new SupplementaryCharge(), cards.size()))));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBaseCost(0);
    }
}
