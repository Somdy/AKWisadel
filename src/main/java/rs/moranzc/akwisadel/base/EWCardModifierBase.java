package rs.moranzc.akwisadel.base;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import rs.moranzc.akwisadel.core.Kazdel;

import java.util.ArrayList;
import java.util.List;

public abstract class EWCardModifierBase extends AbstractCardModifier {
    
    public final String id;
    protected boolean unique;
    protected boolean inherent;
    protected List<TooltipInfo> tips = new ArrayList<>();

    protected EWCardModifierBase(String id, boolean unique, boolean inherent) {
        this.id = id;
        this.unique = unique;
        this.inherent = inherent;
    }

    protected EWCardModifierBase(String id, boolean unique, boolean inherent, String tipTile, String tipDesc) {
        this(id, unique, inherent);
        addTip(tipTile, tipDesc);
    }
    
    protected void addTip(String title, String desc) {
        if (title == null || title.isEmpty() || desc == null || desc.isEmpty())
            return;
        if (tips.stream().noneMatch(t -> t.title.equals(title))) {
            tips.add(new TooltipInfo(title, desc));
        } else {
            tips.stream().filter(t -> t.title.equals(title)).forEach(t -> t.description = desc);
        }
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        if (tips != null && !tips.isEmpty())
            return tips;
        return super.additionalTooltips(card);
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return inherent;
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, id) || !unique;
    }

    @Override
    public String identifier(AbstractCard card) {
        return id;
    }
    
    public static String MakeID(String rawID) {
        return Kazdel.CARD_PREFIX.concat("mod:").concat(rawID);
    }
}