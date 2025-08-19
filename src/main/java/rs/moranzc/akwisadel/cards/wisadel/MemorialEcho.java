package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.utility.GridCardSelectActionBuilder;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class MemorialEcho extends EWCardBase {
    public static final String ID = MakeID(MemorialEcho.class.getSimpleName());
    
    public MemorialEcho() {
        super(ID, "ew/MemorialEcho.png", 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        setMagic(3);
        exhaust = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GridCardSelectActionBuilder(1, strings.customs.get("Msg"), s.exhaustPile, CardUtils::IsDamaged)
                .manipulate((cg, c) -> {
                    if (s.exhaustPile.contains(c)) {
                        CardUtils.MendCard(c);
                        s.exhaustPile.moveToHand(c);
                        c.unfadeOut();
                        c.applyPowers();
                    }
                }));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBaseCost(0);
    }
}
