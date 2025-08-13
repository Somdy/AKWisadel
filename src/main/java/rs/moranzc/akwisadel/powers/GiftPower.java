package rs.moranzc.akwisadel.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWPowerBase;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.utils.DamageUtils;
import rs.moranzc.akwisadel.utils.TexMgr;

public class GiftPower extends EWPowerBase {
    public static final String POWER_ID = MakeID(GiftPower.class.getSimpleName());
    public static final int P_WIDTH = 522;
    public static final int P_HEIGHT = 700;
    private static final float particle_timer_reset = 0.05F;
    private boolean ignited;
    private float particleTimer;
    private int particleIndex;
    
    public GiftPower(AbstractCreature owner, int amount) {
        super(POWER_ID, "gift", PowerType.DEBUFF, owner);
        setValues(null, amount);
        preloadString(() -> mkstring(desc[0], calculateDamageBoost() * 100.0F, calculateExplodingDamage(2.0F)));
        updateDescription();
        ignited = false;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float damageBoost = calculateDamageBoost();
        damage = damage * (1.0F - damageBoost);
        return super.atDamageGive(damage, type);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (DamageUtils.IsDamageInfoFromCard(info, c -> c instanceof EWBombCardBase) && !ignited) {
            ignite(info.owner, 2.0F);
        }
        return super.onAttacked(info, damageAmount);
    }
    
    public void ignite(AbstractCreature s, float times) {
        ignited = true;
        flash();
        addToTop(new DamageAction(owner, new DamageInfo(s, calculateExplodingDamage(times), DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.FIRE));
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }
    
    private int calculateExplodingDamage(float times) {
        return MathUtils.floor(amount * times);
    }

    private float calculateDamageBoost() {
        return amount * 0.03F;
    }
    
    private void updateGiftParticles() {
        if (particleTimer <= 0.0F) {
            particleTimer = particle_timer_reset;
            particleIndex = (particleIndex + 1) % TexMgr.RevenantParticles.length;
        } else {
            particleTimer -= Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        updateGiftParticles();
        super.renderIcons(sb, x, y, c);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(TexMgr.RevenantParticles[particleIndex], owner.hb.x - 180.0F * Settings.scale, owner.hb.cY - 350.0F * Settings.scale,
                P_WIDTH / 2.0F, P_HEIGHT / 2.0F, P_WIDTH, P_HEIGHT, Settings.scale, Settings.scale,
                0.0F, 0, 0, P_WIDTH, P_HEIGHT, false, false);
    }
}