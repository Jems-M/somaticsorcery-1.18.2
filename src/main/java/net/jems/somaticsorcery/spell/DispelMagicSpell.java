package net.jems.somaticsorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class DispelMagicSpell extends Spell {

    public DispelMagicSpell() {
        super();
        this.name = "Dispel Magic";
        this.symbol = "ULURLUR";
        this.level = 3;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        if (user.isSneaking()) {
            user.clearStatusEffects();

        } else {
            try {
                LivingEntity target = getEntityUnderCrosshair(user, world, (int) (30 * rangeModifier));
                target.heal(10.0f * intensityModifier);
                target.clearStatusEffects();
            } catch (NullPointerException e) {
                user.sendSystemMessage(new LiteralText("Spell failed"), Util.NIL_UUID);
            }
        }
    }
}
