package doc.mods.dynamictanks.client.gui;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import doc.mods.dynamictanks.tileentity.UpgradeTileEntity;

public class GuiController extends GuiContainer {

	public static final ResourceLocation Tank_Gui = new ResourceLocation("dynamictanks", "textures/gui/liquidTank.png");
	private GuiTextField commands;	

	public GuiController (InventoryPlayer playerInventory, UpgradeTileEntity tileUpgrade) {
		super(new ControllerContainer(playerInventory, tileUpgrade));
	}

	/*
	 * Textbox Methods
	 */
	
	@Override
	public void initGui() {
		super.initGui();

		commands = new GuiTextField(fontRenderer, guiLeft + 21, guiTop + 78, 92, 15);
		//commands.setFocused(true);
		commands.setMaxStringLength(20);
	}

	public void keyTyped(char c, int i) {
		if (GuiScreen.isCtrlKeyDown())
			commands.setText("Key Pressed"); //insert command reference
		if (commands.isFocused())
			commands.textboxKeyTyped(c, i);
		else
			super.keyTyped(c, i);
	}

	public void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		
		commands.mouseClicked(i, j, k);
	}

	/*
	 * Draw Screen
	 */
	
	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int x = (width - 176) / 2;
		int y = (height - 220) / 2;

		mc.func_110434_K().func_110577_a(Tank_Gui);
		drawTexturedModalRect(x, y, 0, 0, 176, 220);

		commands.drawTextBox();
	}


}
