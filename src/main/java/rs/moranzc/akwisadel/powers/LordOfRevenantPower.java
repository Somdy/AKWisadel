package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.characters.CharWisadel;

public class LordOfRevenantPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(LordOfRevenantPower.class.getSimpleName());
    
    public LordOfRevenantPower(AbstractCreature owner) {
        super(POWER_ID, "lord_of_revenant", PowerType.BUFF, owner);
        setValues(AbstractDungeon.player, -1);
        preloadString(() -> desc[0]);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p instanceof CharWisadel) {
            CharWisadel ew = ((CharWisadel) p);
            ew.forEachLiveRevenant(r -> r.increaseMaxHpBy(2));
        }
    }
}