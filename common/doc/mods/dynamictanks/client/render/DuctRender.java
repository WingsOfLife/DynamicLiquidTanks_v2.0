package doc.mods.dynamictanks.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.client.ClientProxy;
import doc.mods.dynamictanks.tileentity.DuctTileEntity;

@SideOnly(Side.CLIENT)
public class DuctRender extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

	private DuctModel _model;
	public static final ResourceLocation ductTexture = new ResourceLocation("dynamictanks", "textures/ductModel.png");

	public DuctRender() {
		_model = new DuctModel();
	}        

	@SideOnly(Side.CLIENT)
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float scale) {
		DuctTileEntity cable = (DuctTileEntity) tileentity;
		_model.duct = cable;

		bindTexture(ductTexture);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y - 0.6F, (float) z + 0.5F);
		
		if (cable != null && cable.movingFluid != null) {
			GL11.glScaled(1.1, 1.1, 1.1); //TODO: increase scale if has liquid.
		}

		GL11.glDisable(GL11.GL_ALPHA_TEST);
		_model.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glPopMatrix();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Minecraft.getMinecraft().renderEngine.bindTexture(ductTexture);

		GL11.glPushMatrix();
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glTranslated(0, -1.25f, 0);
		GL11.glScalef(1.28f, 1.28f, 1.28f);

		GL11.glDisable(GL11.GL_ALPHA_TEST);
		_model.render(0.0625f);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glPopMatrix();

		return;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProxy.ductRender;
	}
}
