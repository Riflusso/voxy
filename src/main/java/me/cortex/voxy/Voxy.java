package me.cortex.voxy;

import me.cortex.voxy.client.VoxyClient;
import me.cortex.voxy.client.VoxyCommands;
import me.cortex.voxy.client.config.ForgeConfigIntegration;
import me.cortex.voxy.commonImpl.VoxyCommon;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Voxy.MODID)
public class Voxy {
    public static final String MODID = "voxy";

    public Voxy() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Init common environment
        VoxyCommon.cleanInit();

        ForgeConfigIntegration.register();

        // Register client setup event
        if (FMLLoader.getDist() == Dist.CLIENT) {
            modEventBus.addListener(this::onClientSetup);
            MinecraftForge.EVENT_BUS.addListener(this::onRegisterClientCommands);
            modEventBus.addListener(VoxyClient::onIMCProcess);
        }
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(VoxyClient::onClientSetup);
    }

    private void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        if (VoxyCommon.isAvailable()) {
            event.getDispatcher().register(VoxyCommands.register());
        }
    }
}
