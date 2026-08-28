package com.chasemeng.goodfoods;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = specPair.getLeft();
        SPEC = specPair.getRight();
    }

    @SuppressWarnings("deprecation")
    public static int getTntAppleExplosionRadius() {
        return COMMON.tntAppleExplosionRadius.get();
    }

    @SuppressWarnings("deprecation")
    public static int getEnchantedTntAppleExplosionRadius() {
        return COMMON.enchantedTntAppleExplosionRadius.get();
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue tntAppleExplosionRadius;
        public final ForgeConfigSpec.IntValue enchantedTntAppleExplosionRadius;

        CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("GoodFoods Mod Configuration",
                            "GoodFoods 模组配置")
                    .push("general");

            tntAppleExplosionRadius = builder
                    .comment("Explosion radius for normal TNT Apple (in blocks). Default: 10",
                            "普通 TNT 苹果的爆炸半径（单位：格）。默认：10")
                    .defineInRange("tntAppleExplosionRadius", 10, 1, 100);

            enchantedTntAppleExplosionRadius = builder
                    .comment("Explosion radius for Enchanted TNT Apple (in blocks). Can break ANY block including bedrock. Default: 5",
                            "附魔 TNT 苹果的爆炸半径（单位：格）。可破坏任何方块（包括基岩）。默认：5")
                    .defineInRange("enchantedTntAppleExplosionRadius", 5, 1, 50);

            builder.pop();
        }
    }
}