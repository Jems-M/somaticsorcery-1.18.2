package net.jems.somaticsorcery.spell;

import net.jems.somaticsorcery.effect.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class FeatherFallSpell extends Spell {

    public FeatherFallSpell() {
        super();
        this.name = "Feather Fall";
        this.symbol = "RULUR";
        this.level = 1;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        if (user.isSneaking()) {
            user.addStatusEffect(new StatusEffectInstance(ModEffects.FEATHER_FALL, (int) (3600 * durationModifier)));
        } else {
            try {
                LivingEntity target = getEntityUnderCrosshair(user, world, (int) (3 * rangeModifier));
                target.addStatusEffect(new StatusEffectInstance(ModEffects.FEATHER_FALL, (int) (3600 * durationModifier)));
            } catch (NullPointerException e) {
                user.sendSystemMessage(new LiteralText("No valid target"), Util.NIL_UUID);
            }
        }
    }
}
