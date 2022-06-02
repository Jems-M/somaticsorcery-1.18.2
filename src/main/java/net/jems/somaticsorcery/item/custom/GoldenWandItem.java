package net.jems.somaticsorcery.item.custom;

public class GoldenWandItem extends WandItem {
    public GoldenWandItem(Settings settings) {
        super(settings);
        this.durationModifier = 0.5f;
        this.intensityModifier = 1.25f;
        this.maximumSpellLevel = 3;

    }
}
