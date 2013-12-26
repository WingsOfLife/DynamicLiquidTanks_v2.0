package doc.mods.dynamictanks.client.render;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.UP.FPCTileEntity_Basic;
import doc.mods.dynamictanks.UP.FPCTileEntity_RF;
import doc.mods.dynamictanks.client.ClientProxy;
import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.tileentity.TankTileEntity;

@SideOnly(Side.CLIENT)
public class FPCRender extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler
{
	private FPCModel _model;
	Random rnd = new Random();

	public static final ResourceLocation ductTexture = new ResourceLocation("dynamictanks", "textures/ductModel.png");

	public FPCRender()
	{
		_model = new FPCModel();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float scale)
	{    	
		FPCTileEntity_Basic fpcRF = (FPCTileEntity_Basic) tileentity;

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		bindTexture(ductTexture);

		_model.Rotator2.offsetY = (MathHelper.cos(fpcRF.tickCount * 0.6662F) * 1.4f * 0.01f);

		_model.Rotator2.rotateAngleX += 0.0113f;
		_model.Rotator2.rotateAngleY += 0.01f;

		_model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(ductTexture);
		GL11.glPushMatrix();
		//GL11.glRotatef(180, 0, 1, 0);
		GL11.glTranslated(0, -1.25f, 0);
		GL11.glScalef(1.00f, 1.00f, 1.00f);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		_model.render((Entity) null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		TileEntity castless = world.getBlockTileEntity(x, y, z);
		if (castless instanceof FPCTileEntity_Basic) {
			FPCTileEntity_Basic fpcBasic = (FPCTileEntity_Basic) castless;
			if (fpcBasic.getFP().getFluid() == null)
				return false;
			
			float fillAmount = (float) (fpcBasic.getFP().getFluidAmount() > 0 ? 0.1 : 0.0);// fpcBasic.getFP().getCapacity();
			renderer.setRenderBounds(0.07, 0.5, 0.07, 1 - 0.07, 0.5 + fillAmount , 1 - 0.07);
			renderer.renderStandardBlock(FluidManager.omniBlock, x, y, z);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory()
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return ClientProxy.ductRender;
	}
}
