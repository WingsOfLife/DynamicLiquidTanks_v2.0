package doc.mods.dynamictanks.helpers;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraftforge.fluids.FluidTank;

import org.apache.commons.lang3.ArrayUtils;

import doc.mods.dynamictanks.packets.PacketHandler;
import doc.mods.dynamictanks.tileentity.ControllerTileEntity;

public class ControllerCommands {

	public static String[] controllerGetCommandList = {
		"getLiquids", "getNumStored", "getTotalCapacity", "getExtractIndex",
		"getAmountOfEa", "getLiquidAtIndex", "getLiqAmtAtIndex",
		"getNeighbors", "getCurrentCamo", "getNameAtIndex"
	};

	public static String[] controllerSetCommandList = {
		"setCamo", "setCamoMeta", "setExtractIndex"
	};

	public static ArrayList<String> commandList = new ArrayList<String>(Arrays.asList(ArrayUtils.addAll(controllerGetCommandList, controllerSetCommandList)));

	public ControllerCommands(String command) {
		CommandParser cmdParse = new CommandParser(command);
	}


	/*
	 * Return Checks
	 */

	public static String whatToReturn(String command, int[] index, ControllerTileEntity controller) {
		if (!validCommand(command))
			return "Invalid Cmd.";
		
		return getCommandReturns(command, index, controller);
	}

	public static String getCommandReturns(String cmd, int index[], ControllerTileEntity controller) {
		if (cmd.equals("getLiquids")) {
			String list = ";";
			for (FluidTank fT : controller.getAllLiquids())
				if (fT.getFluid().fluidID != 0)
					list += fT.getFluid().getFluid().getName() + ";";
			return "Fluids: " + list;
		}		
		else if (cmd.equals("getNumStored"))
			return "Liquids: " + controller.getStored();
		else if (cmd.equals("getTotalCapacity"))
			return "" + controller.getTankCapacity();
		else if (cmd.equals("getExtractIndex"))
			return "" + controller.getLiquidIndex();

		return setCommandReturns(cmd, index, controller);
	}

	public static String setCommandReturns(String cmd, int index[], ControllerTileEntity controller) {
		if (cmd.equals("setCamo")) {
			if (index.length < 2)
				return "false";

			PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.camo, 
					index[0], 0, 0, 0, 0, 0, controller.xCoord, 
					controller.yCoord, controller.zCoord
					);
			return "true";
		}
		else if (cmd.equals("setCamoMeta")) {
			if (index.length < 3)
				return "false";

			PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.camoMeta, 
					index[0], 0, index[1], 0, 0, 0, controller.xCoord, 
					controller.yCoord, controller.zCoord
					);
			return "true";
		}
		else if (cmd.equals("setExtractIndex")) {
			if (index.length < 2)
				return "false";

			PacketHandler.sendPacketWithInt(PacketHandler.PacketIDs.extractIndex, 
					index[0], 0, 0, 0, 0, 0, controller.xCoord, 
					controller.yCoord, controller.zCoord
					);
			return "Index " + index[0] + " set.";
		}

		return "Invalid Cmd.";
	}

	public static boolean validCommand(String cmd) {
		for (String s : controllerGetCommandList)
			if (cmd.equals(s))
				return true;
		for (String s : controllerSetCommandList)
			if (cmd.equals(s))
				return true;
		return false;
	}
}
