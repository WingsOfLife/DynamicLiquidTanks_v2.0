package doc.mods.dynamictanks.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.LanguageRegistry;
import doc.mods.dynamictanks.common.ModConfig;

public class ItemManager {

	public static Item upgradeItem = null;
	
	public static void registerItems() {
		upgradeItem = new UpgradeItems(ModConfig.ItemIDs.upgradeItems);
		for (int i = 0; i < UpgradeItems.names.length; i++)
			LanguageRegistry.addName(new ItemStack(upgradeItem, 1, i), UpgradeItems.names[i]);
	}
	
	public static void registerRecipes() {
		
	}
	
}
