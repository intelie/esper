package net.esper.example.terminal.common;

public class Status extends BaseTerminalEvent
{
    public Status(TerminalInfo deskInfo)
    {
        super(deskInfo);
    }

    public String getType()
    {
        return "Status";
    }
}
