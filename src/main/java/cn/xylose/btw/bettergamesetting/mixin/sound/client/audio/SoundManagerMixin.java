package cn.xylose.btw.bettergamesetting.mixin.sound.client.audio;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.Minecraft;
import net.minecraft.src.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import paulscode.sound.SoundSystem;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Redirect(method = "playStreaming", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;setVolume(Ljava/lang/String;F)V", remap = false))
    public void recordVolume(SoundSystem instance, String sourcename, float value) {
        instance.setVolume(sourcename, Minecraft.getMinecraft().gameSettings.getRecordVolume() * value);
    }

    @WrapOperation(method = "playSoundFX", at = @At(value = "INVOKE", target = "Lpaulscode/sound/SoundSystem;setVolume(Ljava/lang/String;F)V", remap = false))
    private void modifyUIVolume(SoundSystem instance, String sourcename, float value, Operation<Void> original) {
        instance.setVolume(sourcename, Minecraft.getMinecraft().gameSettings.getUIVolume() * 0.25F);
    }
}
