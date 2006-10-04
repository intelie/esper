package net.esper.example.terminal.common;

import java.io.Serializable;

public class BaseDeskEvent implements Serializable
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
