package cn.xylose.btw.bettergamesetting.mixin.client.renderer;

import net.minecraft.src.Render;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Render.class)
public class RenderMixin {
//    @Shadow protected ResourceLocation[] textures_glowing = new ResourceLocation[16];
//
//    @Inject(method = "setTexture(ILjava/lang/String;Ljava/lang/String;)V", at = @At("TAIL"))
//    protected void setTexture(int index, String path, String glow_path, CallbackInfo ci) {
//        ResourceLocation texture_glowing = new ResourceLocation(glow_path + "_glow.png", false);
//        Iterator var2 = Minecraft.getMinecraft().mcResourcePackRepository.getRepositoryEntries().iterator();
//        ResourcePackRepositoryEntry var3 = null;
//        while (var2.hasNext()) {
//            var3 = (ResourcePackRepositoryEntry) var2.next();
//        }
//        if (var3 != null && var3.getResourcePack().resourceExists(texture_glowing)) {
//            this.textures_glowing[index] = texture_glowing;
//        }
//    }
}
