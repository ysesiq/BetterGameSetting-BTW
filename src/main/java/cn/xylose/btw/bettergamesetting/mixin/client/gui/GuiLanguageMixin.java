package cn.xylose.btw.bettergamesetting.mixin.client.gui;

import cn.xylose.btw.bettergamesetting.client.EnumOptionsExtra;
import cn.xylose.btw.bettergamesetting.api.GuiSlotLanguageInvoker;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiLanguage.class)
public class GuiLanguageMixin extends GuiScreen {
    @Shadow private GuiSmallButton doneButton;
    @Shadow @Final private GameSettings theGameSettings;
    @Shadow private GuiSmallButton downloadTranslations;
    @Shadow private GuiSlotLanguage languageList;

    @Unique private GuiTextField searchField;

    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0))
    private boolean redirect(List instance, Object e) {
        this.buttonList.add(new GuiSmallButton(100, this.width / 2 - 155, this.height - 38, EnumOptionsExtra.FORCE_UNICODE_FONT, this.theGameSettings.getKeyBinding(EnumOptionsExtra.FORCE_UNICODE_FONT)));
        this.buttonList.add(this.doneButton = new GuiSmallButton(6, this.width / 2 - 155 + 160, this.height - 38, I18n.getString("gui.done")));
        this.searchField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 14, 200, 15);
        this.searchField.setMaxStringLength(50);
        this.searchField.setHint(I18n.getString("options.search"));
        return true;
    }

    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiSmallButton;<init>(IIIIILjava/lang/String;)V", ordinal = 1), index = 1)
    private int modifyTranslateButtonX(int original) {
        return this.searchField.xPos - 100 - 5;
    }

    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiSmallButton;<init>(IIIIILjava/lang/String;)V", ordinal = 1), index = 2)
    private int modifyTranslateButtonY(int original) {
        return this.searchField.yPos;
    }

    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiSmallButton;<init>(IIIIILjava/lang/String;)V", ordinal = 1), index = 3)
    private int modifyTranslateButtonWidth(int original) {
        return 100;
    }

    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiSmallButton;<init>(IIIIILjava/lang/String;)V", ordinal = 1), index = 4)
    private int modifyTranslateButtonHeight(int original) {
        return 15;
    }

    @ModifyArg(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiSmallButton;<init>(IIIIILjava/lang/String;)V", ordinal = 1), index = 5)
    private String modifyTranslateButtonString(String string) {
        return "Get BTW Fan translations";
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"))
    private void actionPerformed(GuiButton button, CallbackInfo ci) {
        if (button.enabled && button.id == 100) {
            this.theGameSettings.setOptionValue(EnumOptionsExtra.FORCE_UNICODE_FONT, 1);
            button.displayString = this.theGameSettings.getKeyBinding(EnumOptionsExtra.FORCE_UNICODE_FONT);
            ScaledResolution scaledresolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            this.setWorldAndResolution(this.mc, i, j);
        }
    }

    @Inject(method = "drawScreen", at = @At("TAIL"))
    private void addSearchField(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        this.searchField.drawTextBox();
    }

    @ModifyArg(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiLanguage;drawCenteredString(Lnet/minecraft/src/FontRenderer;Ljava/lang/String;III)V", ordinal = 0), index = 3)
    private int modifyTitleY(int originalX1) {
        return 4;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        if (this.searchField.textboxKeyTyped(typedChar, keyCode)) {
            ((GuiSlotLanguageInvoker) this.languageList).updateFilteredLanguages(this.searchField.getText());
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int eventButton) {
        super.mouseClicked(mouseX, mouseY, eventButton);
        this.searchField.mouseClicked(mouseX, mouseY, eventButton);
    }
}
