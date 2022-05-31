package net.jems.somaticsorcery.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Objects;

public class WandItem extends Item {

    private String currentSymbol = "";

    private boolean correctSymbolDrawn = false;

    private final HashMap<String, String> allSpells = new HashMap<>();


    boolean yawChanged = true;
    boolean pitchChanged = true;
    float startYaw;
    float startPitch;

    public WandItem(Settings settings) {
        super(settings);
        populateSpellList();
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
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
                    user.sendSystemMessage(new LiteralText("right! " + currentSymbol), Util.NIL_UUID);
                }

                if (user.getYaw() - startYaw < -7) {
                    currentSymbol = currentSymbol + 'L';
                    yawChanged = true;
                    user.sendSystemMessage(new LiteralText("left! " + currentSymbol), Util.NIL_UUID);
                }

                if (user.getPitch() - startPitch > 7) {
                    currentSymbol = currentSymbol + 'D';
                    pitchChanged = true;
                    user.sendSystemMessage(new LiteralText("down! " + currentSymbol), Util.NIL_UUID);
                }

                if (user.getPitch() - startPitch < -7) {
                    currentSymbol = currentSymbol + 'U';
                    pitchChanged = true;
                    user.sendSystemMessage(new LiteralText("up! " + currentSymbol), Util.NIL_UUID);
                }

                if (pitchChanged || yawChanged) {
                    if (allSpells.containsKey(currentSymbol)) {
                        user.sendSystemMessage(new LiteralText("Casting: " + allSpells.get(currentSymbol)), Util.NIL_UUID);
                        correctSymbolDrawn = true;

                    }
                }
            }
        }
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }


    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (currentSymbol.equals("UUURRDURRDD")) {
            ServerWorld serverWorld = Objects.requireNonNull(user.getServer()).getOverworld();
            serverWorld.setWeather(18000, 0, false, false);

        }
        currentSymbol = "";
        correctSymbolDrawn = false;
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    private void populateSpellList(){
        allSpells.put("UUURRDURRDD", "Control Weather (Clear)");
        allSpells.put("DDDLLUDLLUU", "Control Weather (Rain)");
    }
}
