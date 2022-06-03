package net.jems.somaticsorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class RegenerateSpell extends Spell {

    public RegenerateSpell() {
        super();
        this.name = "Regenerate";
        this.symbol = "DRUULDRRR";
        this.level = 7;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        if (user.isSneaking()) {
            user.heal(10.0f * intensityModifier);
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, (int) (6000 * durationModifier)));
        } else {
            try {
                LivingEntity target = getEntityUnderCrosshair(user, world, 5);
                target.heal(10.0f * intensityModifier);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, (int) (6000 * durationModifier)));
            } catch (NullPointerException e) {
                user.sendSystemMessage(new LiteralText("Spell failed"), Util.NIL_UUID);
            }
        }
    }
}
