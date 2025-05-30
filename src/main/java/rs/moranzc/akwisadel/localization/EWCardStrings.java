package rs.moranzc.akwisadel.localization;

import java.util.HashMap;
import java.util.Map;

public class EWCardStrings {
    public String id;
    public String name;
    public String desc;
    public String upgradeDesc;
    public Map<String, String> customs = new HashMap<>();

    EWCardStrings(String id, String name, String desc, String upgradeDesc, Map<String, String> customs) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.upgradeDesc = upgradeDesc;
        this.customs.putAll(customs);
    }
    
    EWCardStrings(String id) {
        this(id, String.format("Missing Name for %s", id), String.format("Missing desc for %s", id), String.format("Missing upgrade desc for %s", id), new HashMap<>());
    }
}
