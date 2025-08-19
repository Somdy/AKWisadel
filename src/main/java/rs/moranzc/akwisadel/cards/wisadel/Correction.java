package rs.moranzc.akwisadel.cards.wisadel;

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
import rs.moranzc.akwisadel.actions.common.MendCardsAction;
import rs.moranzc.akwisadel.actions.common.MendCardsInHandAction;
import rs.moranzc.akwisadel.actions.utility.AutoMoveCardsToHandAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.cards.modifiers.DamagedCardModifier;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnModifierAppliedCard;
import rs.moranzc.akwisadel.interfaces.cards.ICallOnOtherCardsDamagedCard;
import rs.moranzc.akwisadel.powers.FurnacePower;
import rs.moranzc.akwisadel.utils.CardUtils;

import java.util.List;

public class Correction extends EWCardBase implements ICallOnModifierAppliedCard, ICallOnOtherCardsDamagedCard {
    public static final String ID = MakeID(Correction.class.getSimpleName());
    
    public Correction() {
        super(ID, "ew/Correction.png", 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new MendCardsInHandAction(1));
        if (upgraded) {
            addToBot(new DrawCardAction(1));
        }
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
    }

    @Override
    public void onModifierApplied(AbstractCardModifier mod) {
        if (DamagedCardModifier.ID.equals(mod.identifier(this))) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    CardUtils.MendCard(Correction.this);
                    cpr().discardPile.group.parallelStream().filter(c -> CardUtils.IsDamaged(c) && c != Correction.this)
                            .findAny()
                            .ifPresent(CardUtils::MendCard);
                }
            });
        }
    }

    @Override
    public void onOtherCardDamaged(AbstractCard cardDamaged) {
        if (cpr().drawPile.contains(this)) {
            addToTop(new AutoMoveCardsToHandAction(cpr().discardPile, this));
        }
    }
}
