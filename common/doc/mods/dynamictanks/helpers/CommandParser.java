package doc.mods.dynamictanks.helpers;

public class CommandParser {

	private int index = -1;
	
	private String commandName = null;
	private String commandUnparsed = null;
	
	public CommandParser(String command) {
		commandUnparsed = command;
		parseCommand(command);
	}
	
	private void parseCommand(String command) {
		String delims = "[()]+";
		String[] parts = command.split(delims);
		
		commandName = parts[0] != null || parts[0] != "" ? parts[0] : null;
		index = (parts[1] != null || parts[1] != "") ? Integer.valueOf(parts[1]) : -1; 
	}
	
	public String getCommandName() {
		return commandName;
	}
	
	public int getIndex() {
		return index;
	}
}
