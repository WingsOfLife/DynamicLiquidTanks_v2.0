package doc.mods.dynamictanks.biome;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class TreeGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		generateSurface(world, random, chunkX * 16, chunkZ * 16);
	}

	private void generateSurface(World world, Random random, int BlockX, int BlockZ) {
		
		BiomeGenBase b = world.getBiomeGenForCoords(BlockX, BlockZ); 
		
		if (!b.biomeName.equals("Haunted Forest"))
			return;
		
		for(int i = 0; i < 5; i++){
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);

			(new CustomTreeGenerator(false, 4, 0, 1, false)).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}
		
		for(int i = 0; i < 150; i++){
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);

			(new HauntedTreeGenerator(false)).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}
		
		for(int i = 0; i < 150; i++){
			int Xcoord1 = BlockX + random.nextInt(16);
			int Ycoord1 = random.nextInt(90);
			int Zcoord1 = BlockZ + random.nextInt(16);

			(new HauntedTreeGenerator2(false, 9, 8, 0, 0)).generate(world, random, Xcoord1, Ycoord1, Zcoord1);
		}

	}

}
