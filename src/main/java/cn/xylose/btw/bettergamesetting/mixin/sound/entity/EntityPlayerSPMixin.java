package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.Minecraft;
import net.minecraft.src.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityPlayerSP.class)
public class EntityPlayerSPMixin {
    @WrapOperation(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/SoundManager;playSoundFX(Ljava/lang/String;FF)V"))
    public void portalVolume(SoundManager instance, String var4, float v, float par1Str, Operation<Void> original) {
        instance.playSoundFX(var4, Minecraft.getMinecraft().gameSettings.getAmbientVolume() * v, par1Str);
    }
}
