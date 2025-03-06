package cn.xylose.btw.bettergamesetting.mixin.common.entity;

//import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
//import com.llamalad7.mixinextras.injector.ModifyReturnValue;
//import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = EntityRenderer.class, priority = 9999, remap = false)
public abstract class EntityRenderMixin {
    @Shadow
    private float farPlaneDistance;
    @Shadow
    private Minecraft mc;

    @Shadow
    protected abstract void setupFog(int par1, float par2);

    @Shadow
    private float fogColorRed;
    @Shadow
    private float fogColorGreen;
    @Shadow
    private float fogColorBlue;

    @Shadow
    public int debugViewDirection;

    @Shadow
    private float prevDebugCamFOV;

    @Shadow
    private float debugCamFOV;

    @Shadow
    private float fovModifierHandPrev;

    @Shadow
    private float fovModifierHand;

    @ModifyConstant(method = "updateRenderer", constant = @Constant(intValue = 3))
    private int modifyRD(int constant) {
        return this.mc.gameSettings.renderDistance * 2;
    }

    @ModifyConstant(method = "updateRenderer", constant = @Constant(floatValue = 3.0F))
    private float modifyRD_1(float constant) {
        return 16;
    }

    @Inject(method = "setupCameraTransform", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glLoadIdentity()V", shift = At.Shift.AFTER))
    private void setupCameraTransform0(float par1, int par2, CallbackInfo ci) {
        this.farPlaneDistance = this.mc.gameSettings.renderDistance * 16;
    }

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/ClippingHelperImpl;getInstance()Lnet/minecraft/src/ClippingHelper;"))
    private void renderWorld(float par1, long par2, CallbackInfo ci) {
        RenderGlobal renderGlobal = this.mc.renderGlobal;
        if ((this.mc.gameSettings.renderDistance >= 4)) {
            setupFog(-1, par1);
            this.mc.mcProfiler.endStartSection("sky");
            renderGlobal.renderSky(par1);
        }
    }

    @Inject(method = "updateFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/WorldClient;getRainStrength(F)F"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void updateFogColor(float par1, CallbackInfo ci, WorldClient var2, EntityLivingBase var3, float var4, Vec3 var5, float var6, float var7, float var8) {
        float var4_1 = 1.0f - ((float) Math.pow(0.25f + ((0.75f * this.mc.gameSettings.renderDistance) / 32.0f), 0.25d));
        this.fogColorRed += (var6 - this.fogColorRed) * var4_1;
        this.fogColorGreen += (var7 - this.fogColorGreen) * var4_1;
        this.fogColorBlue += (var8 - this.fogColorBlue) * var4_1;
    }

//    @ModifyExpressionValue(method = "updateFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GameSettings;renderDistanceI"))
//    private int modifyRenderDistance(int original) {
//        if (original >= 4)
//            return 1;
//        return original;
//    }

    @Redirect(method = "updateFogColor", at = @At(value = "FIELD", target = "Lnet/minecraft/src/GameSettings;renderDistance:I"))
    private int modifyRenderDistance(GameSettings instance) {
        if (instance.renderDistance >= 4)
            return 1;
        return instance.renderDistance;
    }

    /**
     * @author Xy_Lose
     * @reason break Fps limit
     */
    @Overwrite
    public static int performanceToFps(int par0) {
        if (!(Minecraft.getMinecraft().gameSettings.limitFramerate >= 260)) {
            return Minecraft.getMinecraft().gameSettings.limitFramerate;
        }
        return 9999;
    }

//    @WrapOperation(method = "addRainParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/WorldClient;playSound(DDDLjava/lang/String;FFZ)V"))
//    private void modifyRainSound(WorldClient instance, double v, double par1, double par3, String par5, float par7Str, float par8, boolean par9, Operation<Void> original) {
//        this.mc.theWorld.playSound(v, par1, par3, par5, ((IGameSetting) Minecraft.getMinecraft().gameSettings).getWeatherVolume() * par7Str, par8, par9);
//    }


//    @ModifyReturnValue(method = "getFOVModifier", at = @At(value = "RETURN", ordinal = 1))
//    private float getFOVModifierModern(float original, @Local(argsOnly = true) float par1, @Local(argsOnly = true) boolean par2, @Local(name = "var4") float var4) {
//        if (par2) {
//            var4 = this.mc.gameSettings.fovSetting;
//            var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * par1;
//            return var4 + this.prevDebugCamFOV + (this.debugCamFOV - this.prevDebugCamFOV) * par1;
//        }
//        return original;
//    }

    @Inject(
            method = "getFOVModifier",
            at = @At("TAIL"),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void injectFOVModifier(float par1, boolean par2, CallbackInfoReturnable<Float> cir, EntityPlayer var3, float var4, int var6) {
        cir.cancel();

        if (par2) {
            var4 = this.mc.gameSettings.fovSetting;
            var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * par1;
        }

        if (this.mc.renderViewEntity.getHealth() <= 0.0f) {
            float var5 = (float) this.mc.renderViewEntity.deathTime + par1;
            var4 /= (1.0f - 500.0f / (var5 + 500.0f)) * 2.0f + 1.0f;
        }

        if (var6 != 0 && Block.blocksList[var6].blockMaterial == Material.water) {
            var4 = var4 * 60.0f / 70.0f;
        }

        float result = var4 + this.prevDebugCamFOV + (this.debugCamFOV - this.prevDebugCamFOV) * par1;
        cir.setReturnValue(result);
    }
}
