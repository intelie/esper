package com.espertech.esper.example.terminal.jse.simulate;

import com.espertech.esper.example.terminal.jse.listener.ComplexEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * In this sample we use a single complex event listener for all EPA
 * <p/>
 * This agent will output all the generated complex events
 */
public class TerminalComplexEventListener implements ComplexEventListener {

    public void onComplexEvent(String complexEvent) {
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance();
        String date = dateFormat.format(new Date());

        System.out.println("CEP: " + date + " " + complexEvent);
    }
}
