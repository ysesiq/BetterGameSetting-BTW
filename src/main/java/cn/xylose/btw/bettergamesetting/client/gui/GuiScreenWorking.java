package cn.xylose.btw.bettergamesetting.client.gui;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.IProgressUpdate;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate {
    private String field_146591_a = "";
    private String field_146589_f = "";
    private int field_146590_g;
    private boolean field_146592_h;

    /**
     * "Saving level", or the loading,or downloading equivelent
     */
    public void displayProgressMessage(String p_73720_1_) {
        this.resetProgressAndMessage(p_73720_1_);
    }

    /**
     * this string, followed by "working..." and then the "% complete" are the 3 lines shown. This resets progress to 0,
     * and the WorkingString to "working...".
     */
    public void resetProgressAndMessage(String p_73721_1_) {
        this.field_146591_a = p_73721_1_;
        this.resetProgresAndWorkingMessage("Working...");
    }

    /**
     * This is called with "Working..." by resetProgressAndMessage
     */
    public void resetProgresAndWorkingMessage(String p_73719_1_) {
        this.field_146589_f = p_73719_1_;
        this.setLoadingProgress(0);
    }

    @Override
    public void displaySavingString(String var1) {

    }

    @Override
    public void displayLoadingString(String var1) {

    }

    /**
     * Updates the progress bar on the loading screen to the specified amount. Args: loadProgress
     */
    public void setLoadingProgress(int p_73718_1_) {
        this.field_146590_g = p_73718_1_;
    }

    public void func_146586_a() {
        this.field_146592_h = true;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.field_146592_h) {
            this.mc.displayGuiScreen((GuiScreen) null);
        } else {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRenderer, this.field_146591_a, this.width / 2, 70, 16777215);
            this.drawCenteredString(this.fontRenderer, this.field_146589_f + " " + this.field_146590_g + "%", this.width / 2, 90, 16777215);
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
    }
}
