package rs.moranzc.akcharlib.examples;

import basemod.animations.SpineAnimation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akcharlib.bases.AKCharSkinDDBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.core.Kazdel;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiConsumer;

public class EWSkinNovaDD extends AKCharSkinDDBase {
    public static final String ID = Kazdel.MakeID(EWSkinNovaDD.class.getSimpleName());
    public static final String SK_PREVIEW_JSON = "AKWisadelAssets/images/char/skins/nova/build/build_char_1035_wisdel.json";
    public static final String SK_PREVIEW_ATLAS = "AKWisadelAssets/images/char/skins/nova/build/build_char_1035_wisdel.atlas";
    public static final String SK_ALT = "AKWisadelAssets/images/char/skins/nova/char_1035_wisdel.atlas";
    public static final String SK_JSON = "AKWisadelAssets/images/char/skins/nova/char_1035_wisdel.json";
    public static final String SK_DYN_JSON = "AKWisadelAssets/images/char/skins/nova/dyn/dyn_illust_char_1035_wisdel.json";
    public static final String SK_DYN_ATLAS = "AKWisadelAssets/images/char/skins/nova/dyn/dyn_illust_char_1035_wisdel.atlas";
    
    public EWSkinNovaDD() {
        super(ID, 150.0F, 220.0F, Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.15F);
    }

    @Override
    protected SpineAnimation battleModelAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_ALT, SK_JSON, 1.8F);
    }

    @Override
    protected SpineAnimation previewModelAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_PREVIEW_ATLAS, SK_PREVIEW_JSON, 1.8F);
    }

    @Override
    protected SpineAnimation dynPortraitAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_DYN_ATLAS, SK_DYN_JSON, 1.25F);
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
        dynState.clearTracks();
        AnimationState.TrackEntry de = dynState.setAnimation(0, "Idle", true);
        de.setTime(de.getEndTime() * MathUtils.random());
        dynStateData.setMix("Idle", "Interact", 0.15F);
        dynStateData.setMix("Interact", "Idle", 0.15F);
        dynStateData.setMix("Idle", "Special", 0.15F);
        dynStateData.setMix("Special", "Idle", 0.15F);
    }

    @Override
    protected void onLeftClickPreviewModel() {
        if (MathUtils.randomBoolean()) {
            state.setAnimation(0, "Interact", false);
        } else {
            state.setAnimation(0, "Special", false);
        }
        state.addAnimation(0, "Relax", true, 0.0F);
    }

    @Override
    protected void onLeftClickDynPortrait() {
        if (MathUtils.randomBoolean()) {
            dynState.setAnimation(0, "Interact", false);
        } else {
            dynState.setAnimation(0, "Special", false);
        }
        dynState.addAnimation(0, "Idle", true, 0.0F);
    }

    @Override
    public String skinName(AbstractPlayer p) {
        return "超新星";
    }

    @Override
    public String skinDesc(AbstractPlayer p) {
        return "维什戴尔为了解决失控的源石而踏足未知空间时的装扮。按本人的说法，既然是来清理垃圾的，那就要有相应的仪式感。";
    }

    @Override
    public String skinCharPortraitBgUrl(AbstractPlayer p) {
        return "AKWisadelAssets/images/char/Character_Portrait.png";
    }

    @Override
    public String characterName(AbstractPlayer p) {
        return "维什戴尔：超新星";
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

    @Override
    public int index() {
        return DEFAULT_SKIN_INDEX + 1;
    }
}
