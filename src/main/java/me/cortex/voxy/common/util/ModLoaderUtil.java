package me.cortex.voxy.common.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.forgespi.language.IModFileInfo;

import java.nio.file.Path;
import java.util.Optional;

public class ModLoaderUtil {

    public static Path getConfigDir() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static IModFileInfo getModFileById(String modid) {
        return ModList.get().getModFileById(modid);
    }

    public static Optional<? extends ModContainer> getModContainerById(String modId) {
        return ModList.get().getModContainerById(modId);
    }

    public static Dist getDist() {
        return FMLLoader.getDist();
    }
}
