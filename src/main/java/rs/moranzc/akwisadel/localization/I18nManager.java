package rs.moranzc.akwisadel.localization;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import rs.moranzc.akwisadel.core.Kazdel;

import java.nio.charset.StandardCharsets;
import java.util.*;

@SuppressWarnings("unused")
public final class I18nManager {
    private static final Map<String, String> misc_text_map = new HashMap<>();
    private static final Map<String, EWCardStrings> card_map = new HashMap<>();
    
    private static final Gson gson = new Gson();
    
    public static void Initialize() {
        String json = loadJson(StringsPath("cards.json"));
        {
            // load card strings
            JsonProcessor.ProcessCardJson(json, card_map);
        }
        {
            // load misc strings
            json = loadJson(StringsPath("misc.json"));
            Map<String, String> rawMap = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
            misc_text_map.putAll(rawMap);
        }
    }

    private static String loadJson(String path) {
        if (!Gdx.files.internal(path).exists())
            Kazdel.logger.info("No valid string exists: {}", path);
        return Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    public static String StringsPath(String filename) {
        return String.format("AKWisadelAssets/i18n/%s/%s", Kazdel.GameLang(), filename);
    }
    
    public static EWCardStrings GetCardStrings(String cardID) {
        return card_map.merge(cardID, new EWCardStrings(cardID, true), (ov, nv) -> ov);
    }
    
    public static String MT(String key, Object... args) {
        String text = misc_text_map.getOrDefault(key, String.format("Missing Text for [%s]", key));
        try {
            text = String.format(text, args);
        } catch (Exception e) {
            Kazdel.logger.error("Unable to format string \"{}\": {}", text, e.getMessage());
        }
        return text;
    }
    
    public static String MT(String key) {
        return misc_text_map.getOrDefault(key, String.format("Missing Text for [%s]", key));
    }
    
    public static final class JsonProcessor {
        
        public static void ProcessCardJson(String json, Map<String, EWCardStrings> cardMap) {
            Map<String, String> rawMap = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
            Set<String> entries = rawMap.keySet();
            entries.removeIf(e -> e.startsWith("!"));
            if (entries.isEmpty())
                return;
            for (String e : entries) {
                // [ key, field ]
                String[] c = e.split("\\.");
                if (c.length < 2) {
                    Kazdel.logger.info("Invalid card json format: Incomplete entry {}", e);
                    continue;
                }
                String content = rawMap.get(e);
                String rawCardID = c[0];
                if (rawCardID.charAt(0) == '$') {
                    rawCardID = rawCardID.substring(1);
                    //TODO: remap the id
                }
                String cardID = Kazdel.CARD_PREFIX.concat(":").concat(rawCardID);
                EWCardStrings cs = cardMap.merge(cardID, new EWCardStrings(cardID, false), (ov, nv) -> ov);
                String fieldKey = c[1];
                switch (fieldKey) {
                    case "Name":
                        cs.name = content;
                        break;
                    case "Desc":
                        cs.desc = content;
                        break;
                    case "UpgradeDesc":
                        cs.upgradeDesc = content;
                        break;
                    default:
                        cs.customs.put(fieldKey, content);
                }
            }
        }
    }
}
