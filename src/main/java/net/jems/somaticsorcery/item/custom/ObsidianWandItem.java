package net.jems.somaticsorcery.item.custom;

public class ObsidianWandItem extends WandItem {
    public ObsidianWandItem(Settings settings) {
        super(settings);
        this.durationModifier = 1.25f;
        this.intensityModifier = 1.25f;
        this.maximumSpellLevel = 9;

    }
}
