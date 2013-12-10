package doc.mods.dynamictanks.biome;

import cpw.mods.fml.common.registry.GameRegistry;
import doc.mods.dynamictanks.common.ModConfig;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class BiomeManager
{
    public static BiomeGenBase potionBiome;

    public static void registerBiomes()
    {
        potionBiome = new PotionBiome(ModConfig.BiomeIDs.potionBiome).setBiomeName("Haunted Forest").setDisableRain();
        GameRegistry.registerWorldGenerator(new TreeGenerator());
        GameRegistry.addBiome(potionBiome);
        BiomeDictionary.registerBiomeType(potionBiome, BiomeDictionary.Type.MAGICAL);
    }
}
