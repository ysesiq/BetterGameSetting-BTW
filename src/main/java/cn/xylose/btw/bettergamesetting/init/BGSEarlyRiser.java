package cn.xylose.btw.bettergamesetting.init;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class BGSEarlyRiser implements Runnable {

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String GameOptions$Option = remapper.mapClassName("intermediary", "net.minecraft.class_350");
        ClassTinkerers.enumBuilder(GameOptions$Option, String.class, boolean.class, boolean.class)
                .addEnum("BRIGHTNESS", "options.gamma", true, false)
                .addEnum("RECORDS", "options.record", true, false)
                .addEnum("WEATHER", "options.weather", true, false)
                .addEnum("BLOCKS", "options.block", true, false)
                .addEnum("MOBS", "options.hostile", true, false)
                .addEnum("ANIMALS", "options.neutral", true, false)
                .addEnum("PLAYERS", "options.player", true, false)
                .addEnum("AMBIENT", "options.ambient", true, false)
                .addEnum("UI", "options.ui", true, false)
//              .addEnum("MIPMAP_LEVELS", "options.mipmapLevels", true, false)
//              .addEnum("ANISOTROPIC_FILTERING", "options.anisotropicFiltering", true, false)
                .addEnum("FORCE_UNICODE_FONT", "options.forceUnicodeFont", false, true)
//              .addEnum("FULLSCREEN_RESOLUTION", "options.fullscreenResolution", true, false)
                .addEnum("TRANSPARENT_BACKGROUND", "options.transparentBackground", false, true)
                .addEnum("HIGHLIGHT_BUTTON_TEXT", "options.highlightButtonText", false, true)
                .build();
    }
}

