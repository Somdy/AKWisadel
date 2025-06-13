package rs.moranzc.akwisadel.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.relics.CharonsAshes;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;
import rs.moranzc.akwisadel.cards.wisadel.TargetedElimination;
import rs.moranzc.akwisadel.core.Kazdel;
import rs.moranzc.akwisadel.patches.EWEnums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharWisadel extends CustomPlayer {
    public static final String ID = Kazdel.MakeID("CharWisadel");
    private static final CharacterStrings strings = CardCrawlGame.languagePack.getCharacterString(ID);
    public static final String[] NAMES = strings.NAMES;
    public static final String[] TEXT = strings.TEXT;
    public static final String Shoulder_1 = "AKWisadelAssets/images/char/shoulder1.png";
    public static final String Shoulder_2 = "AKWisadelAssets/images/char/shoulder2.png";
    public static final String corpse = "AKWisadelAssets/images/char/corpse.png";
    public static final String SK_ALT = "AKWisadelAssets/images/char/char_1035_wisdel.atlas";
    public static final String SK_JSON = "AKWisadelAssets/images/char/char_1035_wisdel.json";
    public static final int STARTING_HP = 79;
    public static final int MAX_HP = 79;
    public static final int ASCENSION_MAX_HP_LOSS = 9;
    public static final int STARTING_GOLD = 99;
    public static final int ORB_SLOTS = 0;
    public static final int DRAW_PER_TURN = 5;
    public static final Color CARD_TRAIL_COL = new Color(136.0F / 255.0F, 39.0F / 255.0F, 39.0F / 255.0F, 1.0F);
    public static final int MAX_REVENANTS = 3;
    private final List<Revenant> revenants = new ArrayList<>();

    public static final List<AbstractCard> CARDS_DAMAGED_THIS_TURN = new ArrayList<>();
    
    public CharWisadel() {
        super("Wisadel", EWEnums.CHAR_WISADEL, new EnergyOrbGreen(), new SpineAnimation(SK_ALT, SK_JSON, 1.8F));
        initializeClass(null, Shoulder_2, Shoulder_1, corpse, getLoadout(), 0.0F, 0.0F,
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
        amount = amount > revenants.size() ? revenants.size() - amount : amount;
        List<Revenant> summons = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
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
                Vector2 pos = new Vector2(drawX + hb.width / 4.0F + Revenant.WIDTH / 2.0F, 0);
                // Based on a max of 3
                pos.setAngle(90.0F - 120.0F * i);
                r.setPosition(pos.x, pos.y);
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
    public void onVictory() {
        super.onVictory();
        CARDS_DAMAGED_THIS_TURN.clear();
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return new ArrayList<>(Arrays.asList(Strike_Red.ID));
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        return new ArrayList<>(Arrays.asList(CharonsAshes.ID));
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[1], TEXT[1], STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, DRAW_PER_TURN, 
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
        return new TargetedElimination();
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
}
