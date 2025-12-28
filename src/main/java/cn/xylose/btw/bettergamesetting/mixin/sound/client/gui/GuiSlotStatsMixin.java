package cn.xylose.btw.bettergamesetting.mixin.sound.client.gui;

import net.minecraft.src.GuiSlotStats;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GuiSlotStats.class)
public class GuiSlotStatsMixin {
    @ModifyArg(method = "func_77224_a", index = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/src/SoundManager;playSoundFX(Ljava/lang/String;FF)V"))
    private float modifyUIVolume(float volume) {
        return Minecraft.getMinecraft().gameSettings.getUIVolume();
    }
}
