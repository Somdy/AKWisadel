package rs.moranzc.akwisadel.cards.wisadel;

import basemod.AutoAdd;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import rs.moranzc.akwisadel.actions.common.DrawMatchingCardsAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnModifierAppliedCard;
import rs.moranzc.akwisadel.powers.FurnacePower;
import rs.moranzc.akwisadel.utils.CardUtils;

@AutoAdd.Ignore
public class Sprouting extends EWCardBase implements ICallOnModifierAppliedCard {
    public static final String ID = MakeID(Sprouting.class.getSimpleName());
    
    public Sprouting() {
        super(ID, "ew/Sprouting.png", -2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        setMagic(2);
        selfRetain = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainEnergyAction(magicNumber));
        addToBot(new DrawCardAction(extraMagic));
        addToBot(new ApplyPowerAction(s, s, new FurnacePower(s)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        isInnate = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void onModifierApplied(AbstractCardModifier mod) {
        if (DamagedCardModifier.ID.equals(mod.identifier(this))) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    CardUtils.MendCard(Sprouting.this);
                    CardGroup cg = CardUtils.GetAllUnexhaustedCardsInCombat(c -> c.canUpgrade() && c != Sprouting.this);
                    if (cg.isEmpty()) return;
                    for (int i = 0; i < magicNumber && !cg.isEmpty(); i++) {
                        AbstractCard c;
                        if (cg.size() == 1) {
                            c = cg.getTopCard();
                        } else {
                            c = cg.getRandomCard(AbstractDungeon.cardRandomRng);
                        }
                        c.upgrade();
                        AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH + AbstractCard.IMG_WIDTH * i,
                                Settings.HEIGHT / 2.0F));
                        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH + AbstractCard.IMG_WIDTH * i,
                                Settings.HEIGHT / 2.0F));
                        cg.removeCard(c);
                    }
                }
            });
        }
    }
}
