package rs.moranzc.akwisadel.powers;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CtBehavior;
import rs.moranzc.akwisadel.base.EWPowerBase;

import java.util.ArrayList;
import java.util.Arrays;

public class GiftOfRevenantPower extends EWPowerBase {
    public static final String ID = MakeID(GiftOfRevenantPower.class.getSimpleName());
    protected final ArrayList<RewardItem> rewardItems = new ArrayList<>();
    
    public GiftOfRevenantPower(AbstractPlayer owner, int amount) {
        super(ID, "gift_of_revenant", AbstractPower.PowerType.BUFF, owner);
        setValues(owner, amount);
        preloadString(() -> mkstring(desc[0], this.amount));
        updateDescription();
    }
    
    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (stackAmount > 0 && owner.isPlayer) {
            for (int i = 0; i < stackAmount; i++) {
                rewardItems.add(new RewardItem() {{
                    cards = new ArrayList<>(Arrays.asList(new Madness()));
                }});
            }
        }
    }
    
    @Override
    public void onInitialApplication() {
        if (owner.isPlayer) {
            rewardItems.clear();
            rewardItems.add(new RewardItem() {{
                cards = new ArrayList<>(Arrays.asList(new Madness()));
            }});
        }
    }
    
//    @SpirePatch2(clz = AbstractDungeon.class, method = "getRewardCards")
//    public static class InfWisdomPatch {
//        @SpirePostfixPatch
//        public static void Postfix(ArrayList<AbstractCard> __result) {
//            AbstractPlayer p = LMSK.Player();
//            if (p.hasPower(InfWisdomPower.ID)) {
//                AbstractPower power = p.getPower(InfWisdomPower.ID);
//                if (power != null) {
//                    int extras = power.amount;
//                    AbstractCard another = null;
//                    for (int i = 0; i < extras; i++) {
//                        AbstractCard.CardRarity rarity = AbstractDungeon.rollRarity();
//                        another = getAnyOtherColorCard(rarity, (another != null ? another.cardID : null));
//                        another.upgrade();
//                        __result.add(another);
//                    }
//                }
//            }
//        }
//    }
    
    @SpirePatch2(clz = CombatRewardScreen.class, method = "setupItemReward")
    public static class CombatRewardScreenPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(CombatRewardScreen __instance) {
            AbstractPlayer p = AbstractDungeon.player;
            if (p.hasPower(GiftOfRevenantPower.ID)) {
                AbstractPower power = p.getPower(GiftOfRevenantPower.ID);
                if (power != null) {
                    ArrayList<RewardItem> rewards = __instance.rewards;
                    rewards.addAll(((GiftOfRevenantPower) power).rewardItems);
                }
            }
        }
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.MethodCallMatcher matcher = new Matcher.MethodCallMatcher(ProceedButton.class, "show");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}