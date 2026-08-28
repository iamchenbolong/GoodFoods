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
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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

@Mod(GoodFoods.MODID)
public class GoodFoods {
    public static final String MODID = "goodfoods";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // ========== 全部 12 种食物（均含 alwaysEat）==========
    // （此处省略，与之前完全相同，请使用之前完整的食物定义）
    // 但为了完整性，我将包含全部代码，但为了节省长度，这里用注释表示已有定义。
    // 实际使用时，请复制之前完整定义。
    // 注意：为避免截断，我会在最终答案中附上完整的文件下载链接？不行，只能文本。

    // 由于之前已经完整提供过，这里再次完整提供（确保没有遗漏）。
    // 我将在下面逐项列出。

    // 1. 泥土苹果
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

    // 2. 石头苹果
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

    // 3. 铜苹果
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

    // 4. 铁苹果
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

    // 5. 钻石苹果
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

    // 6. 煤炭苹果
    public static final RegistryObject<Item> COAL_APPLE = ITEMS.register("coal_apple",
            CoalAppleItem::new
    );

    // 7. 红石苹果
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

    // 8. 青金石苹果
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

    // 9. 绿宝石苹果
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

    // 10. 下界合金苹果
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

    // 11. 黑曜石苹果
    public static final RegistryObject<Item> OBSIDIAN_APPLE = ITEMS.register("obsidian_apple",
            ObsidianAppleItem::new
    );

    // 12. TNT 苹果
    public static final RegistryObject<Item> TNT_APPLE = ITEMS.register("tnt_apple",
            TNTAppleItem::new
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

    // ========== 自定义类：TNT 苹果（修复黑曜石无法破坏问题）==========
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

                // 1. 生成爆炸效果（视觉+音效）
                Explosion explosion = new Explosion(level, player, x, y, z, radius, false, Explosion.BlockInteraction.DESTROY);
                explosion.explode();
                explosion.finalizeExplosion(true);

                // 2. 手动遍历球形区域，强制破坏除基岩外的所有方块
                int intRadius = (int) Math.ceil(radius);
                for (int dx = -intRadius; dx <= intRadius; dx++) {
                    for (int dy = -intRadius; dy <= intRadius; dy++) {
                        for (int dz = -intRadius; dz <= intRadius; dz++) {
                            double distSq = dx*dx + dy*dy + dz*dz;
                            if (distSq > radius * radius) continue;
                            BlockPos pos = new BlockPos(
                                    (int)(x + dx), (int)(y + dy), (int)(z + dz)
                            );
                            // 如果是基岩，跳过
                            if (level.getBlockState(pos).is(Blocks.BEDROCK)) {
                                continue;
                            }
                            // 破坏方块并掉落物品
                            level.destroyBlock(pos, true);
                        }
                    }
                }
            }
            return result;
        }
    }

    // ========== 配置屏幕（支持多配置项）==========
    @OnlyIn(Dist.CLIENT)
    private static class ConfigScreen extends Screen {
        private final Screen parent;
        private int currentRadius;
        private AbstractSliderButton radiusSlider;

        protected ConfigScreen(Screen parent) {
            super(Component.translatable("gui.goodfoods.config.title"));
            this.parent = parent;
            this.currentRadius = Config.getTntAppleExplosionRadius();
        }

        @Override
        protected void init() {
            super.init();

            // ---- TNT 爆炸半径滑块 ----
            this.radiusSlider = new AbstractSliderButton(
                    this.width / 2 - 100, this.height / 2 - 10, 200, 20,
                    Component.translatable("gui.goodfoods.config.radius"),
                    (double)(currentRadius - 1) / 99
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
                    currentRadius = (int)(this.value * 99) + 1;
                }
            };
            this.addRenderableWidget(this.radiusSlider);

            // 保存按钮
            this.addRenderableWidget(Button.builder(
                            Component.translatable("gui.goodfoods.config.save"),
                            (btn) -> {
                                Config.COMMON.tntAppleExplosionRadius.set(currentRadius);
                                Config.SPEC.save();
                                this.onClose();
                            })
                    .bounds(this.width / 2 - 100, this.height / 2 + 30, 200, 20)
                    .build()
            );

            // 取消按钮
            this.addRenderableWidget(Button.builder(
                            Component.translatable("gui.cancel"),
                            (btn) -> this.onClose())
                    .bounds(this.width / 2 - 100, this.height / 2 + 60, 200, 20)
                    .build()
            );
        }

        @Override
        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            this.renderBackground(guiGraphics);

            guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);

            // 描述文字
            Component radiusDesc = Component.translatable("gui.goodfoods.config.radius.desc");
            int descX = this.width / 2 - this.font.width(radiusDesc) / 2;
            guiGraphics.drawString(this.font, radiusDesc, descX, this.height / 2 - 35, 0xAAAAAA);

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