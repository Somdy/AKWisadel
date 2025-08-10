package rs.moranzc.akcharlib.examples;

import basemod.animations.SpineAnimation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.moranzc.akcharlib.bases.AKCharSkinSDBase;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class ExampleCharExSkinSD extends AKCharSkinSDBase {
    public static final String SK_PREVIEW_JSON = "AKCAssets/images/examplechars/exusiai/original/portrait/preview/build_char_103_angel.json";
    public static final String SK_PREVIEW_ATLAS = "AKCAssets/images/examplechars/exusiai/original/portrait/preview/build_char_103_angel.atlas";
    public static final String SK_JSON = "AKCAssets/images/examplechars/exusiai/original/battle/char_103_angel.json";
    public static final String SK_ATLAS = "AKCAssets/images/examplechars/exusiai/original/battle/char_103_angel.atlas";
    
    public ExampleCharExSkinSD() {
        super("ExampleCharBaseSkin", 150.0F, 220.0F);
    }

    @Override
    protected SpineAnimation battleModelAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_ATLAS, SK_JSON, 1.5F);
    }

    @Override
    protected SpineAnimation previewModelAnimation(AbstractPlayer p) {
        return new SpineAnimation(SK_PREVIEW_ATLAS, SK_PREVIEW_JSON, 0.85F);
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
    }

    @Override
    protected void onLeftClickPreviewModel() {
        state.setAnimation(0, "Interact", false);
        state.addAnimation(0, "Relax", true, 0.0F);
    }

    @Override
    public String skinName(AbstractPlayer p) {
        return "企鹅物流";
    }

    @Override
    public String skinDesc(AbstractPlayer p) {
        return "能天使在企鹅物流时的员工装。 NL 当然，她还是会回到企鹅物流的。";
    }

    @Override
    public String skinCharPortraitBgUrl(AbstractPlayer p) {
        return "AKCAssets/images/examplechars/exusiai/original/portrait/portrait.png";
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
