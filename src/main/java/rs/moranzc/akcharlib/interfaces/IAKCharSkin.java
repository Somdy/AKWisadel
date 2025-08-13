package rs.moranzc.akcharlib.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;

import java.util.ArrayList;

public interface IAKCharSkin extends Disposable {
    int DEFAULT_SKIN_INDEX = 0;
    
    String identifier();
    String skinName(AbstractPlayer p);
    String skinDesc(AbstractPlayer p);
    String skinCharPortraitBgUrl(AbstractPlayer p);
    String characterName(AbstractPlayer p);
    String characterDesc(AbstractPlayer p);
    int characterMaxHp(AbstractPlayer p);
    ArrayList<String> characterStartingRelics(AbstractPlayer p);
    void initialize(AbstractPlayer p);
    void applyOnCharacter(AbstractPlayer p);
    boolean unlocked(AbstractPlayer p, Prefs prefs);
    void switchedToThis(AbstractPlayer p);
    void onPlayerUseCard(AbstractPlayer p, AbstractCard card, AbstractMonster m, int energyOnUse);
    void useFastAttackAnimation(AbstractPlayer p);
    void useStaggerAnimation(AbstractPlayer p);
    void update(CharacterOption opt, Hitbox displayArea, float infoX, float deltaTime);
    void render(SpriteBatch sb, CharacterOption opt, Hitbox displayArea);
    
    default int index() {
        return DEFAULT_SKIN_INDEX;
    }
}