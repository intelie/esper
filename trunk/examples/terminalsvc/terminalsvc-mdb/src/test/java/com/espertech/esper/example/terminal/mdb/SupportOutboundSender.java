package com.espertech.esper.example.terminal.mdb;

public class SupportOutboundSender implements OutboundSender
{
    public void send(String text)
    {
        System.out.println(text);
    }
}
