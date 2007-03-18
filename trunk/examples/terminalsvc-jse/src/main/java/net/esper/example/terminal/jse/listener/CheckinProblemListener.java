package net.esper.example.terminal.jse.listener;

import net.esper.event.EventBean;

public class CheckinProblemListener extends BaseTerminalListener {

    public CheckinProblemListener(ComplexEventListener complexEventListener) {
        super(complexEventListener);
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        String terminal = (String) newEvents[0].get("terminal");
        String message = "Customer checkin problem detected at terminal " + terminal;
        complexEventListener.onComplexEvent(message);
    }
}
