package net.jems.somaticsorcery.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jems.somaticsorcery.SomaticSorcery;
import net.jems.somaticsorcery.item.custom.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item HEXED_OBSIDIAN = registerItem("hexed_obsidian",
            new Item(new FabricItemSettings().group(ModItemGroup.SOMATIC_SORCERY)));

    public static final Item SPELL_SCROLL_LV1 = registerItem("spell_scroll_lv1",
            new Item(new FabricItemSettings().group(ModItemGroup.SOMATIC_SORCERY)));

    public static final Item WOODEN_WAND = registerItem("wooden_wand",
            new WoodenWandItem(new FabricItemSettings().group(ModItemGroup.SOMATIC_SORCERY).maxCount(1)));

    public static final Item STONE_WAND = registerItem("stone_wand",
            new StoneWandItem(new FabricItemSettings().group(ModItemGroup.SOMATIC_SORCERY).maxCount(1)));

    public static final Item IRON_WAND = registerItem("iron_wand",
            new IronWandItem(new FabricItemSettings().group(ModItemGroup.SOMATIC_SORCERY).maxCount(1)));

    public static final Item GOLDEN_WAND = registerItem("golden_wand",
            new GoldenWandItem(new FabricItemSettings().group(ModItemGroup.SOMATIC_SORCERY).maxCount(1)));

    public static final Item DIAMOND_WAND = registerItem("diamond_wand",
            new DiamondWandItem(new FabricItemSettings().group(ModItemGroup.SOMATIC_SORCERY).maxCount(1)));

    public static final Item OBSIDIAN_WAND = registerItem("obsidian_wand",
            new ObsidianWandItem(new FabricItemSettings().group(ModItemGroup.SOMATIC_SORCERY).maxCount(1)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(SomaticSorcery.MOD_ID, name), item);
    }
    public static void registerModItems() {
        SomaticSorcery.LOGGER.info("Registering Mod Items for " + SomaticSorcery.MOD_ID);
    }
}
