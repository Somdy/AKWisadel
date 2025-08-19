package rs.moranzc.akwisadel.cards.wisadel;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.IncreaseBombDamageAction;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

public class OldsAndYoungs extends EWCardBase implements IPartCard {
    public static final String ID = MakeID(OldsAndYoungs.class.getSimpleName());
    
    public OldsAndYoungs() {
        super(ID, "ew/OldsAndYoungs.png", 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
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
        upgradeDamage(1);
        upgradeMagicNumber(2);
    }

    @Override
    public void onAppliedOnBomb(EWBombCardBase card, AbstractPlayer s, AbstractCreature t, float slotEfficiency) {
        addToBot(new IncreaseBombDamageAction(card, MathUtils.floor(magicNumber * slotEfficiency) ));
        addToBot(new MakeTempCardInHandAction(getEtherealCopy(), 2));
    }
    
    private OldsAndYoungs getEtherealCopy() {
        OldsAndYoungs o = new OldsAndYoungs();
        o.isEthereal = true;
        o.selfRetain = false;
        return o;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        if (isEthereal) {
            c.rawDescription = strings.customs.get("Extd0");
            c.initializeDescription();
            c.isEthereal = true;
            c.selfRetain = false;
            c.retain = false;
        }
        return c;
    }
}