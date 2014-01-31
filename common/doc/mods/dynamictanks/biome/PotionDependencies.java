package doc.mods.dynamictanks.biome;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import doc.mods.dynamictanks.common.ModConfig;

public class PotionDependencies {
	
	protected int PotionId   = ModConfig.FluidIDs.potion;
	protected int dependency = -1;
	
	public PotionDependencies(int potionId, int dependencyId) {
		
		PotionId = potionId;
		dependency = dependencyId;
	}
	
	public boolean containedDependency(World world, int x, int z) {
		
		Chunk c = world.getChunkFromBlockCoords(x, z);
		for (int i = c.xPosition; i < (c.xPosition + 16); i++)
			for (int j = c.zPosition; j < (c.zPosition + 16); j++)
				for (int k = 0; k < c.heightMapMinimum; k++)
					if (world.getBlockId(i, k, j) == dependency)
						return true;
		return false;
	}
	
	public int getPotionId() {
		return PotionId;
	}
}
