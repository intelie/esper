/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.epl.join;

import com.espertech.esper.epl.join.assemble.BaseAssemblyNode;
import com.espertech.esper.epl.join.rep.Node;
import com.espertech.esper.client.EventBean;
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
