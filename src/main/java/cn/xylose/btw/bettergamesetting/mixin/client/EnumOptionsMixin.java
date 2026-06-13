package cn.xylose.btw.bettergamesetting.mixin.client;

import cn.xylose.btw.bettergamesetting.api.IEnumOptions;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cn.xylose.btw.bettergamesetting.util.Constants.*;
import static cn.xylose.btw.bettergamesetting.util.Constants.FOV_MAX;

@Mixin(EnumOptions.class)
public abstract class EnumOptionsMixin implements IEnumOptions {
    @Final @Shadow public static EnumOptions RENDER_DISTANCE;
    @Final @Shadow public static EnumOptions FRAMERATE_LIMIT;
    @Final @Shadow public static EnumOptions GAMMA;
    @Final @Shadow public static EnumOptions GUI_SCALE;
    @Final @Shadow public static EnumOptions FOV;

    @Unique public float valueStep;
    @Unique private float valueMin;
    @Unique private float valueMax;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void setMinMaxStepValue(CallbackInfo ci) {
        GAMMA.setValue(GAMMA_MIN, GAMMA_MAX, GAMMA_STEP);
        RENDER_DISTANCE.setValue(RENDER_DISTANCE_MIN, RENDER_DISTANCE_MAX, RENDER_DISTANCE_STEP);
        FRAMERATE_LIMIT.setValue(FPS_LIMIT_MIN, FPS_LIMIT_MAX, FPS_LIMIT_STEP);
        GUI_SCALE.setValue(GUI_SCALE_MIN, GUI_SCALE_MAX, GUI_SCALE_STEP);
        FOV.setValue(FOV_MIN, FOV_MAX, FOV_STEP);
//        EnumOptionsExtra.MIPMAP_LEVELS.setValueMin(0.0F);
//        EnumOptionsExtra.MIPMAP_LEVELS.setValueMax(4.0F);
//        EnumOptionsExtra.MIPMAP_LEVELS.setValueStep(1.0F);
//        EnumOptionsExtra.ANISOTROPIC_FILTERING.setValueMin(1.0F);
//        EnumOptionsExtra.ANISOTROPIC_FILTERING.setValueMax(16.0F);
//        EnumOptionsExtra.ANISOTROPIC_FILTERING.setValueStep(1.0F);
    }


    @Inject(method = "getEnumFloat", at = @At("HEAD"), cancellable = true)
    private void changeToEnumFloat(CallbackInfoReturnable<Boolean> cir) {
        if ((EnumOptions) (Object) this == RENDER_DISTANCE) cir.setReturnValue(true);
        if ((EnumOptions) (Object) this == FRAMERATE_LIMIT) cir.setReturnValue(true);
        if ((EnumOptions) (Object) this == GUI_SCALE) cir.setReturnValue(true);
    }

    @Override
    public float normalizeValue(float value, EnumOptions options) {
        return MathHelper.clamp_float((this.snapToStepClamp(value, options) - options.getValueMin()) / (options.getValueMax() - options.getValueMin()), 0.0F, 1.0F);
    }

    @Override
    public float denormalizeValue(float value, EnumOptions options) {
        return this.snapToStepClamp(options.getValueMin() + (options.getValueMax() - options.getValueMin()) * MathHelper.clamp_float(value, 0.0F, 1.0F), options);
    }

    @Unique
    public float snapToStepClamp(float value, EnumOptions options) {
        value = this.snapToStep(value, options);
        return MathHelper.clamp_float(value, options.getValueMin(), options.getValueMax());
    }

    @Unique
    protected float snapToStep(float value, EnumOptions options) {
        if (options.getValueStep() > 0.0F) {
            value = options.getValueStep() * (float) Math.round(value / options.getValueStep());
        }
        return value;
    }

    @Override
    public float getValueMax() {
        return this.valueMax;
    }

    @Override
    public void setValueMax(float valueMax) {
        this.valueMax = valueMax;
    }

    @Override
    public float getValueMin() {
        return this.valueMin;
    }

    @Override
    public void setValueMin(float valueMin) {
        this.valueMin = valueMin;
    }

    @Override
    public float getValueStep() {
        return this.valueStep;
    }

    @Override
    public void setValueStep(float valueStep) {
        this.valueStep = valueStep;
    }

    @Override
    public void setValue(float min, float max, float step) {
        this.valueMin = min;
        this.valueMax = max;
        this.valueStep = step;
    }
}