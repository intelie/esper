package net.esper.persist;

import net.esper.client.logstate.LogEntryType;
import java.util.List;
import java.util.ArrayList;

public class LogContextNode<StateT>
{
    private final LogService logService;
    private final int contextNodeNum;
    private final int contextDepth;
    private final LogEntryType type;
    private final LogContextNode parent;
    private final List<LogContextNode<?>> childNodes;

    private LogContextChangedCallback logContextChangedCallback;
    private StateT state;

    public LogContextNode(LogService logService, LogEntryType type, int contextNodeNum, int contextDepth, LogContextNode parent, StateT state)
    {
        if (state == null)
        {
            throw new IllegalArgumentException("State has not been supplied");
        }

        if (type == null)
        {
            throw new IllegalArgumentException("Type has not been supplied");
        }

        this.logService = logService;
        this.contextNodeNum = contextNodeNum;
        this.contextDepth = contextDepth;
        this.type = type;
        this.parent = parent;
        this.childNodes = new ArrayList<LogContextNode<?>>();
        this.state = state;
    }

    public <StateX> LogContextNode<StateX> createChild(LogEntryType type, StateX state)
    {
        LogContextNode<StateX> childNode = new LogContextNode<StateX>(logService, type, childNodes.size() + 1, contextDepth + 1, this, state);
        childNodes.add(childNode);
        return childNode;
    }

    public int getContextNodeNum()
    {
        return contextNodeNum;
    }

    public int getContextDepth()
    {
        return contextDepth;
    }

    public LogEntryType getType()
    {
        return type;
    }

    public LogContextNode getParent()
    {
        return parent;
    }

    public List<LogContextNode<?>> getChildNodes()
    {
        return childNodes;
    }

    public StateT getState()
    {
        return state;
    }

    public void setState(StateT state)
    {
        this.state = state;
        if (logContextChangedCallback != null)
        {
            logContextChangedCallback.updated();
        }
    }

    public void update()
    {
        logService.add(this);
    }

    public String toString()
    {
        return "type = " + type;
    }

    public LogContextChangedCallback getStateChangedCallback()
    {
        return logContextChangedCallback;
    }

    public void setStateChangedCallback(LogContextChangedCallback logContextChangedCallback)
    {
        this.logContextChangedCallback = logContextChangedCallback;
    }
}
