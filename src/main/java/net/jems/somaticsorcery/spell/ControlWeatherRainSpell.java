package net.jems.somaticsorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class ControlWeatherRainSpell extends Spell {

    public ControlWeatherRainSpell() {
        super();
        this.name = "Control Weather (Rain)";
        this.symbol = "UUULLDULLDD";
        this.level = 8;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        try {
            ServerWorld serverWorld = user.getServer().getOverworld();
            serverWorld.setWeather(0, (int) (36000 * durationModifier), true, false);
        } catch (NullPointerException e) {
            //user.sendSystemMessage(new LiteralText("hehe null pointer exception "), Util.NIL_UUID);
        }
    }
}
