package doc.mods.dynamictanks.client.render;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class RenderController extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		
		if (RenderManager.instance.livingPlayer.isSneaking()) {
			
			ControllerTileEntity controllerTile = (ControllerTileEntity) tileentity.worldObj.getBlockTileEntity((int) d0, (int) d1, (int) d2);
			
			//drawString(0, tileentity.worldObj.getBlockMetadata((int) d0, (int) d1, (int) d2), "Testing Position", d0, d1, d2);
		}
			
	}
	
	public void drawString(int index, int metaData, String toDraw, double x, double y, double z) {

		float xShift = 0;
		float zShift = 0;
		
		if (metaData == 5)
			xShift = 1.5f;
		if (metaData == 5)
			xShift = -1.5f;
		if (metaData == 3)
			zShift = 1.5f;
		if (metaData == 2)
			zShift = -1.5f;
		
		float var8   = 0.75F;
		float var9   = 0.016666668F * var8;
		float var12  = 10;

		String var13 = toDraw;

		FontRenderer var14 = RenderManager.instance.getFontRenderer();
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F + xShift, (float)y + 1.75F - (0.12f * index), (float)z + 0.5F + zShift);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-var9, -var9, var9);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(0.0F, 0.25F / var9, 0.0F);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator var15 = Tessellator.instance;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		var15.startDrawingQuads();
		int var16 = var14.getStringWidth(var13) / 2;
		var15.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		var15.addVertex((double)(-var16 - 1), -1.0D, 0.0D);
		var15.addVertex((double)(-var16 - 1), 8.0D, 0.0D);
		var15.addVertex((double)(var16 + 1), 8.0D, 0.0D);
		var15.addVertex((double)(var16 + 1), -1.0D, 0.0D);
		var15.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);
		var14.drawString(var13, -var14.getStringWidth(var13) / 2, 0, 553648127);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

}
