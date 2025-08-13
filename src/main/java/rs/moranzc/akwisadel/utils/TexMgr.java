package rs.moranzc.akwisadel.utils;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import rs.moranzc.akwisadel.core.Kazdel;

import java.util.HashMap;
import java.util.Map;

public final class TexMgr {
    private static final Map<String, Texture> cached_loaded_tex = new HashMap<>();
    
    public static final Texture[] RevenantParticles = new Texture[19];
    
    public static void Initialize() {
        
        for (int i = 0; i < 7; i++) {
            RevenantParticles[i] = ImageMaster.loadImage(String.format("AKWisadelAssets/images/char/revenantparticles/%s.png", i));
        }
        for (int i = 7; i < 14; i++) {
            RevenantParticles[i] = ImageMaster.loadImage("AKWisadelAssets/images/char/revenantparticles/7.png");
        }
        for (int i = 14; i < RevenantParticles.length; i++) {
            RevenantParticles[i] = ImageMaster.loadImage(String.format("AKWisadelAssets/images/char/revenantparticles/%s.png", i));
        }
    }
    
    public static Texture LoadRelicTex(String relicID) {
        relicID = relicID.replace(Kazdel.RELIC_PREFIX + ":", "");
        String path = String.format("AKWisadelAssets/images/relics/%s.png", relicID);
        return LoadTex(path);
    }

    public static Texture LoadRelicOutlineTex(String relicID) {
        relicID = relicID.replace(Kazdel.RELIC_PREFIX + ":", "");
        String path = String.format("AKWisadelAssets/images/relics/%s_o.png", relicID);
        return LoadTex(path);
    }
    
    public static Texture LoadTex(String path) {
        if (!cached_loaded_tex.containsKey(path)) {
            Texture t = ImageMaster.loadImage(path);
            cached_loaded_tex.put(path, t);
            return t;
        }
        return cached_loaded_tex.get(path);
    }
}