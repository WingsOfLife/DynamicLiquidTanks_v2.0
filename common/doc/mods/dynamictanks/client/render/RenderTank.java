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
import doc.mods.dynamictanks.tileentity.TankTileEntity;

public class RenderTank implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		RendererHelper.renderStandardInvBlock(renderer, block, metadata);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
        renderer.renderStandardBlock(block, x, y, z);
		
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile != null && tile instanceof TankTileEntity) {
			TankTileEntity tank = ((TankTileEntity) tile);
			if (tank.hasController() && FluidHelper.hasLiquid(tank.getControllerTE())) {
				FluidStack liquid = tank.getControllerTE().getAllLiquids().get(tank.getControllerTE().getLiquidIndex()).getFluid();
				if (liquid != null && ClientProxy.renderPass == 1 && liquid.amount > 0) {
					float amnt = tank.amntToRender();
					if (amnt > 0) {
						renderer.setRenderBounds(
							tank.worldObj.getBlockId(x - 1, y, z) == 0 ? 0.001
									: 0.00,
							tank.worldObj.getBlockId(x, y - 1, z) == 0 ? 0.001
									: 0.00,
							tank.worldObj.getBlockId(x, y, z - 1) == 0 ? 0.001
									: 0.00, 
							tank.worldObj.getBlockId(x + 1, y, z) == 0 ? 0.999
									: 1.0, 
							amnt <= 0 ? 0.0f : amnt, 
							tank.worldObj.getBlockId(x, y, z + 1) == 0 ? 0.999
									: 1.0);
					
						Fluid fluid = liquid.getFluid();
						if (fluid.canBePlacedInWorld())
							BlockSkinRenderHelper.renderMetadataBlock(Block.blocksList[fluid.getBlockID()], 0, x, y, z, renderer, world);
						else
							BlockSkinRenderHelper.renderLiquidBlock(fluid.getStillIcon(), fluid.getFlowingIcon(), x, y, z, renderer, world);
						}
				}
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
		return ClientProxy.tankRender;
	}

}
