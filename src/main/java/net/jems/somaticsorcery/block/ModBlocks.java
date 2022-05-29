package net.jems.somaticsorcery.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jems.somaticsorcery.SomaticSorcery;
import net.jems.somaticsorcery.item.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block WALL_OF_FORCE = registerBlock("wall_of_force",
            new Block(FabricBlockSettings.of(Material.GLASS).dropsNothing().nonOpaque()), ModItemGroup.SOMATIC_SORCERY);

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(SomaticSorcery.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(SomaticSorcery.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }
    public static void registerModBlocks() {
        SomaticSorcery.LOGGER.info("Registering ModBlocks for " + SomaticSorcery.MOD_ID);
    }
}
