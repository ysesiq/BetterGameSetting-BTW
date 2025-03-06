package cn.xylose.btw.bettergamesetting.mixin.common.client;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.ResourceLocation;
import net.minecraft.src.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FontRenderer.class)
public class FontRendererMixin {
    @Shadow private int[] charWidth = new int[256];

    @Inject(method = "<init>", at = @At("RETURN"))
    private void widenCharWidth(GameSettings par1GameSettings, ResourceLocation par2ResourceLocation, TextureManager par3TextureManager, boolean par4, CallbackInfo ci) {
        charWidth = new int[65535];
    }
}
