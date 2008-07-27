package com.espertech.esper.example.terminal.jse.listener;

import com.espertech.esper.event.EventBean;

public class TerminalEventListener extends BaseTerminalListener {

    public TerminalEventListener(ComplexEventListener complexEventListener) {
        super(complexEventListener);
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        String terminal = (String) newEvents[0].get("terminal.id");
        String type = (String) newEvents[0].get("type");
        String message = "Terminal " + terminal + " raised a " + type + " event";
        complexEventListener.onComplexEvent(message);
    }
}
