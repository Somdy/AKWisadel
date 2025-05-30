package rs.moranzc.akwisadel.core;

import basemod.BaseMod;
import basemod.helpers.CardBorderGlowManager;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.Keyword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rs.moranzc.akwisadel.base.EWBombCardBase;
import rs.moranzc.akwisadel.characters.CharWisadel;
import rs.moranzc.akwisadel.localization.I18nManager;
import rs.moranzc.akwisadel.patches.EWEnums;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class Kazdel implements EditStringsSubscriber, EditKeywordsSubscriber, EditCardsSubscriber, PostInitializeSubscriber {

    public static final Logger logger = LogManager.getLogger(Kazdel.class.getName());
    
    public static final String MOD_ID = "akcew";
    public static final String CARD_PREFIX = MOD_ID.concat(".card");
    
    public static void initialize() {
        Kazdel k = new Kazdel();
        BaseMod.subscribe(k);
        registerColors();
    }

    private static void registerColors() {
        BaseMod.addColor(EWEnums.EW_COLOR, CharWisadel.CARD_TRAIL_COL.cpy(),
                cardui("bg_attack_512"), cardui("bg_skill_512"), cardui("bg_power_512"), 
                cardui("card_orb_512"),
                cardui("bg_attack_1024"), cardui("bg_skill_1024"), cardui("bg_power_1024"),
                cardui("card_orb_1024"), cardui("energy_icon"));
    }

    private static String cardui(String filename) {
        return "AKWisadelAssets/images/cards/ui/" + filename + ".png";
    }

    public static String GameLang() {
        return Settings.language.name().toLowerCase();
    }
    
    public static String MakeID(String s) {
        return MOD_ID.concat(":").concat(s);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String keywordJson = Gdx.files.internal(I18nManager.StringsPath("keywords.json"))
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(keywordJson, Keyword[].class);
        assert keywords != null;
        for (Keyword k : keywords) {
            String keyName = k.NAMES[0];
            if (Settings.language == Settings.GameLanguage.ENG)
                for (int i = 0; i < k.NAMES.length; i++) {
                    k.NAMES[i] = k.NAMES[i].toLowerCase();
                }
            BaseMod.addKeyword(MOD_ID, keyName, k.NAMES, k.DESCRIPTION);
        }
    }

    @Override
    public void receiveEditStrings() {
        I18nManager.Initialize();
    }

    @Override
    public void receiveEditCards() {
        CardMst.Initialize();
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            @Override
            public boolean test(AbstractCard card) {
                return EWBombCardBase.CARDS_TO_DAMAGE_PREVIEW.contains(card);
            }

            @Override
            public Color getColor(AbstractCard card) {
                return Color.ORANGE.cpy();
            }

            @Override
            public String glowID() {
                return "CardsToDamageForBomb";
            }
        });
    }

    @Override
    public void receivePostInitialize() {
        
    }
}
