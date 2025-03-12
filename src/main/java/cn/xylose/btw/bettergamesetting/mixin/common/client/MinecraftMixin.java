package cn.xylose.btw.bettergamesetting.mixin.common.client;

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
        if (this.gameSettings.limitFramerate < 10)
            this.gameSettings.limitFramerate = 120;
        if (this.gameSettings.fovSetting < 30)
            this.gameSettings.fovSetting = 70;
        if (this.gameSettings.renderDistance < 2)
            this.gameSettings.renderDistance = 12;
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
        if (!(this.gameSettings.limitFramerate >= 260)) {
            return this.gameSettings.limitFramerate;
        }
        return 9999;
    }

}
