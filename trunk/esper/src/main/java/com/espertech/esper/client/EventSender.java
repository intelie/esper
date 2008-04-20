package com.espertech.esper.client;

public interface EventSender
{
    public void sendEvent(Object event) throws EPException;
}
