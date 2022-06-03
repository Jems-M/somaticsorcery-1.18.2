package net.jems.somaticsorcery.spell;

import net.jems.somaticsorcery.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CallLightningSpell extends Spell {

    public CallLightningSpell() {
        super();
        this.name = "Call Lightning";
        this.symbol = "UULRR";
        this.level = 3;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        user.addStatusEffect(new StatusEffectInstance(ModEffects.CALL_LIGHTNING, (int) (3600 * durationModifier)));
    }
}
