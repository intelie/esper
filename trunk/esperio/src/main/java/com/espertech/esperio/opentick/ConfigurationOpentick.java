package com.espertech.esperio.opentick;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationOpentick
{
    private static Log log = LogFactory.getLog(ConfigurationOpentick.class );
    private Connection connection;
    private Map<String, OpenTickStream> streams;
    private Map<String, List<ExchangeAndSymbol>> symbolLists;
    private List<StreamSymbolList> streamSymbolLists;

    public ConfigurationOpentick()
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

    /**
     * Use the configuration specified in the given application
     * resource.
     * <p/>
     * The resource is found via <tt>getConfigurationInputStream(resource)</tt>.
     * That method can be overridden to implement an arbitrary lookup strategy.
     * <p/>
     * See <tt>getResourceAsStream</tt> for information on how the resource name is resolved.
     * @param resource if the file name of the resource
     * @return Configuration initialized from the resource
     * @throws EPException thrown to indicate error reading configuration
     */
    public ConfigurationOpentick configure(String resource) throws EPException
    {
        if (log.isDebugEnabled())
        {
            log.debug( "Configuring from resource: " + resource );
        }
        InputStream stream = getResourceAsStream(resource);
        ConfigurationParser.doConfigure(this, stream, resource );
        return this;
    }


	/**
	 * Use the configuration specified by the given URL.
	 *
	 * @param url URL from which you wish to load the configuration
	 * @return A configuration configured via the file
	 * @throws EPException is thrown when the URL could not be access
	 */
	public ConfigurationOpentick configure(URL url) throws EPException
    {
        if (log.isDebugEnabled())
        {
            log.debug( "configuring from url: " + url.toString() );
        }
        try {
            ConfigurationParser.doConfigure(this, url.openStream(), url.toString());
            return this;
		}
		catch (IOException ioe) {
			throw new EPException("could not configure from URL: " + url, ioe );
		}
	}

	/**
	 * Use the configuration specified in the given application
	 * file.
	 *
	 * @param configFile <tt>File</tt> from which you wish to load the configuration
	 * @return A configuration configured via the file
	 * @throws EPException when the file could not be found
	 */
	public ConfigurationOpentick configure(File configFile) throws EPException
    {
        if (log.isDebugEnabled())
        {
            log.debug( "configuring from file: " + configFile.getName() );
        }
        try {
            ConfigurationParser.doConfigure(this, new FileInputStream(configFile), configFile.toString());
		}
		catch (FileNotFoundException fnfe) {
			throw new EPException( "could not find file: " + configFile, fnfe );
		}
        return this;
    }

    private static InputStream getResourceAsStream(String resource)
    {
        String stripped = resource.startsWith("/") ?
                resource.substring(1) : resource;

        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader!=null) {
            stream = classLoader.getResourceAsStream( stripped );
        }
        if ( stream == null ) {
            stream = Configuration.class.getResourceAsStream( resource );
        }
        if ( stream == null ) {
            stream = Configuration.class.getClassLoader().getResourceAsStream( stripped );
        }
        if ( stream == null ) {
            throw new EPException( resource + " not found" );
        }
        return stream;
    }

    /**
	 * Use the mappings and properties specified in the given XML document.
	 * The format of the file is defined in
	 * <tt>esper-configuration-2.0.xsd</tt>.
	 *
	 * @param document an XML document from which you wish to load the configuration
	 * @return A configuration configured via the <tt>Document</tt>
	 * @throws EPException if there is problem in accessing the document.
	 */
	public ConfigurationOpentick configure(Document document) throws EPException
    {
        if (log.isDebugEnabled())
        {
		    log.debug( "configuring from XML document" );
        }
        ConfigurationParser.doConfigure(this, document);
        return this;
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
        private long timeoutMSec;

        public ConnectionLogin()
        {
            timeoutMSec = 10000;
        }

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

        public long getTimeoutMSec()
        {
            return timeoutMSec;
        }

        public void setTimeoutMSec(long timeoutMSec)
        {
            this.timeoutMSec = timeoutMSec;
        }
    }

    public static class OpenTickStream implements Serializable
    {
        private boolean enabled;
        private String engineURI;
        private String typeName;

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

        public String getTypeName()
        {
            return typeName;
        }

        public void setTypeName(String typeName)
        {
            this.typeName = typeName;
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
