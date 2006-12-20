package net.esper.example.terminal.mdb;

public class SupportOutboundSender implements OutboundSender
{
    public void send(String text)
    {
        System.out.println(text);
    }
}
