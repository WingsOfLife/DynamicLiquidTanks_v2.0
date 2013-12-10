package doc.mods.dynamictanks.helpers;

public class CommandParser
{
    private int[] index = new int[3];

    private String commandName = null;
    private String commandUnparsed = null;

    public CommandParser(String command)
    {
        commandUnparsed = command;
        parseCommand(command);
    }

    private void parseCommand(String command)
    {
        String delims = "[(,)]+";
        String[] parts = command.split(delims);

        for (String s : parts)
        {
            s.replaceAll(" ", "");
        }

        commandName = parts[0] != null || parts[0] != "" ? parts[0] : null;

        if (parts.length > 1)
            for (int i = 1; i < parts.length; i++)
            {
                index[i - 1] = Integer.valueOf(parts[i].replace(" ", ""));
            }
    }

    public String getCommandName()
    {
        return commandName.replaceAll(" ", "");
    }

    public int[] getIndex()
    {
        return index;
    }
}
