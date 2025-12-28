package cn.xylose.btw.bettergamesetting.mixin.sound.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.Entity;
import net.minecraft.src.ItemFood;
import net.minecraft.src.Minecraft;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFood.class)
public class ItemFoodMixin {
    @WrapOperation(method = "onEaten", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;playSoundAtEntity(Lnet/minecraft/src/Entity;Ljava/lang/String;FF)V"))
    private void onIgniteRightClickVolume(World instance, Entity entity, String par1Entity, float par2Str, float par3, Operation<Void> original) {
        instance.playSoundAtEntity(entity, par1Entity,Minecraft.getMinecraft().gameSettings.getBlockVolume() * par2Str, par3);
    }
}
