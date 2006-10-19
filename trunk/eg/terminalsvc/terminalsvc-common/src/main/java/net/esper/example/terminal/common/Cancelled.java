package net.esper.example.terminal.common;

public class Cancelled extends BaseTerminalEvent
{
    public Cancelled(TerminalInfo deskInfo)
    {
        super(deskInfo);
    }

    public String getType()
    {
        return "Cancelled";
    }
}
