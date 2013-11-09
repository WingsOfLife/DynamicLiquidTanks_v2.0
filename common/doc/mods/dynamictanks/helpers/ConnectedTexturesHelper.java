package doc.mods.dynamictanks.helpers;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;
import doc.mods.dynamictanks.tileentity.TankTileEntity;

public class ConnectedTexturesHelper {

	public static Icon upAndDown, leftRight, left, right, single, top,
	topBottom, bottom, leftBottom, leftTop, rightBottom,
	rightTop, onlyBottom, onlyTop, onlyRight, onlyLeft,
	empty;

	public static Icon getBlocksAroundNoSo(IBlockAccess par1World, int x, int y, int z, int dir, int blockId) {
		if (getBlockId(par1World, x + 1, y, z) == blockId && getBlockId(par1World, x - 1 , y, z) == blockId && getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x, y - 1, z) == blockId) {
			return empty;
		} else if (getBlockId(par1World, x + 1, y, z) == blockId && getBlockId(par1World, x - 1, y, z) == blockId && getBlockId(par1World, x, y + 1, z) == blockId) {
			return onlyBottom;
		} else if (getBlockId(par1World, x + 1, y, z) == blockId && getBlockId(par1World, x - 1, y, z) == blockId && getBlockId(par1World, x, y - 1, z) == blockId) {
			return onlyTop;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x, y - 1, z) == blockId && getBlockId(par1World, x + 1, y, z) == blockId) {
			if (dir == 3)
				return onlyLeft;
			return onlyRight;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x, y - 1, z) == blockId && getBlockId(par1World, x - 1, y, z) == blockId) {
			if (dir == 3)
				return onlyRight;
			return onlyLeft;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x , y - 1, z) == blockId) {
			return upAndDown;
		} else if (getBlockId(par1World, x - 1, y, z) == blockId && getBlockId(par1World, x + 1, y, z) == blockId) {
			return leftRight;
		} else if (getBlockId(par1World, x, y - 1, z) == blockId && getBlockId(par1World, x + 1, y, z) == blockId) {
			if (dir == 3)
				return leftTop;
			return rightTop;
		} else if (getBlockId(par1World, x, y - 1, z) == blockId && getBlockId(par1World, x - 1, y, z) == blockId) {
			if (dir == 3)
				return rightTop;
			return leftTop;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x + 1, y, z) == blockId) {
			if (dir == 3)
				return leftBottom;
			return rightBottom;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x - 1, y, z) == blockId) {
			if (dir == 3)
				return rightBottom;
			return leftBottom;
		} else if (getBlockId(par1World, x - 1, y, z) == blockId) {
			if (dir == 3)
				return left;
			return right;
		} else if (getBlockId(par1World, x + 1, y, z) == blockId) {
			if (dir == 3)
				return right;
			return left;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId) {
			return top;
		} else if (getBlockId(par1World, x, y - 1, z) == blockId) {
			return bottom;
		}  
		return Block.blocksList[blockId].getBlockTextureFromSide(dir);
	}

	public static Icon getBlocksAroundEaWe(IBlockAccess par1World, int x, int y, int z, int dir, int blockId) {
		if (getBlockId(par1World, x, y, z + 1) == blockId && getBlockId(par1World, x, y, z - 1) == blockId && getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x, y - 1, z) == blockId) {
			return empty;
		} else if (getBlockId(par1World, x, y, z + 1) == blockId && getBlockId(par1World, x, y, z - 1) == blockId && getBlockId(par1World, x, y + 1, z) == blockId) {
			return onlyBottom;
		} else if (getBlockId(par1World, x, y, z + 1) == blockId && getBlockId(par1World, x, y, z - 1) == blockId && getBlockId(par1World, x, y - 1, z) == blockId) {
			return onlyTop;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x, y - 1, z) == blockId && getBlockId(par1World, x, y, z + 1) == blockId) {
			if (dir == 5)
				return onlyRight;
			return onlyLeft;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x, y - 1, z) == blockId && getBlockId(par1World, x, y, z - 1) == blockId) {
			if (dir == 5)
				return onlyLeft;
			return onlyRight;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x , y - 1, z) == blockId) {
			return upAndDown;
		} else if (getBlockId(par1World, x, y, z - 1) == blockId && getBlockId(par1World, x, y, z + 1) == blockId) {
			return leftRight;
		} else if (getBlockId(par1World, x, y - 1, z) == blockId && getBlockId(par1World, x, y, z + 1) == blockId) {
			if (dir == 5)
				return rightTop;
			return leftTop;
		} else if (getBlockId(par1World, x, y - 1, z) == blockId && getBlockId(par1World, x, y, z - 1) == blockId) {
			if (dir == 5)
				return leftTop;
			return rightTop;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x, y, z + 1) == blockId) {
			if (dir == 5)
				return rightBottom;
			return leftBottom;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId && getBlockId(par1World, x, y, z - 1) == blockId) {
			if (dir == 5)
				return leftBottom;
			return rightBottom;
		} else if (getBlockId(par1World, x, y, z - 1) == blockId) {
			if (dir == 5)
				return right;
			return left;
		} else if (getBlockId(par1World, x, y, z + 1) == blockId) {
			if (dir == 5)
				return left;
			return right;
		} else if (getBlockId(par1World, x, y + 1, z) == blockId) {
			return top;
		} else if (getBlockId(par1World, x, y - 1, z) == blockId) {
			return bottom;
		}  
		return Block.blocksList[blockId].getBlockTextureFromSide(dir);
	}

	public static Icon getBlocksAroundToBo(IBlockAccess par1World, int x, int y, int z, int dir, int blockId) {
		if (getBlockId(par1World, x + 1, y, z) == blockId && getBlockId(par1World, x - 1 , y, z) == blockId && getBlockId(par1World, x, y, z + 1) == blockId && getBlockId(par1World, x, y, z - 1) == blockId) {
			return empty;
		} else if (getBlockId(par1World, x + 1, y, z) == blockId && getBlockId(par1World, x - 1, y, z) == blockId && getBlockId(par1World, x, y, z - 1) == blockId) {
			return onlyBottom;
		} else if (getBlockId(par1World, x + 1, y, z) == blockId && getBlockId(par1World, x - 1, y, z) == blockId && getBlockId(par1World, x, y, z + 1) == blockId) {
			return onlyTop;
		} else if (getBlockId(par1World, x, y, z + 1) == blockId && getBlockId(par1World, x, y, z - 1) == blockId && getBlockId(par1World, x - 1, y, z) == blockId) {
			return onlyRight;
		} else if (getBlockId(par1World, x, y, z + 1) == blockId && getBlockId(par1World, x, y, z - 1) == blockId && getBlockId(par1World, x + 1, y, z) == blockId) {
			return onlyLeft;
		} else if (getBlockId(par1World, x, y, z + 1) == blockId && getBlockId(par1World, x , y, z - 1) == blockId) {
			return upAndDown;
		} else if (getBlockId(par1World, x + 1, y, z) == blockId && getBlockId(par1World, x - 1, y, z) == blockId) {
			return leftRight;
		} else if (getBlockId(par1World, x, y, z - 1) == blockId && getBlockId(par1World, x + 1, y, z) == blockId) {
			return leftBottom;
		} else if (getBlockId(par1World, x, y, z - 1) == blockId && getBlockId(par1World, x - 1, y, z) == blockId) {
			return rightBottom;
		} else if (getBlockId(par1World, x, y, z + 1) == blockId && getBlockId(par1World, x + 1, y, z) == blockId) {
			return leftTop;
		} else if (getBlockId(par1World, x, y, z + 1) == blockId && getBlockId(par1World, x - 1, y, z) == blockId) {
			return rightTop;
		} else if (getBlockId(par1World, x - 1, y, z) == blockId) {
			return left;
		} else if (getBlockId(par1World, x + 1, y, z) == blockId) {
			return right;
		} else if (getBlockId(par1World, x, y, z + 1) == blockId) {
			return bottom;
		} else if (getBlockId(par1World, x, y, z - 1) == blockId) {
			return top;
		}  
		return Block.blocksList[blockId].getBlockTextureFromSide(dir);
	}

	public static boolean shouldSideRender(IBlockAccess world, int x, int y, int z, int side, Block block)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();

		if(world.getBlockId(x, y, z) == block.blockID 
				|| world.getBlockId(x, y, z) == BlockManager.BlockTankController.blockID) {
			TileEntity tmpTile = world.getBlockTileEntity(x, y, z);
			if(tmpTile instanceof TankTileEntity) {
				TankTileEntity tile = (TankTileEntity) tmpTile;

				if(world.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == block.blockID ||
						world.getBlockId(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == BlockManager.BlockTankController.blockID) {

					TileEntity fTmpTile = world.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);

					if(fTmpTile instanceof ControllerTileEntity) {
						ControllerTileEntity fTile = (ControllerTileEntity) fTmpTile;

						if(tile.getCamo()[0] == -1 && fTile.getCamo() != -1)
							return false;
					}
					else if(fTmpTile instanceof TankTileEntity) {
						TankTileEntity fTile = (TankTileEntity) fTmpTile;

						if(tile.getCamo()[0] == -1 && fTile.getCamo()[0] != -1)
							return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public static int getBlockId(IBlockAccess par1World, int x, int y, int z) {
		return par1World.getBlockId(x, y, z);
	}

	public static Icon blockIcon(int blockId, int side, int meta) {
		return Block.blocksList[blockId].getIcon(side, meta);
	}

	private static int[] transBlockIds = {18, 20};

	public static boolean isCamoTransparent(int camoId) {
		for (int i=0; i<transBlockIds.length; i++)
			if (camoId == transBlockIds[i])
				return true;
		return false;
	}

}
