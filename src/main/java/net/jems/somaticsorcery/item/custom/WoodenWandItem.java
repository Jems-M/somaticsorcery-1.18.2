package net.jems.somaticsorcery.item.custom;

public class WoodenWandItem extends WandItem {
    public WoodenWandItem(Settings settings) {
        super(settings);
        this.durationModifier = 0.25f;
        this.intensityModifier = 0.25f;
        this.rangeModifier = 0.5f;
        this.maximumSpellLevel = 0;

    }
}
