package com.espertech.esper.support.eql;

import com.espertech.esper.eql.join.exec.ExecNode;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.IndentWriter;

import java.util.List;

public class SupportQueryExecNode extends ExecNode
{
    private final String id;
    
    private EventBean[] lastPrefillPath;

    public SupportQueryExecNode(String id)
    {
        this.id = id;
    }

    public void process(EventBean lookupEvent, EventBean[] prefillPath, List<EventBean[]> result)
    {
        lastPrefillPath = prefillPath;
    }

    public String getId()
    {
        return id;
    }

    public EventBean[] getLastPrefillPath()
    {
        return lastPrefillPath;
    }

    public void print(IndentWriter writer)
    {
        writer.println("SupportQueryExecNode");
    }
}
