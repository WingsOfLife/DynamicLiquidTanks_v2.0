package doc.mods.dynamictanks.client.gui;

import java.util.LinkedList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;

import doc.mods.dynamictanks.helpers.CommandParser;
import doc.mods.dynamictanks.helpers.ControllerCommands;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class GuiController extends GuiContainer {

	public static final ResourceLocation Tank_Gui = new ResourceLocation("dynamictanks", "textures/gui/liquidTank.png");

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

		mc.func_110434_K().func_110577_a(Tank_Gui);
		drawTexturedModalRect(x, y, 0, 0, 176, 220);

		commands.drawTextBox();

		for (int v = 0; v < output.length; v++)
			output[v].drawTextBox();
	}

	public void setCommandOutput(String toSet) {
		boolean wasSet = false;

		if (toSet.substring(0, 1).equals(";")) {
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
		String delims = "[()]+";
		String[] parts = toSplit.split(delims);

		clearOutput();

		for (int i = 0; i < parts.length; i++)
			output[i].setText(parts[i]);
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
