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

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class ExampleCharExSkinDD extends AKCharSkinDDBase {
    public static final String SK_PREVIEW_JSON = "AKCAssets/images/examplechars/exusiai/newcovenant/portrait/preview/build_char_1041_angel2_37.json";
    public static final String SK_PREVIEW_ATLAS = "AKCAssets/images/examplechars/exusiai/newcovenant/portrait/preview/build_char_1041_angel2.atlas";
    public static final String SK_ALT = "AKCAssets/images/examplechars/exusiai/newcovenant/battle/char_1041_angel2.atlas";
    public static final String SK_JSON = "AKCAssets/images/examplechars/exusiai/newcovenant/battle/char_1041_angel2.json";
    public static final String SK_DYN_JSON = "AKCAssets/images/examplechars/exusiai/newcovenant/portrait/dyn_illust_char_1041_angel2_37.json";
    public static final String SK_DYN_ATLAS = "AKCAssets/images/examplechars/exusiai/newcovenant/portrait/dyn_illust_char_1041_angel2.atlas";
    
    public ExampleCharExSkinDD() {
        super("ExampleCharDynSkin", 150.0F, 220.0F, Settings.WIDTH * 0.65F, 10.0F);
    }

    @Override
    protected SpineAnimation battleModelAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_ALT, SK_JSON, 1.5F);
    }

    @Override
    protected SpineAnimation previewModelAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_PREVIEW_ATLAS, SK_PREVIEW_JSON, 1.0F);
    }

    @Override
    protected SpineAnimation dynPortraitAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_DYN_ATLAS, SK_DYN_JSON, 1.0F);
    }

    @Override
    protected String shoulder1Url(AbstractPlayer p) {
        return ExampleCharacter.SHOULDER_1;
    }

    @Override
    protected String shoulder2Url(AbstractPlayer p) {
        return ExampleCharacter.SHOULDER_2;
    }

    @Override
    protected String corpseUrl(AbstractPlayer p) {
        return ExampleCharacter.CORPSE;
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
        AnimationState.TrackEntry e = state.setAnimation(0, "Relax", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Relax", "Interact", 0.25F);
        stateData.setMix("Interact", "Relax", 0.25F);
        AnimationState.TrackEntry de = dynState.setAnimation(0, "Idle", true);
        de.setTime(de.getEndTime() * MathUtils.random());
        dynStateData.setMix("Idle", "Interact", 0.15F);
        dynStateData.setMix("Interact", "Idle", 0.15F);
    }

    @Override
    protected void onLeftClickPreviewModel() {
        state.setAnimation(0, "Interact", false);
        state.addAnimation(0, "Relax", true, 0.0F);
    }

    @Override
    protected void onLeftClickDynPortrait() {
        dynState.setAnimation(0, "Interact", false);
        dynState.addAnimation(0, "Idle", true, 0.0F);
    }

    @Override
    public String skinName(AbstractPlayer p) {
        return "礼服";
    }

    @Override
    public String skinDesc(AbstractPlayer p) {
        return "能天使为自己的毕业典礼设计的礼服。";
    }

    @Override
    public String skinCharPortraitBgUrl(AbstractPlayer p) {
        return "AKCAssets/images/examplechars/exusiai/newcovenant/portrait/bg.png";
    }

    @Override
    public String characterName(AbstractPlayer p) {
        return "新约能天使";
    }

    @Override
    public String characterDesc(AbstractPlayer p) {
        return "是的，这也是一段测试文本。";
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
