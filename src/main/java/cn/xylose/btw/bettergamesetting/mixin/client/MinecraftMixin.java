package cn.xylose.btw.bettergamesetting.mixin.client;

import cn.xylose.btw.bettergamesetting.init.BGSClient;
import cn.xylose.btw.bettergamesetting.util.GuiScreenPanoramaHelp;
import com.github.skystardust.InputMethodBlocker.NativeUtils;
import com.github.skystardust.InputMethodBlocker.compat.InputMethodHandler;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = Minecraft.class, priority = 1200)
public abstract class MinecraftMixin {
    @Shadow public GameSettings gameSettings;
    @Shadow public GuiScreen currentScreen;

    @Shadow public abstract IntegratedServer getIntegratedServer();

    @Inject(method = "runGameLoop", at = @At("HEAD"))
    private void optionsLimit(CallbackInfo ci) {
        if (this.gameSettings.limitFramerate < 10 || this.gameSettings.limitFramerate > 260)
            this.gameSettings.limitFramerate = 120;
        if (this.gameSettings.fovSetting < 30 || this.gameSettings.fovSetting > 110)
            this.gameSettings.fovSetting = 70;
        if (this.gameSettings.renderDistance < 2 || this.gameSettings.renderDistance > 24)
            this.gameSettings.renderDistance = 12;
    }

    /**
     * @author Xy_Lose
     * @reason break Fps limit & optimize GuiMainMenu
     */
    @Overwrite
    private int getLimitFramerate() {
        if (this.currentScreen != null && (this.currentScreen instanceof GuiMainMenu)) {
            return 60;
        }
        if (this.gameSettings.limitFramerate <= 260) {
            return this.gameSettings.limitFramerate;
        }
        return 9999;
    }

    @Redirect(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/src/GuiMainMenu"))
    private GuiMainMenu unificationPanorama() {
        return GuiScreenPanoramaHelp.panoramaDummy;
    }

    @Inject(method = "displayGuiScreen", at = @At("HEAD"))
    private void IMBlocker(GuiScreen gui, CallbackInfo ci) {
        if (InputMethodHandler.getInstance().shouldActive(gui)) {
            NativeUtils.active("");
        } else {
            NativeUtils.inactive("");
        }
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Minecraft;displayGuiScreen(Lnet/minecraft/src/GuiScreen;)V", ordinal = 5, shift = At.Shift.AFTER))
    private void commandingInactive(CallbackInfo ci) {
        NativeUtils.inactive("");
    }

    @Inject(method = "launchIntegratedServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Minecraft;displayGuiScreen(Lnet/minecraft/src/GuiScreen;)V"))
    private void launchIntegratedServer(CallbackInfo ci) {
        WorldServer overworld = this.getIntegratedServer().worldServers[0];
        if (overworld != null && !BGSClient.pendingRules.isEmpty()) {
            for (Map.Entry<String, String> entry : BGSClient.pendingRules.entrySet()) {
                overworld.getGameRules().setOrCreateGameRule(entry.getKey(), entry.getValue());
            }
            BGSClient.pendingRules.clear();
        }
    }
}
