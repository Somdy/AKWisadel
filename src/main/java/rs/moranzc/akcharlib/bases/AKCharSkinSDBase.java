package rs.moranzc.akcharlib.bases;

import basemod.ReflectionHacks;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rs.moranzc.akcharlib.core.CLOSURE;
import rs.moranzc.akcharlib.interfaces.IAKCharSkin;
import rs.moranzc.akcharlib.interfaces.IAKSkinnableChar;
import rs.moranzc.akwisadel.core.Kazdel;

import java.util.function.BiConsumer;

/**
 * Provides static character portrait and spine animation in a game
 */
public abstract class AKCharSkinSDBase implements IAKCharSkin {
    protected static final Logger logger = LogManager.getLogger(AKCharSkinSDBase.class.getName());
    protected static final float SKIN_NAME_PADDING = 20.0F * Settings.scale;
    private final String id;
    
    private String cachedSkeletonUrl;
    private Skeleton skeleton;
    private String cachedAtlasUrl;
    private TextureAtlas atlas;
    protected AnimationState state;
    protected AnimationStateData stateData;
    protected Color renderColor = Color.WHITE.cpy();
    protected boolean flipX;
    protected boolean flipY;
    protected Hitbox previewModelHb;
    @Deprecated
    protected Hitbox battleModelHb;
    
    public AKCharSkinSDBase(String id, float previewHbWidth, float previewHbHeight) {
        this.id = id;
        previewModelHb = new Hitbox(previewHbWidth * Settings.scale, previewHbHeight * Settings.scale);
    }
    
    @Override
    public String identifier() {
        return id;
    }

    @Override
    public final void applyOnCharacter(AbstractPlayer p) {
        try {
            if (p instanceof IAKSkinnableChar) {
                IAKSkinnableChar c = ((IAKSkinnableChar) p);
                c.loadSkin(identifier(), battleModelAnimation(p), shoulder1Url(p), shoulder2Url(p), corpseUrl(p), postProcess());
            } else {
                ReflectionHacks.getCachedMethod(p.getClass(), "loadSkin", String.class, SpineAnimation.class, String.class, 
                                String.class, String.class, BiConsumer.class)
                        .invoke(p, identifier(), battleModelAnimation(p), shoulder1Url(p), shoulder2Url(p), corpseUrl(p));
            }
        } catch (Exception e) {
            logger.info("Failed to apply skin {} on character {}: {}", identifier(), p.id, e.getMessage());
        } finally {
            onApplyToCharacter(p);
        }
    }
    
    protected abstract SpineAnimation battleModelAnimation(AbstractPlayer p);
    protected abstract String shoulder1Url(AbstractPlayer p);
    protected abstract String shoulder2Url(AbstractPlayer p);
    protected abstract String corpseUrl(AbstractPlayer p);
    protected abstract BiConsumer<AnimationState, AnimationStateData> postProcess();
    protected abstract void onApplyToCharacter(AbstractPlayer p);

    protected SpineAnimation previewModelAnimation(AbstractPlayer p) {
        return battleModelAnimation(p);
    }

    @Override
    public boolean unlocked(AbstractPlayer player, Prefs prefs) {
        return true;
    }

    @Override
    public void switchedToThis(AbstractPlayer p) {
        SpineAnimation animation = previewModelAnimation(p);
        String atlasUrl = animation.atlasUrl;
        String skeletonUrl = animation.skeletonUrl;
        if (!atlasUrl.equals(cachedAtlasUrl) || !skeletonUrl.equals(cachedSkeletonUrl)) {
            cachedAtlasUrl = atlasUrl;
            cachedSkeletonUrl = skeletonUrl;
            if (atlas != null) 
                atlas.dispose();
            atlas = new TextureAtlas(Gdx.files.internal(atlasUrl));
        }
        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(Settings.renderScale / animation.scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(skeletonUrl));
        skeleton = new Skeleton(skeletonData);
        stateData = new AnimationStateData(skeleton.getData());
        state = new AnimationState(stateData);
        onSwitchedToThis(p);
        CLOSURE.CHAR_SKIN_SAVE_MAP.put(p.chosenClass.name(), identifier());
    }
    
    protected abstract void onSwitchedToThis(AbstractPlayer p);

    @Override
    public void update(CharacterOption opt, Hitbox displayArea, float infoX, float deltaTime) {
        if (state != null) {
            state.update(deltaTime);
        }
        previewModelHb.move(displayArea.cX, displayArea.cY);
        previewModelHb.update();
        if (InputHelper.justClickedLeft && previewModelHb.hovered) {
            previewModelHb.clickStarted = true;
        }
        if (previewModelHb.clicked) {
            previewModelHb.clicked = false;
            onLeftClickPreviewModel();
        }
    }
    
    protected void onLeftClickPreviewModel() { }

    @Override
    public void render(SpriteBatch sb, CharacterOption opt, Hitbox displayArea) {
        state.apply(skeleton);
        skeleton.updateWorldTransform();
        skeleton.setPosition(displayArea.cX, displayArea.cY - previewModelHb.height / 2.0F);
        skeleton.setColor(renderColor);
        skeleton.setFlip(flipX, flipY);
        sb.end();
        CardCrawlGame.psb.begin();
        AbstractCreature.sr.draw(CardCrawlGame.psb, skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
        previewModelHb.render(sb);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, skinName(opt.c), displayArea.cX, 
                displayArea.cY - previewModelHb.height / 2.0F - SKIN_NAME_PADDING, Color.GOLDENROD.cpy());
    }

    @Override
    public void dispose() {
        if (atlas != null) 
            atlas.dispose();
        cachedSkeletonUrl = null;
        cachedAtlasUrl = null;
    }
}