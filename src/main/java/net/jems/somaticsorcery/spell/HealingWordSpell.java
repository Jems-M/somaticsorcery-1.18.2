package net.jems.somaticsorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class HealingWordSpell extends Spell {

    public HealingWordSpell() {
        super();
        this.name = "Healing Word";
        this.symbol = "RRUL";
        this.level = 1;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        if (user.isSneaking()) {
            user.heal(6.0f * intensityModifier);
        } else {
            try {
                LivingEntity target = getEntityUnderCrosshair(user, world, (int) (30 * rangeModifier));
                target.heal(6.0f * intensityModifier);
            } catch (NullPointerException e) {
                user.sendSystemMessage(new LiteralText("No valid target (hold shift to target yourself!)"), Util.NIL_UUID);
            }
        }
    }
}
