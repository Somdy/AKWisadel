package rs.moranzc.akwisadel.cards.wisadel.derived;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.base.EWCardBase;
import rs.moranzc.akwisadel.interfaces.cards.IPartCard;

@NoCompendium
public class Ammo extends EWCardBase {
    public static final String ID = MakeID(Ammo.class.getSimpleName());
    
    public Ammo() {
        super(ID, "ew/Ammo.png", 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        setDamage(20);
        isMultiDamage = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new DamageAllEnemiesAction(s, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeDamage(5);
    }
}