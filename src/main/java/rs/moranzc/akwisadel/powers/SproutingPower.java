package rs.moranzc.akwisadel.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.interfaces.powers.ICallOnCardsMendedPower;

public class SproutingPower extends EWPowerBase implements ICallOnCardsMendedPower {
    public static final String POWER_ID = MakeID(SproutingPower.class.getSimpleName());
    
    public SproutingPower(AbstractCreature owner) {
        super(POWER_ID, "sprouting", PowerType.BUFF, owner);
        setValues(owner, -1);
        preloadString(() -> mkstring(desc[0]));
        updateDescription();
    }

    @Override
    public void onCardMended(AbstractCard cardMended) {
        if (cardMended.canUpgrade()) {
            flash();
            cardMended.upgrade();
        }
    }

    @Override
    public void onVictory() {
        if (!CharWisadel.CARDS_MENDED_THIS_COMBAT.isEmpty()) {
            AbstractCard c = CharWisadel.CARDS_MENDED_THIS_COMBAT.get(CharWisadel.CARDS_MENDED_THIS_COMBAT.size() - 1);
            cpr().masterDeck.group.stream().filter(card -> card.uuid == c.uuid && card.canUpgrade())
                    .forEach(card -> {
                        card.upgrade();
                        AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    });
        }
    }
}