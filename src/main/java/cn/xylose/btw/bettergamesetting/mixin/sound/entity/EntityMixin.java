package cn.xylose.btw.bettergamesetting.mixin.sound.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.Entity;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {
    @WrapOperation(method = "moveEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Entity;playSound(Ljava/lang/String;FF)V"))
    private void moveVolume(Entity instance, String par1Str, float par2, float par3, Operation<Void> original) {
        instance.playSound(par1Str, Minecraft.getMinecraft().gameSettings.getAmbientVolume() * par2, par3);
    }
}
