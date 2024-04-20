package org.thomasphillips.greatsilencehelpermod;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Objects;

public class FoggyWeather {
    static boolean foggyWeather = false;

    @SubscribeEvent
    public static void onRenderFogColors(ViewportEvent.ComputeFogColor event) {
        Level world = event.getCamera().getEntity().level;
        BlockPos pos = event.getCamera().getBlockPosition();
        ResourceLocation biome = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(world.getBiome(pos).get());

        if (foggyWeather) {
            if (world.dimensionTypeId() == BuiltinDimensionTypes.OVERWORLD) { // I dont think this will work

                String fogColorString = "#FF0000";
                Color fogColor = Color.decode(fogColorString);

                float red = fogColor.getRed();
                float green = fogColor.getGreen();
                float blue = fogColor.getBlue();

                event.setRed(red / 255F);
                event.setGreen(green / 255F);
                event.setBlue(blue / 255F);
            }

        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        Level world = event.getCamera().getEntity().level;
        BlockPos pos = event.getCamera().getBlockPosition();
        ResourceLocation biome = world.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getKey(world.getBiome(pos).get());

        FogType fogtype = event.getCamera().getFluidInCamera();
        Entity entity = event.getCamera().getEntity();

        if (foggyWeather) {
            if (world.dimensionTypeId() == BuiltinDimensionTypes.OVERWORLD) { // I dont think this will work
                    float configFogDensity = 6F;
                    float f2;
                    float f3;
                    if (fogtype == FogType.LAVA) {
                        if (entity.isSpectator()) {
                            f2 = -8.0F;
                            f3 = configFogDensity * 0.5F;
                        } else if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(MobEffects.FIRE_RESISTANCE)) {
                            f2 = 0.0F;
                            f3 = 3.0F;
                        } else {
                            f2 = 0.25F;
                            f3 = 1.0F;
                        }
                    } else if (entity instanceof LivingEntity && ((LivingEntity) entity).hasEffect(MobEffects.BLINDNESS)) {
                        int i = Objects.requireNonNull(((LivingEntity) entity).getEffect(MobEffects.BLINDNESS)).getDuration();
                        float f1 = Mth.lerp(Math.min(1.0F, (float) i / 20.0F), configFogDensity, 5.0F);
                        if (event.getMode() == FogRenderer.FogMode.FOG_SKY) {
                            f2 = 0.0F;
                            f3 = f1 * 0.8F;
                        } else {
                            f2 = f1 * 0.25F;
                            f3 = f1;
                        }
                    } else if (fogtype == FogType.POWDER_SNOW) {
                        if (entity.isSpectator()) {
                            f2 = -8.0F;
                            f3 = configFogDensity * 0.5F;
                        } else {
                            f2 = 0.0F;
                            f3 = 2.0F;
                        }
                    } else if (event.getMode() == FogRenderer.FogMode.FOG_SKY) {
                        f2 = 0.0F;
                        f3 = configFogDensity;
                    } else {
                        float f4 = Mth.clamp(configFogDensity / 10.0F, 4.0F, 64.0F);
                        f2 = configFogDensity - f4;
                        f3 = configFogDensity;
                    }
                    RenderSystem.setShaderFogStart(f2);
                    RenderSystem.setShaderFogEnd(f3);
                }
            }
        }
    }
