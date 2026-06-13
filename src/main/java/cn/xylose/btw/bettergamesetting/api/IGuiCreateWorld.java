package cn.xylose.btw.bettergamesetting.api;

import net.minecraft.src.GuiButton;

import java.util.List;
import java.util.Map;

public interface IGuiCreateWorld {
    Map<Integer, String> bgs$getHoverTexts();
    int bgs$getCurrentTab();
    List<GuiButton> bgs$getTabButtons();
}
