package net.esper.example.terminal.common;

public class LowPaper extends BaseTerminalEvent
{
    public LowPaper(TerminalInfo deskInfo)
    {
        super(deskInfo);
    }

    public String getType()
    {
        return "LowPaper";
    }
}
