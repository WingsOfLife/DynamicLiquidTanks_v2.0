package doc.mods.dynamictanks.items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.common.ModConfig;

public class ItemManager
{
	public static Item memoryItem    = null;

	public static void registerItems()
	{
		memoryItem      = new MemoryItem(ModConfig.ItemIDs.memoryItem);
		LanguageRegistry.addName(new ItemStack(memoryItem), "Fluid Storage");
	}

	public static void registerRecipes() {
		
	}
}
