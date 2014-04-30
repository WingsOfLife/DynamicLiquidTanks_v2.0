package doc.mods.dynamictanks.helpers;

import java.util.Comparator;

import net.minecraftforge.fluids.IFluidHandler;
import doc.mods.dynamictanks.helpers.grapher.BlockPosition;

public class FluidHandlerPart {

	private IFluidHandler myHandler;
	private BlockPosition myPosition;

	public FluidHandlerPart(IFluidHandler myHandler, BlockPosition myPosition) {

		this.myHandler  = myHandler;
		this.myPosition = myPosition;
	}

	public IFluidHandler getFluidHandler() {

		return myHandler;
	}

	public BlockPosition getPosition() {

		return myPosition;
	}
}
