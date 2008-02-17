package com.espertech.esper.example.terminal.jse.listener;

import com.espertech.esper.event.EventBean;

import java.util.Formatter;

public class CountPerTypeListener extends BaseTerminalListener {

    public CountPerTypeListener(ComplexEventListener complexEventListener) {
        super(complexEventListener);
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        Formatter out = new Formatter(new StringBuffer());
        out.format("%10s | %10s\n", "type", "count");
        out.format("---------- | ----------\n");

        int total = 0;
        for (int i = 0; i < newEvents.length; i++) {
            String type = (String) newEvents[i].get("type");
            long count = (Long) newEvents[i].get("countPerType");
            out.format("%10s | %10s\n", type, count);
            total += count;
        }
        out.format("%10s | %10s\n", "total =", total);

        complexEventListener.onComplexEvent("Current count per type:\n" + out.out().toString());
    }
}
