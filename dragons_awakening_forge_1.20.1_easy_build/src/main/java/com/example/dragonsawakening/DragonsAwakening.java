package com.example.dragonsawakening;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

@Mod(DragonsAwakening.MOD_ID)
public class DragonsAwakening {
    public static final String MOD_ID = "dragons_awakening";
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    public static final RegistryObject<SoundEvent> BOSS_THEME = SOUNDS.register(
            "boss_theme",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "boss_theme"))
    );

    public DragonsAwakening() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        SOUNDS.register(modBus);
    }
}
