package rs.moranzc.akwisadel.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akwisadel.powers.GiftPower;

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
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
                if (m != null && !m.isDeadOrEscaped()) {
                    addToTop(new ApplyPowerAction(m, AbstractDungeon.player, new GiftPower(m, 1)));
                    addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, 2, DamageInfo.DamageType.THORNS), 
                            AttackEffect.BLUNT_LIGHT));
                }
            }
        });
    }
    
    public int damage(int damage, DamageInfo info, CharWisadel ew) {
        currHP -= damage;
        dead = currHP <= 0;
        if (dead) {
            return Math.abs(currHP);
        }
        return 0;
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
