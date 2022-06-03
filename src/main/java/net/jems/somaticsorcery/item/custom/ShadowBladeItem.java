package net.jems.somaticsorcery.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.light.LightingProvider;

public class ShadowBladeItem extends SwordItem {

    private int tickSecondsTimer;


    public ShadowBladeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        tickSecondsTimer = 0;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        tickSecondsTimer++;
        if (tickSecondsTimer % 20 == 0) {
            stack.damage(1, (LivingEntity) entity, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            tickSecondsTimer = 0;
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int lightLevel = attacker.world.getLightingProvider().getLight(attacker.getBlockPos(), attacker.world.getAmbientDarkness());
        int bonusDamage = (16 - lightLevel) / 2;
        target.damage(DamageSource.GENERIC, bonusDamage);
        return super.postHit(stack, target, attacker);
    }
}
