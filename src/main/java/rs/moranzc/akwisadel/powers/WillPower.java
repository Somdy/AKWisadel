package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.utility.GridCardSelectActionBuilder;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.utils.CardUtils;

public class WillPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(WillPower.class.getSimpleName());
    
    public WillPower(AbstractCreature owner) {
        super(POWER_ID, "will", PowerType.BUFF, owner);
        setValues(owner, -1);
        preloadString(() -> mkstring(desc[0]));
        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        addToBot(new GridCardSelectActionBuilder(1, desc[1], cpr().drawPile, CardUtils::IsDamaged)
                .appendCards(cpr().discardPile, false).appendCards(cpr().hand, false)
                .anyNumber(true).canCancel(true)
                .manipulate((g, c) -> {
                    CardUtils.MendCard(c);
                    CardGroup cg = CardUtils.LocateCardGroupWhereExists(c);
                    if (cg != null && cg != cpr().hand)
                        cg.moveToHand(c);
                }));
    }
}