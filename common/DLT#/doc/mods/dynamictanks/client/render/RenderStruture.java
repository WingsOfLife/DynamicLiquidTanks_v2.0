package doc.mods.dynamictanks.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import doc.mods.dynamictanks.client.ClientProxy;
import doc.mods.dynamictanks.helpers.FluidHelper;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class RenderStruture implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

		RendererHelper.renderStandardInvBlock(renderer, block, metadata);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		TileEntity toRender = world.getBlockTileEntity(x, y, z);

		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderStandardBlock(block, x, y, z);

		if (toRender instanceof ControllerTileEntity) {

			ControllerTileEntity controller = (ControllerTileEntity) toRender;
			renderer.setRenderBounds(0.001, 0.001, 0.001, 0.999, 0.999, 0.999);

			if (FluidHelper.hasLiquid(controller)) {

				FluidStack liquid = controller.getAllLiquids().get(0).getFluid();
				Fluid fluid = liquid.getFluid();

				if (fluid.canBePlacedInWorld())
					BlockSkinRenderHelper.renderMetadataBlock(Block.blocksList[fluid.getBlockID()], 0, x, y, z, renderer, world);
				else
					BlockSkinRenderHelper.renderLiquidBlock(fluid.getStillIcon(), fluid.getFlowingIcon(), x, y, z, renderer, world);
			}
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {

		return true;
	}

	@Override
	public int getRenderId() {

		return ClientProxy.controlPass;
	}

}
