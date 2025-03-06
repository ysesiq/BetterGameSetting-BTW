package cn.xylose.btw.bettergamesetting.mixin.common.client.gui;

import cn.xylose.btw.bettergamesetting.client.gui.GuiSoundSetting;
import cn.xylose.btw.bettergamesetting.client.gui.controls.GuiNewControls;
import cn.xylose.btw.bettergamesetting.client.gui.resourcepack.GuiScreenResourcePacks;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiOptions.class)
public class GuiOptionsMixin extends GuiScreen {
    @Shadow @Final @Mutable private static EnumOptions[] relevantOptions;
    @Shadow @Final @Mutable private static EnumOptions[] relevantOptionsMainMenu;
    @Shadow @Final private GameSettings options;

    @Inject(method = "initGui", at = @At("TAIL"))
    private void addButton(CallbackInfo ci) {
        GuiButton button_audio_settings = new GuiButton(300, this.width / 2 - 152, this.height / 6 + 96 - 30, 150, 20, I18n.getString("options.sounds"));
        this.buttonList.add(button_audio_settings);
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    private void actionPerformed(GuiButton par1GuiButton, CallbackInfo ci) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 300) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiSoundSetting(this, this.options));
            }
            if (par1GuiButton.id == 100) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiNewControls(this, this.options));
            }
            if (par1GuiButton.id == 105) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
            }
        }
    }

    static {
        relevantOptions = new EnumOptions[]{EnumOptions.FOV, EnumOptions.DIFFICULTY};
        relevantOptionsMainMenu = new EnumOptions[]{EnumOptions.FOV};
    }
}
