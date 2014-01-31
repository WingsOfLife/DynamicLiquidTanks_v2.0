package doc.mods.dynamictanks.client.render;

import java.util.Random;

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
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.client.ClientProxy;

public class PotionMixerRender extends TileEntitySpecialRenderer
{
    private PotionMixerModel _model;
    Random rnd = new Random();

    public static final ResourceLocation ductTexture = new ResourceLocation("dynamictanks", "textures/ductModel.png");

    public PotionMixerRender()
    {
        _model = new PotionMixerModel();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        bindTexture(ductTexture);
        _model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    /*@SideOnly(Side.CLIENT)
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
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
    	renderer.renderStandardBlock(BlockManager.BlockMixer, x, y, z);
    	return true;
    }

    @Override
    public boolean shouldRender3DInInventory() {
    	return true;
    }

    @Override
    public int getRenderId() {
    	return ClientProxy.potiRender;
    }*/

}
