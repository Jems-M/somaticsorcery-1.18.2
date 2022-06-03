package net.jems.somaticsorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class InvisibilitySpell extends Spell {

    public InvisibilitySpell() {
        super();
        this.name = "Invisibility";
        this.symbol = "LDRRU";
        this.level = 2;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        if (user.isSneaking()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, (int) (3600 * durationModifier)));
        } else {
            try {
                LivingEntity target = getEntityUnderCrosshair(user, world, 5);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, (int) (3600 * durationModifier)));
            } catch (NullPointerException e) {
                user.sendSystemMessage(new LiteralText("Spell failed"), Util.NIL_UUID);
            }
        }
    }
}
