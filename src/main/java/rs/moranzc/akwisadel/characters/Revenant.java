package rs.moranzc.akwisadel.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import rs.moranzc.akwisadel.localization.I18nManager;
import rs.moranzc.akwisadel.powers.GiftPower;
import rs.moranzc.akwisadel.powers.LordOfRevenantPower;
import rs.moranzc.akwisadel.powers.OrderOfRevenantPower;

public class Revenant {
    private static Texture REVENANT_TEX = ImageMaster.loadImage("AKWisadelAssets/images/char/Revenant.png");
    public static final int WIDTH = 150;
    public static final int HEIGHT = 125;
    public static final int P_WIDTH = 522;
    public static final int P_HEIGHT = 700;
    private static final float particle_timer_reset = 0.05F;
    
    private Hitbox hb;
    
    protected int baseDamage;
    protected int damage;
    protected boolean isDamageModified;
    protected int baseGiftToApply;
    protected int giftToApply;
    protected boolean isGiftToApplyModified;
    protected int maxHP;
    protected int currHP;
    protected boolean dead;
    protected int moveTimes;
    
    public Revenant() {
        hb = new Hitbox(WIDTH * Settings.scale, HEIGHT * Settings.scale);
        maxHP = currHP = 7;
        if (cpr().hasPower(LordOfRevenantPower.POWER_ID)) {
            increaseMaxHpBy(2);
        }
        baseDamage = damage = 2;
        baseGiftToApply = giftToApply = 1;
        moveTimes = 1;
    }
    
    public void setPosition(float cX, float cY) {
        hb.move(cX, cY);
    }
    
    public void takeMove() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.player instanceof CharWisadel) {
                    AbstractPower p = AbstractDungeon.player.getPower(OrderOfRevenantPower.POWER_ID);
                    if (p != null) {
                        moveTimes += p.amount;
                        p.flash();
                    }
                }
                for (int i = 0; i < moveTimes; i++) {
                    AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
                    if (m != null && !m.isDeadOrEscaped()) {
                        addToTop(new ApplyPowerAction(m, cpr(), new GiftPower(m, giftToApply)));
                        addToTop(new DamageAction(m, new DamageInfo(cpr(), damage, DamageInfo.DamageType.THORNS), AttackEffect.BLUNT_LIGHT));
                        addToTop(new VFXAction(new SmallLaserEffect(hb.cX, hb.cY, m.hb.cX, m.hb.cY) {{ color = Color.SCARLET.cpy(); }}));
                    }
                }
            }
        });
    }
    
    public void increaseMaxHpBy(int times) {
        maxHP *= times;
        currHP *= times;
    }
    
    public int damage(int damage, DamageInfo info, CharWisadel ew) {
        currHP -= damage;
        dead = currHP <= 0;
        if (dead) {
            return Math.abs(currHP);
        }
        return 0;
    }
    
    public void applyPowers() {
        float cDamage = baseDamage;
        float cGift = baseGiftToApply;
        AbstractPower p = cpr().getPower(LordOfRevenantPower.POWER_ID);
        if (p instanceof LordOfRevenantPower) {
            cDamage *= 2.0F;
            cGift *= 2.0F;
        }
        damage = MathUtils.floor(cDamage);
        giftToApply = MathUtils.floor(cGift);
        isDamageModified = damage != baseDamage;
        isGiftToApplyModified = giftToApply != baseGiftToApply;
    }
    
    public void update() {
        if (currHP <= 0 && !dead) {
            dead = true;
            return;
        }
        hb.update();
        applyPowers();
    }
    
    public void render(SpriteBatch sb) {
        hb.render(sb);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(REVENANT_TEX, hb.x, hb.y, WIDTH / 2.0F, HEIGHT / 2.0F, WIDTH, HEIGHT, Settings.scale, Settings.scale, 
                0.0F, 0, 0, WIDTH, HEIGHT, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, currHP + "/" + maxHP, hb.cX, hb.y - 15.0F * Settings.scale);
        FontHelper.renderFont(sb, FontHelper.energyNumFontRed, String.valueOf(damage), hb.cX - hb.width * 0.2F, hb.y + hb.height,
                isDamageModified ? Color.GREEN.cpy() : Color.WHITE.cpy());
        FontHelper.renderFont(sb, FontHelper.energyNumFontRed, "(" + giftToApply + ")", 
                hb.cX + (String.valueOf(damage).length()) * Settings.scale, hb.y + hb.height, 
                isGiftToApplyModified ? Color.GREEN.cpy() : Color.WHITE.cpy());
        if (hb.hovered) {
            TipHelper.renderGenericTip(hb.x + hb.width, hb.cY, I18nManager.MT("Revenant.title"), 
                    I18nManager.MT("Revenant.desc", damage, giftToApply));
        }
    }
    
    protected AbstractPlayer cpr() {
        return AbstractDungeon.player;
    }
}
