package com.espertech.esper.support.eql.join;

import com.espertech.esper.eql.join.assemble.BaseAssemblyNode;
import com.espertech.esper.eql.join.rep.Node;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.IndentWriter;

import java.util.List;
import java.util.LinkedList;

public class SupportJoinProcNode extends BaseAssemblyNode
{
    private List<EventBean[]> rowsList = new LinkedList<EventBean[]>();
    private List<Integer> streamNumList = new LinkedList<Integer>();
    private List<EventBean> myEventList = new LinkedList<EventBean>();
    private List<Node> myNodeList = new LinkedList<Node>();

    public SupportJoinProcNode(int streamNum, int numStreams)
    {
        super(streamNum, numStreams);
    }

    public void init(List<Node>[] result)
    {

    }

    public void process(List<Node>[] result)
    {

    }

    public void result(EventBean[] row, int streamNum, EventBean myEvent, Node myNode)
    {
        rowsList.add(row);
        streamNumList.add(streamNum);
        myEventList.add(myEvent);
        myNodeList.add(myNode);
    }

    public void print(IndentWriter indentWriter)
    {
        throw new UnsupportedOperationException("unsupported");
    }

    public List<EventBean[]> getRowsList()
    {
        return rowsList;
    }

    public List<Integer> getStreamNumList()
    {
        return streamNumList;
    }

    public List<EventBean> getMyEventList()
    {
        return myEventList;
    }

    public List<Node> getMyNodeList()
    {
        return myNodeList;
    }
}
