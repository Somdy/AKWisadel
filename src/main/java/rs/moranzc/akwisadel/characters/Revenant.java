package rs.moranzc.akwisadel.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;

public class Revenant {
    private static Texture REVENANT_TEX = ImageMaster.loadImage("AKWisadelAssets/images/char/Revenant.png");
    public static final int WIDTH = 150;
    public static final int HEIGHT = 150;
    
    private Hitbox hb;
    
    protected int damage;
    protected int maxHP;
    protected int currHP;
    protected boolean dead;
    
    public Revenant() {
        hb = new Hitbox(WIDTH * Settings.scale, HEIGHT * Settings.scale);
        maxHP = currHP = 7;
        damage = 2;
    }
    
    public void setPosition(float cX, float cY) {
        hb.move(cX, cY);
    }
    
    public void takeMove() {
        
    }
    
    public void damage(DamageInfo info, CharWisadel ew) {
        dead = currHP <= 0;
    }
    
    public void update() {
        if (currHP <= 0 && !dead) {
            dead = true;
            return;
        }
    }
    
    public void render(SpriteBatch sb) {
        hb.render(sb);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(REVENANT_TEX, hb.x, hb.y, WIDTH / 2.0F, HEIGHT / 2.0F, WIDTH, HEIGHT, Settings.scale, Settings.scale, 
                0.0F, 0, 0, WIDTH, HEIGHT, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, currHP + "/" + maxHP, hb.cX, hb.y - 15.0F * Settings.scale);
        TipHelper.renderGenericTip(hb.x + hb.width, hb.cY, "header", "body");
    }
}
