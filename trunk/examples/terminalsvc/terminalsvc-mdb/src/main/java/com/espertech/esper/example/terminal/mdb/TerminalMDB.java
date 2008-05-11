package com.espertech.esper.example.terminal.mdb;

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.ejb.EJBException;
import javax.jms.*;

public class TerminalMDB implements MessageDrivenBean, MessageListener
{
    private static OutboundQueueSender outboundQueueSender;
    private static EPServiceMDBAdapter mdbAdapter;
    private static Boolean isInitialized = new Boolean(false);

    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
    {
        //System.out.println(TerminalMDB.class.getName() + "::setMessageDrivenContext invoked");
    }

    public void ejbCreate() throws EJBException
    {
        //System.out.println(TerminalMDB.class.getName() + "::ejbCreate invoked");

        synchronized(isInitialized)
        {
            if (!isInitialized)
            {
                System.out.println(TerminalMDB.class.getName() + "::ejbCreate initializing sender and engine");

                // Connect to outbound queue
                outboundQueueSender = new OutboundQueueSender();

                // Get engine instance - same engine instance for all MDB instances
                mdbAdapter = new EPServiceMDBAdapter(outboundQueueSender);

                isInitialized = true;
            }
        }
    }

    public void ejbRemove() throws EJBException
    {
        System.out.println(TerminalMDB.class.getName() + "::ejbRemove invoked");
    }

    public void onMessage(Message message)
    {
        Object event = null;
        try
        {
            ObjectMessage objMessage = (ObjectMessage) message;
            //System.out.println("onMessage received event=" + objMessage.getObject());
            event = objMessage.getObject();
        }
        catch (JMSException ex)
        {
            String messageText = "Error sending response message";
            System.out.println(messageText + ":" + ex);
            return;
        }

        try
        {
            mdbAdapter.sendEvent(event);
        }
        catch (RuntimeException ex)
        {
            String messageText = "Error processing event, event=" + event;
            System.out.println(messageText + ":" + ex);
            return;
        }
    }
}
