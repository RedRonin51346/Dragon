package com.example.dragonsawakening.client;

import com.example.dragonsawakening.DragonsAwakening;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DragonsAwakening.MOD_ID, value = Dist.CLIENT)
public class DragonFightClient {
    private static int introTicks = 0;
    private static int victoryTicks = 0;
    private static int dragonMissingTicks = 0;
    private static int lastDragonId = -1;
    private static boolean themeStarted = false;
    private static boolean introPlayed = false;

    private static final int INTRO_LENGTH = 100;
    private static final int VICTORY_LENGTH = 100;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        Level level = mc.level;
        if (player == null || level == null) {
            reset();
            return;
        }

        if (level.dimension() != Level.END) {
            reset();
            return;
        }

        EnderDragon dragon = level.getEntitiesOfClass(
                EnderDragon.class, player.getBoundingBox().inflate(256)
        ).stream().findFirst().orElse(null);

        if (dragon != null && dragon.isAlive()) {
            dragonMissingTicks = 0;
            if (dragon.getId() != lastDragonId) {
                lastDragonId = dragon.getId();
                introTicks = INTRO_LENGTH;
                introPlayed = true;
                themeStarted = false;
            }
            if (!themeStarted && introTicks <= 0) {
                mc.getSoundManager().stop(null, SoundSource.MUSIC);
                mc.level.playLocalSound(player.getX(), player.getY(), player.getZ(),
                        DragonsAwakening.BOSS_THEME.get(), SoundSource.MUSIC,
                        0.85F, 1.0F, false);
                themeStarted = true;
            }
        } else if (lastDragonId != -1) {
            dragonMissingTicks++;
            if (dragonMissingTicks == 1) {
                victoryTicks = VICTORY_LENGTH;
                mc.getSoundManager().stop(null, SoundSource.MUSIC);
            }
            if (dragonMissingTicks > 200) reset();
        }

        if (introTicks > 0) introTicks--;
        if (victoryTicks > 0) victoryTicks--;
    }

    @SubscribeEvent
    public static void onDragonDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.level != null && mc.level.dimension() == Level.END) {
            victoryTicks = VICTORY_LENGTH;
            mc.getSoundManager().stop(null, SoundSource.MUSIC);
        }
    }

    @SubscribeEvent
    public static void onRenderGui(RenderGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null || mc.level.dimension() != Level.END) return;
        GuiGraphics gui = event.getGuiGraphics();
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();

        if (introTicks > 0) {
            int alpha = Math.min(220, (INTRO_LENGTH - introTicks) * 8);
            gui.fill(0, 0, width, height, (alpha << 24));
            if (introTicks < 75) {
                gui.drawCenteredString(mc.font, Component.literal("THE ENDER DRAGON"),
                        width / 2, height / 2 - 10, 0xFFE7C7FF);
                gui.drawCenteredString(mc.font, Component.literal("THE END AWAKENS"),
                        width / 2, height / 2 + 8, 0xFFFFFFFF);
            }
        }

        if (victoryTicks > 0) {
            int alpha = Math.min(220, (VICTORY_LENGTH - victoryTicks) * 8);
            gui.fill(0, 0, width, height, (alpha << 24));
            gui.drawCenteredString(mc.font, Component.literal("THE END IS YOURS"),
                    width / 2, height / 2 - 5, 0xFFE7C7FF);
            gui.drawCenteredString(mc.font, Component.literal("THE DRAGON HAS FALLEN"),
                    width / 2, height / 2 + 12, 0xFFFFFFFF);
        }
    }

    private static void reset() {
        introTicks = 0;
        victoryTicks = 0;
        dragonMissingTicks = 0;
        lastDragonId = -1;
        themeStarted = false;
        introPlayed = false;
    }
}
