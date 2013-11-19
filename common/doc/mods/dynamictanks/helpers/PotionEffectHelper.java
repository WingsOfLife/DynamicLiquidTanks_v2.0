package doc.mods.dynamictanks.helpers;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;

public class PotionEffectHelper {
	
	public static void applyPotionEffects(EntityPlayer player, int potionId, int durationDivisor, boolean override) {

		List list = PotionHelper.getPotionEffects(potionId, override);

		if (list != null)
		{
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				PotionEffect potioneffect = (PotionEffect)iterator.next();
				potioneffect.duration = potioneffect.getDuration() / durationDivisor;
				player.addPotionEffect(new PotionEffect(potioneffect));
			}
		}
	}
}
