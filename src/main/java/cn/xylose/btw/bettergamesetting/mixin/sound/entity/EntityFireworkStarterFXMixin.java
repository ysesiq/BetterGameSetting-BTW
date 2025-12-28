package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.EntityFireworkStarterFX;
import net.minecraft.src.Minecraft;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityFireworkStarterFX.class)
public class EntityFireworkStarterFXMixin {
    @WrapOperation(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;playSound(DDDLjava/lang/String;FFZ)V"))
    private void fireworkStarterFXVolume(World instance, double par1, double par3, double par5, String par7Str, float par8, float par9, boolean par10, Operation<Void> original) {
        instance.playSound(par1, par3, par5, par7Str, Minecraft.getMinecraft().gameSettings.getAmbientVolume() * par8, par9, par10);
    }
}
