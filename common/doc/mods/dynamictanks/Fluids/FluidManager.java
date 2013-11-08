package doc.mods.dynamictanks.Fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.common.ModConfig;

public class FluidManager {

	public static FluidPotion fluidPotion = null;
	
	public static void registerFluids() {
		
		fluidPotion = new FluidPotion(ModConfig.FluidIDs.fluidPotion, DynamicLiquidTanksCore.fluidPotion, Material.water);
		
		 GameRegistry.registerBlock(fluidPotion, "liquidPotion");
		 
		 LanguageRegistry.addName(fluidPotion, "Liquid Potion");
	}
	
}
