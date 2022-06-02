package net.jems.somaticsorcery.item.custom;

public class StoneWandItem extends WandItem {
    public StoneWandItem(Settings settings) {
        super(settings);
        this.durationModifier = 0.5f;
        this.intensityModifier = 0.5f;
        this.maximumSpellLevel = 1;

    }
}
