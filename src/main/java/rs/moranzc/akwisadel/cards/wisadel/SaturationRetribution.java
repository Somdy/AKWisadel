package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import rs.moranzc.akwisadel.actions.utility.RunnableAction;
import rs.moranzc.akwisadel.actions.utility.XCostActionBuilder;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.interfaces.cards.IOmegaFormAffectable;
import rs.moranzc.akwisadel.powers.OmegaFormPower;

import java.util.List;
import java.util.stream.Collectors;

public class SaturationRetribution extends EWCardBase implements IOmegaFormAffectable {
    public static final String ID = MakeID(SaturationRetribution.class.getSimpleName());
    private boolean changed;
    private boolean affectedByOmega;
    
    public SaturationRetribution() {
        super(ID, "ew/SaturationRetribution.png", -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        setDamage(12);
        setMagic(1);
        changed = false;
        affectedByOmega = false;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new XCostActionBuilder(freeToPlayOnce, energyOnUse)
                .effectTimes(x -> changed ? magicNumber * x : x)
                .act(x -> {
                    if (!changed) {
                        addToTop(new RunnableAction(this::change));
                        List<AbstractMonster> ms = AbstractDungeon.getMonsters().monsters.stream()
                                .filter(m -> m != null && !m.isDeadOrEscaped())
                                .collect(Collectors.toList());
                        while (ms.size() > x) {
                            int index = AbstractDungeon.cardRandomRng.random(ms.size() - 1);
                            ms.remove(index);
                        }
                        ms.forEach(m -> {
                            if (!affectedByOmega) {
                                addToTop(new DamageAction(m, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                            } else {
                                addToTop(new DamageAllEnemiesAction(s, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                            }
                        });
                    } else {
                        for (int i = 0; i < 4; i++) {
                            if (affectedByOmega) {
                                addToTop(new DamageAllEnemiesAction(s, baseDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                                continue;
                            }
                            AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                            if (m != null) {
                                calculateCardDamage(m);
                                addToTop(new DamageAction(m, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                            }
                        }
                    }
                }));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }

    @Override
    public void applyPowers() {
        if (changed) {
            baseDamage = magicNumber * EnergyPanel.totalCount;
        }
        super.applyPowers();
        if (changed) {
            rawDescription = String.format(strings.customs.get("Extd0") + strings.customs.get("Extd1"), damage);
            initializeDescription();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (changed) {
            baseDamage = magicNumber * EnergyPanel.totalCount;
        }
        super.calculateCardDamage(mo);
        if (changed) {
            rawDescription = String.format(strings.customs.get("Extd0") + strings.customs.get("Extd1"), damage);
            initializeDescription();
        }
    }

    private void change() {
        changed = true;
        rawDescription = strings.customs.get("Extd0");
        initializeDescription();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        if (c instanceof SaturationRetribution && changed) {
            ((SaturationRetribution) c).change();
        }
        return c;
    }

    @Override
    public void affectByOmegaFormPreUse(OmegaFormPower omega) {
        affectedByOmega = true;
    }
}