package doc.mods.dynamictanks.biome;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE;
import doc.mods.dynamictanks.Fluids.FluidManager;
import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;

public class WorldGenEventHandler {

	@ForgeSubscribe
	public void DecorateBiomeEvent(Decorate e) {
		
		BiomeGenBase biome = e.world.getWorldChunkManager().getBiomeGenAt(e.chunkX, e.chunkZ);
		
		for (int j = 0; j < 50; ++j) {
            int k = e.chunkX + e.rand.nextInt(16) + 8;
            int l = e.rand.nextInt(e.rand.nextInt(120) + 8);
            int i1 = e.chunkZ + e.rand.nextInt(16) + 8;
            if ((new GenerateFluids(FluidManager.clenseBlock.blockID)).generate(e.world, e.rand, k, l, i1)) {
            	System.out.println("Generated at :" + k + " : " + l + ": " + i1);
            }
        }
	}
	
}
