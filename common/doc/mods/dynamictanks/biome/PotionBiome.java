package doc.mods.dynamictanks.biome;

import java.util.Random;

import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.block.BlockManager;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenLiquids;

public class PotionBiome extends BiomeGenBase {

	public PotionBiome(int par1) {
		super(par1);
		
		spawnableCreatureList.clear();
		spawnableMonsterList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntityWitch.class, 50, 1, 2));
		
		this.topBlock = (byte) BlockManager.BlockHD.blockID;
        this.fillerBlock = (byte) Block.dirt.blockID;
        
        theBiomeDecorator.bigMushroomsPerChunk = 1;
        theBiomeDecorator.treesPerChunk = 0;
        theBiomeDecorator.sandGen = null;
        theBiomeDecorator.flowersPerChunk = 0;
        theBiomeDecorator.grassPerChunk = 0;
        theBiomeDecorator.generateLakes = false;
        theBiomeDecorator.clayPerChunk = 1;
        
        waterColorMultiplier = 0xE01B1B;
	}
	
	@Override
	public void decorate(World world, Random random, int par3, int par4) {
		//super.decorate(world, random, par3, par4);
		
		for (int j = 0; j < 20; ++j)
        {
            int k = par3 + random.nextInt(16) + 8;
            int l = random.nextInt(random.nextInt(random.nextInt(112) + 8) + 8);
            int i1 = par4 + random.nextInt(16) + 8;
            (new WorldGenLiquids(FluidManager.poisonBlock.blockID)).generate(world, random, k, l, i1);
        }
	}
}
