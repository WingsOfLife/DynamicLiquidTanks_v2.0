package doc.mods.dynamictanks.helpers;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

public class ControllerCommands {

	public String[] controllerGetCommandList = {
			"getLiquids", "getNumStored", "getTotalCapacity",
			"getAmountOfEa", "getLiquidAtIndex", "getLiqAmtAtIndex",
			"getNeighbors", "getCurrentCamo", "getNameAtIndex"
	};
	
	public String[] controllerSetCommandList = {
			"setCamo", "setCamoWithMeta", "setExtractIndex"
	};
	
	public ArrayList<String> commandList = new ArrayList<String>(Arrays.asList(ArrayUtils.addAll(controllerGetCommandList, controllerSetCommandList)));
	
	public ControllerCommands(String command) {
		CommandParser cmdParse = new CommandParser(command);
	}
	
	
	/*
	 * Switch Case
	 * If/Else
	 */
	
	public String whatToReturn(String command) {
		if (!commandList.contains(command))
			return "Command not recognized. Invalid Command.";
		
		return "Command not recognized. Invalid Command.";
	}
}
