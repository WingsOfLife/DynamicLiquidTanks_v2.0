package doc.mods.dynamictanks.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.PotionMixerTileEntity;

public class GuiMixer extends GuiContainer
{
    public static final ResourceLocation Tank_Gui = new ResourceLocation("dynamictanks", "textures/gui/potionMixer.png");

    private PotionMixerTileEntity controller;

    public GuiMixer(InventoryPlayer playerInventory, PotionMixerTileEntity tileMixer)
    {
        super(new ContainerMixer(playerInventory, tileMixer));
        controller = tileMixer;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        int x = (width - 176) / 2;
        int y = (height - 155) / 2;
        mc.renderEngine.bindTexture(Tank_Gui);
        drawTexturedModalRect(x, y, 0, 0, 176, 155);
    }
}
