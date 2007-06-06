package net.esper.multithread.dispatchmodel;

import java.util.LinkedHashMap;

public class DispatchProducer
{
    private final UpdateDispatchViewModel dispatchProcessor;
    private int currentCount;
    private LinkedHashMap<Integer, int[]> payloads = new LinkedHashMap<Integer, int[]>();

    public DispatchProducer(UpdateDispatchViewModel dispatchProcessor)
    {
        this.dispatchProcessor = dispatchProcessor;
    }

    public synchronized int next()
    {
        currentCount++;

        int[] payload = new int[] {currentCount, 0};
        payloads.put(currentCount, payload);

        dispatchProcessor.add(payload);

        return currentCount;
    }

    public LinkedHashMap<Integer, int[]> getPayloads()
    {
        return payloads;
    }
}
