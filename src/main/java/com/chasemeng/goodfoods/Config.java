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

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue tntAppleExplosionRadius;

        CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("GoodFoods Mod Configuration",
                            "GoodFoods 模组配置")
                    .push("general");

            tntAppleExplosionRadius = builder
                    .comment("Explosion radius for TNT Apple (in blocks). Default: 10",
                            "TNT苹果的爆炸半径（单位：格）。默认：10")
                    .defineInRange("tntAppleExplosionRadius", 10, 1, 100);

            builder.pop();
        }
    }
}