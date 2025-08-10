package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.common.DamageCardsAction;
import rs.moranzc.akwisadel.base.EWPowerBase;

import java.util.ArrayList;
import java.util.List;

public class DieOfDeathPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(DieOfDeathPower.class.getSimpleName());
    private List<AbstractCard> cardsToDamage;
    
    public DieOfDeathPower(AbstractCreature owner, List<AbstractCard> cardsToDamage) {
        super(POWER_ID, "die_of_death", PowerType.BUFF, owner);
        distinctID();
        this.cardsToDamage = new ArrayList<>(cardsToDamage);
        setValues(null, -1);
        preloadString(() -> mkstring(desc[0], buildCardsStrings()));
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        if (!cardsToDamage.isEmpty()) {
            flashWithoutSound();
            addToBot(new DamageCardsAction(AbstractDungeon.player, cardsToDamage));
            cardsToDamage.clear();
        }
    }

    private String buildCardsStrings() {
        StringBuilder sbr = new StringBuilder();
        cardsToDamage.forEach(c -> sbr.append(c.name).append("，"));
        sbr.deleteCharAt(sbr.length() - 1);
        return sbr.toString();
    }
}