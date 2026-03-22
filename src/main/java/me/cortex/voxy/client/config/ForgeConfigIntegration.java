package me.cortex.voxy.client.config;

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
                        var screen = new SodiumOptionsGUI(parent);
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