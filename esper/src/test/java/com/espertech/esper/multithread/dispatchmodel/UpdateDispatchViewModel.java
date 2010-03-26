package com.espertech.esper.multithread.dispatchmodel;

public interface UpdateDispatchViewModel
{
    public void add(int[] payload);
    public void execute();
}
