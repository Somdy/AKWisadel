package rs.moranzc.akcharlib.interfaces;

import basemod.animations.SpineAnimation;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;

import java.util.function.BiConsumer;

public interface IAKSkinnableChar {
    void loadSkin(String skinID, SpineAnimation anim, String shoulder1, String shoulder2, String corpse,
                  BiConsumer<AnimationState, AnimationStateData> postProcessor);
}