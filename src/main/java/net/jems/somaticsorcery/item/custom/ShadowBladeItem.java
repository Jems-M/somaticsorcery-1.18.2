package net.jems.somaticsorcery.item.custom;

import net.jems.somaticsorcery.SomaticSorcery;
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

    ItemStack stack;
    LivingEntity attacker;


    public ShadowBladeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        tickSecondsTimer = 0;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        tickSecondsTimer++;
        this.stack = stack;
        if (entity instanceof LivingEntity) {
            this.attacker = (LivingEntity) entity;
        }
        if (tickSecondsTimer % 3600 == 0) {
            stack.decrement(1);
            tickSecondsTimer = 0;
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int lightLevel = attacker.world.getLightingProvider().getLight(attacker.getBlockPos(), attacker.world.getAmbientDarkness());
        int bonusDamage = (16 - lightLevel) / 2;
        target.damage(DamageSource.GENERIC, getAttackDamage() + bonusDamage);
        return true;
    }
}
