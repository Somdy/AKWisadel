package rs.moranzc.akwisadel.base;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.utility.DamageCardsOnBombAction;
import rs.moranzc.akwisadel.cards.BombCardMgr;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.utils.DamageUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class EWBombCardBase extends EWCardBase {
    
    public static final List<AbstractCard> CARDS_TO_DAMAGE_PREVIEW = new ArrayList<>();
    
    public int baseSlot;
    public int slot;
    public boolean isSlotModified;
    public boolean upgradedSlot;
    public float slotEfficiency;
    
    public EWBombCardBase(String id, String img, int cost, int slots, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        setSlots(slots);
    }

    @Override
    public void update() {
        super.update();
        if (hb.hovered && cpr().hand.contains(this) && slot > 0) {
            CARDS_TO_DAMAGE_PREVIEW.clear();
            CARDS_TO_DAMAGE_PREVIEW.addAll(BombCardMgr.AutoCollectCardsForBomb(this, cpr()));
        }
    }

    @Override
    protected final void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageCardsOnBombAction(this, s, t, BombCardMgr.ProvokeBombCard(this, s, t)));
    }

    // Called by BombCardMgr
    public abstract void onUse(AbstractPlayer s, AbstractCreature t, List<AbstractCard> cardsToDamage);
    
    public void increaseDamageForCombat(int amount) {
        baseDamage += amount;
        if (baseDamage < 0)
            baseDamage = 0;
    }
    
    protected void setSlots(int slots, float efficiency) {
        baseSlot = slots;
        slot = baseSlot;
        slotEfficiency = efficiency;
    }
    
    protected void setSlots(int slots) {
        setSlots(slots, 1.0F);
    }

    protected void upgradeSlot(int delta) {
        baseSlot += delta;
        slot = baseSlot;
        upgradedSlot = true;
    }

    @Override
    protected void addToBot(AbstractGameAction action) {
        try {
            tryAddCardFromToDamageInfoOfAction(action);
        } catch (IllegalAccessException e) {
            Kazdel.logger.info("Failed to add information to action {}", action.getClass().getSimpleName());
        }
        super.addToBot(action);
    }

    @Override
    protected void addToTop(AbstractGameAction action) {
        try {
            tryAddCardFromToDamageInfoOfAction(action);
        } catch (IllegalAccessException e) {
            Kazdel.logger.info("Failed to add information to action {}", action.getClass().getSimpleName());
        }
        super.addToTop(action);
    }

    private void tryAddCardFromToDamageInfoOfAction(AbstractGameAction action) throws IllegalAccessException {
        Field[] fields = action.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getDeclaringClass().isAssignableFrom(DamageInfo.class)) {
                f.setAccessible(true);
                DamageInfo info = (DamageInfo) f.get(action);
                DamageUtils.AddCardFromToDamageInfo(info, this);
                f.set(action, info);
            }
        }
    }
}
