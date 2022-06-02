package net.jems.somaticsorcery.item.custom;

public class WoodenWandItem extends WandItem {
    public WoodenWandItem(Settings settings) {
        super(settings);
        this.durationModifier = 0.25f;
        this.intensityModifier = 0.25f;
        this.maximumSpellLevel = 0;

    }
}
