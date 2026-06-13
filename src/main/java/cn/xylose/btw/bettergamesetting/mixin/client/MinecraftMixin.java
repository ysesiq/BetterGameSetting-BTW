package cn.xylose.btw.bettergamesetting.mixin.client;

import cn.xylose.btw.bettergamesetting.client.KeyBindingExtra;
import cn.xylose.btw.bettergamesetting.init.BGSClient;
import cn.xylose.btw.bettergamesetting.util.GuiScreenPanoramaHelp;
import cn.xylose.btw.bettergamesetting.util.Mth;
import cn.xylose.btw.bettergamesetting.util.ScreenUtil;
import com.github.skystardust.InputMethodBlocker.NativeUtils;
import com.github.skystardust.InputMethodBlocker.compat.InputMethodHandler;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import static cn.xylose.btw.bettergamesetting.util.Constants.*;

@Mixin(value = Minecraft.class, priority = 1200)
public abstract class MinecraftMixin {
    @Shadow public GameSettings gameSettings;
    @Shadow public GuiScreen currentScreen;
    @Shadow public abstract IntegratedServer getIntegratedServer();

    @Inject(method = "runGameLoop", at = @At("HEAD"))
    private void optionsLimit(CallbackInfo ci) {
        GameSettings options = this.gameSettings;
        options.limitFramerate = Mth.clamp(options.limitFramerate, FPS_LIMIT_MIN, FPS_LIMIT_MAX, FPS_LIMIT_DEFAULT);
        options.fovSetting = Mth.clamp((int) options.fovSetting, FOV_MIN, FOV_MAX, FOV_DEFAULT);
        options.renderDistance = Mth.clamp(options.renderDistance, RENDER_DISTANCE_MIN, RENDER_DISTANCE_MAX, RENDER_DISTANCE_DEFAULT);
    }

    @ModifyReturnValue(method = "getLimitFramerate", at = @At("RETURN"))
    private int getLimitFps(int original) {
        if (this.currentScreen != null && (this.currentScreen instanceof GuiMainMenu)) {
            return 60;
        }
        if (!(this.gameSettings.limitFramerate >= FPS_LIMIT_MAX)) {
            return this.gameSettings.limitFramerate;
        }
        return 9999;
    }

    @Redirect(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/src/GuiMainMenu"))
    private GuiMainMenu unificationPanorama() {
        ScreenUtil.instance = new ScreenUtil();
        return GuiScreenPanoramaHelp.panoramaDummy;
    }

    @Inject(method = "startGame", at = @At("TAIL"))
    private void initKeyCategory(CallbackInfo ci) {
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindAchievements.keyDescription, "key.categories.misc");

        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindForward.keyDescription, "key.categories.movement");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindJump.keyDescription, "key.categories.movement");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindRight.keyDescription, "key.categories.movement");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindBack.keyDescription, "key.categories.movement");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindLeft.keyDescription, "key.categories.movement");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindSneak.keyDescription, "key.categories.movement");

        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindInventory.keyDescription, "key.categories.inventory");

        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindDrop.keyDescription, "key.categories.gameplay");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindAttack.keyDescription, "key.categories.gameplay");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindUseItem.keyDescription, "key.categories.gameplay");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindPickBlock.keyDescription, "key.categories.gameplay");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindSpecial.keyDescription, "key.categories.gameplay");

        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindChat.keyDescription, "key.categories.multiplayer");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindCommand.keyDescription, "key.categories.multiplayer");
        KeyBindingExtra.setKeyKeyCategory(this.gameSettings.keyBindPlayerList.keyDescription, "key.categories.multiplayer");
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
