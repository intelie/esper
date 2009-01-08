/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.opentick;

import com.espertech.esper.client.EPException;
import com.espertech.esper.util.DOMElementIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class ConfigurationParser
{
    /**
     * Use the configuration specified in the given input stream.
     * @param configuration is the configuration object to populate
     * @param stream	   Inputstream to be read from
     * @param resourceName The name to use in warning/error messages
     * @throws com.espertech.esper.client.EPException is thrown when the configuration could not be parsed
     */
    protected static void doConfigure(ConfigurationOpentick configuration, InputStream stream, String resourceName)
    {
        Document document = getDocument(stream, resourceName);
        doConfigure(configuration, document);
    }

    protected static Document getDocument(InputStream stream, String resourceName)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        Document document = null;

        try
        {
            builder = factory.newDocumentBuilder();
            document = builder.parse(stream);
        }
        catch (ParserConfigurationException ex)
        {
            throw new EPException("Could not get a DOM parser configuration: " + resourceName, ex);
        }
        catch (SAXException ex)
        {
            throw new EPException("Could not parse configuration: " + resourceName, ex);
        }
        catch (IOException ex)
        {
            throw new EPException("Could not read configuration: " + resourceName, ex);
        }
        finally {
            try {
                stream.close();
            }
            catch (IOException ioe) {
                ConfigurationParser.log.warn( "could not close input stream for: " + resourceName, ioe );
            }
        }

        return document;
    }

    /**
     * Parse the W3C DOM document.
     * @param configuration is the configuration object to populate
     * @param doc to parse
     */
    protected static void doConfigure(ConfigurationOpentick configuration, Document doc)
    {
        Element root = doc.getDocumentElement();

        DOMElementIterator eventTypeNodeIterator = new DOMElementIterator(root.getChildNodes());
        while (eventTypeNodeIterator.hasNext())
        {
            Element element = eventTypeNodeIterator.next();
            String nodeName = element.getNodeName();
            if (nodeName.equals("connection"))
            {
                handleConnection(configuration, element);
            }
            if (nodeName.equals("streams"))
            {
                handleStreams(configuration, element);
            }
            if (nodeName.equals("symbollists"))
            {
                handleSymbolLists(configuration, element);
            }
            if (nodeName.equals("stream-symbollists"))
            {
                handleStreamSymbollists(configuration, element);
            }
        }
    }

    private static void handleSymbolLists(ConfigurationOpentick configuration, Element parentElement)
    {
        DOMElementIterator eventTypeNodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (eventTypeNodeIterator.hasNext())
        {
            Element element = eventTypeNodeIterator.next();
            String nodeName = element.getNodeName();
            if (nodeName.equals("symbollist"))
            {
                String name = element.getAttributes().getNamedItem("name").getTextContent();
                String exchange = element.getAttributes().getNamedItem("exchange").getTextContent();
                String symbols = element.getAttributes().getNamedItem("symbols").getTextContent();
                configuration.addSymbolList(name, exchange, symbols);
            }
        }
    }

    private static void handleStreamSymbollists(ConfigurationOpentick configuration, Element parentElement)
    {
        DOMElementIterator eventTypeNodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (eventTypeNodeIterator.hasNext())
        {
            Element element = eventTypeNodeIterator.next();
            String nodeName = element.getNodeName();
            if (nodeName.equals("stream-symbollist"))
            {
                String stream = element.getAttributes().getNamedItem("stream").getTextContent();
                String symbollist = element.getAttributes().getNamedItem("symbollist").getTextContent();
                configuration.addStreamSymbolList(stream, symbollist);
            }
        }
    }

    private static void handleConnection(ConfigurationOpentick configuration, Element parentElement)
    {
        DOMElementIterator eventTypeNodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (eventTypeNodeIterator.hasNext())
        {
            Element element = eventTypeNodeIterator.next();
            String nodeName = element.getNodeName();
            if (nodeName.equals("hosts"))
            {
                handleHosts(configuration, element);
            }
            if (nodeName.equals("login"))
            {
                String name = element.getAttributes().getNamedItem("name").getTextContent();
                String password = element.getAttributes().getNamedItem("password").getTextContent();
                long timeoutMsec = Long.parseLong(element.getAttributes().getNamedItem("timeout-msec").getTextContent());
                ConfigurationOpentick.ConnectionLogin login = new ConfigurationOpentick.ConnectionLogin();
                login.setName(name);
                login.setPassword(password);
                login.setTimeoutMSec(timeoutMsec);
                configuration.getConnection().setLogin(login);
            }
        }
    }

    private static void handleStreams(ConfigurationOpentick configuration, Element parentElement)
    {
        DOMElementIterator eventTypeNodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (eventTypeNodeIterator.hasNext())
        {
            Element element = eventTypeNodeIterator.next();
            String nodeName = element.getNodeName();
            if (nodeName.equals("stream"))
            {
                String name = element.getAttributes().getNamedItem("name").getTextContent();
                boolean enable = Boolean.parseBoolean(element.getAttributes().getNamedItem("enable").getTextContent());
                String engineURI = element.getAttributes().getNamedItem("engine-uri").getTextContent();
                String typeName = element.getAttributes().getNamedItem("type-name").getTextContent();
                
                ConfigurationOpentick.OpenTickStream stream = new ConfigurationOpentick.OpenTickStream();
                stream.setEnabled(enable);
                stream.setEngineURI(engineURI);
                stream.setTypeName(typeName);
                configuration.addStream(name, stream);
            }
        }
    }

    private static void handleHosts(ConfigurationOpentick configuration, Element parentElement)
    {
        DOMElementIterator eventTypeNodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (eventTypeNodeIterator.hasNext())
        {
            Element element = eventTypeNodeIterator.next();
            String nodeName = element.getNodeName();
            if (nodeName.equals("host"))
            {
                String name = element.getAttributes().getNamedItem("name").getTextContent();
                int port = Integer.parseInt(element.getAttributes().getNamedItem("port").getTextContent());
                ConfigurationOpentick.ConnectionHost host = new ConfigurationOpentick.ConnectionHost();
                host.setHostname(name);
                host.setPort(port);
                configuration.getConnection().addHost(host);
            }
        }
    }

    private static Log log = LogFactory.getLog(ConfigurationParser.class);
}
