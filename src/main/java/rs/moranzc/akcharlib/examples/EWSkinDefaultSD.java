package rs.moranzc.akcharlib.examples;

import basemod.animations.SpineAnimation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akcharlib.bases.AKCharSkinSDBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.core.Kazdel;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class EWSkinDefaultSD extends AKCharSkinSDBase {
    public static final String ID = Kazdel.MakeID(EWSkinDefaultSD.class.getSimpleName());
    public static final String SK_PREVIEW_JSON = "AKWisadelAssets/images/char/skins/default/build/build_char_1035_wisdel.json";
    public static final String SK_PREVIEW_ATLAS = "AKWisadelAssets/images/char/skins/default/build/build_char_1035_wisdel.atlas";
    public static final String SK_JSON = "AKWisadelAssets/images/char/char_1035_wisdel.json";
    public static final String SK_ATLAS = "AKWisadelAssets/images/char/char_1035_wisdel.atlas";
    
    public EWSkinDefaultSD() {
        super(ID, 150.0F, 220.0F);
    }

    @Override
    protected SpineAnimation battleModelAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_ATLAS, SK_JSON, 1.8F);
    }

    @Override
    protected SpineAnimation previewModelAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_PREVIEW_ATLAS, SK_PREVIEW_JSON, 1.8F);
    }

    @Override
    protected String shoulder1Url(AbstractPlayer p) {
        return CharWisadel.SHOULDER_1;
    }

    @Override
    protected String shoulder2Url(AbstractPlayer p) {
        return CharWisadel.SHOULDER_2;
    }

    @Override
    protected String corpseUrl(AbstractPlayer p) {
        return CharWisadel.CORPSE;
    }

    @Override
    protected BiConsumer<AnimationState, AnimationStateData> postProcess() {
        return (state, stateData) -> {
            AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
            e.setTime(e.getEndTime() * MathUtils.random());
        };
    }

    @Override
    protected void onApplyToCharacter(AbstractPlayer p) { }

    @Override
    protected void onSwitchedToThis(AbstractPlayer p) {
        state.clearTracks();
        AnimationState.TrackEntry e = state.setAnimation(0, "Relax", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Relax", "Interact", 0.25F);
        stateData.setMix("Interact", "Relax", 0.25F);
    }

    @Override
    protected void onLeftClickPreviewModel() {
        state.setAnimation(0, "Interact", false);
        state.addAnimation(0, "Relax", true, 0.0F);
    }

    @Override
    public String skinName(AbstractPlayer p) {
        return "何以为我";
    }

    @Override
    public String skinDesc(AbstractPlayer p) {
        return "干员默认服装。";
    }

    @Override
    public String skinCharPortraitBgUrl(AbstractPlayer p) {
        return "AKWisadelAssets/images/char/Character_Portrait.png";
    }

    @Override
    public String characterName(AbstractPlayer p) {
        return p.getLoadout().name;
    }

    @Override
    public String characterDesc(AbstractPlayer p) {
        return p.getLoadout().flavorText;
    }

    @Override
    public int characterMaxHp(AbstractPlayer p) {
        return p.maxHealth;
    }

    @Override
    public ArrayList<String> characterStartingRelics(AbstractPlayer p) {
        return null;
    }

    @Override
    public void initialize(AbstractPlayer p) {
        
    }

    @Override
    public void onPlayerUseCard(AbstractPlayer p, AbstractCard card, AbstractMonster m, int energyOnUse) {

    }

    @Override
    public void useFastAttackAnimation(AbstractPlayer p) {

    }

    @Override
    public void useStaggerAnimation(AbstractPlayer p) {

    }
}
