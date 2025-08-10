package rs.moranzc.akwisadel.base;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import rs.moranzc.akwisadel.actions.utility.DamageCardsOnBombAction;
import rs.moranzc.akwisadel.cards.BombCardMgr;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.powers.AdvancedExtendedPower;
import rs.moranzc.akwisadel.utils.DamageUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class EWBombCardBase extends EWCardBase {
    public static final Map<AbstractCard, List<AbstractCard>> CARDS_TO_DAMAGE_PREVIEW_MAP = new HashMap<>();
    public static final List<AbstractCard> CARDS_TO_DAMAGE_PREVIEW = new ArrayList<>();
    public static AbstractCard LastCardPreviewed = null;
    
    public int baseSlot;
    public int slot;
    public boolean isSlotModified;
    public boolean upgradedSlot;
    public float slotEfficiency;
    public List<AbstractCard> lastCardsToDamage = new ArrayList<>();
    
    public EWBombCardBase(String id, String img, int cost, int slots, CardType type, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, rarity, target);
        setSlots(slots);
    }

    @Override
    public void update() {
        super.update();
        if (shouldShowBombPreview()) {
            List<AbstractCard> cardsToPreview = CARDS_TO_DAMAGE_PREVIEW_MAP.getOrDefault(this, new ArrayList<>());
            cardsToPreview.clear();
            BombCardMgr.AutoCollectCardsForBomb(cardsToPreview, this, cpr());
            CARDS_TO_DAMAGE_PREVIEW_MAP.put(this, cardsToPreview);
        } else {
            List<AbstractCard> cardsToPreview = CARDS_TO_DAMAGE_PREVIEW_MAP.get(this);
            if (cardsToPreview != null)
                cardsToPreview.clear();
        }
    }
    
    protected boolean shouldShowBombPreview() {
        return AbstractDungeon.player != null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && hb.hovered && cpr().hoveredCard == this && cpr().hand.contains(this) && slot > 0;
    }

    @Override
    public void applyPowers() {
        int real = baseSlot;
        AbstractPower p = cpr().getPower(AdvancedExtendedPower.POWER_ID);
        if (p != null && p.amount > 0)
            baseSlot += p.amount;
        super.applyPowers();
        slot = baseSlot;
        baseSlot = real;
        isSlotModified = slot != baseSlot;
    }

    @Override
    protected final void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageCardsOnBombAction(this, s, t, BombCardMgr.ProvokeBombCard(this, s, t)));
    }
    
    public final void onUseRepeatedByOmega(AbstractPlayer s, AbstractCreature t) {
        onUse(s, t, lastCardsToDamage);
    }

    // Called by BombCardMgr
    public abstract void onUse(AbstractPlayer s, AbstractCreature t, List<AbstractCard> cardsToDamage);
    
    public void increaseDamageForCombat(int amount) {
        baseDamage += amount;
        if (baseDamage < 0)
            baseDamage = 0;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSlot) {
            slot = baseSlot;
            isSlotModified = true;
        }
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
    
    protected DamageInfo createBombInfo(AbstractCreature s, int damage, DamageInfo.DamageType type) {
        DamageInfo info = new DamageInfo(s, damage, type);
        DamageUtils.AddCardFromToDamageInfo(info, this);
        return info;
    }
}
