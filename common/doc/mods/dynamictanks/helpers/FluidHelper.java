package doc.mods.dynamictanks.helpers;

import java.util.LinkedList;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class FluidHelper {

	public static int getTotal(LinkedList<FluidTank> tanks, FluidStack toFind, int amount, int currentIndex) {
		if (currentIndex >= tanks.size() || tanks.isEmpty())
			return amount;
		if (tanks.get(currentIndex).getFluid() != null)
			return getTotal(tanks, toFind, amount + (tanks.get(currentIndex).getFluid().isFluidEqual(toFind) ? tanks.get(currentIndex).getFluid().amount : 0), currentIndex + 1);
		return getTotal(tanks, toFind, amount, currentIndex + 1);
	}
	
	public static String getName(LinkedList<FluidTank> tanks, int index) {
		if (index >= tanks.size())
			return "Empty";
		
		if (tanks.get(index).getFluid() != null)
			return tanks.get(index).getFluid().getFluid().getName();
		
		return "Empty";
	}
}
