package cn.xylose.btw.bettergamesetting.init;

import api.AddonHandler;
import api.BTWAddon;
import cn.xylose.btw.bettergamesetting.config.BGSConfigManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.src.GameRules;

import java.util.HashMap;
import java.util.Map;

public class BGSClient extends BTWAddon implements ClientModInitializer {
    public static String resourceId = "bgs";
    public static final Map<String, String> DEFAULT_GAMERULE_VALUE = new HashMap<>();
    public static final GameRules gameRules = new GameRules();
    public static Map<String, String> pendingRules = new HashMap<>();

    @Override
    public void postSetup() {
        this.modID = "better_game_setting";
        this.addonName = FabricLoader.getInstance().getModContainer(modID).get().getMetadata().getName();
    }

    @Override
    public void preInitialize() {
//        EnumExtends.buildEnumExtending();
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.addonName + " " + getVersionString() + " Initializing...");
    }

    @Override
    public void onInitializeClient() {
        addResourcePackDomain(resourceId);
        BGSConfigManager.initializeConfig();
    }
}
