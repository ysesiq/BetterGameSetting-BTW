package cn.xylose.btw.bettergamesetting.mixin.sound.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.ItemFireball;
import net.minecraft.src.Minecraft;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFireball.class)
public class ItemFireballMixin {
    @WrapOperation(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/World;playSoundEffect(DDDLjava/lang/String;FF)V"))
    private void onFireballRightClickVolume(World instance, double x, double y, double z, String par5, float par7Str, float par8, Operation<Void> original) {
        instance.playSoundEffect(x, y, z, par5, Minecraft.getMinecraft().gameSettings.getBlockVolume() * par7Str, par8);
    }
}
