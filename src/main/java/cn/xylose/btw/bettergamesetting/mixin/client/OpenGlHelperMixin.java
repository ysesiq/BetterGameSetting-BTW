package cn.xylose.btw.bettergamesetting.mixin.client;

import cn.xylose.btw.bettergamesetting.util.OpenGlHelperExtra;
import net.minecraft.src.OpenGlHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OpenGlHelper.class)
public class OpenGlHelperMixin {
    @Inject(method = "initializeTextures", at = @At("TAIL"))
    private static void setIsNvidiaGL(CallbackInfo ci) {
        OpenGlHelperExtra.isNvidiaGL = GL11.glGetString(GL11.GL_VENDOR).toLowerCase().contains("nvidia");
    }
}
