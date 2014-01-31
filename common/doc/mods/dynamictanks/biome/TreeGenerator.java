package doc.mods.dynamictanks.biome;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import doc.mods.dynamictanks.block.BlockManager;
import doc.mods.dynamictanks.common.ModConfig;

public class TreeGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		generateSurface(world, random, chunkX * 16, chunkZ * 16);
	}

	private void generateSurface(World world, Random random, int BlockX, int BlockZ)
	{
		BiomeGenBase b = world.getBiomeGenForCoords(BlockX, BlockZ);

		for (int i = 0; i < 1; i++)
		{
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);
			(new WorldGenFountainOfYouth()).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}

		if (ModConfig.miscBoolean.worldPuddleGen == true && !b.biomeName.equals("Ocean") && !b.biomeName.equals("River") && !b.biomeName.equals("FrozenOcean") && !b.biomeName.equals("FrozenRiver") && !b.biomeName.equals("Swampland")) {
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);
			int potionID = -1;

			if ((potionID = (new PotionDependencyList()).potionFromDependency(world, Xcoord1, Zcoord1)) != -1)
				if ((new GenerateLiquids(3, potionID)).generate(world, random, Xcoord1, Ycoord1, Zcoord1))
					System.out.println(Xcoord1 + "::" + Ycoord1 + "::" + Zcoord1);
		}

		if (!b.biomeName.equals("Haunted Forest"))
		{
			return;
		}

		for (int i = 0; i < 15; i++)
		{
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);
			(new WorldGenCreepyTree(true)).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}

		for (int i = 0; i < 10; i++)
		{
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);
			(new WorldGenHauntedTree(true)).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}

		/*for (int i = 0; i < 15; i++)
		{
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);
			(new WorldGenPotionPuddles(BlockManager.BlockHD.blockID)).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}*/

		for (int i = 0; i < 10; i++)
		{
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);
			(new GenMetadataFlowers(BlockManager.BlockDF.blockID, 0, false)).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}

		for (int i = 0; i < 10; i++)
		{
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);
			(new GenMetadataFlowers(BlockManager.BlockDF.blockID, 1, false)).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}

		for (int i = 0; i < 10; i++)
		{
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);
			(new GenMetadataFlowers(BlockManager.BlockDF.blockID, 2, false)).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}
	}
}
