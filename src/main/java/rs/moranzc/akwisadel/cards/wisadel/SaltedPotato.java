package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.actions.common.DamageCardsInHandAction;
import rs.moranzc.akwisadel.actions.common.MendCardsInHandAction;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;

public class SaltedPotato extends EWCardBase {
    public static final String ID = MakeID(SaltedPotato.class.getSimpleName());
    
    public SaltedPotato() {
        super(ID, "ew/SaltedPotato.png", 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setBlock(9);
        setMagic(3);
    }

    @Override
    protected void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new GainBlockAction(s, s, block));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (s instanceof CharWisadel) {
                    CharWisadel ew = ((CharWisadel) s);
                    ew.forEachLiveRevenant(r -> r.modifyMaxHp(magicNumber));
                }
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }
}
