package com.espertech.esper.example.terminal.jse.listener;

import com.espertech.esper.event.EventBean;

public class TerminalStatusListener extends CountPerTypeListener {

    public TerminalStatusListener(ComplexEventListener complexEventListener) {
        super(complexEventListener);
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        String terminal = (String) newEvents[0].get("terminal");
        String text = (String) newEvents[0].get("text");
        String message = "Terminal " + terminal + " detected '" + text + "'";
        complexEventListener.onComplexEvent(message);
    }
}
