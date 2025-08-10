package rs.moranzc.akwisadel.cards.wisadel;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import rs.moranzc.akwisadel.actions.common.DamageCardsAction;
import rs.moranzc.akwisadel.base.EWCardBase;

import java.util.ArrayList;
import java.util.List;

public class IndiscriminateBombing extends EWCardBase {
    public static final String ID = MakeID(IndiscriminateBombing.class.getSimpleName());
    
    public IndiscriminateBombing() {
        super(ID, "ew/IndiscriminateBombing.png", 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        setDamage(5);
        setMagic(2);
        isMultiDamage = true;
    }

    @Override
    public void onUse(AbstractPlayer s, AbstractCreature t) {
        for (int i = 0; i < magicNumber; i++) {
            addToBot(new DamageAllEnemiesAction(s, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int amount = AbstractDungeon.getMonsters().monsters.stream()
                        .filter(m -> m != null && !m.isDeadOrEscaped())
                        .mapToInt(m -> 1).sum();
                List<AbstractCard> cards = new ArrayList<>(s.hand.group);
                amount = Math.min(cards.size(), amount);
                if (!cards.isEmpty() && amount > 0) {
                    int cardsToRemove = cards.size() - amount;
                    for (int i = 0; i < cardsToRemove; i++) {
                        AbstractCard c = cards.get(AbstractDungeon.cardRandomRng.random(0, cards.size() - 1));
                        cards.remove(c);
                    }
                    addToTop(new DamageCardsAction(s, cards));
                }
            }
        });
    }

    @Override
    protected void onUpgrade() {
        upgradeTexts();
        upgradeMagicNumber(1);
    }
}