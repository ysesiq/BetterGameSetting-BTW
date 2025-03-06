package cn.xylose.btw.bettergamesetting.client.gui.resourcepack;

import net.minecraft.src.I18n;
import net.minecraft.src.Minecraft;

import java.util.List;

public class GuiResourcePackSelected extends GuiResourcePackList {

    public GuiResourcePackSelected(Minecraft mcIn, int widthIn, int heightIn, List<ResourcePackListEntry> resourcePackList) {
        super(mcIn, widthIn, heightIn, resourcePackList);
    }

    protected String getListHeader() {
        return I18n.getString("resourcePack.selected.title");
    }

    @Override
    protected void elementClicked(int i, boolean bl) {}
}
