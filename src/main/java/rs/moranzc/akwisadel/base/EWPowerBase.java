package rs.moranzc.akwisadel.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.localization.I18nManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class EWPowerBase extends AbstractPower {
    private static final Map<String, TextureAtlas.AtlasRegion> img48_map = new HashMap<>();
    private static final Map<String, TextureAtlas.AtlasRegion> img128_map = new HashMap<>();
    private static int idIndex = 0;
    private Supplier<String> descBuilder;
    protected PowerStrings powerStrings;
    protected String[] desc;
    public AbstractCreature source;
    public boolean stackable;
    
    public EWPowerBase(String baseID, String imgName, PowerType type, AbstractCreature owner) {
        ID = baseID;
        this.type = type;
        this.owner = owner;
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(baseID);
        name = powerStrings.NAME;
        desc = powerStrings.DESCRIPTIONS;
        loadImg(imgName);
    }

    protected void distinctID(String distinction) {
        ID = ID.concat(distinction);
    }

    protected void distinctID() {
        distinctID(Integer.toString(idIndex));
        idIndex++;
    }

    protected void setValues(AbstractCreature owner, AbstractCreature source, int amount) {
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.stackable = amount > 0 || canGoNegative;
    }

    protected void setValues(AbstractCreature source, int amount) {
        setValues(owner, source, amount);
    }

    protected void preloadString(Supplier<String> getString) {
        this.descBuilder = getString;
    }

    protected String mkstring(String str, Object... args) {
        return String.format(str, args);
    }

    protected String you(boolean lowercase) {
        String u = I18nManager.MT("PronYouPlayer");
        if (lowercase)
            u = u.toLowerCase();
        return u;
    }

    protected String getOwnerPron(boolean lowercase) {
        if (owner == AbstractDungeon.player)
            return you(lowercase);
        return owner.name;
    }

    @Override
    public void updateDescription() {
        String desc ="Missing description";
        if (descBuilder != null)
            desc = descBuilder.get();
        description = desc;
    }
    
    protected void loadImg(String filename) {
        try {
            loadRegion(filename);
        } catch (Exception e) {
            loadImgFromSeparateFiles(filename);
        }
    }
    
    private void loadImgFromSeparateFiles(String filename) {
        TextureAtlas.AtlasRegion tex = img48_map.get(filename);
        if (tex == null) {
            String filepath = String.format("AKWisadelAssets/images/powers/%s_48.png", filename);
            if (!Gdx.files.internal(filepath).exists()) {
                Kazdel.logger.info("Missing true image of 48 for power {}", ID);
                filepath = "AKWisadelAssets/images/powers/wildcard_48.png";
            }
            tex = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(filepath), 0, 0, 48, 48);
            img48_map.put(filename, tex);
        }
        region48 = tex;
        tex = img128_map.get(filename);
        if (tex == null) {
            String filepath = String.format("AKWisadelAssets/images/powers/%s_128.png", filename);
            if (!Gdx.files.internal(filepath).exists()) {
                Kazdel.logger.info("Missing true image of 128 for power {}", ID);
                filepath = "AKWisadelAssets/images/powers/wildcard_128.png";
            }
            tex = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(filepath), 0, 0, 128, 128);
            img128_map.put(filename, tex);
        }
        region128 = tex;
    }

    public int getMaxAmount() {
        return 999;
    }

    @Override
    public void stackPower(int stackAmount) {
        if (this.stackable) {
            this.fontScale = 8.0F;
            this.amount += stackAmount;
            if (this.amount > this.getMaxAmount()) {
                this.amount = this.getMaxAmount();
            }
        } else {
            Kazdel.logger.info("{} does not stack", name);
        }
    }

    protected AbstractPlayer cpr() {
        return AbstractDungeon.player;
    }

    public static String MakeID(String rawID) {
        return Kazdel.MakeID(rawID);
    }
}