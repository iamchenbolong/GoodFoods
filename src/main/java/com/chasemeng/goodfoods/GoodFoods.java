package com.chasemeng.goodfoods;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

@Mod(GoodFoods.MODID)
public class GoodFoods {
    public static final String MODID = "goodfoods";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // ========== 普通苹果（13种） ==========
    public static final RegistryObject<Item> DIRT_APPLE = ITEMS.register("dirt_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationMod(0.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 20, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 1.0f)
                            .alwaysEat()
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> STONE_APPLE = ITEMS.register("stone_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(20)
                            .saturationMod(4.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 600, 0), 1.0f)
                            .alwaysEat()
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> COPPER_APPLE = ITEMS.register("copper_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationMod(4.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 1), 1.0f)
                            .alwaysEat()
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> IRON_APPLE = ITEMS.register("iron_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(10)
                            .saturationMod(6.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 1), 1.0f)
                            .alwaysEat()
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> DIAMOND_APPLE = ITEMS.register("diamond_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationMod(15.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0), 1.0f)
                            .alwaysEat()
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> COAL_APPLE = ITEMS.register("coal_apple",
            CoalAppleItem::new
    );

    public static final RegistryObject<Item> REDSTONE_APPLE = ITEMS.register("redstone_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationMod(6.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 6000, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 6000, 0), 1.0f)
                            .alwaysEat()
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> LAPIS_APPLE = ITEMS.register("lapis_lazuli_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationMod(6.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 1200, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 2), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0), 1.0f)
                            .alwaysEat()
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> EMERALD_APPLE = ITEMS.register("emerald_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(10)
                            .saturationMod(8.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 6000, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 6000, 0), 1.0f)
                            .alwaysEat()
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> NETHERITE_APPLE = ITEMS.register("netherite_apple",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(16)
                            .saturationMod(20.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 2), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 2), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 6000, 2), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 6000, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 2), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 6000, 2), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.HARM, 1, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0), 1.0f)
                            .alwaysEat()
                            .build()
                    )
            )
    );

    public static final RegistryObject<Item> OBSIDIAN_APPLE = ITEMS.register("obsidian_apple",
            ObsidianAppleItem::new
    );

    public static final RegistryObject<Item> TNT_APPLE = ITEMS.register("tnt_apple",
            TNTAppleItem::new
    );

    public static final RegistryObject<Item> BEDROCK_APPLE = ITEMS.register("bedrock_apple",
            BedrockAppleItem::new
    );

    // ========== 附魔苹果（6种） ==========
    public static final RegistryObject<Item> ENCHANTED_DIAMOND_APPLE = ITEMS.register("enchanted_diamond_apple",
            EnchantedDiamondAppleItem::new
    );

    public static final RegistryObject<Item> ENCHANTED_OBSIDIAN_APPLE = ITEMS.register("enchanted_obsidian_apple",
            EnchantedObsidianAppleItem::new
    );

    public static final RegistryObject<Item> ENCHANTED_TNT_APPLE = ITEMS.register("enchanted_tnt_apple",
            EnchantedTNTAppleItem::new
    );

    public static final RegistryObject<Item> ENCHANTED_NETHERITE_APPLE = ITEMS.register("enchanted_netherite_apple",
            EnchantedNetheriteAppleItem::new
    );

    public static final RegistryObject<Item> ENCHANTED_EMERALD_APPLE = ITEMS.register("enchanted_emerald_apple",
            EnchantedEmeraldAppleItem::new
    );

    // ========== 新物品 ==========
    public static final RegistryObject<Item> CAN = ITEMS.register("can",
            () -> new Item(new Item.Properties().stacksTo(16))
    );

    public static final RegistryObject<Item> SPORTS_DRINK = ITEMS.register("sports_drink",
            SportsDrinkItem::new
    );

    public static final RegistryObject<Item> MILKSHAKE = ITEMS.register("milkshake",
            MilkshakeItem::new
    );

    // ========== 创造模式标签 ==========
    public static final RegistryObject<CreativeModeTab> GOODFOODS_TAB =
            CREATIVE_MODE_TABS.register("goodfoods_tab",
                    () -> CreativeModeTab.builder()
                            .withTabsBefore(CreativeModeTabs.FOOD_AND_DRINKS)
                            .icon(() -> DIRT_APPLE.get().getDefaultInstance())
                            .title(Component.translatable("creativeTab.goodfoods.goodfoods_tab"))
                            .displayItems((params, output) -> {
                                output.accept(DIRT_APPLE.get());
                                output.accept(STONE_APPLE.get());
                                output.accept(COPPER_APPLE.get());
                                output.accept(IRON_APPLE.get());
                                output.accept(DIAMOND_APPLE.get());
                                output.accept(COAL_APPLE.get());
                                output.accept(REDSTONE_APPLE.get());
                                output.accept(LAPIS_APPLE.get());
                                output.accept(EMERALD_APPLE.get());
                                output.accept(NETHERITE_APPLE.get());
                                output.accept(OBSIDIAN_APPLE.get());
                                output.accept(TNT_APPLE.get());
                                output.accept(BEDROCK_APPLE.get());
                                output.accept(ENCHANTED_DIAMOND_APPLE.get());
                                output.accept(ENCHANTED_OBSIDIAN_APPLE.get());
                                output.accept(ENCHANTED_TNT_APPLE.get());
                                output.accept(ENCHANTED_NETHERITE_APPLE.get());
                                output.accept(ENCHANTED_EMERALD_APPLE.get());
                                output.accept(CAN.get());
                                output.accept(SPORTS_DRINK.get());
                                output.accept(MILKSHAKE.get());
                            })
                            .build()
            );

    public GoodFoods(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
        context.registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, Config.SPEC);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((modContainer, parent) -> new ConfigScreen(parent)));
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("GoodFoods mod common setup completed.");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // 可选
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("GoodFoods server starting.");
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof net.minecraft.world.entity.animal.MushroomCow) {
            Player player = event.getEntity();
            ItemStack hand = player.getItemInHand(event.getHand());
            if (!hand.isEmpty() && hand.getItem() == CAN.get()) {
                if (!player.level().isClientSide) {
                    hand.shrink(1);
                    player.getInventory().add(new ItemStack(SPORTS_DRINK.get()));
                    player.level().playSound(null, player.blockPosition(),
                            net.minecraft.sounds.SoundEvents.BOTTLE_FILL, net.minecraft.sounds.SoundSource.PLAYERS, 1.0f, 1.0f);
                }
                event.setCanceled(true);
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("GoodFoods client setup.");
            LOGGER.info("Minecraft player: {}", Minecraft.getInstance().getUser().getName());
        }
    }

    // ========== 自定义类：煤炭苹果 ==========
    public static class CoalAppleItem extends Item {
        public CoalAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationMod(2.0f)
                            .alwaysEat()
                            .build()
                    )
            );
        }

        @Override
        public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (entity instanceof Player) {
                entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 6000, 0, false, false));
                entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 6000, 0, false, false));
            }
            return result;
        }
    }

    // ========== 自定义类：黑曜石苹果 ==========
    public static class ObsidianAppleItem extends Item {
        public ObsidianAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(10)
                            .saturationMod(10.0f)
                            .alwaysEat()
                            .build()
                    )
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                player.hurt(level.damageSources().magic(), 12.0F);
                if (!player.isAlive()) return result;
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 4, false, false));
            }
            return result;
        }
    }

    // ========== 自定义类：普通 TNT 苹果 ==========
    public static class TNTAppleItem extends Item {
        public TNTAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(0)
                            .saturationMod(0.0f)
                            .alwaysEat()
                            .build()
                    )
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                float radius = Config.getTntAppleExplosionRadius();
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();
                Explosion explosion = new Explosion(level, player, x, y, z, radius, false, Explosion.BlockInteraction.DESTROY);
                explosion.explode();
                explosion.finalizeExplosion(true);
            }
            return result;
        }
    }

    // ========== 自定义类：基岩苹果 ==========
    public static class BedrockAppleItem extends Item {
        public BedrockAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(20)
                            .saturationMod(20.0f)
                            .alwaysEat()
                            .build()
                    )
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 999999, 4, false, false));
            }
            return result;
        }
    }

    // ========== 附魔物品基类 ==========
    private static abstract class EnchantedAppleItem extends Item {
        public EnchantedAppleItem(Properties properties) {
            super(properties);
        }

        @Override
        public boolean isFoil(@Nonnull ItemStack stack) {
            return true;
        }
    }

    // ========== 附魔钻石苹果 ==========
    public static class EnchantedDiamondAppleItem extends EnchantedAppleItem {
        public EnchantedDiamondAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationMod(15.0f)
                            .alwaysEat()
                            .build()
                    )
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 2, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 3, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 2, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 1200, 2, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 2, false, false));
            }
            return result;
        }
    }

    // ========== 附魔黑曜石苹果 ==========
    public static class EnchantedObsidianAppleItem extends EnchantedAppleItem {
        public EnchantedObsidianAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(10)
                            .saturationMod(10.0f)
                            .alwaysEat()
                            .build()
                    )
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                player.hurt(level.damageSources().magic(), 4.0F);
                if (!player.isAlive()) return result;
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 4, false, false));
            }
            return result;
        }
    }

    // ========== 附魔下界合金苹果 ==========
    public static class EnchantedNetheriteAppleItem extends EnchantedAppleItem {
        public EnchantedNetheriteAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(16)
                            .saturationMod(20.0f)
                            .alwaysEat()
                            .build()
                    )
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 4, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 4, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 6000, 4, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 6000, 2, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 4, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 6000, 4, false, false));
            }
            return result;
        }
    }

    // ========== 附魔 TNT 苹果（保护管理员方块 + 光源方块） ==========
    public static class EnchantedTNTAppleItem extends EnchantedAppleItem {
        // 定义不可破坏方块集合（管理员方块 + 光源方块）
        private static final Set<Block> PROTECTED_BLOCKS = new HashSet<>();

        static {
            // 命令方块
            PROTECTED_BLOCKS.add(Blocks.COMMAND_BLOCK);
            PROTECTED_BLOCKS.add(Blocks.CHAIN_COMMAND_BLOCK);
            PROTECTED_BLOCKS.add(Blocks.REPEATING_COMMAND_BLOCK);
            // 屏障
            PROTECTED_BLOCKS.add(Blocks.BARRIER);
            // 结构方块
            PROTECTED_BLOCKS.add(Blocks.STRUCTURE_BLOCK);
            PROTECTED_BLOCKS.add(Blocks.STRUCTURE_VOID);
            // 拼图方块
            PROTECTED_BLOCKS.add(Blocks.JIGSAW);
            // 光源方块（新增）
            PROTECTED_BLOCKS.add(Blocks.LIGHT);
        }

        public EnchantedTNTAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(0)
                            .saturationMod(0.0f)
                            .alwaysEat()
                            .build()
                    )
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                float radius = Config.getEnchantedTntAppleExplosionRadius();
                BlockPos center = player.blockPosition();

                // 生成爆炸粒子效果（不破坏方块）
                Explosion explosion = new Explosion(level, player, center.getX(), center.getY(), center.getZ(),
                        radius, false, Explosion.BlockInteraction.KEEP);
                explosion.explode();
                explosion.finalizeExplosion(true);

                // 手动破坏所有非保护方块
                int radiusInt = (int) Math.ceil(radius);
                for (int dx = -radiusInt; dx <= radiusInt; dx++) {
                    for (int dy = -radiusInt; dy <= radiusInt; dy++) {
                        for (int dz = -radiusInt; dz <= radiusInt; dz++) {
                            double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);
                            if (dist <= radius) {
                                BlockPos pos = center.offset(dx, dy, dz);
                                BlockState state = level.getBlockState(pos);
                                Block block = state.getBlock();
                                if (!state.isAir() && !PROTECTED_BLOCKS.contains(block)) {
                                    // 基岩特殊处理：掉落基岩本身
                                    if (block == Blocks.BEDROCK) {
                                        Block.popResource(level, pos, new ItemStack(Blocks.BEDROCK));
                                    } else {
                                        Block.dropResources(state, level, pos);
                                    }
                                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                                }
                                // 保护方块被跳过，不破坏
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

    // ========== 附魔绿宝石苹果 ==========
    public static class EnchantedEmeraldAppleItem extends EnchantedAppleItem {
        public EnchantedEmeraldAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(10)
                            .saturationMod(8.0f)
                            .alwaysEat()
                            .build()
                    )
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            ItemStack result = super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.LUCK, 36000, 4, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 36000, 4, false, false));
            }
            return result;
        }
    }

    // ========== 自定义类：运动饮料 ==========
    public static class SportsDrinkItem extends Item {
        public SportsDrinkItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationMod(0.4f)
                            .alwaysEat()
                            .build()
                    )
                    .stacksTo(1)
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 6000, 4, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 4, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 6000, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 6000, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 6000, 4, false, false));
            }
            return new ItemStack(CAN.get());
        }
    }

    // ========== 自定义类：奶昔 ==========
    public static class MilkshakeItem extends Item {
        public MilkshakeItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationMod(0.8f)
                            .alwaysEat()
                            .build()
                    )
                    .stacksTo(1)
            );
        }

        @Override
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            super.finishUsingItem(stack, level, entity);
            if (!level.isClientSide && entity instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 3600, 4, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 3600, 0, false, false));
                player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20, 0, false, false));
            }
            return new ItemStack(CAN.get());
        }
    }

    // ========== 配置屏幕 ==========
    @OnlyIn(Dist.CLIENT)
    private static class ConfigScreen extends Screen {
        private final Screen parent;
        private int currentTntRadius;
        private int currentEnchantedTntRadius;
        private AbstractSliderButton tntSlider;
        private AbstractSliderButton enchantedTntSlider;

        protected ConfigScreen(Screen parent) {
            super(Component.translatable("gui.goodfoods.config.title"));
            this.parent = parent;
            this.currentTntRadius = Config.getTntAppleExplosionRadius();
            this.currentEnchantedTntRadius = Config.getEnchantedTntAppleExplosionRadius();
        }

        @Override
        protected void init() {
            super.init();

            int yBase = this.height / 2 - 50;

            this.tntSlider = new AbstractSliderButton(
                    this.width / 2 - 100, yBase + 20, 200, 20,
                    Component.translatable("gui.goodfoods.config.radius"),
                    (double)(currentTntRadius - 1) / 99
            ) {
                {
                    this.updateMessage();
                }

                @Override
                protected void updateMessage() {
                    int val = (int)(this.value * 99) + 1;
                    this.setMessage(Component.translatable("gui.goodfoods.config.radius_value", val));
                }

                @Override
                protected void applyValue() {
                    currentTntRadius = (int)(this.value * 99) + 1;
                }
            };
            this.addRenderableWidget(this.tntSlider);

            this.enchantedTntSlider = new AbstractSliderButton(
                    this.width / 2 - 100, yBase + 70, 200, 20,
                    Component.translatable("gui.goodfoods.config.enchanted_radius"),
                    (double)(currentEnchantedTntRadius - 1) / 49
            ) {
                {
                    this.updateMessage();
                }

                @Override
                protected void updateMessage() {
                    int val = (int)(this.value * 49) + 1;
                    this.setMessage(Component.translatable("gui.goodfoods.config.enchanted_radius_value", val));
                }

                @Override
                protected void applyValue() {
                    currentEnchantedTntRadius = (int)(this.value * 49) + 1;
                }
            };
            this.addRenderableWidget(this.enchantedTntSlider);

            this.addRenderableWidget(Button.builder(
                            Component.translatable("gui.goodfoods.config.save"),
                            (btn) -> {
                                Config.COMMON.tntAppleExplosionRadius.set(currentTntRadius);
                                Config.COMMON.enchantedTntAppleExplosionRadius.set(currentEnchantedTntRadius);
                                Config.SPEC.save();
                                this.onClose();
                            })
                    .bounds(this.width / 2 - 100, yBase + 110, 200, 20)
                    .build()
            );

            this.addRenderableWidget(Button.builder(
                            Component.translatable("gui.cancel"),
                            (btn) -> this.onClose())
                    .bounds(this.width / 2 - 100, yBase + 140, 200, 20)
                    .build()
            );
        }

        @Override
        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            this.renderBackground(guiGraphics);
            guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);

            int yBase = this.height / 2 - 50;
            Component tntDesc = Component.translatable("gui.goodfoods.config.radius.desc");
            guiGraphics.drawString(this.font, tntDesc, this.width / 2 - this.font.width(tntDesc) / 2, yBase, 0xAAAAAA);

            Component enchDesc = Component.translatable("gui.goodfoods.config.enchanted_radius.desc");
            guiGraphics.drawString(this.font, enchDesc, this.width / 2 - this.font.width(enchDesc) / 2, yBase + 50, 0xAAAAAA);

            super.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        @Override
        public void onClose() {
            if (this.minecraft != null) {
                this.minecraft.setScreen(parent);
            }
        }

        @Override
        public boolean isPauseScreen() {
            return true;
        }
    }
}