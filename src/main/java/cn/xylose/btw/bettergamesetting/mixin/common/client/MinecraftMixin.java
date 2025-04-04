package cn.xylose.btw.bettergamesetting.mixin.common.client;

import cn.xylose.btw.bettergamesetting.util.OpenGlHelperExtra;
import com.github.skystardust.InputMethodBlocker.NativeUtils;
import com.github.skystardust.InputMethodBlocker.compat.InputMethodHandler;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class, priority = 9999)
public class MinecraftMixin {
    @Shadow public GameSettings gameSettings;
    @Shadow public GuiScreen currentScreen;

    @Inject(method = "runGameLoop", at = @At("HEAD"))
    private void inject(CallbackInfo ci) {
        if (this.gameSettings.limitFramerate < 10 || this.gameSettings.limitFramerate > 260)
            this.gameSettings.limitFramerate = 120;
        if (this.gameSettings.fovSetting < 30 || this.gameSettings.fovSetting > 110)
            this.gameSettings.fovSetting = 70;
        if (this.gameSettings.renderDistance < 2 || this.gameSettings.renderDistance > 24)
            this.gameSettings.renderDistance = 12;
        if (!OpenGlHelperExtra.isNvidiaGL)
            this.gameSettings.advancedOpengl = false;
    }

    /**
     * @author Xy_Lose
     * @reason break Fps limit & optimize GuiMainMenu
     */
    @Overwrite
    private int getLimitFramerate() {
        if (this.currentScreen != null && (this.currentScreen instanceof GuiMainMenu)) {
            return 24;
        }
        if (this.gameSettings.limitFramerate <= 260) {
            return this.gameSettings.limitFramerate;
        }
        return 9999;
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
}
