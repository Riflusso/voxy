package me.cortex.voxy.client.config;

import me.cortex.voxy.client.mixin.sodium.AccessorSodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.screen.ConfigCorruptedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import me.cortex.voxy.common.Logger;
import me.cortex.voxy.commonImpl.VoxyCommon;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;

public class ForgeConfigIntegration {
    public static void register() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> {
                    if (VoxyCommon.isAvailable()) {
                        Screen screen = SodiumClientMod.options().isReadOnly() ? new ConfigCorruptedScreen(() -> AccessorSodiumOptionsGUI.newScreen(parent)) : AccessorSodiumOptionsGUI.newScreen(parent);
                        //Sorry jelly and douira, please dont hurt me
                        try {
                            //We cant use .setPage() as that invokes rebuildGui, however the screen hasnt been initalized yet
                            // causing things to crash
                            var field = SodiumOptionsGUI.class.getDeclaredField("currentPage");
                            field.setAccessible(true);
                            field.set(screen, VoxyConfigScreenPages.voxyOptionPage);
                            field.setAccessible(false);
                        } catch (Exception e) {
                            Logger.error("Failed to set the current page to voxy", e);
                        }
                        return screen;
                    } else {
                        return null;
                    }
                })
        );
    }
}