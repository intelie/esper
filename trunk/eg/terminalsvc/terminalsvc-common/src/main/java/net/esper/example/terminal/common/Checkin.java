package net.esper.example.terminal.common;

public class Checkin extends BaseTerminalEvent
{
    public Checkin(TerminalInfo deskInfo)
    {
        super(deskInfo);
    }

    public String getType()
    {
        return "Checkin";
    }
}
