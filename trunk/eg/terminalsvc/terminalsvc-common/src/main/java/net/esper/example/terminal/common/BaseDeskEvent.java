package net.esper.example.terminal.common;

public class BaseDeskEvent
{
    private final DeskInfo desk;

    public BaseDeskEvent(DeskInfo deskInfo)
    {
        this.desk = deskInfo;
    }

    public DeskInfo getDesk()
    {
        return desk;
    }
}
