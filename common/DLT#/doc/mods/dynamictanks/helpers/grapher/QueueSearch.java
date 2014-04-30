package doc.mods.dynamictanks.helpers.grapher;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import codechicken.multipart.minecraft.McSidedMetaPart;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.block.BlockPiping;
import doc.mods.dynamictanks.helpers.FluidHandlerPart;
import doc.mods.dynamictanks.tileentity.ModifierTileEntity;

public class QueueSearch {

	public static ArrayList<FluidHandlerPart> queueSearch(World world, BlockPosition start, ArrayList<FluidHandlerPart> output) {

		Queue<BlockPosition> unexplored = new Queue<BlockPosition>();
		Queue<BlockPosition> explored   = new Queue<BlockPosition>();

		unexplored.put(start);

		while (!unexplored.isEmpty()) {

			BlockPosition searching = unexplored.get();
			explored.put(searching);

			TileEntity testingTile = world.getBlockTileEntity(searching.x(), searching.y(), searching.z());
			
			if (world.getBlockId(searching.x(), searching.y(), searching.z()) == BlockManager.BlockPiping.blockID)
				BlockPiping.setSearched(world, searching.x(), searching.y(), searching.z());

			else if (world.getBlockTileEntity(searching.x(), searching.y(), searching.z()) instanceof TileMultipart) {

				TileMultipart tMultipart = (TileMultipart) world.getBlockTileEntity(searching.x(), searching.y(), searching.z());

				for (TMultiPart tPart : tMultipart.jPartList())				
					if (tPart.getType().equals("dlt_piping"))
						((McSidedMetaPart) tPart).meta = 1;
			}

			if (isGoalAround(world, searching) != null) 
				if (!outputHas(output, isGoalAround(world, searching)))
					output.add(new FluidHandlerPart(isGoalAround(world, searching), goalBlockPos(world, searching)));


			for (ForgeDirection fDir : ForgeDirection.VALID_DIRECTIONS)
				if (isSearchable(world, new BlockPosition(searching.x() + fDir.offsetX, searching.y() + fDir.offsetY, searching.z() + fDir.offsetZ)))
					unexplored.put(new BlockPosition(searching.x() + fDir.offsetX, searching.y() + fDir.offsetY, searching.z() + fDir.offsetZ));

		}

		cleanQueue(explored, world);
		return output;
	}

	public static boolean outputHas(ArrayList<FluidHandlerPart> output, IFluidHandler compareTo) {
		
		for (FluidHandlerPart fhP : output)
			if (fhP.getFluidHandler().equals(compareTo))
				return true;
		
		return false;
	}
	
	public static IFluidHandler isGoalAround(World world, BlockPosition near) {

		for (ForgeDirection fDir : ForgeDirection.VALID_DIRECTIONS) {

			int x = fDir.offsetX + near.x();
			int y = fDir.offsetY + near.y();
			int z = fDir.offsetZ + near.z();

			if (world.getBlockTileEntity(x, y, z) instanceof IFluidHandler && !((world.getBlockTileEntity(x, y, z) instanceof ModifierTileEntity))) //Modify Goal //TODO
				return (IFluidHandler) world.getBlockTileEntity(x, y, z);
		}

		return null;
	}
	
	public static BlockPosition goalBlockPos(World world, BlockPosition near) {

		for (ForgeDirection fDir : ForgeDirection.VALID_DIRECTIONS) {

			int x = fDir.offsetX + near.x();
			int y = fDir.offsetY + near.y();
			int z = fDir.offsetZ + near.z();

			if (world.getBlockTileEntity(x, y, z) instanceof IFluidHandler) //Modify Goal //TODO
				return new BlockPosition(x, y, z);
		}

		return null;
	}

	public static boolean isSearchable(World world, BlockPosition pos) {

		if (world.getBlockId(pos.x(), pos.y(), pos.z()) == BlockManager.BlockModifier.blockID)
			return false;
		
		if (world.getBlockId(pos.x(), pos.y(), pos.z()) == BlockManager.BlockPiping.blockID && world.getBlockMetadata(pos.x(), pos.y(), pos.z()) != BlockPiping.SEARCHED)
			return true;

		TileEntity test = world.getBlockTileEntity(pos.x(), pos.y(), pos.z());
		if (world.getBlockTileEntity(pos.x(), pos.y(), pos.z()) instanceof TileMultipart) {

			TileMultipart tMultipart = (TileMultipart) world.getBlockTileEntity(pos.x(), pos.y(), pos.z());

			for (TMultiPart tPart : tMultipart.jPartList())				
				if (tPart.getType().equals("dlt_piping") && tPart instanceof McSidedMetaPart)
					if (((McSidedMetaPart) tPart).meta != BlockPiping.SEARCHED)
						return true;
		}

		return false;
	}

	public static void cleanQueue(Queue<BlockPosition> toClear, World world) {

		while (!toClear.isEmpty()) {

			BlockPosition bPos = toClear.get();
			
			if (world.getBlockTileEntity(bPos.x(), bPos.y(), bPos.z()) instanceof TileMultipart) {

				TileMultipart tMultipart = (TileMultipart) world.getBlockTileEntity(bPos.x(), bPos.y(), bPos.z());

				for (TMultiPart tPart : tMultipart.jPartList())				
					if (tPart.getType().equals("dlt_piping"))
						((McSidedMetaPart) tPart).meta = 0;
			} else if (!(world.getBlockTileEntity(bPos.x(), bPos.y(), bPos.z()) instanceof ModifierTileEntity)) {
				
				world.setBlockMetadataWithNotify(bPos.x(), bPos.y(), bPos.z(), 0, 3);
			}
		}
	}
}
