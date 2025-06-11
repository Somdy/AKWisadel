package rs.moranzc.akwisadel.localization;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.core.Kazdel;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public final class I18nManager {
    private static final Map<String, LocaleTip> misc_tip_map = new HashMap<>();
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
            Map<String, String> rawTips = rawMap.entrySet().stream()
                    .filter(e -> "tip".equals(e.getKey().split("\\.")[0]))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            JsonProcessor.ProcessTip(rawTips, misc_tip_map);
            rawTips.forEach((k,v) -> rawMap.remove(k));
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
        return card_map.merge(cardID, new EWCardStrings(cardID), (ov,nv) -> ov);
    }
    
    public static LocaleTip GetTip(String key) {
        return misc_tip_map.get(key);
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
                String cardID = EWCardBase.MakeID(rawCardID);
                EWCardStrings cs = cardMap.merge(cardID, new EWCardStrings(cardID), (ov,nv) -> ov);
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
        
        public static void ProcessTip(Map<String, String> rawMap, Map<String, LocaleTip> tipMap) {
            if (rawMap == null || rawMap.isEmpty() || tipMap == null)
                return;
            Set<String> keys = rawMap.keySet();
            for (String k : keys) {
                // [ key, component ]
                String[] args = k.split("\\.");
                String tipKey = args[0];
                String componentKey = args[1].toLowerCase();
                String content = rawMap.get(k);
                LocaleTip t = new LocaleTip(tipKey);
                tipMap.merge(tipKey, t, (ov, nv) -> {
                    ov.components.put(componentKey, content);
                    return ov;
                });
            }
        }
    }
    
    public static final class LocaleTip {
        public final String title;
        final Map<String, String> components = new HashMap<>();

        private LocaleTip(String title) {
            this.title = title;
        }
        
        public String get(String componentKey) {
            return components.get(componentKey);
        }
        
        public String desc() {
            return get("desc");
        }
    }
}
