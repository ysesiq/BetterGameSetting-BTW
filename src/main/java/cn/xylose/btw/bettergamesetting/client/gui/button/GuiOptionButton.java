package cn.xylose.btw.bettergamesetting.client.gui.button;

import net.minecraft.src.EnumOptions;
import net.minecraft.src.GuiButton;

public class GuiOptionButton extends GuiButton {
    private final EnumOptions enumOptions;

    public GuiOptionButton(int buttonId, int x, int y, String displayString) {
        this(buttonId, x, y, (EnumOptions) null, displayString);
    }

    public GuiOptionButton(int buttonId, int x, int y, int buttonLength, int buttonWidth, String displayString) {
        super(buttonId, x, y, buttonLength, buttonWidth, displayString);
        this.enumOptions = null;
    }

    public GuiOptionButton(int buttonId, int x, int y, EnumOptions optionIn, String displayString) {
        super(buttonId, x, y, 150, 20, displayString);
        this.enumOptions = optionIn;
    }

    public EnumOptions returnEnumOptions() {
        return this.enumOptions;
    }
}
