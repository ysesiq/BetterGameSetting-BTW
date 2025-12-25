package cn.xylose.btw.bettergamesetting.init;

import cn.xylose.btw.bettergamesetting.util.EnumExtends;
//import com.chocohead.mm.AsmTransformer;
import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import cn.xylose.btw.bettergamesetting.util.BGSEnumExtender;
import net.fabricmc.loader.impl.launch.knot.Knot;
import net.minecraft.src.EnumOptions;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.function.Consumer;

public class BGSEarlyRiser implements PreLaunchEntrypoint {

    @Override
    public void onPreLaunch() {

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
//        .addEnum("MIPMAP_LEVELS", "options.mipmapLevels", true, false)
//        .addEnum("ANISOTROPIC_FILTERING", "options.anisotropicFiltering", true, false)
                .addEnum("FORCE_UNICODE_FONT", "options.forceUnicodeFont", false, true)
//        .addEnum("FULLSCREEN_RESOLUTION", "options.fullscreenResolution", true, false)
                .addEnum("TRANSPARENT_BACKGROUND", "options.transparentBackground", false, true)
                .addEnum("HIGHLIGHT_BUTTON_TEXT", "options.highlightButtonText", false, true)
                .build();

//        AsmTransformer asmTransformer = new AsmTransformer();
//        EnumExtends.buildEnumExtending();
//        asmTransformer.buildAndInitializeTransformer(url -> {
//            try {
//                ClassLoader cl = Thread.currentThread().getContextClassLoader();
//                Class<?> clazz = cl.getClass();
//
//                if (clazz.getName().endsWith("KnotClassLoader")) {
//                    Method method = clazz.getDeclaredMethod("addUrlFwd", URL.class);
//                    method.setAccessible(true);
//                    method.invoke(cl, url);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
    }
}

