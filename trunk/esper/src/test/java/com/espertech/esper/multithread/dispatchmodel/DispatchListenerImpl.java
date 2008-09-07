package com.espertech.esper.multithread.dispatchmodel;

import java.util.ArrayList;
import java.util.List;

public class DispatchListenerImpl implements DispatchListener
{
    private ArrayList<int[][]> received = new ArrayList<int[][]>();

    public synchronized void dispatched(int[][] objects)
    {
        received.add(objects);
    }

    public List<int[][]> getReceived()
    {
        return received;
    }
}
