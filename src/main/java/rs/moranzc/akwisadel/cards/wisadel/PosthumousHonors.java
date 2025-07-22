package rs.moranzc.akwisadel.cards.wisadel;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCardsInHandAction;
import rs.moranzc.akwisadel.actions.common.DrawMatchingCardsAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.core.CardMst;
import rs.moranzc.akwisadel.utils.CardUtils;

public class PosthumousHonors extends EWCardBase {
    public static final String ID = MakeID(PosthumousHonors.class.getSimpleName());
    
    public PosthumousHonors() {
        super(ID, "ew/PosthumousHonors.png", 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2);
        exhaust = true;
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractCard c = CardMst.GetRandomCard(ca -> ca.rarity == CardRarity.RARE);
                CardUtils.DamageCard(c);
                if (!c.selfRetain) {
                    CardModifierManager.addModifier(c, new RetainMod());
                }
                addToTop(new MakeTempCardInHandAction(c, 1));
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBaseCost(0);
    }
}
