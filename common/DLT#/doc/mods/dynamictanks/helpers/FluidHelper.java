package doc.mods.dynamictanks.helpers;

import java.util.LinkedList;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidHandler;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class FluidHelper {

	public static int getFluidAmtIndex(LinkedList<FluidTank> tanks, int index) {
		if (index >= tanks.size())
			return 0;

		return tanks.get(index).getFluidAmount();
	}

	public static String getName(LinkedList<FluidTank> tanks, int index)
	{
		if (index >= tanks.size())
		{
			return "Empty";
		}

		if (tanks.get(index).getFluid() != null)
		{
			return tanks.get(index).getFluid().getFluid().getName();
		}

		return "Empty";
	}

	public static boolean hasLiquid(ControllerTileEntity controller)
	{
		if (controller != null)
		{
			for (FluidTank fT : controller.getAllLiquids())
			{
				if (fT.getFluid() != null)
				{
					return true;
				}
			}
		}

		return false;
	}

	public static int getHandlerCapacity(IFluidHandler fluidHandler) {

		if (fluidHandler == null)
			return 0;

		return fluidHandler.getTankInfo(ForgeDirection.UP)[0].capacity;
	}

	public static boolean fluidHandlerFilled(IFluidHandler fluidHandler) {

		if (fluidHandler == null)
			return false;

		if (fluidHandler.getTankInfo(ForgeDirection.UP) == null)
			return false;

		if (fluidHandler.getTankInfo(ForgeDirection.UP).length == 0)
			return false;

		if (fluidHandler.getTankInfo(ForgeDirection.UP)[0].fluid == null)
			return false;

		return true;
	}

	public static boolean fluidHandlerFilledWithFuel(IFluidHandler fluidHandler) {

		if (fluidHandler == null)
			return false;

		if (fluidHandler.getTankInfo(ForgeDirection.UP) == null)
			return false;

		if (fluidHandler.getTankInfo(ForgeDirection.UP).length == 0)
			return false;

		if (fluidHandler.getTankInfo(ForgeDirection.UP)[0].fluid == null)
			return false;

		return getFluidFromHandler(fluidHandler).isFluidEqual(new FluidStack(FluidRegistry.LAVA, 1));
	}

	public static FluidStack getFluidFromHandler(IFluidHandler fluidHandler) {

		if (fluidHandlerFilled(fluidHandler))
			return fluidHandler.getTankInfo(ForgeDirection.UP)[0].fluid;

		return null;
	}

	public static int getAmntFromHandler(IFluidHandler fluidHandler) {

		if (fluidHandlerFilled(fluidHandler))
			return getFluidFromHandler(fluidHandler).amount;

		return 0;
	}

	public static int combinedAmount(FluidHandlerPart o1, FluidHandlerPart o2) {

		return getAmntFromHandler(o1.getFluidHandler()) + getAmntFromHandler(o2.getFluidHandler());
	}

	public static void setAmntForHandler(IFluidHandler fluidHandler, int newAmount) {

		if (fluidHandlerFilled(fluidHandler)) {

			getFluidFromHandler(fluidHandler).amount = newAmount;

			if (newAmount == 0)
				fluidHandler.drain(ForgeDirection.UNKNOWN, 0, true);
		}
	}

	public static int getLiquidAmountScaledForGUI(int amount, int capacity)
	{
		double f = (((amount * 0.01) / (capacity * 0.01)) * 35.0D);

		if (f > 35)
		{
			return 35;
		}

		return (int) f;
	}

	public static int getPowerAmountScaledForGUI(int amount, int capacity)
	{
		double f = (((amount * 0.01) / (capacity * 0.01)) * 58.0D);

		if (f > 58)
			return 58;

		return 58 - (int) f;
	}

	public static boolean hasPotion(ControllerTileEntity controller)   //TODO
	{
		/*if (controller != null)
        	for (FluidTank fT : controller.getAllLiquids())
        		if (fT.getFluid() != null)
        			if (fT.getFluid().equals(new FluidStack(FluidManager.potionFluid, FluidContainerRegistry.BUCKET_VOLUME)))
        				return true;*/
		return false;
	}
}
