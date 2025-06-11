package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import rs.moranzc.akwisadel.base.EWCardBase;

public class TheaterOfDeath extends EWCardBase {
    public static final String ID = MakeID(TheaterOfDeath.class.getSimpleName());
    
    public TheaterOfDeath() {
        super(ID, "ew/TheaterOfDeath.png", 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(15);
        setMagic(2);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        boolean boss = AbstractDungeon.getMonsters().monsters.stream().anyMatch(m -> m != null && m.type == AbstractMonster.EnemyType.BOSS);
        if (t != null && boss) {
            addToBot(new VFXAction(new ClashEffect(t.hb.cX, t.hb.cY), 0.1F));
        }
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), boss ? AbstractGameAction.AttackEffect.NONE
                : AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                        .anyMatch(c -> c.type == CardType.ATTACK && c != TheaterOfDeath.this)) {
                    addToTop(new DrawCardAction(magicNumber));
                }
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(5);
    }

    @Override
    public void applyPowers() {
        int real = baseDamage;
        if (AbstractDungeon.getMonsters().monsters.stream().anyMatch(m -> m != null && m.type == AbstractMonster.EnemyType.BOSS)) {
            baseDamage *= 2;
        }
        super.applyPowers();
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int real = baseDamage;
        if (AbstractDungeon.getMonsters().monsters.stream().anyMatch(m -> m != null && m.type == AbstractMonster.EnemyType.BOSS)) {
            baseDamage *= 2;
        }
        super.calculateCardDamage(mo);
        baseDamage = real;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().anyMatch(c -> c.type == CardType.ATTACK && c != this)) {
            glowColor = GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            glowColor = BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
}