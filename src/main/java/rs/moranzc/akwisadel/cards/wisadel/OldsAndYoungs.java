package rs.moranzc.akwisadel.cards.wisadel;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.IncreaseBombDamageAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

@NoCompendium
public class OldsAndYoungs extends EWCardBase implements IPartCard {
    public static final String ID = MakeID(OldsAndYoungs.class.getSimpleName());
    
    public OldsAndYoungs() {
        super(ID, "ew/OldsAndYoungs.png", 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(4);
        setMagic(4);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAction(t, new DamageInfo(s, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }

    @Override
    public void onAppliedOnBomb(EWBombCardBase card, AbstractPlayer s, AbstractCreature t, float slotEfficiency) {
        addToBot(new IncreaseBombDamageAction(card, MathUtils.floor(magicNumber * slotEfficiency) ));
        addToBot(new MakeTempCardInHandAction(this, MathUtils.floor(2 * slotEfficiency)));
    }
}