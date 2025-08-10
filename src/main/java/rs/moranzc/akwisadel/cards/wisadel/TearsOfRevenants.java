package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.common.ApplyPowerToEnemiesAction;
import rs.moranzc.akwisadel.actions.unique.TearsOfRevenantsAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.interfaces.cards.IOmegaFormAffectable;
import rs.moranzc.akwisadel.powers.GiftPower;
import rs.moranzc.akwisadel.powers.OmegaFormPower;

public class TearsOfRevenants extends EWCardBase implements IOmegaFormAffectable {
    public static final String ID = MakeID(TearsOfRevenants.class.getSimpleName());
    private boolean affectedByOmega;
    
    public TearsOfRevenants() {
        super(ID, "ew/TearsOfRevenants.png", 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        setDamage(7);
        setMagic(1);
        affectedByOmega = false;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        if (!affectedByOmega) {
            addToBot(new TearsOfRevenantsAction(this, s, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    amount = AbstractDungeon.getMonsters().monsters.stream()
                            .filter(m -> m != null && !m.isDeadOrEscaped())
                            .mapToInt(m -> 1).sum();
                    for (int i = 0; i < amount; i++) {
                        addToTop(new ApplyPowerToEnemiesAction(s, m -> new GiftPower(m, magicNumber)));
                        addToTop(new DamageAllEnemiesAction(s, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                    }
                }
            });
        }
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(3);
    }

    @Override
    public void affectByOmegaFormPreUse(OmegaFormPower omega) {
        affectedByOmega = true;
    }
}