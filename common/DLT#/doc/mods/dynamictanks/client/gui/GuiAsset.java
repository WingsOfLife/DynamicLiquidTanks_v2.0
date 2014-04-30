package doc.mods.dynamictanks.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.helpers.StringHelper;
import doc.mods.dynamictanks.tileentity.ModifierTileEntity;

public class GuiAsset extends GuiContainer {

	private static final ResourceLocation Asset_Gui     = new ResourceLocation("dynamictanks", "textures/gui/AssetManager.png");

	private int centerX = this.width  / 2;
	private int centerY = this.height / 2;

	private int elementWidth           = 10;
	private int elementHeight          = 50;
	
	private int elementWidth2          = 16;
	private int elementHeight2         = 16;

	private int elementXAddLeft        = 58;
	private int elementYAddMiddle      = 104;
	
	private int elementXAddLeft2       = 52;
	private int elementYAddMiddle2     = 8;
	
	private int elementXAddLeft3       = 45;

	private ModifierTileEntity thisMod;

	public GuiAsset(InventoryPlayer playerInventory, ModifierTileEntity tileMod) {

		super(new ContainerAsset(playerInventory, tileMod));
		thisMod = tileMod;
	}

	public void initGui() {

		super.initGui();

		this.buttonList.add(new GuiConfirmMerge(this, 1, guiLeft - 5, guiTop - 26));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float par3) {

		centerX = this.width  / 2;
		centerY = this.height / 2;

		super.drawScreen(mouseX, mouseY, par3);
		drawItemStackTooltip(new ItemStack(Item.bucketWater), mouseX, mouseY);

		//System.out.println(centerY - mouseY);

		if (mouseX >= (centerX + elementXAddLeft) && mouseX <= (centerX + elementXAddLeft + elementWidth)) {
			if (mouseY >= (centerY - elementYAddMiddle) && mouseY <= (centerY - elementYAddMiddle + elementHeight)) {

				List list = new ArrayList<String>();
				list.add("Power Stored: ");
				list.add(thisMod.getEnergyStored(null) + "/" + thisMod.getMaxEnergyStored(null) + " RF");
				list.add("Requires: " + thisMod.RFPerTick + " RF/t to run.");
				list.add("Alternatively: 10mB of Lava per Second");

				drawTooltip(list, mouseX, mouseY);
			}
		}
		
		if (mouseX >= (centerX + elementXAddLeft2) && mouseX <= (centerX + elementXAddLeft2 + elementWidth2)) {
			if (mouseY >= (centerY - elementYAddMiddle2) && mouseY <= (centerY - elementYAddMiddle2 + elementHeight2)) {

				List list = new ArrayList<String>();
				list.add("Information: ");
				if (thisMod.getDrain() < thisMod.controlledAssets.size() && FluidHelper.fluidHandlerFilled(thisMod.controlledAssets.get(thisMod.getDrain()).getFluidHandler()))
					list.add("Drain Output: " + thisMod.getDrain() + " [" + 
							StringHelper.Cap(FluidHelper.getFluidFromHandler(thisMod.controlledAssets.get(thisMod.getDrain()).getFluidHandler()).getFluid().getName()) + ", "
							+ thisMod.controlledAssets.get(thisMod.getDrain()).getFluidHandler().getTankInfo(ForgeDirection.UP)[0].fluid.amount + " mB]");
				else
					list.add("Drain Output: " + thisMod.getDrain());
				list.add(EnumChatFormatting.GOLD + "Hold LCTRL and click a slot, to select it.");
				list.add(EnumChatFormatting.GOLD + "The currently selected slot will output.");

				drawTooltip(list, mouseX, mouseY);
			}
		}
		
		if (mouseX >= (centerX + elementXAddLeft3) && mouseX <= (centerX + elementXAddLeft3 + elementWidth)) {
			if (mouseY >= (centerY - elementYAddMiddle) && mouseY <= (centerY - elementYAddMiddle + elementHeight)) {

				List list = new ArrayList<String>();
				list.add("Delay: ");
				list.add(thisMod.tickCount + "/" + thisMod.getTickCount() + " t");
				list.add(EnumChatFormatting.GOLD + "Displays the ticks until the next update.");

				drawTooltip(list, mouseX, mouseY);
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

		int x = (width - 176) / 2;
		int y = (height - 220) / 2;
		mc.renderEngine.bindTexture(Asset_Gui);
		drawTexturedModalRect(x, y, 0, 0, 176, 220);

		int tickCount = thisMod.tickCount;
		int maxTick = thisMod.getTickCount();
		
		float currentTick = 58 * ((float) thisMod.tickCount) / ((float) thisMod.getTickCount());
		float currentPower = FluidHelper.getPowerAmountScaledForGUI(thisMod.getEnergyStored(null), thisMod.getMaxEnergyStored(null));
		
		drawTexturedModalRect(guiLeft + 147, guiTop - 26 + (int) currentPower, 197, 0, 8, 54 - (int) currentPower); //red
		drawTexturedModalRect(guiLeft + 134, guiTop - 26 + (int) currentTick, 188, 0, 8, 54 - (int) currentTick); //blue

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);

		drawTexturedModalRect(guiLeft + 144, guiTop - 26, 175, 0, 12, 54); // glass tube overlay red
		drawTexturedModalRect(guiLeft + 131, guiTop - 26, 175, 0, 12, 54); // glass tube overlay blue/workload

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
		//drawString(fontRenderer, "Out: " + thisMod.getDrain(), guiLeft + 130, guiTop + 31, 0x656565);
	}

	private void drawTooltip(List list, int par2, int par3) {

		FontRenderer font = this.fontRenderer;
		drawHoveringText(list, par2, par3, (font == null ? fontRenderer : font));
	}

	@Override
	protected void drawItemStackTooltip(ItemStack par1ItemStack, int par2, int par3) {

		for (int j1 = 0; j1 < this.inventorySlots.inventorySlots.size(); ++j1)
		{
			Slot slot = (Slot) this.inventorySlots.inventorySlots.get(j1);
			//this.drawSlotInventory(slot);

			if (isMouseOverSlot(slot, par2, par3) && slot.func_111238_b() && slot.inventory.getStackInSlot(slot.getSlotIndex()) != null) {

				List list = slot.inventory.getStackInSlot(slot.getSlotIndex()).getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

				for (int k = 0; k < list.size(); ++k)
				{
					if (k == 0)
					{
						list.set(k, "\u00a7" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + (String)list.get(k));
					}
					else
					{
						list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
					}
				}

				List myList = new ArrayList<String>();
				//myList.add(slot.inventory.getStackInSlot(slot.getSlotIndex()).getDisplayName());
				
				if (!(slot instanceof FluidSlot))
					return;
				
				if (slot.getSlotIndex() < thisMod.controlledAssets.size() && FluidHelper.fluidHandlerFilled(thisMod.controlledAssets.get(slot.getSlotIndex()).getFluidHandler())) {

					myList.add("Available: " + thisMod.controlledAssets.get(slot.getSlotIndex()).getFluidHandler().getTankInfo(ForgeDirection.UP)[0].fluid.amount + " mB");
					myList.add("Tank At: " + thisMod.controlledAssets.get(slot.getSlotIndex()).getPosition().toString());
					int blockID = thisMod.worldObj.getBlockId(thisMod.controlledAssets.get(slot.getSlotIndex()).getPosition().x(), thisMod.controlledAssets.get(slot.getSlotIndex()).getPosition().y(), thisMod.controlledAssets.get(slot.getSlotIndex()).getPosition().z());

					if (blockID != -1)
						myList.add(Block.blocksList[blockID].getLocalizedName());
				} else if (slot.getSlotIndex() < thisMod.controlledAssets.size()) {

					myList.add("Available: Empty");
					myList.add("Tank At: " + thisMod.controlledAssets.get(slot.getSlotIndex()).getPosition().toString());

					int blockID = thisMod.worldObj.getBlockId(thisMod.controlledAssets.get(slot.getSlotIndex()).getPosition().x(), thisMod.controlledAssets.get(slot.getSlotIndex()).getPosition().y(), thisMod.controlledAssets.get(slot.getSlotIndex()).getPosition().z());

					if (blockID != -1)
						myList.add(Block.blocksList[blockID].getLocalizedName());
				}

				for (int k = 0; k < myList.size(); ++k)
					list.add((String)myList.get(k));

				FontRenderer font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);

				this.drawHoveringText(list, par2, par3, (font == null ? fontRenderer : font));
			}
		}
	}

	//TODO
	protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
	{
		if (!par1List.isEmpty())
		{
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			int k = 0;
			int itCounter = 0;
			Iterator iterator = par1List.iterator();

			while (iterator.hasNext())
			{
				String s = (String)iterator.next();
				int l = font.getStringWidth(s);

				if (l > k && itCounter == 0)
				{
					k = l;
				} else if ((int) (l * 0.5f) > k && itCounter > 0){
					
					k = (int) (l * 0.5f);
				}
				
				itCounter++;
			}

			int i1 = par2 + 12;
			int j1 = par3 - 12;
			int k1 = 8;

			if (par1List.size() > 1)
			{
				k1 += 2 + (par1List.size() - 1) * 5.5; //Height
			}

			if (i1 + k > this.width)
			{
				i1 -= 28 + k;
			}

			if (j1 + k1 + 6 > this.height)
			{
				j1 = this.height - k1 - 6;
			}
			
			i1 = this.guiLeft + 170;
			j1 = this.guiTop - 20;
			
			this.zLevel = 300.0F;
			itemRenderer.zLevel = 300.0F;
			int l1 = -267386864;
			this.drawGradientRect(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
			this.drawGradientRect(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
			this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
			this.drawGradientRect(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
			int i2 = 1347420415;
			int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
		this.drawGradientRect(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
		this.drawGradientRect(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
		this.drawGradientRect(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
		this.drawGradientRect(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);

		for (int k2 = 0; k2 < par1List.size(); ++k2)
		{
			String s1 = (String)par1List.get(k2);

			//s1 = s1.replace("Bucket ", "");

			if (k2 > 0) {

				GL11.glPushMatrix();
				GL11.glScalef(0.5f, 0.5f, 0f);
				GL11.glTranslatef((i1 * 1f), (j1 * 1f), 0);

				if (k2 > 1)
					if (k2 == 3)
						font.drawString(s1, i1, j1 - 20, -1);
					else
						font.drawString(s1, i1, j1 - 12, -1);
				else
					font.drawString(s1, i1, j1 - 4, -1);

				GL11.glScalef(1f, 1f, 1f);
				GL11.glPopMatrix();
			} else
				font.drawStringWithShadow(s1, i1, j1, -1);

			if (k2 == 0)
			{
				j1 += 2;
			}

			j1 += 10;
		}

		this.zLevel = 0.0F;
		itemRenderer.zLevel = 0.0F;
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		}
	}

	private boolean isMouseOverSlot(Slot par1Slot, int par2, int par3) {

		return this.isPointInRegion(par1Slot.xDisplayPosition, par1Slot.yDisplayPosition, 16, 16, par2, par3);
	}
}