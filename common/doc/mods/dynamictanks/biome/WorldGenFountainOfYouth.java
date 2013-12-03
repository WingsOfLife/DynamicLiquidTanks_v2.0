package doc.mods.dynamictanks.biome;

/*
 *** MADE BY MITHION'S .SCHEMATIC TO JAVA CONVERTING TOOL v1.6 ***
 */
import java.util.Random;

import doc.mods.dynamictanks.Fluids.FluidManager;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFountainOfYouth extends WorldGenerator {
	protected int[] GetValidSpawnBlocks() {
		return new int[] { Block.sand.blockID };
	}

	public boolean LocationIsValidSpawn(World world, int i, int j, int k) {
		int distanceToAir = 0;
		int checkID = world.getBlockId(i, j, k);

		while (checkID != 0) {
			distanceToAir++;
			checkID = world.getBlockId(i, j + distanceToAir, k);
		}

		if (distanceToAir > 1) {
			return false;
		}

		j += distanceToAir - 1;

		int blockID = world.getBlockId(i, j, k);
		int blockIDAbove = world.getBlockId(i, j + 1, k);
		int blockIDBelow = world.getBlockId(i, j - 1, k);
		for (int x : GetValidSpawnBlocks()) {
			if (blockIDAbove != 0) {
				return false;
			}
			if (blockID == x) {
				return true;
			} else if (blockID == Block.snow.blockID && blockIDBelow == x) {
				return true;
			}
		}
		return false;
	}

	public WorldGenFountainOfYouth() {
	}

	public boolean generate(World world, Random rand, int i, int j, int k) {
		// check that each corner is one of the valid spawn blocks
		if (!LocationIsValidSpawn(world, i, j, k)
				|| !LocationIsValidSpawn(world, i + 4, j, k)
				|| !LocationIsValidSpawn(world, i + 4, j, k + 4)
				|| !LocationIsValidSpawn(world, i, j, k + 4)) {
			return false;
		}

		world.setBlock(i + 0, j + 1, k + 0, Block.sandStone.blockID, 2, 2);
		world.setBlock(i + 0, j + 1, k + 1, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 0, j + 1, k + 2, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 0, j + 1, k + 3, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 0, j + 1, k + 4, Block.sandStone.blockID, 2, 2);
		world.setBlock(i + 0, j + 2, k + 0, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 0, j + 2, k + 4, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 0, j + 3, k + 0, Block.sandStone.blockID, 2, 2);
		world.setBlock(i + 0, j + 3, k + 4, Block.sandStone.blockID, 2, 2);
		world.setBlock(i + 0, j + 4, k + 0, Block.blockLapis.blockID);
		world.setBlock(i + 0, j + 4, k + 1, Block.stairsSandStone.blockID, 7, 2);
		world.setBlock(i + 0, j + 4, k + 2, Block.stoneSingleSlab.blockID, 9, 2);
		world.setBlock(i + 0, j + 4, k + 3, Block.stairsSandStone.blockID, 6, 2);
		world.setBlock(i + 0, j + 4, k + 4, Block.blockLapis.blockID);
		world.setBlock(i + 0, j + 5, k + 0, Block.stairsSandStone.blockID, 2, 2);
		world.setBlock(i + 0, j + 5, k + 1, Block.stairsSandStone.blockID);
		world.setBlock(i + 0, j + 5, k + 2, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 0, j + 5, k + 3, Block.stairsSandStone.blockID);
		world.setBlock(i + 0, j + 5, k + 4, Block.stairsSandStone.blockID);
		world.setBlock(i + 1, j + 0, k + 1, Block.glowStone.blockID);
		world.setBlock(i + 1, j + 0, k + 3, Block.glowStone.blockID);
		world.setBlock(i + 1, j + 1, k + 0, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 1, j + 1, k + 4, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 1, j + 4, k + 0, Block.stairsSandStone.blockID, 5, 2);
		world.setBlock(i + 1, j + 4, k + 4, Block.stairsSandStone.blockID, 5, 2);
		world.setBlock(i + 1, j + 5, k + 0, Block.stairsSandStone.blockID, 2, 2);
		world.setBlock(i + 1, j + 5, k + 1, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 1, j + 5, k + 2, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 1, j + 5, k + 3, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 1, j + 5, k + 4, Block.stairsSandStone.blockID, 3, 2);
		world.setBlock(i + 1, j + 6, k + 1, Block.stairsSandStone.blockID, 2, 2);
		world.setBlock(i + 1, j + 6, k + 2, Block.stairsSandStone.blockID);
		world.setBlock(i + 1, j + 6, k + 3, Block.stairsSandStone.blockID);
		world.setBlock(i + 2, j + 1, k + 0, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 2, j + 1, k + 2, Block.blockGold.blockID);
		world.setBlock(i + 2, j + 1, k + 4, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 2, j + 2, k + 2, FluidManager.clenseBlock.blockID);
		world.setBlock(i + 2, j + 4, k + 0, Block.stoneSingleSlab.blockID, 9, 2);
		world.setBlock(i + 2, j + 4, k + 4, Block.stoneSingleSlab.blockID, 9, 2);
		world.setBlock(i + 2, j + 5, k + 0, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 2, j + 5, k + 1, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 2, j + 5, k + 2, Block.redstoneLampActive.blockID);
		world.setBlock(i + 2, j + 5, k + 3, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 2, j + 5, k + 4, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 2, j + 6, k + 1, Block.stairsSandStone.blockID, 2, 2);
		world.setBlock(i + 2, j + 6, k + 2, Block.blockRedstone.blockID);
		world.setBlock(i + 2, j + 6, k + 3, Block.stairsSandStone.blockID, 3, 2);
		world.setBlock(i + 2, j + 7, k + 2, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 3, j + 0, k + 1, Block.glowStone.blockID);
		world.setBlock(i + 3, j + 0, k + 3, Block.glowStone.blockID);
		world.setBlock(i + 3, j + 1, k + 0, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 3, j + 1, k + 4, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 3, j + 4, k + 0, Block.stairsSandStone.blockID, 4, 2);
		world.setBlock(i + 3, j + 4, k + 4, Block.stairsSandStone.blockID, 4, 2);
		world.setBlock(i + 3, j + 5, k + 0, Block.stairsSandStone.blockID, 2, 2);
		world.setBlock(i + 3, j + 5, k + 1, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 3, j + 5, k + 2, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 3, j + 5, k + 3, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 3, j + 5, k + 4, Block.stairsSandStone.blockID, 3, 2);
		world.setBlock(i + 3, j + 6, k + 1, Block.stairsSandStone.blockID, 1, 2);
		world.setBlock(i + 3, j + 6, k + 2, Block.stairsSandStone.blockID, 1, 2);
		world.setBlock(i + 3, j + 6, k + 3, Block.stairsSandStone.blockID, 3, 2);
		world.setBlock(i + 4, j + 1, k + 0, Block.sandStone.blockID, 2, 2);
		world.setBlock(i + 4, j + 1, k + 1, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 4, j + 1, k + 2, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 4, j + 1, k + 3, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 4, j + 1, k + 4, Block.sandStone.blockID, 2, 2);
		world.setBlock(i + 4, j + 2, k + 0, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 4, j + 2, k + 4, Block.sandStone.blockID, 1, 2);
		world.setBlock(i + 4, j + 3, k + 0, Block.sandStone.blockID, 2, 2);
		world.setBlock(i + 4, j + 3, k + 4, Block.sandStone.blockID, 2, 2);
		world.setBlock(i + 4, j + 4, k + 0, Block.blockLapis.blockID);
		world.setBlock(i + 4, j + 4, k + 1, Block.stairsSandStone.blockID, 7, 2);
		world.setBlock(i + 4, j + 4, k + 2, Block.stoneSingleSlab.blockID, 9, 2);
		world.setBlock(i + 4, j + 4, k + 3, Block.stairsSandStone.blockID, 6, 2);
		world.setBlock(i + 4, j + 4, k + 4, Block.blockLapis.blockID);
		world.setBlock(i + 4, j + 5, k + 0, Block.stairsSandStone.blockID, 1, 2);
		world.setBlock(i + 4, j + 5, k + 1, Block.stairsSandStone.blockID, 1, 2);
		world.setBlock(i + 4, j + 5, k + 2, Block.stoneSingleSlab.blockID, 1, 2);
		world.setBlock(i + 4, j + 5, k + 3, Block.stairsSandStone.blockID, 1, 2);
		world.setBlock(i + 4, j + 5, k + 4, Block.stairsSandStone.blockID, 3, 2);
		world.setBlock(i + 0, j + 0, k + 0, Block.sand.blockID);
		world.setBlock(i + 0, j + 0, k + 1, Block.sand.blockID);
		world.setBlock(i + 0, j + 0, k + 2, Block.sand.blockID);
		world.setBlock(i + 0, j + 0, k + 3, Block.sand.blockID);
		world.setBlock(i + 0, j + 0, k + 4, Block.sand.blockID);
		world.setBlock(i + 1, j + 0, k + 0, Block.sand.blockID);
		world.setBlock(i + 1, j + 0, k + 2, Block.sand.blockID);
		world.setBlock(i + 1, j + 0, k + 4, Block.sand.blockID);
		world.setBlock(i + 2, j + 0, k + 0, Block.sand.blockID);
		world.setBlock(i + 2, j + 0, k + 1, Block.sand.blockID);
		world.setBlock(i + 2, j + 0, k + 2, Block.sand.blockID);
		world.setBlock(i + 2, j + 0, k + 3, Block.sand.blockID);
		world.setBlock(i + 2, j + 0, k + 4, Block.sand.blockID);
		world.setBlock(i + 3, j + 0, k + 0, Block.sand.blockID);
		world.setBlock(i + 3, j + 0, k + 2, Block.sand.blockID);
		world.setBlock(i + 3, j + 0, k + 4, Block.sand.blockID);
		world.setBlock(i + 4, j + 0, k + 0, Block.sand.blockID);
		world.setBlock(i + 4, j + 0, k + 1, Block.sand.blockID);
		world.setBlock(i + 4, j + 0, k + 2, Block.sand.blockID);
		world.setBlock(i + 4, j + 0, k + 3, Block.sand.blockID);
		world.setBlock(i + 4, j + 0, k + 4, Block.sand.blockID);

		return true;
	}
}