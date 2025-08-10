package rs.moranzc.akwisadel.base;

import basemod.abstracts.CustomRelic;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.utils.TexMgr;

public abstract class EWRelicBase extends CustomRelic {
    
    public EWRelicBase(String id, RelicTier tier, LandingSound sfx) {
        super(id, TexMgr.LoadRelicTex(id), TexMgr.LoadRelicOutlineTex(id), tier, sfx);
    }
    
    public static String MakeID(String rawID) {
        return Kazdel.RELIC_PREFIX.concat(":").concat(rawID);
    }
}