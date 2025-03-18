package cn.xylose.btw.bettergamesetting.config;

import com.terraformersmc.modmenu.gui.widget.ConfigOptionListWidget;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.I18n;

public class BGSOptionsScreen extends GuiScreen {

    private static final int DONE = 0;

    private GuiScreen previous;
    private String title;
    private ConfigOptionListWidget list;
    private int mouseX;
    private int mouseY;

    public BGSOptionsScreen(GuiScreen previous) {
        this.previous = previous;
        this.title = I18n.getString("bgs.options");
    }

    @Override
    public void initGui() {
        this.list = new ConfigOptionListWidget(this.mc, this.width, this.height, 32, this.height - 32, 25, BGSConfig.asOptions());
        this.buttonList.add(new GuiButton(DONE, this.width / 2 - 100, this.height - 27, 200, 20, I18n.getString("gui.done")));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.drawDefaultBackground();
        this.list.drawScreen(mouseX, mouseY, delta);
        this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 5, 0xffffff);
        super.drawScreen(mouseX, mouseY, delta);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int button) {
        this.list.mouseClicked(mouseX, mouseY, button);
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        switch (button.id) {
            case DONE:
                BGSConfigManager.save();
                BGSOptionsScreen.this.mc.displayGuiScreen(BGSOptionsScreen.this.previous);
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        BGSConfigManager.save();
    }
}
