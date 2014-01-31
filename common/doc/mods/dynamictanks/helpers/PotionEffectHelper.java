package doc.mods.dynamictanks.helpers;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;

public class PotionEffectHelper
{
	public static void applyPotionEffects(EntityPlayer player, List list, int durationDivisor, boolean override)
	{
		if (list != null)
		{
			for (Object obj : list) {
				if (obj instanceof PotionEffect) {
					PotionEffect potioneffect = (PotionEffect) obj;
					potioneffect.duration = potioneffect.getDuration() / durationDivisor;
					player.addPotionEffect(new PotionEffect(potioneffect));
				}
			}
		}
	}
}
