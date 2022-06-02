package net.jems.somaticsorcery.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CallLightningEffect extends StatusEffect {

    private int timer;
    private boolean lightningAvailable;

    public CallLightningEffect(StatusEffectCategory category, int color) {
        super(category, color);
        timer = 0;
        lightningAvailable = true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!lightningAvailable) {
            timer++;
        }
        if (timer >= 120) {
            lightningAvailable = true;
            timer = 0;
        }

        super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
