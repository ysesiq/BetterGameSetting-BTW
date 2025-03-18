package cn.xylose.btw.bettergamesetting.config;

import com.google.common.collect.ImmutableMap;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.Minecraft;

import java.util.Map;

public class BGSModMenuCompat implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return BGSOptionsScreen::new;
    }

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        return ImmutableMap.of("minecraft", parent -> new GuiOptions(parent, Minecraft.getMinecraft().gameSettings, true));
    }
}
