package rs.moranzc.akcharlib.core;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.StartGameSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akcharlib.examples.EWSkinNovaDD;
import rs.moranzc.akcharlib.examples.EWSkinDefaultSD;
import rs.moranzc.akcharlib.interfaces.IAKSkinnableChar;
import rs.moranzc.akcharlib.ui.AKCharSkinManagedPanel;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.patches.EWEnums;

import java.util.HashMap;
import java.util.Map;

@SpireInitializer
public class CLOSURE implements CustomSavable<Map<String, String>>, PostInitializeSubscriber, StartGameSubscriber {

    public static AKCharSkinManagedPanel SkinPanel;
    public static final Map<String, String> CHAR_SKIN_SAVE_MAP = new HashMap<>();
    
    public static void initialize() {
        CLOSURE c = new CLOSURE();
        BaseMod.subscribe(c);
        BaseMod.addSaveField(CLOSURE.class.getName(), c);
    }
    
    public void receivePostInitialize() {
        SkinPanel = new AKCharSkinManagedPanel();
        SkinPanel.registerCharacterAsSkinnable(EWEnums.CHAR_WISADEL);
        SkinPanel.addSkinToCharacter(EWEnums.CHAR_WISADEL, new EWSkinDefaultSD());
        SkinPanel.addSkinToCharacter(EWEnums.CHAR_WISADEL, new EWSkinNovaDD());
    }

    @Override
    public void receiveStartGame() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p instanceof IAKSkinnableChar) {
            String skinID = CHAR_SKIN_SAVE_MAP.get(p.chosenClass.name());
            Kazdel.logger.info("Reloading skin {} for {}", skinID, p.chosenClass);
            SkinPanel.getSkins(p.chosenClass).stream().filter(s -> s.identifier().equals(skinID))
                    .findFirst()
                    .ifPresent(s -> s.applyOnCharacter(p));
        }
        SkinPanel.reset();
    }

    @Override
    public Map<String, String> onSave() {
        CHAR_SKIN_SAVE_MAP.forEach((k,v) -> Kazdel.logger.info("Saving skin {} for {}", v, k));
        return CHAR_SKIN_SAVE_MAP;
    }

    @Override
    public void onLoad(Map<String, String> map) {
        CHAR_SKIN_SAVE_MAP.clear();
        if (map != null) {
            CHAR_SKIN_SAVE_MAP.putAll(map);
        }
    }
}