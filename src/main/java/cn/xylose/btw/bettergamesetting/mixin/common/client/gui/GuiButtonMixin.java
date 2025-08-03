package cn.xylose.btw.bettergamesetting.mixin.common.client.gui;

import cn.xylose.btw.bettergamesetting.config.BGSConfig;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiButton.class)
public class GuiButtonMixin extends Gui {
    @ModifyConstant(method = "drawButton", constant = @Constant(intValue = 0xFFFFA0))
    private int highLightButtonText(int constant) {
        return BGSConfig.HIGHLIGHT_BUTTON_TEXT.getValue() ? constant : 0xFFFFFF;
    }
}

