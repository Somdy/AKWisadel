package rs.moranzc.akcharlib.core;

import basemod.BaseMod;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import rs.moranzc.akcharlib.examples.ExampleCharExSkinDD;
import rs.moranzc.akcharlib.examples.ExampleCharExSkinSD;
import rs.moranzc.akcharlib.examples.ExampleCharacter;
import rs.moranzc.akcharlib.patches.PlayerEnum;
import rs.moranzc.akcharlib.ui.AKCharSkinManagedPanel;

import java.util.HashMap;
import java.util.Map;

@SpireInitializer
public class CLOSURE implements EditCharactersSubscriber, PostInitializeSubscriber {

    public static AKCharSkinManagedPanel SkinPanel;
    
    public static void initialize() {
        CLOSURE c = new CLOSURE();
        BaseMod.subscribe(c);
    }

    public static void main(String[] args) {
        final Map<String, Integer> vamap = new HashMap<>();
        vamap.put("Ironclad", 0);
        vamap.put("Silent", 1);
        System.out.println(merge(vamap, "Ironclad", 1));
        System.out.println(merge(vamap, "Silent", 1));
        System.out.println(merge(vamap, "Defect", 1));
        vamap.forEach((k,v) -> System.out.printf("%s:%d\n", k, v));
    }
    
    private static int compute(Map<String, Integer> map, String key) {
        return map.compute(key, (k,v) -> {
            if (v == null)
                return 0;
            return ++v;
        });
    }
    
    private static int merge(Map<String, Integer> map, String key, int value) {
        return map.merge(key, value, (o,n) -> o + 1);
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new ExampleCharacter(), ExampleCharacter.BUTTON, ExampleCharacter.PORTRAIT, PlayerEnum.EXAMPLE_CHAR);
    }

    @Override
    public void receivePostInitialize() {
        SkinPanel = new AKCharSkinManagedPanel();
        SkinPanel.registerCharacterAsSkinnable(PlayerEnum.EXAMPLE_CHAR);
        SkinPanel.addSkinToCharacter(PlayerEnum.EXAMPLE_CHAR, new ExampleCharExSkinSD());
        SkinPanel.addSkinToCharacter(PlayerEnum.EXAMPLE_CHAR, new ExampleCharExSkinDD());
    }
}