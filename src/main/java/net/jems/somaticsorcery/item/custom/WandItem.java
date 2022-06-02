package net.jems.somaticsorcery.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;


import java.util.ArrayList;
import java.util.List;


public abstract class WandItem extends Item {

    private String currentSymbol = "";

    private boolean correctSymbolDrawn = false;

    private final ArrayList<Spell> allSpells = new ArrayList<>();

    boolean currentlyCasting = false;
    boolean yawChanged = false;
    boolean pitchChanged = false;
    float startYaw;
    float startPitch;

    float durationModifier;
    float intensityModifier;
    int maximumSpellLevel;

    private final String symbolControlWeatherClear = "UUURRDURRDD";
    private final String symbolControlWeatherRain = "UUULLDULLDD";
    private final String symbolControlWeatherThunder = "DDDLLUDLLUU";
    private final String symbolConjureBarrage = "LURRD";
    private final String symbolThunderwave = "LLUR";
    private final String symbolHealingWord = "RRUL";
    private final String symbolRegenerate = "DRUULDRRR";
    private final String symbolCallLightning = "UULRR";
    private final String symbolInvisibility = "LDRRU";
    private final String symbolMindSpike = "LDDDD";


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
                        return;
                    }
                }
            }
            switch (currentSymbol) {
                case symbolControlWeatherClear:
                    try {
                        ServerWorld serverWorld = user.getServer().getOverworld();
                        serverWorld.setWeather(36000, 0, false, false);
                    } catch (NullPointerException e) {
                        //user.sendSystemMessage(new LiteralText("hehe null pointer exception "), Util.NIL_UUID);
                    }
                    break;

                case symbolControlWeatherRain:
                    try {
                        ServerWorld serverWorld = user.getServer().getOverworld();
                        serverWorld.setWeather(0, 36000, true, false);
                    } catch (NullPointerException e) {
                        //user.sendSystemMessage(new LiteralText("hehe null pointer exception "), Util.NIL_UUID);
                    }
                    break;

                case symbolControlWeatherThunder:
                    try {
                        ServerWorld serverWorld = user.getServer().getOverworld();
                        serverWorld.setWeather(0, 36000, true, true);
                    } catch (NullPointerException e) {
                        //user.sendSystemMessage(new LiteralText("hehe null pointer exception"), Util.NIL_UUID);
                    }
                    break;

                case symbolConjureBarrage:
                    final int spread = 6;
                    int numArrows = 8 + (int) Math.ceil(Math.random() * 4);

                    for (int i = 0; i < numArrows; i++) {
                        ArrowItem arrowItem = (ArrowItem) (stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);
                        PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, stack, user);
                        persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
                        if (Math.ceil(Math.random() * 6) == 6) {
                            persistentProjectileEntity.setCritical(true);
                        }
                        if (Math.ceil(Math.random() * 6) == 6) {
                            persistentProjectileEntity.setOnFireFor(100);
                        }
                        persistentProjectileEntity.setVelocity(user,
                                user.getPitch() - spread + (float) ((Math.random() * 2 * spread)),
                                user.getYaw() - (2 * spread) + (float) ((Math.random() * 4 * spread)),
                                0, 5 * (float) (Math.random()), 1);
                        world.spawnEntity(persistentProjectileEntity);
                    }
                    break;

                case symbolThunderwave:
                    world.createExplosion(user, user.getX(), user.getBodyY(0.0625), user.getZ(), 3.0f, Explosion.DestructionType.NONE);
                    break;

                case symbolHealingWord:
                    if (user.isSneaking()) {
                        user.heal(5.0f);
                    } else {
                        try {
                            LivingEntity target = getEntityUnderCrosshair(user, world, 30);
                            target.heal(5.0f);
                        } catch (NullPointerException e) {
                            user.sendSystemMessage(new LiteralText("Spell failed"), Util.NIL_UUID);
                        }
                    }
                    break;

                case symbolRegenerate:
                    if (user.isSneaking()) {
                        user.heal(10.0f);
                        user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 6000));
                    } else {
                        try {
                            LivingEntity target = getEntityUnderCrosshair(user, world, 5);
                            target.heal(10.0f);
                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 6000));
                        } catch (NullPointerException e) {
                            user.sendSystemMessage(new LiteralText("Spell failed"), Util.NIL_UUID);
                        }
                    }
                    break;

                case symbolCallLightning:

                    break;

                case symbolInvisibility:
                    if (user.isSneaking()) {
                        user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 3600));
                    } else {
                        try {
                            LivingEntity target = getEntityUnderCrosshair(user, world, 5);
                            target.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 3600));
                        } catch (NullPointerException e) {
                            user.sendSystemMessage(new LiteralText("Spell failed"), Util.NIL_UUID);
                        }
                    }
                    break;

                case symbolMindSpike:
                    try {
                        LivingEntity target = getEntityUnderCrosshair(user, world, 30);
                        target.damage(DamageSource.sting(user), 3);
                        target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 3600));
                    } catch (NullPointerException e) {
                        user.sendSystemMessage(new LiteralText("Spell failed"), Util.NIL_UUID);
                    }
                    break;

            }
            currentSymbol = "";
            correctSymbolDrawn = false;
            super.onStoppedUsing(stack, world, user, remainingUseTicks);
        }
    }

    public LivingEntity getEntityUnderCrosshair(LivingEntity user, World world, int range) throws NullPointerException {
        Vec3d vec3d = user.getRotationVec(1.0f).normalize();
        Vec3d vec3d2;
        Box searchBox = new Box(user.getX() - range, user.getY() - range, user.getZ() - range,
                user.getX() + range, user.getY() + range, user.getZ() + range);

        List<Entity> entities = world.getOtherEntities(user, searchBox);

        for (Entity i : entities) {
            if (i instanceof LivingEntity) {
                vec3d2 = new Vec3d(i.getX() - user.getX(), i.getEyeY() - user.getEyeY(), i.getZ() - user.getZ());
                double d = vec3d2.length();
                double e = vec3d.dotProduct(vec3d2.normalize());
                if (e > 0.985 - 0.025 / d) {
                    return (LivingEntity) i;
                }

            }
        }
        throw new NullPointerException();
    }


    private void populateSpellList() {
        allSpells.add(new Spell ("Control Weather (Clear)", symbolControlWeatherClear, 8));

        allSpells.add(new Spell ("Control Weather (Rain)", symbolControlWeatherRain, 8));

        allSpells.add(new Spell ("Control Weather (Thunder)", symbolControlWeatherThunder, 8));

        allSpells.add(new Spell ("Conjure Barrage", symbolConjureBarrage, 3));

        allSpells.add(new Spell("Thunderwave", symbolThunderwave, 1));

        allSpells.add(new Spell("Healing Word", symbolHealingWord, 1));

        allSpells.add(new Spell("Regenerate", symbolRegenerate, 7));

        allSpells.add(new Spell("Call Lightning", symbolCallLightning, 3));

        allSpells.add(new Spell("Invisibility", symbolInvisibility, 2));

        allSpells.add(new Spell("Mind Spike", symbolMindSpike, 2));

    }
}
