package cn.xylose.btw.bettergamesetting.mixin.client.renderer;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public abstract class RenderGlobalMixin {
    @Shadow private int renderChunksWide;
    @Shadow private int renderDistance;
    @Shadow private int renderChunksTall;
    @Shadow private int renderChunksDeep;
    
   @ModifyConstant(method = "<init>", constant = @Constant(intValue = 32))
    private int modify(int constant) {
        return 64;
    }

    @Inject(method = "loadRenderers", at = @At(value = "FIELD", target = "Lnet/minecraft/src/RenderGlobal;renderChunksDeep:I", shift = At.Shift.AFTER))
    private void loadRenderers(CallbackInfo ci) {
        int var1 = Math.min(65, (this.renderDistance * 2) + 1);
        this.renderChunksWide = var1;
        this.renderChunksTall = 16;
        this.renderChunksDeep = var1;
    }
}
