package net.jems.somaticsorcery.item.custom;

public class ObsidianWandItem extends WandItem {
    public ObsidianWandItem(Settings settings) {
        super(settings);
        this.durationModifier = 1.5f;
        this.intensityModifier = 1.5f;
        this.rangeModifier = 2.0f;
        this.maximumSpellLevel = 9;

    }
}
