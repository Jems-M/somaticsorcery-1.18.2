package net.jems.somaticsorcery.spell;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class MindSpikeSpell extends Spell {

    public MindSpikeSpell() {
        super();
        this.name = "Mind Spike";
        this.symbol = "LDDD";
        this.level = 2;
    }

    @Override
    public void cast(World world, LivingEntity user, ItemStack stack,
                     float durationModifier, float intensityModifier, float rangeModifier) {
        try {
            LivingEntity target = getEntityUnderCrosshair(user, world, (int) (30 * rangeModifier));
            target.damage(DamageSource.sting(user), 3);
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, (int) (1200 * durationModifier)));
        } catch (NullPointerException e) {
            user.sendSystemMessage(new LiteralText("Spell failed"), Util.NIL_UUID);
        }
    }
}
