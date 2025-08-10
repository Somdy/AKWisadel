package rs.moranzc.akcharlib.examples;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.BagOfPreparation;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;
import rs.moranzc.akcharlib.interfaces.IAKSkinnableChar;
import rs.moranzc.akcharlib.patches.PlayerEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.BiConsumer;

public class ExampleCharacter extends CustomPlayer implements IAKSkinnableChar {
    public static final String ID = "ExampleChar";
    public static final String SHOULDER_1 = "AKCAssets/images/examplechars/exusiai/newcovenant/shoulder.png";
    public static final String SHOULDER_2 = "AKCAssets/images/examplechars/exusiai/newcovenant/shoulder2.png";
    public static final String CORPSE = "AKCAssets/images/examplechars/exusiai/newcovenant/corpse.png";
    public static final String SK_ALT = "AKCAssets/images/examplechars/exusiai/newcovenant/battle/char_1041_angel2.atlas";
    public static final String SK_JSON = "AKCAssets/images/examplechars/exusiai/newcovenant/battle/char_1041_angel2.json";
    public static final String BUTTON = "AKCAssets/images/examplechars/exusiai/button.png";
    public static final String PORTRAIT = "AKCAssets/images/examplechars/exusiai/newcovenant/portrait.png";
    
    public ExampleCharacter() {
        super("ExampleChar", PlayerEnum.EXAMPLE_CHAR, new EnergyOrbRed(), null, null);
        initializeClass(null, SHOULDER_2, SHOULDER_1, CORPSE, getLoadout(), 20.0F, -10.0F, 
                300.0F, 370.0F, new EnergyManager(3));
        loadAnimation(SK_ALT, SK_JSON, 1.5F);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        dialogX = drawX + Settings.scale * 1.5F;
        dialogY = drawY + Settings.scale * 200.0F;
    }

    @Override
    public void loadSkin(String skinID, SpineAnimation anim, String shoulder1, String shoulder2, String corpse,
                         BiConsumer<AnimationState, AnimationStateData> postProcessor) {
        shoulderImg.dispose();
        shoulder2Img.dispose();
        corpseImg.dispose();
        shoulderImg = ImageMaster.loadImage(shoulder1);
        shoulder2Img = ImageMaster.loadImage(shoulder2);
        corpseImg = ImageMaster.loadImage(corpse);
        if (atlas != null)
            atlas.dispose();
        loadAnimation(anim.atlasUrl, anim.skeletonUrl, anim.scale);
        if (postProcessor != null) {
            postProcessor.accept(state, stateData);
        } else {
            AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
            e.setTime(e.getEndTime() * MathUtils.random());
        }
    }

    @Override
    public Texture getEnergyImage() {
        return ImageMaster.RED_ORB_FLASH_VFX;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return new ArrayList<>(Arrays.asList(Strike_Red.ID, Strike_Red.ID, Strike_Red.ID, Strike_Red.ID, Strike_Red.ID));
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        return new ArrayList<>(Collections.singletonList(BagOfPreparation.ID));
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("能天使", "这是一段测试文本。 NL 这也是一段测试文本。", 60, 60, 0, 0, 
                5, this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return "能天使";
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCard.CardColor.RED;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.RED.cpy();
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Madness();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.RED.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {

    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "";
    }

    @Override
    public String getLocalizedCharacterName() {
        return "能天使";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new ExampleCharacter();
    }

    @Override
    public String getSpireHeartText() {
        return "";
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.RED.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[0];
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[0];
    }
}
