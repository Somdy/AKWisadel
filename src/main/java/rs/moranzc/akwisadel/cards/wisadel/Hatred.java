package rs.moranzc.akwisadel.cards.wisadel;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import javassist.CtBehavior;
import rs.moranzc.akwisadel.base.EWCardBase;

public class Hatred extends EWCardBase {
    public static final String ID = MakeID(Hatred.class.getSimpleName());
    public static final int DEFAULT_STRENGTH_AMOUNT = 1;
    
    public Hatred() {
        super(ID, "ew/Hatred.png", 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(DEFAULT_STRENGTH_AMOUNT);
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        addToBot(new ApplyPowerAction(s, s, new StrengthPower(s, magicNumber)));
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        isInnate = true;
    }

    @Override
    public void onLoadedMisc() {
        applyMiscToStr();
    }

    private void applyMiscToStr() {
        baseMagicNumber = DEFAULT_STRENGTH_AMOUNT;
        baseMagicNumber += misc;
        magicNumber = baseMagicNumber;
        applyPowers();
    }
    
    private void onEliteOrBossKilled() {
        misc += 1;
        applyMiscToStr();
    }
    
    @SpirePatch(clz = AbstractMonster.class, method = "die", paramtypez = {boolean.class})
    public static class DiePatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractMonster _inst, boolean b) {
            if (b && _inst.type == AbstractMonster.EnemyType.BOSS || _inst.type == AbstractMonster.EnemyType.ELITE) {
                AbstractPlayer p = AbstractDungeon.player;
                p.masterDeck.group.stream().filter(c -> Hatred.ID.equals(c.cardID))
                        .map(c -> (Hatred) c)
                        .forEach(Hatred::onEliteOrBossKilled);
            }
        }
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher.FieldAccessMatcher matcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "player");
                return LineFinder.findInOrder(ctBehavior, matcher);
            }
        }
    }
}