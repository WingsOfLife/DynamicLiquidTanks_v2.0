package doc.mods.dynamictanks.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.helpers.StringHelper;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class GuiController extends GuiContainer
{
	private static final ResourceLocation Tank_Gui      = new ResourceLocation("dynamictanks", "textures/gui/liquidTankNew.png");
	private static final ResourceLocation BLOCK_TEXTURE = TextureMap.locationBlocksTexture;

	private static final int[] xDraw                    = { 126, 143, 126 + 6, 143, 126, 143 };
	private static final int[] yDraw                    = { 15, 15, 55, 55, 95, 95 };

	//private CustomTextField commands;
	//private GuiTextField[] output                      = new GuiTextField[7];

	private ControllerTileEntity controller;

	private int topOutput                              = -24;
	private int currentNode                            = 0;

	public GuiController(InventoryPlayer playerInventory, ControllerTileEntity tileController)
	{
		super(new ContainerController(playerInventory, tileController));
		controller = tileController;
	}

	/*
	 * Textbox Methods
	 */

	/*@Override
	public void initGui() {

		super.initGui();
		commands = new CustomTextField(fontRenderer, guiLeft + 22, guiTop + 60, 90, 10);
		commands.setFocused(false);
		commands.setMaxStringLength(20);

		for (int i = 0; i < output.length; i++)	{
			output[i] = new GuiTextField(fontRenderer, guiLeft + 23, guiTop + (topOutput += 10), 92, 10);
			output[i].setEnableBackgroundDrawing(false);
		}

		if (controller != null && !controller.recentDisplayed.isEmpty())
			for (int i = 0; i < controller.recentDisplayed.size(); i++)
				output[i].setText(controller.recentDisplayed.get(i));
	}*/

	public void updateScreen() {}

	/*public void keyTyped(char c, int i)	{

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
	}*/

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {

		super.drawScreen(mouseX, mouseY, par3);

		int elementWidth           = 12;
		int elementHeight          = 36;

		int elementXAddLeft        = 37 + 6;
		int elementXAddRight       = 55 + 6;

		int elementYAddTop         = 104;
		int elementYAddMiddle      = 64;
		int elementYAddBottom      = 24;

		int centerX = this.width  / 2;
		int centerY = this.height / 2;

		/* First Column */
		if (mouseX >= (centerX + elementXAddLeft) && mouseX <= (centerX + elementXAddLeft + elementWidth)) {
			/*if (mouseY >= (centerY - elementYAddTop) && mouseY <= (centerY - elementYAddTop + elementHeight)) {
				List list = new ArrayList<String>();
				list.add(StringHelper.Cap(FluidHelper.getName(controller.getAllLiquids(), 0)));
				String s = EnumChatFormatting.DARK_GRAY + "" + FluidHelper.getFluidAmtIndex(controller.getAllLiquids(), 0);
				s =  s + " / " + controller.getTankCapacity() + " mB";
				list.add(s);

				drawToolTip(list, mouseX, mouseY);
			}*/

			if (mouseY >= (centerY - elementYAddMiddle) && mouseY <= (centerY - elementYAddMiddle + elementHeight)) {
				List list = new ArrayList<String>();
				list.add(StringHelper.Cap(FluidHelper.getName(controller.getAllLiquids(), 0)));
				String s = EnumChatFormatting.DARK_GRAY + "" + FluidHelper.getFluidAmtIndex(controller.getAllLiquids(), 0);
				s =  s + " / " + controller.getTankCapacity() + " mB";
				list.add(s);

				drawToolTip(list, mouseX, mouseY);
			}

			/*if (mouseY >= (centerY - elementYAddBottom) && mouseY <= (centerY - elementYAddBottom + elementHeight)) {
				List list = new ArrayList<String>();
				list.add(StringHelper.Cap(FluidHelper.getName(controller.getAllLiquids(), 4)));
				String s = EnumChatFormatting.DARK_GRAY + "" + FluidHelper.getFluidAmtIndex(controller.getAllLiquids(), 4);
				s =  s + " / " + controller.getTankCapacity() + " mB";
				list.add(s);

				drawToolTip(list, mouseX, mouseY);
			}*/
		}

		/* Second Column */
		/*if (mouseX >= (centerX + elementXAddRight) && mouseX <= (centerX + elementXAddRight + elementWidth)) {
			if (mouseY >= (centerY - elementYAddTop) && mouseY <= (centerY - elementYAddTop + elementHeight)) {
				List list = new ArrayList<String>();
				list.add(StringHelper.Cap(FluidHelper.getName(controller.getAllLiquids(), 1)));
				String s = EnumChatFormatting.DARK_GRAY + "" + FluidHelper.getFluidAmtIndex(controller.getAllLiquids(), 1);
				s =  s + " / " + controller.getTankCapacity() + " mB";
				list.add(s);

				drawToolTip(list, mouseX, mouseY);
			}

			if (mouseY >= (centerY - elementYAddMiddle) && mouseY <= (centerY - elementYAddMiddle + elementHeight)) {
				List list = new ArrayList<String>();
				list.add(StringHelper.Cap(FluidHelper.getName(controller.getAllLiquids(), 3)));
				String s = EnumChatFormatting.DARK_GRAY + "" + FluidHelper.getFluidAmtIndex(controller.getAllLiquids(), 3);
				s =  s + " / " + controller.getTankCapacity() + " mB";
				list.add(s);

				drawToolTip(list, mouseX, mouseY);
			}

			if (mouseY >= (centerY - elementYAddBottom) && mouseY <= (centerY - elementYAddBottom + elementHeight)) {
				List list = new ArrayList<String>();
				list.add(StringHelper.Cap(FluidHelper.getName(controller.getAllLiquids(), 5)));
				String s = EnumChatFormatting.DARK_GRAY + "" + FluidHelper.getFluidAmtIndex(controller.getAllLiquids(), 5);
				s =  s + " / " + controller.getTankCapacity() + " mB";
				list.add(s);

				drawToolTip(list, mouseX, mouseY);
			}
		}*/
	}

	/*public void mouseClicked(int i, int j, int k) {

		super.mouseClicked(i, j, k);
		commands.mouseClicked(i, j, k);
	}*/

	/*
	 * Draw Screen
	 */

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {

		int x = (width - 176) / 2;
		int y = (height - 141) / 2;
		mc.renderEngine.bindTexture(Tank_Gui);
		drawTexturedModalRect(x, y, 0, 0, 176, 141);
		//commands.drawTextBox();

		/*for (int v = 0; v < output.length; v++)
			output[v].drawTextBox();*/

		//drawString(fontRenderer, "Current Index: " + controller.getLiquidIndex(), guiLeft + 20, guiTop + 76, 0xFFFFFF);
		//FluidHelper.getFluidAmtIndex(controller.getAllLiquids(), controller.getLiquidIndex()) + " mB"
		drawString(fontRenderer, "Dynamic Tank", guiLeft + 16, guiTop + 18, 0x656565);
		drawString(fontRenderer, "", guiLeft + 16, guiTop + 18, 0xFFFFFF);
		//drawString(fontRenderer, "Fluid: " + StringHelper.Cap(FluidHelper.getName(controller.getAllLiquids(), controller.getLiquidIndex())), guiLeft + 20, guiTop + 20, 0xFFFFFF);
		//drawString(fontRenderer, "Amount: " + FluidHelper.getFluidAmtIndex(controller.getAllLiquids(), controller.getLiquidIndex()) + " mB", guiLeft + 20, guiTop + 30, 0xFFFFFF);

		if (!FluidHelper.hasLiquid(controller))
			return;

		if (controller.getAllLiquids().get(0).getFluid() != null) {
			Icon liqIcon = null;
			Fluid fluid = controller.getAllLiquids().get(0).getFluid().getFluid();

			if (fluid != null && fluid.getStillIcon() != null)
				liqIcon = fluid.getStillIcon();

			mc.renderEngine.bindTexture(BLOCK_TEXTURE);

			if (liqIcon != null)
				drawTexturedModelRectFromIcon(guiLeft + xDraw[2], guiTop - 1 + yDraw[2] -
						FluidHelper.getLiquidAmountScaledForGUI(controller.getAllLiquids().get(0).getFluidAmount(),
								controller.getTankCapacity()), liqIcon, 12,
								FluidHelper.getLiquidAmountScaledForGUI(controller.getAllLiquids().get(0).getFluidAmount(),
										controller.getTankCapacity()));
		}

		mc.renderEngine.bindTexture(Tank_Gui);

		/*for (int p = 0; p < 6; p++)
			drawTexturedModalRect(guiLeft + xDraw[p] - 2, guiTop + yDraw[p] - 36, 175, 0, 14, 37);*/
		drawTexturedModalRect(guiLeft + xDraw[2] - 2, guiTop + yDraw[2] - 37, 175, 0, 14, 37);
	}

	private void drawToolTip(List list, int par2, int par3) {

		FontRenderer font = this.fontRenderer;
		drawHoveringText(list, par2, par3, (font == null ? fontRenderer : font));
	}

	/*public void setCommandOutput(String toSet) {

		boolean wasSet = false;

		if (toSet.substring(toSet.length() - 1, toSet.length()).equals(";")) {
			setMultipleLines(toSet);
			wasSet = true;
		}
		else {
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
	}*/

	/*public void setMultipleLines(String toSplit) {

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
	}*/

	/*public String cycleRecent(String direction)	{

		if (controller.recentSent.isEmpty())
			return "";

		commands.setFocused(true);
		String output = "";

		if (direction.equals("Up"))	{
			if ((currentNode + 1) > controller.recentSent.size())
				currentNode = 0;

			output = controller.recentSent.get(currentNode);
			currentNode++;
		}
		else if (direction.equals("Down")) {
			if ((currentNode - 1) < 0)
				currentNode = (controller.recentSent.size() - 1);
			else
				currentNode--;

			output = controller.recentSent.get(currentNode);
		}

		return output;
	}*/
}
