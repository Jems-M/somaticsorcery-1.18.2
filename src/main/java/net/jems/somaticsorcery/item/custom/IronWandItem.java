package net.jems.somaticsorcery.item.custom;

public class IronWandItem extends WandItem {
    public IronWandItem(Settings settings) {
        super(settings);
        this.durationModifier = 0.75f;
        this.intensityModifier = 0.75f;
        this.maximumSpellLevel = 2;

    }
}
