package rs.moranzc.akwisadel.interfaces.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import rs.moranzc.akwisadel.base.EWBombCardBase;

public interface IPartCard {
    void onAppliedOnBomb(EWBombCardBase card, AbstractPlayer s, AbstractCreature t, float slotEfficiency);
}
