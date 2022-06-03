package net.jems.somaticsorcery.item.custom;

public class DiamondWandItem extends WandItem {
    public DiamondWandItem(Settings settings) {
        super(settings);
        this.durationModifier = 1f;
        this.intensityModifier = 1f;
        this.rangeModifier = 1.5f;
        this.maximumSpellLevel = 4;

    }
}
