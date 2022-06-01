package net.jems.somaticsorcery.item.custom;

import net.jems.somaticsorcery.SomaticSorcery;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.Util;
import net.minecraft.world.World;


import java.util.ArrayList;
import java.util.HashMap;


public class WandItem extends Item {

    private String currentSymbol = "";

    private boolean correctSymbolDrawn = false;

    private final ArrayList<Spell> allSpells = new ArrayList<>();

    boolean currentlyCasting = false;
    boolean yawChanged = false;
    boolean pitchChanged = false;
    float startYaw;
    float startPitch;

    String symbolControlWeatherClear = "UUURRDURRDD";
    String symbolControlWeatherRain = "UUULLDULLDD";
    String symbolControlWeatherThunder = "DDDLLUDLLUU";
    String symbolConjureBarrage = "LURRD";


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

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        //user.sendSystemMessage(new LiteralText("use() moment"), Util.NIL_UUID);
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
                        if (i.checkSymbol(currentSymbol)) {
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
            //user.sendSystemMessage(new LiteralText("onStoppedUsing() moment"), Util.NIL_UUID);
            currentlyCasting = false;
            if (currentSymbol.equals(symbolControlWeatherClear)) {
                try {
                    ServerWorld serverWorld = user.getServer().getOverworld();
                    serverWorld.setWeather(36000, 0, false, false);
                } catch (NullPointerException e) {
                    user.sendSystemMessage(new LiteralText("hehe null pointer exception "), Util.NIL_UUID);
                }
            } else if (currentSymbol.equals(symbolControlWeatherRain)) {
                try {
                    ServerWorld serverWorld = user.getServer().getOverworld();
                    serverWorld.setWeather(0, 36000, true, false);
                } catch (NullPointerException e) {
                    user.sendSystemMessage(new LiteralText("hehe null pointer exception "), Util.NIL_UUID);
                }
            } else if (currentSymbol.equals(symbolControlWeatherThunder)) {
                try {
                    ServerWorld serverWorld = user.getServer().getOverworld();
                    serverWorld.setWeather(0, 36000, true, true);
                } catch (NullPointerException e) {
                    //user.sendSystemMessage(new LiteralText("hehe null pointer exception"), Util.NIL_UUID);
                    //literally don't do anything. yes, neal would be sad. but neal doesn't have to know :)
                }
            } else if (currentSymbol.equals(symbolConjureBarrage)) {
                final int spread = 3;
                int numArrows = 4 + (int) Math.ceil(Math.random() * 4);

                for (int i = 0; i < numArrows; i++) {
                    ArrowItem arrowItem = (ArrowItem) (stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);
                    PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, stack, user);
                    persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
                    if (Math.ceil(Math.random() * 4) == 4) {
                        persistentProjectileEntity.setCritical(true);
                    }
                    persistentProjectileEntity.setVelocity(user,
                            user.getPitch() - spread + (float) ((Math.random() * 2 * spread)),
                            user.getYaw() - (2 * spread) + (float) ((Math.random() * 4 * spread)),
                            0, 4 * (float) (Math.random()), 1);
                    world.spawnEntity(persistentProjectileEntity);
                }
            }
            currentSymbol = "";
            correctSymbolDrawn = false;
            super.onStoppedUsing(stack, world, user, remainingUseTicks);
        }
    }

    private void populateSpellList(){
        String[] symbols;
        Spell newSpell;
        symbols = new String[]{symbolControlWeatherClear};
        newSpell = new Spell ("Control Weather (Clear)", symbols);
        allSpells.add(newSpell);

        symbols = new String[]{symbolControlWeatherRain};
        newSpell = new Spell ("Control Weather (Clear)", symbols);
        allSpells.add(newSpell);

        symbols = new String[]{symbolControlWeatherThunder};
        newSpell = new Spell ("Control Weather (Clear)", symbols);
        allSpells.add(newSpell);

        symbols = new String[]{symbolConjureBarrage};
        newSpell = new Spell ("Conjure Barrage", symbols);
        allSpells.add(newSpell);

    }
}
