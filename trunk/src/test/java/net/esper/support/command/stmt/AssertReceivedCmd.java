package net.esper.support.command.stmt;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPStatement;
import net.esper.client.UpdateListener;
import net.esper.support.util.SupportUpdateListener;
import net.esper.event.EventBean;

import java.util.Iterator;

import junit.framework.Assert;

public class AssertReceivedCmd implements StmtLevelCommand
{
    private int statementNum;
    private int listenerNum;
    private Object[][] newEventProps;
    private Object[][] oldEventProps;

    public AssertReceivedCmd(int statementNum, int listenerNum, Object[][] newEventProps, Object[][] oldEventProps)
    {
        this.statementNum = statementNum;
        this.listenerNum = listenerNum;
        this.newEventProps = newEventProps;
        this.oldEventProps = oldEventProps;
    }

    public void execute(EPServiceProvider engine)
    {
        EPStatement statement = engine.getEPAdministrator().getStatements().get(statementNum);

        Iterator<UpdateListener> it = statement.getListeners().iterator();
        for (int i = 0; i < listenerNum; i++)
        {
            it.next();
            if (!it.hasNext())
            {
                break;
            }
        }
        if (!it.hasNext())
        {
            throw new IllegalStateException("Listener " + listenerNum + " for statement " + statementNum + " not found");
        }
        SupportUpdateListener listener = (SupportUpdateListener) it.next();

        Assert.assertEquals(1, listener.getLastNewData().length);
        Assert.assertEquals(1, listener.getLastOldData().length);

        EventBean newEvent = listener.getLastNewData()[0];
        for (int i = 0; i < newEventProps.length; i++)
        {
            String name = (String) newEventProps[i][0];
            Object value = newEventProps[i][1];
            Assert.assertEquals("newEvent property named " + name, value, newEvent.get(name));
        }

        EventBean oldEvent = listener.getLastOldData()[0];
        for (int i = 0; i < oldEventProps.length; i++)
        {
            String name = (String) oldEventProps[i][0];
            Object value = oldEventProps[i][1];
            Assert.assertEquals("oldEvent property named " + name,value, oldEvent.get(name));
        }

        listener.reset();
    }

    public String toString()
    {
        return this.getClass().getSimpleName() +
                " statementNum=" + statementNum +
                " listenerNum=" + listenerNum; 
    }
}
