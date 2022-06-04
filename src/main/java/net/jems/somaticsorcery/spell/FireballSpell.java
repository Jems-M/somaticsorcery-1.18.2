package net.jems.somaticsorcery.spell;

import net.jems.somaticsorcery.entity.FireballProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

public class FireballSpell extends Spell {

    public FireballSpell() {
        super();
        this.name = "Fireball";
        this.symbol = "RURLUR";
        this.level = 3;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {

        Vec3d vec3d = user.getRotationVec(1.0f);
        FireballProjectileEntity fireball = new FireballProjectileEntity(EntityType.FIREBALL, world, user);
        fireball.setPosition(user.getX() + vec3d.x * 4.0, user.getBodyY(0.5) + 0.5, user.getZ() + vec3d.z * 4.0);
        world.spawnEntity(fireball);

    }
}
