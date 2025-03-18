package cn.xylose.btw.bettergamesetting.mixin.common.client;

import cn.xylose.btw.bettergamesetting.util.OptionHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import cn.xylose.btw.bettergamesetting.api.IGameSetting;
import cn.xylose.btw.bettergamesetting.api.IKeyBinding;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static cn.xylose.btw.bettergamesetting.util.OptionHelper.gson;

@Mixin(GameSettings.class)
public abstract class GameSettingsMixin implements IGameSetting {
    @Shadow private File optionsFile;
    @Shadow public int renderDistance;
    @Shadow public int limitFramerate;
    @Shadow public float gammaSetting;
    @Shadow public String skin;
    @Shadow public float fovSetting;
    @Shadow public boolean clouds;
    @Shadow protected abstract float parseFloat(String var1);
    @Shadow public abstract void saveOptions();
    @Shadow public abstract float getOptionFloatValue(EnumOptions par1EnumOptions);
    @Shadow protected Minecraft mc;
//    @Unique public float recordVolume = 1.0F;
//    @Unique public float weatherVolume = 1.0F;
//    @Unique public float blockVolume = 1.0F;
//    @Unique public float hostileVolume = 1.0F;
//    @Unique public float neutralVolume = 1.0F;
//    @Unique public float playerVolume = 1.0F;
//    @Unique public float ambientVolume = 1.0F;
    @Unique public List<String> resourcePacks = new ArrayList<>();

//    @Inject(method = "<init>(Lnet/minecraft/src/Minecraft;Ljava/io/File;)V", at = @At("RETURN"))
//    private void newDefaultValue(Minecraft par1Minecraft, File par2File, CallbackInfo ci) {
//        this.renderDistance = 8;
//        this.limitFramerate = 120;
//        this.gammaSetting = 0.5F;
//        this.fovSetting = 70.0F;
//        this.forceUnicodeFont = false;
//    }

    @Inject(method = "<init>()V", at = @At("RETURN"))
    private void newDefaultValue_1(CallbackInfo ci) {
        this.renderDistance = 8;
        this.limitFramerate = 120;
        this.gammaSetting = 0.5F;
        this.fovSetting = 70.0F;
    }

//    @Inject(method = "setOptionValue", at = @At("TAIL"))
//    public void setOptionValue(EnumOptions par1EnumOptions, int par2, CallbackInfo ci) {
//        if (par1EnumOptions == EnumOptionsExtra.FORCE_UNICODE_FONT) {
//            this.forceUnicodeFont = !this.forceUnicodeFont;
//            this.mc.fontRenderer.setUnicodeFlag(this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.forceUnicodeFont);
//        }
//    }

    @Inject(method = "setOptionFloatValue", at = @At("TAIL"))
    public void setOptionFloatValue(EnumOptions par1EnumOptions, float par2, CallbackInfo ci) {
        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE) {
            this.renderDistance = (int) par2;
        }
        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT) {
            this.limitFramerate = (int) par2;
        }
        if (par1EnumOptions == EnumOptions.FOV) {
            this.fovSetting = (int) OptionHelper.denormalizeValue(par2, 30.0F, 110.0F, 1.0F);
        }
//        if (par1EnumOptions == EnumOptionsExtra.RECORDS) {
//            this.recordVolume = par2;
//        }
//        if (par1EnumOptions == EnumOptionsExtra.WEATHER) {
//            this.weatherVolume = par2;
//        }
//        if (par1EnumOptions == EnumOptionsExtra.BLOCKS) {
//            this.blockVolume = par2;
//        }
//        if (par1EnumOptions == EnumOptionsExtra.MOBS) {
//            this.hostileVolume = par2;
//        }
//        if (par1EnumOptions == EnumOptionsExtra.ANIMALS) {
//            this.neutralVolume = par2;
//        }
//        if (par1EnumOptions == EnumOptionsExtra.PLAYERS) {
//            this.playerVolume = par2;
//        }
//        if (par1EnumOptions == EnumOptionsExtra.AMBIENT) {
//            this.ambientVolume = par2;
//        }
    }

    @Inject(method = "getOptionFloatValue", at = @At("HEAD"), cancellable = true)
    public void getOptionFloatValue(EnumOptions par1EnumOptions, CallbackInfoReturnable<Float> cir) {
        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE) {
            cir.setReturnValue((float) this.renderDistance);
        }
        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT) {
            cir.setReturnValue((float) this.limitFramerate);
        }
        if (par1EnumOptions == EnumOptions.FOV) {
            cir.setReturnValue(OptionHelper.normalizeValue(this.fovSetting, 30.0F, 110.0F, 1.0F));
        }
//        if (par1EnumOptions == EnumOptionsExtra.RECORDS) {
//            cir.setReturnValue(this.recordVolume);
//        }
//        if (par1EnumOptions == EnumOptionsExtra.WEATHER) {
//            cir.setReturnValue(this.weatherVolume);
//        }
//        if (par1EnumOptions == EnumOptionsExtra.BLOCKS) {
//            cir.setReturnValue(this.blockVolume);
//        }
//        if (par1EnumOptions == EnumOptionsExtra.MOBS) {
//            cir.setReturnValue(this.hostileVolume);
//        }
//        if (par1EnumOptions == EnumOptionsExtra.ANIMALS) {
//            cir.setReturnValue(this.neutralVolume);
//        }
//        if (par1EnumOptions == EnumOptionsExtra.PLAYERS) {
//            cir.setReturnValue(this.playerVolume);
//        }
//        if (par1EnumOptions == EnumOptionsExtra.AMBIENT) {
//            cir.setReturnValue(this.ambientVolume);
//        }
    }

    @Inject(method = "getKeyBinding", at = @At("HEAD"), cancellable = true)
    public void getKeyBinding(EnumOptions par1EnumOptions, CallbackInfoReturnable<String> cir) {
        String var2 = I18n.getString(par1EnumOptions.getEnumString()) + ": ";
        float var5 = this.getOptionFloatValue(par1EnumOptions);
        if (par1EnumOptions == EnumOptions.RENDER_DISTANCE) {
            cir.setReturnValue(var2 + this.renderDistance + I18n.getString("options.chunks"));
        }
        if (par1EnumOptions == EnumOptions.FRAMERATE_LIMIT) {
            if (this.limitFramerate >= 260) {
                cir.setReturnValue(var2 + I18n.getString("options.framerateLimit.max"));
            } else {
                cir.setReturnValue(var2 + this.limitFramerate + " fps");
            }
        }
        if (par1EnumOptions == EnumOptions.FOV) {
            if (var5 == 0.5F) {
                cir.setReturnValue(var2 + I18n.getString("options.fov.min"));
            } else if (var5 == 1.0F) {
                cir.setReturnValue(var2 + I18n.getString("options.fov.max"));
            } else {
                cir.setReturnValue(var2 + (int) this.fovSetting);
            }
        }
//        if (par1EnumOptions == EnumOptionsExtra.FORCE_UNICODE_FONT) {
//            cir.setReturnValue(var2 + getTranslationBoolean(this.forceUnicodeFont));
//        }
    }

    @Inject(method = "loadOptions", at = @At("TAIL"))
    public void loadOptions(CallbackInfo ci) {
        try {
            if (!this.optionsFile.exists()) {
                return;
            }
            BufferedReader var1 = new BufferedReader(new FileReader(this.optionsFile));
            String s;
            while ((s = var1.readLine()) != null) {
                String[] astring = s.split(":");
                if (astring[0].equals("renderDistance")) {
                    int val = Integer.parseInt(astring[1]);
                    if (val == 0) {
                        this.renderDistance = 12;
                    } else if (val == 1) {
                        this.renderDistance = 8;
                    } else {
                        this.renderDistance = val;
                    }
                }
                if (astring[0].equals("maxFps")) {
                    int val2 = Integer.parseInt(astring[1]);
                    if (val2 == 2) {
                        this.limitFramerate = 35;
                    } else if (val2 == 1 || val2 == 3) {
                        this.limitFramerate = 120;
                    } else if (val2 == 0) {
                        this.limitFramerate = 200;
                    } else {
                        this.limitFramerate = val2;
                    }
                }
                if (astring[0].equals("fovSetting")) {
                    this.fovSetting = this.parseFloat(astring[1]);
                }
                if (astring[0].equals("resourcePacks")) {
                    this.resourcePacks = (List) gson.fromJson(s.substring(s.indexOf(58) + 1), OptionHelper.typeListString);
                    if (this.resourcePacks == null) {
                        this.resourcePacks = new ArrayList();
                    }
                }
//                if (astring[0].equals("forceUnicodeFont")) {
//                    this.forceUnicodeFont = astring[1].equals("true");
//                }
//                if (astring[0].equals("record")) {
//                    this.recordVolume = this.parseFloat(astring[1]);
//                }
//                if (astring[0].equals("weather")) {
//                    this.weatherVolume = this.parseFloat(astring[1]);
//                }
//                if (astring[0].equals("block")) {
//                    this.blockVolume = this.parseFloat(astring[1]);
//                }
//                if (astring[0].equals("hostile")) {
//                    this.hostileVolume = this.parseFloat(astring[1]);
//                }
//                if (astring[0].equals("neutral")) {
//                    this.neutralVolume = this.parseFloat(astring[1]);
//                }
//                if (astring[0].equals("player")) {
//                    this.playerVolume = this.parseFloat(astring[1]);
//                }
//                if (astring[0].equals("ambient")) {
//                    this.ambientVolume = this.parseFloat(astring[1]);
//                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @ModifyConstant(method = "saveOptions", constant = @Constant(stringValue = "viewDistance:"))
//    private String modifyViewDistanceName(String x) {
//        return "renderDistance:";
//    }
//
//    @ModifyConstant(method = "saveOptions", constant = @Constant(stringValue = "fov:"))
//    private String modifyFovName(String x) {
//        return "fovSetting:";
//    }
//
//    @ModifyConstant(method = "saveOptions", constant = @Constant(stringValue = "fpsLimit:"))
//    private String modifyFpsLimitName(String x) {
//        return "maxFps:";
//    }

    @Inject(method = "saveOptions", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;println(Ljava/lang/String;)V", ordinal = 40), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void saveExtraOption(CallbackInfo ci, PrintWriter printwriter) {
        printwriter.println("resourcePacks:" + gson.toJson(this.resourcePacks));
//        printwriter.println("record:" + this.recordVolume);
//        printwriter.println("weather:" + this.weatherVolume);
//        printwriter.println("block:" + this.blockVolume);
//        printwriter.println("hostile:" + this.hostileVolume);
//        printwriter.println("neutral:" + this.neutralVolume);
//        printwriter.println("player:" + this.playerVolume);
//        printwriter.println("ambient:" + this.ambientVolume);
    }

    /**
     * @author Xy_Lose
     * @reason Modify Render Distance
     */
    @Overwrite
    public boolean shouldRenderClouds() {
        return this.clouds && this.renderDistance >= 4;
    }

    @Unique
    private static String getTranslationBoolean(boolean value) {
        return value ? I18n.getString("options.on") : I18n.getString("options.off");
    }

    @Override
    public void setOptionKeyBinding(KeyBinding key, int keyCode) {
        ((IKeyBinding) key).setKeyCode(keyCode);
        this.saveOptions();
    }

//    @Override
//    public float getRecordVolume() {
//        return this.recordVolume;
//    }
//
//    @Override
//    public float getWeatherVolume() {
//        return this.weatherVolume;
//    }
//
//    @Override
//    public float getBlockVolume() {
//        return this.blockVolume;
//    }
//
//    @Override
//    public float getHostileVolume() {
//        return this.hostileVolume;
//    }
//
//    @Override
//    public float getNeutralVolume() {
//        return this.neutralVolume;
//    }
//
//    @Override
//    public float getPlayerVolume() {
//        return this.playerVolume;
//    }
//
//    @Override
//    public float getAmbientVolume() {
//        return this.ambientVolume;
//    }

    @Override
    public List<String> getResourcePacks() {
        return this.resourcePacks;
    }

}