package net.jems.somaticsorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class ConjureBarrageSpell extends Spell {

    public ConjureBarrageSpell() {
        super();
        this.name = "Conjure Barrage";
        this.symbol = "LURRD";
        this.level = 3;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        final int spread = 6;
        int numArrows = (int) ((8 * intensityModifier) + Math.ceil(Math.random() * (4 *  intensityModifier)));

        for (int i = 0; i < numArrows; i++) {
            ArrowItem arrowItem = (ArrowItem) (stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);
            PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, stack, user);
            persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
            if (Math.ceil(Math.random() * 6) == 6) {
                persistentProjectileEntity.setCritical(true);
            }
            if (Math.ceil(Math.random() * 6) == 6) {
                persistentProjectileEntity.setOnFireFor(100);
            }
            persistentProjectileEntity.setVelocity(user,
                    user.getPitch() - spread + (float) ((Math.random() * 2 * spread)),
                    user.getYaw() - (2 * spread) + (float) ((Math.random() * 4 * spread)),
                    0, (4 * (float) (Math.random())) + intensityModifier, 1);
            world.spawnEntity(persistentProjectileEntity);
        }
    }
}
