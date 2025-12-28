package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Minecraft;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityPlayer.class)
public class EntityPlayerMixin {
    @WrapOperation(method = "updateItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;playSound(Ljava/lang/String;FF)V"))
    private void useItemVolume(EntityPlayer instance, String par1Str, float par2, float par3, Operation<Void> original) {
        instance.playSound(par1Str, Minecraft.getMinecraft().gameSettings.getPlayerVolume() * par2, par3);
    }

    @WrapOperation(method = "addExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;playSoundAtEntity(Lnet/minecraft/src/Entity;Ljava/lang/String;FF)V"))
    private void experienceVolume(World instance, Entity entity, String par1Entity, float par2Str, float par3, Operation<Void> original) {
        instance.playSoundAtEntity(entity, par1Entity, Minecraft.getMinecraft().gameSettings.getPlayerVolume(), 1.0F);
    }

    @WrapOperation(method = "decreaseAirSupply", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;playSoundAtEntity(Lnet/minecraft/src/Entity;Ljava/lang/String;FF)V"))
    private void decreaseAirSupplyVolume(World instance, Entity entity, String par1Entity, float par2Str, float par3, Operation<Void> original) {
        instance.playSoundAtEntity(entity, par1Entity, Minecraft.getMinecraft().gameSettings.getPlayerVolume() * par2Str, par3);
    }

    @WrapOperation(method = "playStepSound", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;playSoundAtEntity(Lnet/minecraft/src/Entity;Ljava/lang/String;FF)V"))
    private void StepSoundVolume(World instance, Entity entity, String par1Entity, float par2Str, float par3, Operation<Void> original) {
        instance.playSoundAtEntity(entity, par1Entity, Minecraft.getMinecraft().gameSettings.getPlayerVolume() * par2Str, par3);
    }

}
