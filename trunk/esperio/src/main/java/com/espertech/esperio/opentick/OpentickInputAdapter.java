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

public class OpentickInputAdapter implements InputAdapter, AdapterSPI
{
    private static final Log log = LogFactory.getLog(OpentickInputAdapter.class);

    private final ConfigurationOpentick configuration;
    private final OTEventBasedClient openTickClient;
    private final ArrayList<Integer> opentickRequestIds;
    private final Map<String, Pair<Class, Integer>> knownStreamNames;

    private boolean isStarted;
    private boolean isActive;
    private EPServiceProvider defaultEpService;

    public OpentickInputAdapter(ConfigurationOpentick configuration)
    {
        this.configuration = configuration;
        openTickClient = new OTEventBasedClient();
        opentickRequestIds = new ArrayList<Integer>();

        knownStreamNames = new HashMap<String, Pair<Class, Integer>>();
        knownStreamNames.put("OTQuote", new Pair<Class, Integer>(OTQuote.class, OTConstants.OT_TICK_TYPE_QUOTE));
        knownStreamNames.put("OTMMQuote", new Pair<Class, Integer>(OTMMQuote.class, OTConstants.OT_TICK_TYPE_MMQUOTE));
        knownStreamNames.put("OTTrade", new Pair<Class, Integer>(OTTrade.class, OTConstants.OT_TICK_TYPE_TRADE));
        knownStreamNames.put("OTBBO", new Pair<Class, Integer>(OTBBO.class, OTConstants.OT_TICK_TYPE_BBO));
    }

    public ConfigurationOpentick getConfiguration()
    {
        return configuration;
    }

    public void start() throws EPException
    {
        log.info("Adding hosts");
        if ((configuration.getConnection() == null) || (configuration.getConnection().getHosts() == null) ||
            (configuration.getConnection().getHosts().isEmpty()))
        {
            throw new EPException("Configuration does not have any opentick hosts");
        }
        for (ConfigurationOpentick.ConnectionHost host : configuration.getConnection().getHosts())
        {
            log.info("Adding opentick host " + host.getHostname() + " and port " + host.getPort());
            openTickClient.addHost(host.getHostname(), host.getPort());
        }

        log.info("Registering listeners");
        openTickClient.addLoginListener(new LoginListener());
        openTickClient.addStatusChangedListener(new StatusListener());
        openTickClient.addErrorListener(new ErrorListener());
        openTickClient.addEquityInitListener(new EquityInitListener());

        if (configuration.getConnection().getLogin() == null)
        {
            throw new EPException("Configuration does not have login information");
        }
        String loginId = configuration.getConnection().getLogin().getName();
        String pwd = configuration.getConnection().getLogin().getPassword();
        log.info("Logging in using login id '" + loginId + "' and password '" + pwd + "'");
        try
        {
            openTickClient.login(configuration.getConnection().getLogin().getName(), pwd);
        }
        catch (OTLoginException e)
        {
            String message = "Failed to log into opentick provider using login id '" + loginId + "'";
            log.error(message, e);
            throw new EPException(message + ": " + e.getMessage(), e);
        }

        //Wait until logged in
        long timeout = configuration.getConnection().getLogin().getTimeoutMSec();
        log.info("Waiting for log-in confirmation within " + timeout + " msec");
        long startTime = System.currentTimeMillis();
        while (!openTickClient.isLoggedIn())
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                throw new EPException("Interruped: " + e.getMessage(), e);
            }
            if (openTickClient.getStatus() == OTConstants.OT_STATUS_INACTIVE)
            {
                throw new EPException("OpenTick client reported OT_STATUS_INACTIVE");
            }
            long timeDelta = System.currentTimeMillis() - startTime;
            if (timeDelta > timeout)
            {
                throw new EPException("OpenTick not logging in: timeout occured, maximum wait time was specified as " + timeout + " msec");
            }
        }

        log.info("Adding OpenTick listeners");
        for (Map.Entry<String, ConfigurationOpentick.OpenTickStream> entry : configuration.getStreams().entrySet())
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

            String type;
            if (streamTypeInfo.getSecond() == OTConstants.OT_TICK_TYPE_QUOTE)
            {
                openTickClient.addRtQuoteListener(new OTListenerEsperSender(provider));
                type = "OT_TICK_TYPE_QUOTE";
            }
            else if (streamTypeInfo.getSecond() == OTConstants.OT_TICK_TYPE_MMQUOTE)
            {
                openTickClient.addRtMMQuoteListener(new OTListenerEsperSender(provider));
                type = "OT_TICK_TYPE_MMQUOTE";
            }
            else if (streamTypeInfo.getSecond() == OTConstants.OT_TICK_TYPE_TRADE)
            {
                openTickClient.addRtTradeListener(new OTListenerEsperSender(provider));
                type = "OT_TICK_TYPE_TRADE";
            }
            else if (streamTypeInfo.getSecond() == OTConstants.OT_TICK_TYPE_BBO)
            {
                openTickClient.addRtBBOListener(new OTListenerEsperSender(provider));
                type = "OT_TICK_TYPE_BBO";
            }
            else
            {
                throw new EPException("Unknown stream by name '" + streamName + "', valid values are " + knownStreamNames.values());
            }
            log.info("Added OpenTick " + type + " listener forwarding to Esper engine URI " + engineURI);
        }

        activateStreams();
        isStarted = true;

        log.info("Completed startup");
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
        log.info("Logging out");
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
        log.info("Cancelling " + opentickRequestIds.size() + " tick streams");
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
        log.info("Computing subscriptions");
        Set<OpentickSubscriberSpec> subscribers = computeSubscribers();

        for (OpentickSubscriberSpec subscriber : subscribers)
        {
            try
            {
                String exchange = subscriber.getExchange();
                String symbol = subscriber.getSymbol();
                OTDataEntity entity = new OTDataEntity(exchange, symbol);
                int requestId = openTickClient.requestTickStream(entity, subscriber.getOptionalType());
                opentickRequestIds.add(requestId);

                log.info("Requested tick stream for exchange " + exchange + " and symbol " + symbol + " type " + subscriber.getOptionalType());
            }
            catch (OTException e)
            {
                String message = "Failed to request tick stream from opentick provider";
                log.error(message, e);
                throw new EPException(message + ": " + e.getMessage(), e);
            }
        }
        isActive = true;
        log.info("Completed requesting tick streams");
    }

    private Set<OpentickSubscriberSpec> computeSubscribers()
    {
        Set<OpentickSubscriberSpec> subscribers = new LinkedHashSet<OpentickSubscriberSpec>();

        // for each enabled stream
        for (Map.Entry<String, ConfigurationOpentick.OpenTickStream> entry : configuration.getStreams().entrySet())
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
            for (ConfigurationOpentick.StreamSymbolList symbolList : configuration.getStreamSymbolLists())
            {
                if (!symbolList.getStreamName().equals(streamName))
                {
                    continue;
                }

                String symbolListName = symbolList.getSymbolListName();
                List<ConfigurationOpentick.ExchangeAndSymbol> combinations = configuration.getSymbolLists().get(symbolListName);
                if (combinations == null)
                {
                    continue;
                }

                // For each combination of exchange and symbol
                for (ConfigurationOpentick.ExchangeAndSymbol combination : combinations)
                {
                    subscribers.add(new OpentickSubscriberSpec(combination.getExchange(), combination.getSymbol(), streamTypeInfo.getSecond()));
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
}
