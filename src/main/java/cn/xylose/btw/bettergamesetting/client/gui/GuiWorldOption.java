package cn.xylose.btw.bettergamesetting.client.gui;

import api.world.difficulty.DifficultyParam;
import btw.client.gui.LockButton;
import btw.world.BTWDifficulties;
import cn.xylose.btw.bettergamesetting.client.gui.gamerule.GuiGameRules;
import cn.xylose.btw.bettergamesetting.init.BGSClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.I18n;

public class GuiWorldOption extends GuiScreen {
	private GuiScreen parentGuiScreen;
	protected String screenTitle = "World Options";
	private GameSettings option;
	private static EnumOptions[] options = new EnumOptions[] {
			EnumOptions.DIFFICULTY
	};
	public GuiWorldOption(GuiScreen parentGuiScreen, GameSettings option) {
		this.parentGuiScreen = parentGuiScreen;
		this.option = option;
	}
	
	public void initGui() {
		this.screenTitle = I18n.getString("options.worldOptions.title");
		this.buttonList.clear();
		int j = 2;
		int length = options.length;
		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 30, I18n.getString("gui.done")));
		this.buttonList.add(new GuiButton(201, this.width / 2 + 2, 60, 150, 20, I18n.getString("editGamerule.inGame.button")));
		
		for (int i = 0; i < length; ++i) {
			EnumOptions op = options[i];
			GuiSmallButton button = new GuiSmallButton(op.returnEnumOrdinal(), this.width / 2 - 155 + j % 2 * 160, 40 + 20 * (j >> 1), op, this.option.getKeyBinding(op));
			if (op == EnumOptions.DIFFICULTY) {
				button.displayString = I18n.getString("selectWorld.difficulty") + ": "
						+ MinecraftServer.getServer().worldServers[0].worldInfo.getDifficulty().getLocalizedName();
				button.width -= 20;
				LockButton lockButton = new LockButton(300, this.width / 2 - 155 + j % 2 * 160 + button.width, 40 + 20 * (j >> 1));
				boolean isLocked = MinecraftServer.getServer().worldServers[0].worldInfo.getData(BTWDifficulties.DIFFICULTY_LOCKED);
				lockButton.isLocked = isLocked;
				lockButton.enabled = !isLocked;
				button.enabled = !isLocked;
				this.buttonList.add(lockButton);
				if (!MinecraftServer.getServer().worldServers[0].worldInfo.getDifficulty().<Boolean>getParamValue(DifficultyParam.CanDifficultyBeChanged.class)) {
					button.enabled = false;
					lockButton.enabled = false;
					lockButton.isLocked = true;
				}
			}
			this.buttonList.add(button);
			++j;
		}
	}
	
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 200) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(this.parentGuiScreen);
			}
			if (button.id == 201) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(new GuiGameRules(this, BGSClient.gameRules));
			}
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
