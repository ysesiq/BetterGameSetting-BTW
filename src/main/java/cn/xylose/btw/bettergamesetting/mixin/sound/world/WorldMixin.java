package cn.xylose.btw.bettergamesetting.mixin.sound.world;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.Minecraft;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(World.class)
public class WorldMixin {

    @WrapOperation(
            method = "playSoundAtEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/src/IWorldAccess;playSound(Ljava/lang/String;DDDFF)V"
            )
    )
    private void wrapEntitySound(IWorldAccess instance, String s, double x, double y, double z, float v, float p, Operation<Void> original) {
        float scale = resolveEntityVolume(s);
        instance.playSound(s, x, y, z, v * scale, p);
    }

    private static float resolveEntityVolume(String sound) {
        Minecraft mc = Minecraft.getMinecraft();

        if (sound == null) {
            return mc.gameSettings.getNeutralVolume();
        }

        if (sound.startsWith("mob.creeper")
                || sound.startsWith("mob.zombie")
                || sound.startsWith("mob.skeleton")
                || sound.startsWith("mob.spider")
                || sound.startsWith("mob.ghast")
                || sound.startsWith("mob.slime")) {
            return mc.gameSettings.getHostileVolume();
        }

        if (sound.startsWith("damage.")
                || sound.startsWith("random.hurt")
                || sound.startsWith("random.breath")) {
            return mc.gameSettings.getPlayerVolume();
        }

        if (sound.startsWith("mob.")) {
            return mc.gameSettings.getNeutralVolume();
        }

        return mc.gameSettings.getNeutralVolume();
    }
}
