package doc.mods.dynamictanks.biome;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import doc.mods.dynamictanks.Fluids.FluidManager;

public class PotionDependencyList {
	
	ArrayList<PotionDependencies> potionDL = new ArrayList<PotionDependencies>();
	
	public PotionDependencyList() {
		
		potionDL.add(new PotionDependencies(FluidManager.fireBlock.blockID, Block.lavaStill.blockID));
		potionDL.add(new PotionDependencies(FluidManager.harmingBlock.blockID, Block.cactus.blockID));
		potionDL.add(new PotionDependencies(FluidManager.healingBlock.blockID, Block.oreDiamond.blockID));
		potionDL.add(new PotionDependencies(FluidManager.invisBlock.blockID, Block.carrot.blockID));
		potionDL.add(new PotionDependencies(FluidManager.nightBlock.blockID, Block.oreGold.blockID));
		potionDL.add(new PotionDependencies(FluidManager.poisonBlock.blockID, Block.web.blockID));
		potionDL.add(new PotionDependencies(FluidManager.regenBlock.blockID, Block.potato.blockID));
		potionDL.add(new PotionDependencies(FluidManager.slowBlock.blockID, Block.snow.blockID));
		potionDL.add(new PotionDependencies(FluidManager.strengthBlock.blockID, Block.mobSpawner.blockID));
		potionDL.add(new PotionDependencies(FluidManager.swiftBlock.blockID, Block.ice.blockID));
		potionDL.add(new PotionDependencies(FluidManager.weakBlock.blockID, Block.vine.blockID));
	}
	
	public int potionFromDependency(World world, int x, int z) {
		for (PotionDependencies pD : potionDL)
			if (pD.containedDependency(world, x, z))
				return pD.getPotionId();
		
		return -1;
	}
}
