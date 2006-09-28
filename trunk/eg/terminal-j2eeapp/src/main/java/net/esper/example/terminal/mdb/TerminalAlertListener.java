package net.esper.example.atm;

import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.ejb.EJBException;
import javax.jms.MessageListener;
import javax.jms.Message;

public class TerminalAlertListener implements MessageDrivenBean, MessageListener
{
    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
    {
    }

    public void ejbRemove() throws EJBException
    {
    }

    public void onMessage(Message message)
    {
        System.out.println("Message=" + message.toString());
    }
}
