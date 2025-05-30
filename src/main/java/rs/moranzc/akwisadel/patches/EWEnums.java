package rs.moranzc.akwisadel.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;

public class EWEnums {
    @SpireEnum(name = "akcew_cardcolor")
    public static AbstractCard.CardColor EW_COLOR;
    @SpireEnum(name = "akcew_cardcolor")
    public static CardLibrary.LibraryType EW_LIBRARY;
    
    @SpireEnum(name = "akcew_cardtag_part")
    public static AbstractCard.CardTags EW_PART;
    
    @SpireEnum(name = "akcew_wisadel")
    public static AbstractPlayer.PlayerClass CHAR_WISADEL;
}
