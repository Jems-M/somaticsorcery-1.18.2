package net.jems.somaticsorcery.item.custom;

import net.jems.somaticsorcery.spell.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.Util;
import net.minecraft.world.World;


import java.util.ArrayList;


public abstract class WandItem extends Item {

    private String currentSymbol = "";

    private boolean correctSymbolDrawn = false;

    private final ArrayList<Spell> allSpells = new ArrayList<>();

    private boolean currentlyCasting = false;
    private boolean yawChanged = false;
    private boolean pitchChanged = false;
    private float startYaw;
    private float startPitch;

    protected float durationModifier;
    protected float intensityModifier;
    protected float rangeModifier;
    protected int maximumSpellLevel;

    public WandItem(Settings settings) {
        super(settings);
        populateSpellList();
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }


    public float getDurationModifier() {
        return durationModifier;
    }


    public float getIntensityModifier() {
        return intensityModifier;
    }


    public float getRangeModifier() {
        return rangeModifier;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        currentSymbol = "";
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        //user.sendSystemMessage(new LiteralText("usageTick() moment"), Util.NIL_UUID);
        if (!currentlyCasting) {
            startYaw = user.getYaw();
            startPitch = user.getPitch();
            currentSymbol = "";
        }
        currentlyCasting = true;
        if (!world.isClient()) {
            if (!correctSymbolDrawn) {
                if (yawChanged || pitchChanged) {
                    startYaw = user.getYaw();
                    startPitch = user.getPitch();
                    yawChanged = false;
                    pitchChanged = false;
                }
                if (user.getYaw() - startYaw > 7) {
                    currentSymbol = currentSymbol + 'R';
                    yawChanged = true;
                    user.sendSystemMessage(new LiteralText(currentSymbol), Util.NIL_UUID);
                }

                if (user.getYaw() - startYaw < -7) {
                    currentSymbol = currentSymbol + 'L';
                    yawChanged = true;
                    user.sendSystemMessage(new LiteralText(currentSymbol), Util.NIL_UUID);
                }

                if (user.getPitch() - startPitch > 7) {
                    currentSymbol = currentSymbol + 'D';
                    pitchChanged = true;
                    user.sendSystemMessage(new LiteralText(currentSymbol), Util.NIL_UUID);
                }

                if (user.getPitch() - startPitch < -7) {
                    currentSymbol = currentSymbol + 'U';
                    pitchChanged = true;
                    user.sendSystemMessage(new LiteralText(currentSymbol), Util.NIL_UUID);
                }

                if (pitchChanged || yawChanged) {
                    for (Spell i : allSpells) {
                        if (i.getSymbol().equals(currentSymbol)) {
                            user.sendSystemMessage(new LiteralText("Casting: " + i.getName()), Util.NIL_UUID);
                            correctSymbolDrawn = true;
                        }
                    }
                }
            }
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }


    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!world.isClient()) {
            currentlyCasting = false;
            for (Spell i : allSpells) {
                if (currentSymbol.equals(i.getSymbol())) {
                    if (i.getLevel() > this.maximumSpellLevel) {
                        user.sendSystemMessage(new LiteralText("My wand isn't powerful enough to cast this spell..."), Util.NIL_UUID);
                    } else {
                        i.cast();
                    }
                }
            }
            currentSymbol = "";
            correctSymbolDrawn = false;
            super.onStoppedUsing(stack, world, user, remainingUseTicks);
        }
    }


    private void populateSpellList() {
        allSpells.add(new ControlWeatherClearSpell());
        allSpells.add(new ControlWeatherRainSpell());
        allSpells.add(new ControlWeatherThunderSpell());
        allSpells.add(new ConjureBarrageSpell());
        allSpells.add(new ThunderwaveSpell());
        allSpells.add(new HealingWordSpell());
        allSpells.add(new RegenerateSpell());
        allSpells.add(new CallLightningSpell());
        allSpells.add(new InvisibilitySpell());
        allSpells.add(new MindSpikeSpell());
        allSpells.add(new DispelMagicSpell());
    }
}
