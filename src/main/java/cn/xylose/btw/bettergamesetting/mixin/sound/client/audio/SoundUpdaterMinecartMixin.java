package cn.xylose.btw.bettergamesetting.mixin.sound.client.audio;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.Entity;
import net.minecraft.src.Minecraft;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundUpdaterMinecart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SoundUpdaterMinecart.class)
public class SoundUpdaterMinecartMixin {
    @WrapOperation(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/SoundManager;playEntitySound(Ljava/lang/String;Lnet/minecraft/src/Entity;FFZ)V"))
    public void neutralVolume(SoundManager instance, String var7, Entity var6, float v, float par1Str, boolean par2Entity, Operation<Void> original) {
        instance.playEntitySound(var7, var6, Minecraft.getMinecraft().gameSettings.getNeutralVolume() * v, par1Str, par2Entity);
    }
}
