package net.jems.somaticsorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
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
        Vec3d vec3d = user.getRotationVec(1.0f);
        world.createExplosion(user, user.getX() + vec3d.x * 4.0,
                user.getBodyY(0.5) + 0.5, user.getZ() + vec3d.z * 4.0,
                2.5f + intensityModifier, Explosion.DestructionType.NONE);
    }
}
