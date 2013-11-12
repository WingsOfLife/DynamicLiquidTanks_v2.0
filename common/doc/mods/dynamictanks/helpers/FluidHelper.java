package doc.mods.dynamictanks.helpers;

import java.util.LinkedList;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import doc.mods.dynamictanks.DynamicLiquidTanksCore;
import doc.mods.dynamictanks.Fluids.FluidManager;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

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

	public static boolean hasLiquid(ControllerTileEntity controller) {
		if (controller != null) {
			for (FluidTank fT : controller.getAllLiquids()) {
				if (fT.getFluid() != null) {
					return true;
				}
			}
		}
		return false;
	}

	public static int getLiquidAmountScaledForGUI(int amount, int capacity) {
		double f = (((amount * 0.01) / (capacity * 0.01)) * 35.0D);
		if (f > 35)
			return 35;
		return (int) f;
	}

	public static boolean hasPotion(ControllerTileEntity controller) { //TODO
		if (controller != null) 
			for (FluidTank fT : controller.getAllLiquids())
				if (fT.getFluid() != null)
					if (fT.getFluid().equals(new FluidStack(FluidManager.potionFluid, FluidContainerRegistry.BUCKET_VOLUME))) 
						return true;

		return false;
	}
}
