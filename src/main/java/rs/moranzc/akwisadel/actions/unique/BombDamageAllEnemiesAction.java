package rs.moranzc.akwisadel.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.utils.DamageUtils;

public class BombDamageAllEnemiesAction extends AbstractGameAction {
    private final int[] damages;
    private final EWBombCardBase bomb;
    private boolean firstFramed = false;

    public BombDamageAllEnemiesAction(EWBombCardBase bomb, AbstractCreature source, int[] damages, DamageInfo.DamageType type, AttackEffect effect) {
        this.bomb = bomb;
        this.damages = damages;
        this.source = source;
        damageType = type;
        attackEffect = effect;
        duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (damages == null || damages.length < AbstractDungeon.getMonsters().monsters.size()) {
            isDone = true;
            return;
        }
        if (!firstFramed) {
            firstFramed = true;
            AbstractDungeon.getMonsters().monsters.stream().filter(m -> m != null && !m.isDeadOrEscaped())
                    .forEach(m -> AbstractDungeon.effectList.add(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, attackEffect)));
        }
        if (firstFramed) {
            if (source instanceof AbstractPlayer) {
                source.powers.forEach(p -> p.onDamageAllEnemies(damages));
            }
            for (int i = 0; i < AbstractDungeon.getMonsters().monsters.size(); i++) {
                AbstractMonster m = AbstractDungeon.getMonsters().monsters.get(i);
                if (m == null || m.isDeadOrEscaped())
                    continue;
                if (attackEffect == AttackEffect.POISON) {
                    m.tint.color.set(Color.CHARTREUSE.cpy());
                    m.tint.changeColor(Color.WHITE.cpy());
                } else if (attackEffect == AttackEffect.FIRE) {
                    m.tint.color.set(Color.RED.cpy());
                    m.tint.changeColor(Color.WHITE.cpy());
                }
                DamageInfo info = new DamageInfo(source, damages[i], damageType);
                DamageUtils.AddCardFromToDamageInfo(info, bomb);
                m.damage(info);
            }
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}
