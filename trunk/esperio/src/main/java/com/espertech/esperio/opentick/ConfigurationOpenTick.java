package com.espertech.esperio.opentick;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

public class ConfigurationOpenTick
{
    private Connection connection;
    private Map<String, OpenTickStream> streams;
    private Map<String, List<ExchangeAndSymbol>> symbolLists;
    private List<StreamSymbolList> streamSymbolLists;

    public ConfigurationOpenTick()
    {
        connection = new Connection();
        streams = new LinkedHashMap<String, OpenTickStream>();
        symbolLists = new LinkedHashMap<String, List<ExchangeAndSymbol>>();
        streamSymbolLists = new ArrayList<StreamSymbolList>();
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    public Map<String, OpenTickStream> getStreams()
    {
        return streams;
    }

    public void setStreams(Map<String, OpenTickStream> streams)
    {
        this.streams = streams;
    }

    public void addStream(String name, OpenTickStream stream)
    {
        streams.put(name, stream);
    }

    public void addSymbolList(String listname, String exchange, String symbolList)
    {
        String[] symbols = symbolList.split(",");
        List<ExchangeAndSymbol> exchangeAndSymbolList = new ArrayList<ExchangeAndSymbol>();
        for (int i = 0; i < symbols.length; i++)
        {
            String symbol = symbols[i].trim();
            if (symbol.length() == 0)
            {
                continue;
            }
            exchangeAndSymbolList.add(new ExchangeAndSymbol(exchange, symbol));
        }
        symbolLists.put(listname, exchangeAndSymbolList);
    }

    public Map<String, List<ExchangeAndSymbol>> getSymbolLists()
    {
        return symbolLists;
    }

    public void setSymbolLists(Map<String, List<ExchangeAndSymbol>> symbolLists)
    {
        this.symbolLists = symbolLists;
    }

    public void addStreamSymbolList(String streamName, String symbolListName)
    {
        streamSymbolLists.add(new StreamSymbolList(streamName, symbolListName));
    }

    public List<StreamSymbolList> getStreamSymbolLists()
    {
        return streamSymbolLists;
    }

    public static class Connection implements Serializable
    {
        private List<ConnectionHost> hosts;
        private ConnectionLogin login;

        public Connection()
        {
            hosts = new ArrayList<ConnectionHost>();
        }

        public void addHost(ConnectionHost host)
        {
            hosts.add(host);
        }

        public void setLogin(ConnectionLogin login)
        {
            this.login = login;
        }

        public List<ConnectionHost> getHosts()
        {
            return hosts;
        }

        public ConnectionLogin getLogin()
        {
            return login;
        }
    }

    public static class ConnectionHost implements Serializable
    {
        private String hostname;
        private int port;

        public String getHostname()
        {
            return hostname;
        }

        public void setHostname(String hostname)
        {
            this.hostname = hostname;
        }

        public int getPort()
        {
            return port;
        }

        public void setPort(int port)
        {
            this.port = port;
        }
    }

    public static class ConnectionLogin implements Serializable
    {
        private String name;
        private String password;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }
    }

    public static class OpenTickStream implements Serializable
    {
        private boolean enabled;
        private String engineURI;
        private String alias;

        public boolean isEnabled()
        {
            return enabled;
        }

        public void setEnabled(boolean enabled)
        {
            this.enabled = enabled;
        }

        public String getEngineURI()
        {
            return engineURI;
        }

        public void setEngineURI(String engineURI)
        {
            this.engineURI = engineURI;
        }

        public String getAlias()
        {
            return alias;
        }

        public void setAlias(String alias)
        {
            this.alias = alias;
        }
    }

    public static class ExchangeAndSymbol implements Serializable
    {
        private String exchange;
        private String symbol;

        public ExchangeAndSymbol(String exchange, String symbol)
        {
            this.exchange = exchange;
            this.symbol = symbol;
        }

        public String getExchange()
        {
            return exchange;
        }

        public void setExchange(String exchange)
        {
            this.exchange = exchange;
        }

        public String getSymbol()
        {
            return symbol;
        }

        public void setSymbol(String symbol)
        {
            this.symbol = symbol;
        }
    }

    public static class StreamSymbolList implements Serializable
    {
        private String streamName;
        private String symbolListName;

        public StreamSymbolList(String streamName, String maskName)
        {
            this.streamName = streamName;
            this.symbolListName = maskName;
        }

        public String getStreamName()
        {
            return streamName;
        }

        public void setStreamName(String streamName)
        {
            this.streamName = streamName;
        }

        public String getSymbolListName()
        {
            return symbolListName;
        }

        public void setSymbolListName(String symbolListName)
        {
            this.symbolListName = symbolListName;
        }
    }
}
