package net.esper.example.servershell.jms;

import javax.jms.*;

public class JMSContext
{
	private ConnectionFactory factory;
    private Connection connection;
    private Session session;
    private Destination destination;

    public JMSContext(ConnectionFactory factory, Connection connection, Session session, Destination destination)
    {
        this.factory = factory;
        this.connection = connection;
        this.session = session;
        this.destination = destination;
    }

    public void destroy() throws JMSException
    {
        session.close();
        connection.close();
    }

    public Connection getConnection()
    {
        return connection;
    }

    public Destination getDestination()
    {
        return destination;
    }

    public ConnectionFactory getFactory()
    {
        return factory;
    }

    public Session getSession()
    {
        return session;
    }
}
