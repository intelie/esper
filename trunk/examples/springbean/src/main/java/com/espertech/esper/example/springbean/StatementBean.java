package com.espertech.esper.example.springbean;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EPStatement;

import java.util.LinkedHashSet;
import java.util.Set;

public class StatementBean
{
    private final String epl;
    private EPStatement epStatement;
    Set<UpdateListener> listeners = new LinkedHashSet<UpdateListener>();

    public StatementBean(String epl) {
        this.epl = epl;
    }

    public String getEPL(){
        return epl;
    }

    public void setListeners(UpdateListener... listeners) {
        for (UpdateListener listener : listeners) {
            addListener(listener);
        }
    }

    public void addListener(UpdateListener listener) {
        listeners.add(listener);
        if (epStatement != null) {
            epStatement.addListener(listener);
        }
    }

    void setEPStatement(EPStatement epStatement) {
        this.epStatement = epStatement;
        for (UpdateListener listener : listeners) {
            epStatement.addListener(listener);
        }
    }
}
