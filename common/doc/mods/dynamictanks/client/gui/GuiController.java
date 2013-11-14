package doc.mods.dynamictanks.client.gui;

import java.util.LinkedList;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import org.lwjgl.input.Keyboard;

import doc.mods.dynamictanks.helpers.CommandParser;
import doc.mods.dynamictanks.helpers.ControllerCommands;
import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.helpers.StringHelper;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class GuiController extends GuiContainer {

	public static final ResourceLocation Tank_Gui = new ResourceLocation("dynamictanks", "textures/gui/liquidTank.png");
	public static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;

	public static final int[] xDraw = { 126, 143, 126, 143, 126, 143 };
	public static final int[] yDraw = { 15, 15, 55, 55, 95, 95 };

	private CustomTextField commands;	
	private GuiTextField[] output = new GuiTextField[7];

	private ControllerTileEntity controller;

	private int topOutput = -24;
	private int currentNode = 0;

	public GuiController (InventoryPlayer playerInventory, ControllerTileEntity tileController) {
		super(new ContainerController(playerInventory, tileController));
		controller = tileController;
	}

	/*
	 * Textbox Methods
	 */

	@Override
	public void initGui() {
		super.initGui();

		commands = new CustomTextField(fontRenderer, guiLeft + 22, guiTop + 60, 90, 10);
		commands.setFocused(true);
		commands.setMaxStringLength(20);

		for (int i = 0; i < output.length; i++) {
			output[i] = new GuiTextField(fontRenderer, guiLeft + 23, guiTop + (topOutput += 10), 92, 10);
			output[i].setEnableBackgroundDrawing(false);
		}

		if (!controller.recentDisplayed.isEmpty())
			for (int i = 0; i < controller.recentDisplayed.size(); i++)
				output[i].setText(controller.recentDisplayed.get(i));
	}

	public void updateScreen() {

	}

	public void keyTyped(char c, int i) {
		if ((Keyboard.isKeyDown(28) || Keyboard.isKeyDown(156)) && commands.isFocused()) {
			if (commands.getText().equals(""))
				return;

			if (commands.getText().equals("clear")) {
				commands.setText("");
				clearOutput();
				return;
			}				

			CommandParser cmdParse = new CommandParser(commands.getText());
			controller.recentSent.add(commands.getText());
			setCommandOutput(ControllerCommands.whatToReturn(cmdParse.getCommandName(), cmdParse.getIndex(), controller)); //insert command reference
		}
		if (Keyboard.isKeyDown(200)) 
			commands.setText(cycleRecent("Up"));
		if (Keyboard.isKeyDown(208)) 
			commands.setText(cycleRecent("Down"));
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

		mc.renderEngine.bindTexture(Tank_Gui);
		drawTexturedModalRect(x, y, 0, 0, 176, 220);

		commands.drawTextBox();

		for (int v = 0; v < output.length; v++)
			output[v].drawTextBox();

		String liquidName = StringHelper.Cap(FluidHelper.getName(controller.getAllLiquids(), controller.getLiquidIndex()));
		String type = "Liquid: ";
		if (FluidHelper.isLiquidPotion(controller, controller.getLiquidIndex())) {
			liquidName = StringHelper.returnLastWord(FluidHelper.getName(controller.getAllLiquids(), controller.getLiquidIndex()));
			type = "Potion ";
		}
		
		drawString(fontRenderer, type + liquidName, guiLeft + 20, guiTop + 76, 0xFFFFFF);
		drawString(fontRenderer, "Stored: " + StringHelper.parseCommas("" + FluidHelper.getTotal(controller.getAllLiquids(), controller.getAllLiquids().get(controller.getLiquidIndex()).getFluid(), 0, 0), "", " mB"), guiLeft + 20, guiTop + 86, 0xFFFFFF);

		if (!FluidHelper.hasLiquid(controller))
			return;

		for (int l = 0; l < controller.getAllLiquids().size(); l++) {
			if (controller.getAllLiquids().get(l).getFluid() != null) {			
				Icon liqIcon = null;
				Fluid fluid = controller.getAllLiquids().get(l).getFluid().getFluid();

				if (fluid != null && fluid.getStillIcon() != null)
					liqIcon = fluid.getStillIcon();

				mc.renderEngine.bindTexture(BLOCK_TEXTURE);

				if (liqIcon != null)
					drawTexturedModelRectFromIcon(guiLeft + xDraw[l], guiTop + yDraw[l] - 
							FluidHelper.getLiquidAmountScaledForGUI(controller.getAllLiquids().get(l).getFluidAmount(), 
									controller.getTankCapacity()), liqIcon, 12, 
									FluidHelper.getLiquidAmountScaledForGUI(controller.getAllLiquids().get(l).getFluidAmount(), 
											controller.getTankCapacity()));
			}
		}

		mc.renderEngine.bindTexture(Tank_Gui);
		for (int p = 0; p < 6; p++)
			drawTexturedModalRect(guiLeft + xDraw[p] - 2, guiTop + yDraw[p] - 36, 175, 0, 14, 37);
	}

	public void setCommandOutput(String toSet) {
		boolean wasSet = false;

		if (toSet.substring(toSet.length() - 1, toSet.length()).equals(";")) {
			setMultipleLines(toSet);
			wasSet = true;
		} else {
			for (GuiTextField gTF: output) {
				if (gTF.getText() == null || gTF.getText() == "") {
					gTF.setText(toSet);
					controller.recentDisplayed.add(toSet);
					wasSet = true;
					break;
				}				
			}
		}
		if (!wasSet) {
			clearOutput();
			output[0].setText(toSet);;
			controller.recentDisplayed.add(toSet);
		}

		commands.setText("");
	}

	public void setMultipleLines(String toSplit) {
		String delims = "[;]+";
		String[] parts = toSplit.split(delims);

		clearOutput();

		for (int i = 0; i < parts.length; i++)
			output[i].setText(StringHelper.Cap(parts[i]));
	}

	public void clearOutput() {
		for (GuiTextField gTF: output)
			gTF.setText("");
		controller.recentDisplayed = new LinkedList<String>();
	}

	public String cycleRecent(String direction) {
		if (controller.recentSent.isEmpty())
			return "";

		commands.setFocused(true);
		String output = "";
		if (direction.equals("Up")) {
			if ((currentNode + 1) > controller.recentSent.size())
				currentNode = 0;		
			output = controller.recentSent.get(currentNode);
			currentNode++;
		} else if (direction.equals("Down")) {
			if ((currentNode - 1) < 0)
				currentNode = (controller.recentSent.size() - 1);
			else
				currentNode--;
			output = controller.recentSent.get(currentNode);

		}

		return output;
	}

}
