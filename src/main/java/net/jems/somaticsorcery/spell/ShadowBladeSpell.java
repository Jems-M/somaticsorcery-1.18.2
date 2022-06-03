package net.jems.somaticsorcery.spell;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jems.somaticsorcery.item.ModItemGroup;
import net.jems.somaticsorcery.item.ModItems;
import net.jems.somaticsorcery.item.custom.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.world.World;

public class ShadowBladeSpell extends Spell {

    public ShadowBladeSpell() {
        super();
        this.name = "Shadow Blade";
        this.symbol = "LLDUU";
        this.level = 2;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {

        if (user instanceof PlayerEntity) {
            if (user.getActiveItem().getItem() instanceof WoodenWandItem) {
                ((PlayerEntity) user).getInventory().offerOrDrop(ModItems.WOODEN_SHADOW_BLADE.getDefaultStack());
            } else if (user.getActiveItem().getItem() instanceof StoneWandItem) {
                ((PlayerEntity) user).getInventory().offerOrDrop(ModItems.STONE_SHADOW_BLADE.getDefaultStack());
            } else if (user.getActiveItem().getItem() instanceof IronWandItem) {
                ((PlayerEntity) user).getInventory().offerOrDrop(ModItems.IRON_SHADOW_BLADE.getDefaultStack());
            } else if (user.getActiveItem().getItem() instanceof GoldenWandItem) {
                ((PlayerEntity) user).getInventory().offerOrDrop(ModItems.GOLDEN_SHADOW_BLADE.getDefaultStack());
            } else if (user.getActiveItem().getItem() instanceof DiamondWandItem) {
                ((PlayerEntity) user).getInventory().offerOrDrop(ModItems.DIAMOND_SHADOW_BLADE.getDefaultStack());
            } else if (user.getActiveItem().getItem() instanceof ObsidianWandItem) {
                ((PlayerEntity) user).getInventory().offerOrDrop(ModItems.OBSIDIAN_SHADOW_BLADE.getDefaultStack());
            }

        }


    }
}
