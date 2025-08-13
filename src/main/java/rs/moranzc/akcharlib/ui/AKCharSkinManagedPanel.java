package rs.moranzc.akcharlib.ui;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rs.moranzc.akcharlib.interfaces.IAKCharSkin;
import rs.moranzc.akcharlib.interfaces.IAKSkinnableChar;
import rs.moranzc.akwisadel.core.Kazdel;

import java.util.*;

@SuppressWarnings({"unused"})
public final class AKCharSkinManagedPanel {
    public static final String SKIN_INDEX_PREFIX = "LAST_AKSKIN_INDEX#";
    private static final Set<String> registered_skinnable_characters = new HashSet<>();
    private static final Logger logger = LogManager.getLogger(AKCharSkinManagedPanel.class.getName());
    private final Map<String, List<IAKCharSkin>> allCharSkinsMap = new HashMap<>();
    private final List<IAKCharSkin> availableSkins = new ArrayList<>();
    private IAKCharSkin currSkin;
    private final float skinPanelWidth;
    private final Hitbox nextArrow;
    private final Hitbox prevArrow;
    private final Hitbox displayArea;
    private final Map<String, Integer> currSkinIndex = new HashMap<>();
    private CharacterOption o;
    private String skinCharHp;
    private String skinCharName;
    private String skinCharDesc;
    private ArrayList<String> skinStartingRelics;
    private Texture skinCharPortraitBg;
    private String skinCharPortraitBgUrl;
    private String skinName;
    private String skinDesc;
    private boolean skinsUpdated = false;
    
    public AKCharSkinManagedPanel() {
        float skinNameWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "PlaceholderSkinName", 99999.0F, 0.0F);
        skinPanelWidth = skinNameWidth + 80.0F * Settings.scale;
        this.nextArrow = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
        this.prevArrow = new Hitbox(80.0F * Settings.scale, 80.0F * Settings.scale);
        this.displayArea = new Hitbox(skinNameWidth + 40.0F * Settings.scale, 80.0F * Settings.scale);
    }
    
    public boolean registerCharacterAsSkinnable(AbstractPlayer.PlayerClass playerClz) {
        return registered_skinnable_characters.add(playerClz.name());
    }
    
    public boolean addSkinToCharacter(AbstractPlayer.PlayerClass playerClz, IAKCharSkin skin) {
        if (registered_skinnable_characters.contains(playerClz.name())) {
            List<IAKCharSkin> tmp = allCharSkinsMap.compute(playerClz.name(), (k,v) -> v == null ? new ArrayList<>() : v);
            if (tmp.stream().noneMatch(s -> s.identifier().equals(skin.identifier()))) {
                tmp.add(skin);
                skinsUpdated = false;
                return true;
            }
        }
        return false;
    }
    
    public boolean hasSkins(CharacterOption o) {
        skinsUpdated = false;
        updateAvailableSkins(o);
        return !availableSkins.isEmpty();
    }
    
    public void initialize(CharacterSelectScreen css) {
        for (CharacterOption o : css.options) {
            if (o.c instanceof IAKSkinnableChar || registered_skinnable_characters.contains(getCharMapKey(o))) {
                Prefs prefs = o.c.getPrefs();
                int lastSavedSkinIndex = prefs.getInteger(getCharSavedSkinIndexKey(o), 0);
                currSkinIndex.put(getCharMapKey(o), lastSavedSkinIndex);
            }
        }
    }
    
    public List<IAKCharSkin> getSkins(AbstractPlayer.PlayerClass playerClass) {
        return allCharSkinsMap.get(playerClass.name());
    }
    
    public void reset() {
        skinsUpdated = false;
        if (skinCharPortraitBg != null) {
            skinCharPortraitBg.dispose();
            skinCharPortraitBg = null;
        }
        skinCharPortraitBgUrl = null;
    }
    
    public void update(CharacterOption o, float infoX) {
        this.o = o;
        updateAvailableSkins(o);
        updateSkinInfo(o);
        updateSkinUI(o, infoX);
        if (currSkin == null)
            switchSkin(o);
        currSkin.update(o, displayArea, infoX, Gdx.graphics.getDeltaTime());
    }

    public void render(SpriteBatch sb) {
        if (availableSkins.isEmpty())
            return;
        prevArrow.render(sb);
        displayArea.render(sb);
        nextArrow.render(sb);
        if (prevArrow.hovered) {
            sb.setColor(Color.WHITE.cpy());
        } else {
            sb.setColor(Color.LIGHT_GRAY.cpy());
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, prevArrow.cX - 24.0F, prevArrow.cY - 24.0F, 24.0F, 24.0F, 
                48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F, 
                0, 0, 48, 48, false, false);
        if (nextArrow.hovered) {
            sb.setColor(Color.WHITE.cpy());
        } else {
            sb.setColor(Color.LIGHT_GRAY.cpy());
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, nextArrow.cX - 24.0F, nextArrow.cY - 24.0F, 24.0F, 24.0F,
                48.0F, 48.0F, Settings.scale * 1.5F, Settings.scale * 1.5F, 0.0F,
                0, 0, 48, 48, false, false);
        if (currSkin == null)
            switchSkin(o);
        currSkin.render(sb, o, displayArea);
    }
    
    private void updateSkinUI(CharacterOption o, float infoX) {
        prevArrow.move(infoX - 55.0F * Settings.scale + prevArrow.width / 2.0F, 160.0F * Settings.scale + prevArrow.height / 2.0F + Settings.HEIGHT / 2.0F);
        displayArea.move(prevArrow.cX + skinPanelWidth / 2.0F, prevArrow.cY);
        nextArrow.move(displayArea.cX + skinPanelWidth / 2.0F, prevArrow.cY);
        prevArrow.update();
        displayArea.update();
        nextArrow.update();
        if (displayArea.hovered) {
            TipHelper.renderGenericTip(nextArrow.cX + 50.0F * Settings.scale, nextArrow.cY, skinName, skinDesc);
        }
        if (InputHelper.justClickedLeft && prevArrow.hovered) {
            prevArrow.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        }
        if (InputHelper.justClickedLeft && nextArrow.hovered) {
            nextArrow.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        }
        if (prevArrow.clicked) {
            prevArrow.clicked = false;
            currSkinIndex.merge(getCharMapKey(o), 0, (ov, nv) -> (ov - 1 + availableSkins.size()) % availableSkins.size());
            if (availableSkins.size() > 1)
                switchSkin(o);
        }
        if (nextArrow.clicked) {
            nextArrow.clicked = false;
            currSkinIndex.merge(getCharMapKey(o), 0, (ov, nv) -> (ov + 1) % availableSkins.size());
            if (availableSkins.size() > 1)
                switchSkin(o);
        }
    }
    
    private void updateSkinInfo(CharacterOption o) {
        ReflectionHacks.setPrivate(o, CharacterOption.class, "hp", skinCharHp);
        ReflectionHacks.setPrivate(o, CharacterOption.class, "name", skinCharName);
        ReflectionHacks.setPrivate(o, CharacterOption.class, "flavorText", skinCharDesc);
        ReflectionHacks.setPrivate(o, CharacterOption.class, "infoY", Settings.HEIGHT / 2.0F + 250.0F * Settings.scale);
        CharSelectInfo charInfo = ReflectionHacks.getPrivate(o, CharacterOption.class, "charInfo");
        if (skinStartingRelics != null) {
            charInfo.relics = skinStartingRelics;
        }
        Texture currPortraitImg = ReflectionHacks.getPrivate(o, CharacterOption.class, "portraitImg");
        if (currPortraitImg != skinCharPortraitBg && skinCharPortraitBg != null) {
            ReflectionHacks.setPrivate(o, CharacterOption.class, "portraitImg", skinCharPortraitBg);
            ReflectionHacks.setPrivate(o, CharacterOption.class, "portraitUrl", null);
        }
        CardCrawlGame.mainMenuScreen.charSelectScreen.bgCharImg = skinCharPortraitBg;
    }
    
    private void switchSkin(CharacterOption o) {
        int currCharSkinIndex = currSkinIndex.compute(getCharMapKey(o), (k,v) -> v == null ? 0 : Math.min(v, availableSkins.size() - 1));
        currSkin = availableSkins.get(currCharSkinIndex);
        Kazdel.logger.info("Current skin for char {}: {}", o.c.chosenClass, currSkin.identifier());
        skinCharHp = currSkin.characterMaxHp(o.c) + "/" + currSkin.characterMaxHp(o.c);
        skinCharName = currSkin.characterName(o.c);
        skinCharDesc = currSkin.characterDesc(o.c);
        skinStartingRelics = currSkin.characterStartingRelics(o.c);
        String oldPortraitUrl = skinCharPortraitBgUrl;
        skinCharPortraitBgUrl = currSkin.skinCharPortraitBgUrl(o.c);
        if (skinCharPortraitBgUrl != null && !skinCharPortraitBgUrl.equals(oldPortraitUrl)) {
            if (skinCharPortraitBg != null)
                skinCharPortraitBg.dispose();
            skinCharPortraitBg = ImageMaster.loadImage(skinCharPortraitBgUrl);
        } else if (skinCharPortraitBgUrl == null) {
            //TODO: Load default bg
        }
        skinName = currSkin.skinName(o.c);
        skinDesc = currSkin.skinDesc(o.c);
        // save skin prefs
        Prefs prefs = o.c.getPrefs();
        prefs.putInteger(getCharSavedSkinIndexKey(o), currCharSkinIndex);
        currSkin.switchedToThis(o.c);
    }
    
    private String getCharSavedSkinIndexKey(CharacterOption o) {
        return SKIN_INDEX_PREFIX.concat(o.c.chosenClass.name());
    }
    
    private String getCharMapKey(CharacterOption o) {
        return o.c.chosenClass.name();
    }
    
    private void updateAvailableSkins(CharacterOption o) {
        if (skinsUpdated) 
            return;
        availableSkins.clear();
        List<IAKCharSkin> tmp = allCharSkinsMap.compute(getCharMapKey(o), (k,v) -> v == null ? new ArrayList<>() : v);
        for (IAKCharSkin s : tmp) {
            if (!s.unlocked(o.c, o.c.getPrefs()))
                continue;
            availableSkins.add(s);
            availableSkins.sort((o1, o2) -> o2.index() - o1.index());
        }
        skinsUpdated = true;
    }
}