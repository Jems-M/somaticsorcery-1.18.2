package net.jems.somaticsorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ThunderwaveSpell extends Spell {

    public ThunderwaveSpell() {
        super();
        this.name = "Thunderwave";
        this.symbol = "LLUR";
        this.level = 1;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        world.createExplosion(user, user.getX(), user.getBodyY(0.0625), user.getZ(), 3.0f + intensityModifier, Explosion.DestructionType.NONE);
    }
}
