package rs.moranzc.akwisadel.cards.wisadel;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.wisadel.derived.DustAndSmoke;
import rs.moranzc.akwisadel.cards.wisadel.derived.SupplementaryCharge;
import rs.moranzc.akwisadel.powers.LogisticalSupportPower;
import rs.moranzc.akwisadel.utils.CardUtils;

import java.util.stream.IntStream;

public class LogisticalSupport2 extends EWCardBase {
    public static final String ID = MakeID(LogisticalSupport2.class.getSimpleName());
    
    public LogisticalSupport2() {
        super(ID, "ew/LogisticalSupport.png", 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(1);
        MultiCardPreview.add(new SupplementaryCharge(), new DustAndSmoke());
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                transformCard(cpr().drawPile);
                transformCard(cpr().hand);
                transformCard(cpr().discardPile);
                transformCard(cpr().exhaustPile);
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        isInnate = true;
    }
    
    private void transformCard(CardGroup cg) {
        cg.group.stream().filter(c -> c.hasTag(CardTags.STARTER_STRIKE))
                .mapToInt(c -> cg.group.indexOf(c))
                .forEach(i -> cg.group.set(i, new SupplementaryCharge()));
        cg.group.stream().filter(c -> c.hasTag(CardTags.STARTER_DEFEND))
                .mapToInt(c -> cg.group.indexOf(c))
                .forEach(i -> cg.group.set(i, new DustAndSmoke()));
    }
}