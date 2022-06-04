package net.jems.somaticsorcery.spell;


import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public abstract class Spell {
    protected String name;
    protected String symbol;
    protected int level;

    public Spell() {
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getLevel() {
        return level;
    }

    public void cast(LivingEntity user) {
        user.sendSystemMessage(new LiteralText("... a mistake has been made"), Util.NIL_UUID);
    }

    public abstract void cast(World world, LivingEntity user, ItemStack stack,
                              float durationModifier, float intensityModifier, float rangeModifier);


    public static LivingEntity getEntityUnderCrosshair(LivingEntity user, World world, int range) throws NullPointerException {
        Vec3d vec3d = user.getRotationVec(1.0f).normalize();
        Vec3d vec3d2;
        Box searchBox = new Box(user.getX() - range, user.getY() - range, user.getZ() - range,
                user.getX() + range, user.getY() + range, user.getZ() + range);

        List<Entity> entities = world.getOtherEntities(user, searchBox);

        for (Entity i : entities) {
            if (i instanceof LivingEntity) {
                vec3d2 = new Vec3d(i.getX() - user.getX(), i.getEyeY() - user.getEyeY(), i.getZ() - user.getZ());
                double d = vec3d2.length();
                double e = vec3d.dotProduct(vec3d2.normalize());
                if (e > 0.985 - 0.025 / d) {
                    return (LivingEntity) i;
                }

            }
        }
        throw new NullPointerException();
    }

}
