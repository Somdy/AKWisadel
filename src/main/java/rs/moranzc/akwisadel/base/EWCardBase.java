package rs.moranzc.akwisadel.base;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.util.Strings;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;
import rs.moranzc.akwisadel.localization.EWCardStrings;
import rs.moranzc.akwisadel.localization.I18nManager;
import rs.moranzc.akwisadel.patches.EWEnums;

import java.util.Arrays;

public abstract class EWCardBase extends CustomCard {
    
    protected final EWCardStrings strings;

    public int baseExtraMagic;
    public int extraMagic;
    public boolean isExtraMagicModified;
    public boolean upgradedExtraMagic;
    
    public EWCardBase(String id, String imgName, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, "undefined", GetPortrait(imgName, type), cost, "undefined", type, EWEnums.EW_COLOR, rarity, target);
        strings = I18nManager.GetCardStrings(id);
        initDefaultStrings();
        if (this instanceof IPartCard) {
            selfRetain = true;
        }
    }
    
    protected void initDefaultStrings() {
        name = strings.name;
        initializeTitle();
        updateDescription(strings.desc);
    }

    public void updateDescription(String newDescription) {
        rawDescription = newDescription;
        initializeDescription();
    }

    @Override
    public final void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCreature target = m;
        onUse(p, target);
    }
    
    protected abstract void onUse(AbstractPlayer s, AbstractCreature t);

    protected void setExtraMagic(int extraMagics) {
        baseExtraMagic = extraMagics;
        extraMagic = baseExtraMagic;
    }

    protected void upgradeExtraMagic(int delta) {
        baseExtraMagic += delta;
        extraMagic = baseExtraMagic;
        upgradedExtraMagic = true;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedExtraMagic) {
            extraMagic = baseExtraMagic;
            isExtraMagicModified = true;
        }
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) {
            onUpgrade();
        }
    }
    
    protected abstract void onUpgrade();

    protected void upgradeTexts() {
        upgradeName();
        if (strings.upgradeDesc != null && !Strings.isBlank(strings.upgradeDesc)) {
            updateDescription(strings.upgradeDesc);
        }
    }
    
    protected void setDamage(int base) {
        baseDamage = damage = base;
    }

    protected void setBlock(int base) {
        baseBlock = block = base;
    }

    protected void setMagic(int base) {
        baseMagicNumber = magicNumber = base;
    }
    
    protected void addTags(CardTags... tagsToAdd) {
        tags.addAll(Arrays.asList(tagsToAdd));
    }
    
    protected AbstractPlayer cpr() {
        return AbstractDungeon.player;
    }

    public static String MakeID(String rawID) {
        return Kazdel.CARD_PREFIX.concat(":").concat(rawID);
    }
    
    public static String GetPortrait(String filename, CardType type) {
        String targetUrl = String.format("AKWisadelAssets/images/cards/portraits/%s", filename);
        if (Gdx.files.internal(targetUrl).exists())
            return targetUrl;
        return String.format("AKWisadelAssets/images/cards/portraits/wildcards/%s/wildcard_%s.png", Kazdel.GameLang(), type.name().toLowerCase());
    }
}