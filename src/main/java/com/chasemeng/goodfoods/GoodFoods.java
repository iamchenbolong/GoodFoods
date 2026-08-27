package com.chasemeng.goodfoods;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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

import javax.annotation.Nonnull;

@Mod(GoodFoods.MODID)
public class GoodFoods {
    public static final String MODID = "goodfoods";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // ----- 食物注册（保持不变） -----
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

    // ⭐ 煤炭苹果：使用自定义类，移除夜晚判断，持续5分钟（6000 tick）
    public static final RegistryObject<Item> COAL_APPLE = ITEMS.register("coal_apple",
            CoalAppleItem::new
    );

    // ----- 创造模式标签（保持不变，已包含所有食物） -----
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
        // 可选：添加到原版食物标签
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

    // ⭐ 自定义煤炭苹果物品类（修改后的版本）
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
        @Nonnull
        public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
            // 先执行原食物效果（恢复饥饿和饱和度）
            ItemStack result = super.finishUsingItem(stack, level, entity);

            // 任何时候（不分昼夜）都给予隐身和夜视效果，持续5分钟（6000 tick）
            if (entity instanceof Player) {
                // 隐身效果 6000 tick（5分钟）
                entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 6000, 0, false, false));
                // 夜视效果 6000 tick
                entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 6000, 0, false, false));
            }

            return result;
        }

        @Override
        @Nonnull
        public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
            return super.use(level, player, hand);
        }
    }
}