package cn.xylose.btw.bettergamesetting.init;

import btw.AddonHandler;
import btw.BTWAddon;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class BetterGameSettingClient extends BTWAddon implements ClientModInitializer {
    public static String resourceID = "bgs";

    @Override
    public void postSetup() {
        this.modID = "better_game_setting";
        this.addonName = FabricLoader.getInstance().getModContainer(modID).get().getMetadata().getName();
        this.shouldVersionCheck = false;
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.addonName + " " + getVersionString() + " Initializing...");
    }

    @Override
    public void onInitializeClient() {
        addResourcePackDomain(resourceID);
    }
}
