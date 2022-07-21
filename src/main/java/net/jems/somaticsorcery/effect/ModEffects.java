package net.jems.somaticsorcery.effect;

import net.jems.somaticsorcery.SomaticSorcery;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static StatusEffect CALL_LIGHTNING;

    public static StatusEffect FEATHER_FALL;

    public static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(SomaticSorcery.MOD_ID, name), effect);
    }

    public static void registerEffects() {
        CALL_LIGHTNING = registerStatusEffect("call_lightning",
                new CallLightningEffect(StatusEffectCategory.BENEFICIAL, 16767488));
        FEATHER_FALL = registerStatusEffect("feather_fall",
                new FlyEffect(StatusEffectCategory.BENEFICIAL, 14606046));
    }
}
