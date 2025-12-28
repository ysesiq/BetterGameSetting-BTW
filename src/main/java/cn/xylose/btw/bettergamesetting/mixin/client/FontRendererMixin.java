package cn.xylose.btw.bettergamesetting.mixin.client;

import de.thexxturboxx.fontfixer.FontFixer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.ResourceLocation;
import net.minecraft.src.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin({FontRenderer.class})
public class FontRendererMixin {
    private static final Map<FontRenderer, FontFixer> registeredFixers = new HashMap();

    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void init2(GameSettings par1GameSettings, ResourceLocation par2ResourceLocation, TextureManager par3TextureManager, boolean par4, CallbackInfo ci) {
        registeredFixers.put((FontRenderer) (Object) this, new FontFixer(par1GameSettings, par2ResourceLocation, par3TextureManager, par4));
    }

    @ModifyConstant(method = {"<init>"}, constant = {@Constant(intValue = 256)})
    private int modifyChanceTableSize(int val) {
        return Short.MAX_VALUE;
    }

    /**
     * @author ThexXTURBOXx
     * @reason fix font
     */
    @Overwrite
    public int drawStringWithShadow(String text, int x, int y, int color) {
        return (registeredFixers.get((FontRenderer) (Object) this)).drawStringWithShadow(text, x, y, color);
    }

    /**
     * @author ThexXTURBOXx
     * @reason fix font
     */
    @Overwrite
    public int drawString(String string, int i, int j, int k) {
        return (registeredFixers.get((FontRenderer) (Object) this)).drawString(string, i, j, k);
    }

    /**
     * @author ThexXTURBOXx
     * @reason fix font
     */
    @Overwrite
    public int getStringWidth(String text) {
        return (registeredFixers.get((FontRenderer) (Object) this)).getStringWidth(text);
    }

    /**
     * @author ThexXTURBOXx
     * @reason fix font
     */
    @Overwrite
    public int getCharWidth(char c) {
        return (registeredFixers.get((FontRenderer) (Object) this)).getCharWidth(c);
    }

    /**
     * @author ThexXTURBOXx
     * @reason fix font
     */
    @Overwrite
    public void setUnicodeFlag(boolean bl) {
        (registeredFixers.get((FontRenderer) (Object) this)).setUnicodeFlag(bl);
    }

    /**
     * @author ThexXTURBOXx
     * @reason fix font
     */
    @Overwrite
    public void setBidiFlag(boolean rightToLeft) {
        (registeredFixers.get((FontRenderer) (Object) this)).setBidiFlag(rightToLeft);
    }

}
