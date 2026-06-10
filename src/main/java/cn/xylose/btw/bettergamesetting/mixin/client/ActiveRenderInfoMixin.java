package cn.xylose.btw.bettergamesetting.mixin.client;

import cn.xylose.btw.bettergamesetting.util.OptionHelper;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.src.ActiveRenderInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ActiveRenderInfo.class)
public abstract class ActiveRenderInfoMixin {
    @ModifyExpressionValue(
            method = "getBlockIdAtEntityViewpoint",
            at = @At(value = "FIELD", target = "Lnet/minecraft/src/GameSettings;fovSetting:F")
    )
    private static float normalizeFovForThirdPersonLiquidViewpoint(float original) {
        return OptionHelper.normalizeValue(original, 30.0F, 110.0F, 1.0F);
    }
}
