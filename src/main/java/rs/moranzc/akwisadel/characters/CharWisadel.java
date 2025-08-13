package rs.moranzc.akwisadel.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import rs.moranzc.akcharlib.examples.EWSkinNovaDD;
import rs.moranzc.akcharlib.interfaces.IAKSkinnableChar;
import rs.moranzc.akwisadel.cards.wisadel.*;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.patches.EWEnums;
import rs.moranzc.akwisadel.powers.ExplosiveDawnPower;
import rs.moranzc.akwisadel.relics.StarterRelicEW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static rs.moranzc.akwisadel.core.Kazdel.*;

public class CharWisadel extends CustomPlayer implements IAKSkinnableChar {
    public static final String ID = Kazdel.MakeID("CharWisadel");
    private static final CharacterStrings strings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static final String[] NAMES = strings.NAMES;
    public static final String[] TEXT = strings.TEXT;
    public static final String SHOULDER_1 = "AKWisadelAssets/images/char/shoulder2.png";
    public static final String SHOULDER_2 = "AKWisadelAssets/images/char/shoulder1.png";
    public static final String CORPSE = "AKWisadelAssets/images/char/corpse.png";
    public static final String SK_ALT = "AKWisadelAssets/images/char/char_1035_wisdel.atlas";
    public static final String SK_JSON = "AKWisadelAssets/images/char/char_1035_wisdel.json";
    private static final String[] ORB_TEXTURES = new String[]{
            "AKWisadelAssets/images/topPanel/energyOrb/layer5.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer4.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer3.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer2.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer1.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer6.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer5d.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer4d.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer3d.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer2d.png",
            "AKWisadelAssets/images/topPanel/energyOrb/layer1d.png"
    };
    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};
    public static final int STARTING_HP = 79;
    public static final int MAX_HP = 79;
    public static final int ASCENSION_MAX_HP_LOSS = 9;
    public static final int STARTING_GOLD = 99;
    public static final int ORB_SLOTS = 0;
    public static final int DRAW_PER_TURN = 5;
    public static final int MAX_REVENANTS = 3;
    private final List<Revenant> revenants = new ArrayList<>();
    private String currSkinID = EWSkinNovaDD.ID;

    public static final List<AbstractCard> CARDS_DAMAGED_THIS_TURN = new ArrayList<>();
    
    public CharWisadel() {
        super("Wisadel", EWEnums.CHAR_WISADEL, ORB_TEXTURES, "AKWisadelAssets/images/topPanel/energyOrb/vfx.png", LAYER_SPEED, 
                new SpineAnimation(SK_ALT, SK_JSON, 1.8F));
        initializeClass(null, SHOULDER_2, SHOULDER_1, CORPSE, getLoadout(), 0.0F, 0.0F,
                200.0F, 220.0F, new EnergyManager(3));
        stateData.setMix("Idle", "Die", 0.1F);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.2F);
        dialogX = drawX + 1.5F * Settings.scale;
        dialogY = drawY + 150.0F * Settings.scale;
    }
    
    public List<Revenant> summonRevenants(int amount) {
        if (amount <= 0 || revenants.size() >= MAX_REVENANTS)
            return new ArrayList<>();
        List<Revenant> summons = new ArrayList<>();
        for (int i = 0; i < amount && revenants.size() < MAX_REVENANTS; i++) {
            Revenant r = new Revenant();
            revenants.add(r);
            summons.add(r);
        }
        return summons;
    }
    
    public int blockDamageByRevenant(DamageInfo info, int damage) {
        if (revenants.isEmpty())
            return damage;
        Revenant r = revenants.get(0);
        return r.damage(damage, info, this);
    }
    
    public int countRevenants() {
        return revenants.stream().filter(r -> !r.dead).mapToInt(r -> 1).sum();
    }
    
    public int countRevenantsTotalHP() {
        return revenants.stream().filter(r -> !r.dead).mapToInt(r -> r.currHP).sum();
    }
    
    public void modifyRevenantMoveTimes(int delta) {
        revenants.forEach(r -> {
            r.moveTimes += delta;
            if (r.moveTimes < 0)
                r.moveTimes = 0;
        });
    }
    
    public void letRevenantsTakeMove() {
        revenants.forEach(Revenant::takeMove);
    }
    
    public void forEachLiveRevenant(Consumer<Revenant> action) {
        revenants.stream().filter(r -> !r.dead).forEach(action);
    }

    @Override
    public void renderPlayerImage(SpriteBatch sb) {
        super.renderPlayerImage(sb);
        for (Revenant r : revenants) {
            if (r != null)
                r.render(sb);
        }
    }

    @Override
    public void update() {
        super.update();
        revenants.removeIf(r -> r.dead);
        for (int i = 0; i < revenants.size(); i++) {
            Revenant r = revenants.get(i);
            if (r != null) {
                float initialOffset = 25.0F;
                float maxAngle = 270.0F;
                float angle = i * (maxAngle / 3) + initialOffset;
                float dist = 240.0F * Settings.scale;
                float cX = dist * MathUtils.cosDeg(angle) + hb.cX + hb.width * 0.25F;
                float cY = dist * MathUtils.sinDeg(angle) + hb.cY;
                r.setPosition(cX, cY);
                r.update();
            }
        }
    }

    @Override
    public void applyStartOfTurnOrbs() {
        super.applyStartOfTurnOrbs();
        CARDS_DAMAGED_THIS_TURN.clear();
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        letRevenantsTakeMove();
    }

    @Override
    public void onVictory() {
        super.onVictory();
        CARDS_DAMAGED_THIS_TURN.clear();
        revenants.clear();
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if (c.type == AbstractCard.CardType.ATTACK) {
            if (hasPower(ExplosiveDawnPower.POWER_ID)) {
                state.setAnimation(0, "Skill_3_Loop", false);
                state.addAnimation(0, "Skill_3_Idle", true, 0.0F);
            } else if (c instanceof LocalizedLiquidation) {
                state.setAnimation(0, "Skill_1", false);
            } else {
                int randomAttack = MathUtils.random(0, 2);
                if (randomAttack == 0) {
                    state.setAnimation(0, "Attack_A", false);
                } else if (randomAttack == 1) {
                    state.setAnimation(0, "Attack_B", false);
                } else {
                    state.setAnimation(0, "Attack_C", false);
                }
                state.addAnimation(0, "Idle", true, 0.0F);
            }
        }
        super.useCard(c, monster, energyOnUse);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return new ArrayList<>(Arrays.asList(Strike_EW.ID, Strike_EW.ID, Strike_EW.ID, Strike_EW.ID, 
                Defend_EW.ID, Defend_EW.ID, Defend_EW.ID, Defend_EW.ID,
                HomemadeBomb.ID, SmokeGrenade.ID, FirstRevenant.ID));
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        return new ArrayList<>(Arrays.asList(StarterRelicEW.ID));
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[1], STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, DRAW_PER_TURN, 
                this, getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return EWEnums.EW_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return CARD_TRAIL_COL.cpy();
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new LocalizedLiquidation();
    }

    @Override
    public Color getCardTrailColor() {
        return CARD_TRAIL_COL.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
//        CardCrawlGame.sound.play("BOOM");
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new CharWisadel();
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[2];
    }

    @Override
    public Color getSlashAttackColor() {
        return CARD_TRAIL_COL.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, 
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, 
                AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL };
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    @Override
    public void loadSkin(String skinID, SpineAnimation anim, String shoulder1, String shoulder2, String corpse, BiConsumer<AnimationState, AnimationStateData> postProcessor) {
        currSkinID = skinID;
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
}
