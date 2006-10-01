package net.esper.example.terminal.common;

public class BaseDeskEvent
{
    private final DeskInfo deskInfo;

    public BaseDeskEvent(DeskInfo deskInfo)
    {
        this.deskInfo = deskInfo;
    }

    public DeskInfo getDeskInfo()
    {
        return deskInfo;
    }
}
