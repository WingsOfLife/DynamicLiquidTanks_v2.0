package doc.mods.dynamictanks.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.IFluidHandler;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.client.ClientProxy;

public class RenderPipe implements ISimpleBlockRenderingHandler {

	public final static int[][] partSwitch = { {0, -1, 0}, { 0, +1, 0 }, { 0, 0, -1 }, { 0, 0, +1 }, { -1, 0, 0 }, { +1, 0, 0 } };

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {

		RendererHelper.renderStandardInvBlock(renderer, block, metadata);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		renderer.setRenderBounds(0.30F, 0.30F, 0.30F, 0.70F, 0.70F, 0.70F);
		renderer.renderStandardBlock(block, x, y, z);
		//BlockManager.BlockPiping.setBlockBounds(0.35F, 0.35F, 0.35F, 0.65F, 0.65F, 0.65F);

		if (world.getBlockId(x + 1, y, z) == BlockManager.BlockPiping.blockID || world.getBlockTileEntity(x + 1, y, z) instanceof IFluidHandler || hasPartInSpace(world, x + 1, y, z)) {
			renderer.setRenderBounds(0.65F, 0.35F, 0.35F, 1F, 0.65F, 0.65F);
			renderer.renderStandardBlock(block, x, y, z);
		}

		if (world.getBlockId(x - 1, y, z) == BlockManager.BlockPiping.blockID || world.getBlockTileEntity(x - 1, y, z) instanceof IFluidHandler || hasPartInSpace(world, x - 1, y, z)) {
			renderer.setRenderBounds(0.0F, 0.35F, 0.35F, 0.35F, 0.65F, 0.65F);
			renderer.renderStandardBlock(block, x, y, z);
		}

		if (world.getBlockId(x, y, z + 1) == BlockManager.BlockPiping.blockID || world.getBlockTileEntity(x, y, z + 1) instanceof IFluidHandler || hasPartInSpace(world, x, y, z + 1)) {
			renderer.setRenderBounds(0.35F, 0.35F, 0.65F, .65F, 0.65F, 1F);
			renderer.renderStandardBlock(block, x, y, z);
		}

		if (world.getBlockId(x, y, z - 1) == BlockManager.BlockPiping.blockID || world.getBlockTileEntity(x, y, z - 1) instanceof IFluidHandler || hasPartInSpace(world, x, y, z - 1)) {
			renderer.setRenderBounds(0.35F, 0.35F, 0.0f, .65F, 0.65F, 0.35F);
			renderer.renderStandardBlock(block, x, y, z);
		}

		if (world.getBlockId(x, y + 1, z) == BlockManager.BlockPiping.blockID || world.getBlockTileEntity(x, y + 1, z) instanceof IFluidHandler || hasPartInSpace(world, x, y + 1, z)) {
			renderer.setRenderBounds(0.35f, 0.65f, 0.35f, 0.65F, 1.0F, 0.65F);
			renderer.renderStandardBlock(block, x, y, z);
		}

		if (world.getBlockId(x, y - 1, z) == BlockManager.BlockPiping.blockID || world.getBlockTileEntity(x, y - 1, z) instanceof IFluidHandler || hasPartInSpace(world, x, y - 1, z)) {
			renderer.setRenderBounds(0.35f, 0.0f, 0.35f, 0.65F, 0.35F, 0.65F);
			renderer.renderStandardBlock(block, x, y, z);
		}

		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {

		return true;
	}

	@Override
	public int getRenderId() {

		return ClientProxy.pipeRender;
	}

	public static boolean hasPartInSpace(IBlockAccess world, int x, int y, int z) {

		/*if (world instanceof PartMetaAccess) {

			World toUse = ((PartMetaAccess) world).part.getWorld();

			if (toUse.getBlockTileEntity(x, y, z) instanceof TileMultipart) {

				TileMultipart tMultipart = (TileMultipart) toUse.getBlockTileEntity(x, y, z);

				for (TMultiPart tPart : tMultipart.jPartList())				
					if (tPart.getType().equals("dlt_piping"))
						return isConnectionBlocked(world, x, y, z, side);
			}
			
			return false;
		}*/

		if (world.getBlockTileEntity(x, y, z) instanceof TileMultipart) {

			TileMultipart tMultipart = (TileMultipart) world.getBlockTileEntity(x, y, z);

			for (TMultiPart tPart : tMultipart.jPartList())				
				if (tPart.getType().equals("dlt_piping"))
					return true;//return isConnectionBlocked(world, x, y, z, side);
		}

		return false;
	}

	public static boolean isConnectionBlocked(IBlockAccess world, int x, int y, int z, int side) {

		/*if (world instanceof PartMetaAccess) {

			World toUse = ((PartMetaAccess) world).part.getWorld();

			if (toUse.getBlockTileEntity(x, y, z) instanceof TileMultipart) {

				TileMultipart tMultipart = (TileMultipart) toUse.getBlockTileEntity(x, y, z);

				if (tMultipart.partMap(getOpposite(side)) != null && !tMultipart.partMap(getOpposite(side)).getType().equals("mcr_hllw"))
					return false; 
			}
			
			return true;
		}*/

		if (world.getBlockTileEntity(x, y, z) instanceof TileMultipart) {

			TileMultipart tMultipart = (TileMultipart) world.getBlockTileEntity(x, y, z);

			if (tMultipart.partMap(side) != null && !tMultipart.partMap(side).getType().equals("mcr_hllw"))
				return false; 
		}

		return true;
	}

	public static int getOpposite(int side) {

		switch (side) {

		case 0: return 1;
		case 1: return 0;
		case 2: return 3;
		case 3: return 2;
		case 4: return 5;
		case 5: return 6;
		}

		return -1;
	}

}
