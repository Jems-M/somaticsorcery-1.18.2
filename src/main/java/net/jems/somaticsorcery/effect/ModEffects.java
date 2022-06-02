package net.jems.somaticsorcery.effect;

import net.jems.somaticsorcery.SomaticSorcery;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {
    public static StatusEffect CALL_LIGHTNING;

    public static StatusEffect registerStatusEffect(String name) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(SomaticSorcery.MOD_ID, name),
                new CallLightningEffect(StatusEffectCategory.BENEFICIAL, 16767488));
    }

    public static void registerEffects() {
        CALL_LIGHTNING = registerStatusEffect("call_lightning");
    }
}
