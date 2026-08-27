package com.chasemeng.goodfoods;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(GoodFoods.MODID)
public class GoodFoods {
    public static final String MODID = "goodfoods";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // ----- 已有食物 -----
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
                            .build()
                    )
            )
    );

    // ----- 创造模式标签 -----
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
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("GoodFoods mod common setup completed.");
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // 可选添加到原版食物标签
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("GoodFoods server starting.");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("GoodFoods client setup.");
            LOGGER.info("Minecraft player: {}", Minecraft.getInstance().getUser().getName());
        }
    }

    // ----- 自定义煤炭苹果 -----
    public static class CoalAppleItem extends Item {
        public CoalAppleItem() {
            super(new Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationMod(2.0f)
                            .build()
                    )
            );
        }

        @Override
        public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
            // 先调用父类处理恢复饱食度
            ItemStack result = super.finishUsingItem(stack, level, entity);

            // 无论昼夜，给予隐身和夜视，持续5分钟（6000 tick）
            if (entity instanceof Player) {
                entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 6000, 0, false, false));
                entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 6000, 0, false, false));
            }

            return result;
        }
    }
}