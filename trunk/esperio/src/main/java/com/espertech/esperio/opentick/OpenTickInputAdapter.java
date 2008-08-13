package com.espertech.esperio.opentick;

import com.espertech.esperio.InputAdapter;
import com.espertech.esperio.AdapterSPI;
import com.espertech.esperio.AdapterState;
import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.collection.Pair;
import com.opentick.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class OpenTickInputAdapter implements InputAdapter, AdapterSPI
{
    private static final Log log = LogFactory.getLog(OpenTickInputAdapter.class);

    private final ConfigurationOpenTick configuration;
    private final EsperOpenTickClient openTickClient;
    private final ArrayList<Integer> opentickRequestIds;
    private final Map<String, Pair<Class, Integer>> knownStreamNames;

    private boolean isStarted;
    private boolean isActive;
    private EPServiceProvider defaultEpService;

    public OpenTickInputAdapter(ConfigurationOpenTick configuration)
    {
        this.configuration = configuration;
        openTickClient = new EsperOpenTickClient();
        opentickRequestIds = new ArrayList<Integer>();

        knownStreamNames = new HashMap<String, Pair<Class, Integer>>();
        knownStreamNames.put("OTQuote", new Pair<Class, Integer>(OTQuote.class, OTConstants.OT_TICK_TYPE_QUOTE));
        knownStreamNames.put("OTMMQuote", new Pair<Class, Integer>(OTMMQuote.class, OTConstants.OT_TICK_TYPE_MMQUOTE));
        knownStreamNames.put("OTTrade", new Pair<Class, Integer>(OTTrade.class, OTConstants.OT_TICK_TYPE_TRADE));
        knownStreamNames.put("OTBBO", new Pair<Class, Integer>(OTBBO.class, OTConstants.OT_TICK_TYPE_BBO));
    }

    public void start() throws EPException
    {
        log.info("Adding hosts");
        if ((configuration.getConnection() == null) || (configuration.getConnection().getHosts() == null) ||
            (configuration.getConnection().getHosts().isEmpty()))
        {
            throw new EPException("Configuration does not have any opentick hosts");
        }
        for (ConfigurationOpenTick.ConnectionHost host : configuration.getConnection().getHosts())
        {
            log.debug("Adding opentick host " + host.getHostname() + " and port " + host.getPort());
            openTickClient.addHost(host.getHostname(), host.getPort());
        }

        log.info("Registering listeners");
        openTickClient.addLoginListener(new LoginListener());
        openTickClient.addStatusChangedListener(new StatusListener());
        openTickClient.addErrorListener(new ErrorListener());
        openTickClient.addEquityInitListener(new EquityInitListener());

        log.info("Logging in");
        if (configuration.getConnection().getLogin() == null)
        {
            throw new EPException("Configuration does not have login information");
        }
        String loginId = configuration.getConnection().getLogin().getName();
        try
        {
            openTickClient.login(configuration.getConnection().getLogin().getName(), configuration.getConnection().getLogin().getPassword());
        }
        catch (OTLoginException e)
        {
            String message = "Failed to log into opentick provider using login id '" + loginId + "'";
            log.error(message, e);
            throw new EPException(message + ": " + e.getMessage(), e);
        }

        //Wait until logged in
        int count = 0;
        while (!openTickClient.isLoggedIn())
        {
            try
            {
                Thread.sleep(100);
                count++;
            }
            catch (InterruptedException e)
            {
                throw new EPException("Interruped: " + e.getMessage(), e);
            }
            if (openTickClient.getStatus() == OTConstants.OT_STATUS_INACTIVE)
            {
                throw new EPException("OpenTick client reported OT_STATUS_INACTIVE");
            }
            if (count > 50)
            {
                throw new EPException("OpenTick not logging in: timeout occured");
            }
        }

        log.info("Adding subscriptions to OT and sending to Esper");
        for (Map.Entry<String, ConfigurationOpenTick.OpenTickStream> entry : configuration.getStreams().entrySet())
        {
            if (!entry.getValue().isEnabled())
            {
                continue;
            }

            String streamName = entry.getKey();
            String aliasName = entry.getValue().getAlias();
            String engineURI = entry.getValue().getEngineURI();

            Pair<Class, Integer> streamTypeInfo = knownStreamNames.get(streamName);
            if (streamTypeInfo == null)
            {
                throw new EPException("Unknown stream by name '" + streamName + "', valid values are " + knownStreamNames.values());
            }

            // register alias with engine
            EPServiceProvider provider = EPServiceProviderManager.getProvider(engineURI);
            provider.getEPAdministrator().getConfiguration().addEventTypeAlias(aliasName, streamTypeInfo.getFirst());

            if (streamTypeInfo.getSecond() == OTConstants.OT_TICK_TYPE_QUOTE)
            {
                openTickClient.addRtQuoteListener(new OTListenerEsperSender(provider));
            }
            else if (streamTypeInfo.getSecond() == OTConstants.OT_TICK_TYPE_MMQUOTE)
            {
                openTickClient.addRtMMQuoteListener(new OTListenerEsperSender(provider));
            }
            else if (streamTypeInfo.getSecond() == OTConstants.OT_TICK_TYPE_TRADE)
            {
                openTickClient.addRtTradeListener(new OTListenerEsperSender(provider));
            }
            else if (streamTypeInfo.getSecond() == OTConstants.OT_TICK_TYPE_BBO)
            {
                openTickClient.addRtBBOListener(new OTListenerEsperSender(provider));
            }
            else
            {
                throw new EPException("Unknown stream by name '" + streamName + "', valid values are " + knownStreamNames.values());
            }
        }

        activateStreams();
        isStarted = true;
    }

    public void pause() throws EPException
    {
        deactivateStreams();
    }

    public void resume() throws EPException
    {
        activateStreams();
    }

    public void stop() throws EPException
    {
        deactivateStreams();
    }

    public void destroy() throws EPException
    {
        try
        {
            openTickClient.logout();
        }
        catch (OTException e)
        {
            String message = "Failed to log out of opentick provider";
            log.error(message, e);
            throw new EPException(message + ": " + e.getMessage(), e);
        }
        isStarted = false;
    }

    public AdapterState getState()
    {
        if (isStarted && isActive)
        {
            return AdapterState.STARTED;
        }
        if (isStarted)
        {
            return AdapterState.PAUSED;
        }
        else
        {
            return AdapterState.DESTROYED;
        }
    }

    private void deactivateStreams()
    {
        for (int requestId : opentickRequestIds)
        {
            try
            {
                openTickClient.cancelTickStream(requestId);
            }
            catch (OTException e)
            {
                String message = "Failed to cancel tick stream";
                log.error(message, e);
            }
        }
        opentickRequestIds.clear();
        isActive = false;
    }

    private void activateStreams()
    {
        Set<OpenTickSubscriberSpec> subscribers = computeSubscribers();
        for (OpenTickSubscriberSpec subscriber : subscribers)
        {
            try
            {
                OTDataEntity entity = new OTDataEntity(subscriber.getExchange(), subscriber.getSymbol());
                int requestId = openTickClient.requestTickStream(entity, subscriber.getOptionalType());
                opentickRequestIds.add(requestId);
            }
            catch (OTException e)
            {
                String message = "Failed to request tick stream from opentick provider";
                log.error(message, e);
                throw new EPException(message + ": " + e.getMessage(), e);
            }
        }
        isActive = true;
    }

    private Set<OpenTickSubscriberSpec> computeSubscribers()
    {
        Set<OpenTickSubscriberSpec> subscribers = new LinkedHashSet<OpenTickSubscriberSpec>();

        // for each enabled stream
        for (Map.Entry<String, ConfigurationOpenTick.OpenTickStream> entry : configuration.getStreams().entrySet())
        {
            if (!entry.getValue().isEnabled())
            {
                continue;
            }
            String streamName = entry.getKey();

            Pair<Class, Integer> streamTypeInfo = knownStreamNames.get(streamName);
            if (streamTypeInfo == null)
            {
                throw new EPException("Unknown stream by name '" + streamName + "', valid values are " + knownStreamNames.values());
            }

            // For each symbol list assigned to the same stream
            for (ConfigurationOpenTick.StreamSymbolList symbolList : configuration.getStreamSymbolLists())
            {
                if (!symbolList.getStreamName().equals(streamName))
                {
                    continue;
                }

                String symbolListName = symbolList.getSymbolListName();
                List<ConfigurationOpenTick.ExchangeAndSymbol> combinations = configuration.getSymbolLists().get(symbolListName);
                if (combinations == null)
                {
                    continue;
                }

                // For each combination of exchange and symbol
                for (ConfigurationOpenTick.ExchangeAndSymbol combination : combinations)
                {
                    subscribers.add(new OpenTickSubscriberSpec(combination.getExchange(), combination.getSymbol(), streamTypeInfo.getSecond()));
                }
            }
        }

        return subscribers;
    }

    public void setEPServiceProvider(EPServiceProvider epService)
    {
        defaultEpService = epService;
    }

    public EPServiceProvider getEPServiceProvider()
    {
        return defaultEpService;
    }

    final class StatusListener implements OTListener<Integer>
    {
        public void dataArrived(Integer data)
        {
            log.info("Status changed: " + data);
        }
    }

    final class LoginListener implements OTListener
    {
        public void dataArrived(Object data)
        {
            log.info("Logged in: " + data);
        }
    }

    final class ErrorListener implements OTListener<OTError>
    {
        public void dataArrived(OTError data)
        {
            log.error("Error reported: " + data);
        }
    }

    final class EquityInitListener implements OTListener<OTEquityInit>
    {
        public void dataArrived(OTEquityInit data)
        {
            log.info("EquityInit: " + data);
        }
    }
}
