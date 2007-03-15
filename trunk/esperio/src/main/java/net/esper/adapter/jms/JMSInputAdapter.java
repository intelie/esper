package net.esper.adapter.jms;

import net.esper.adapter.*;
import net.esper.client.*;
import net.esper.core.*;
import org.apache.commons.logging.*;

/**
 * Created for ESPER.
 */
public abstract class JMSInputAdapter implements InputAdapter, AdapterSPI
{
    protected final AdapterStateManager stateManager = new AdapterStateManager();
    protected EPServiceProviderSPI epServiceProviderSPI;
    protected long currentTime = 0;
    protected long startTime;
    protected JMSMessageUnmarshaler jmsMessageUnmarshaler;

    private final Log log = LogFactory.getLog(this.getClass());

    public JMSMessageUnmarshaler getJmsMessageUnmarshaler()
    {
        return jmsMessageUnmarshaler;
    }

    public void setJmsMessageUnmarshaler(
            JMSMessageUnmarshaler jmsMessageUnmarshaler)
    {
        this.jmsMessageUnmarshaler = jmsMessageUnmarshaler;
    }

    public EPServiceProvider getEPServiceProvider()
    {
        return epServiceProviderSPI;
    }

    public void setEPServiceProvider(EPServiceProvider epService)
    {
        if (epService == null)
        {
            throw new IllegalArgumentException("Null service provider");
        }
        if (!(epService instanceof EPServiceProviderSPI))
        {
            throw new IllegalArgumentException("Cannot downcast service provider to SPI");
        }
        epServiceProviderSPI = (EPServiceProviderSPI) epService;
    }

    public void start() throws EPException
    {
        log.debug(".start");
        if (epServiceProviderSPI.getEPRuntime() == null)
        {
            throw new EPException(
                    "Attempting to start an Adapter that hasn't had the epService provided");
        }
        startTime = getCurrentTime();
        log.debug(".start startTime==" + startTime);
        stateManager.start();
    }

    public void pause() throws EPException
    {
        log.debug(".pause");
        stateManager.pause();
    }

    public void resume() throws EPException
    {
        log.debug(".resume");
        stateManager.resume();
    }

    public void stop() throws EPException
    {
        log.debug(".stop");
        stateManager.stop();
    }

    public void destroy() throws EPException
    {
        log.debug(".destroy");
        stateManager.destroy();
    }

    public AdapterState getState()
    {
        return stateManager.getState();
    }

    protected long getCurrentTime()
    {
        return System.currentTimeMillis();
    }


}
