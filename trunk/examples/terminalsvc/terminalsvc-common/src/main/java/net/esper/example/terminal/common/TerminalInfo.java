package net.esper.example.terminal.common;

import java.io.Serializable;

public class TerminalInfo implements Serializable
{
    private String id;

    public TerminalInfo(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
}
