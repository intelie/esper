package com.espertech.esperio.socket.core;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.event.EventBeanManufactureException;
import com.espertech.esper.event.EventBeanManufacturer;
import com.espertech.esper.event.EventTypeSPI;
import com.espertech.esper.event.WriteablePropertyDescriptor;
import com.espertech.esper.util.SimpleTypeParser;
import com.espertech.esper.util.SimpleTypeParserFactory;
import com.espertech.esper.epl.core.MethodResolutionServiceImpl;
import com.espertech.esperio.socket.config.DataType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class WorkerThread extends Thread {

    private static Log log = LogFactory.getLog(WorkerThread.class);

    private final EPServiceProviderSPI engine;
    private final EsperSocketServiceRunnable runnable;
    private final String serviceName;
    private final Socket socket;
    private final Map<String, WriterCacheEntry> streamCache = new HashMap<String, WriterCacheEntry>();
    private final MethodResolutionServiceImpl methods;
    private ObjectInputStream ois;
    private BufferedReader br;
    private boolean isShutdown;

    public WorkerThread(String serviceName, EPServiceProviderSPI engine, EsperSocketServiceRunnable runnable, Socket socket, DataType dataType) throws IOException {
        this.serviceName = serviceName;
        this.engine = engine;
        this.runnable = runnable;
        this.socket = socket;
        this.methods = new MethodResolutionServiceImpl(engine.getEngineImportService(), engine.getTimeProvider(), false);

        if ((dataType == null) || (dataType == DataType.OBJECT)) {
            ois = new ObjectInputStream(socket.getInputStream());
        }
        else {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
    }

    public void setShutdown(boolean shutdown) {
        isShutdown = shutdown;
    }

    public void run() {

        try {
            while (!Thread.interrupted() && socket.isConnected()) {

                if (ois != null) {
                    Object object = ois.readObject();
                    handleObject(object);
                }
                else {
                    String str = br.readLine();
                    handleString(str);
                }
            }
        }
        catch (EOFException ex) {
            log.debug("EOF received from connection");
        }
        catch (IOException ex) {
            if (!isShutdown) {
                log.error("I/O error: " + ex.getMessage(), ex);
            }
        }
        catch (ClassNotFoundException ex) {
            log.error("Class not found: " + ex.getMessage());
        }
        finally {
            try {
                socket.close();
                runnable.remove(this);
            } catch (IOException ignore) {}
        }
    }

    private void handleObject(Object input) {
        try {
            if (input instanceof Map) {
                Map map = (Map) input;
                String type = (String) map.get("stream");
                if (type == null) {
                    log.warn("Expected value for event type not found in map event provided to adapter");
                    return;
                }
                engine.getEPRuntime().sendEvent(map, type);
            }
            else {
                engine.getEPRuntime().sendEvent(input);
            }
        }
        catch (Throwable t) {
            log.error("Unexpected exception encountered sending event " + input + " service '" + serviceName + "' :" + t.getMessage(), t);
        }
    }

    private void handleString(String input) {
        if (input == null) {
            return;
        }
        try {
            Map<String, String> parameters = new HashMap<String, String>();
            WStringTokenizer tokenizer = new WStringTokenizer(input, ",");
            while (tokenizer.hasMoreTokens()) {
                String item = tokenizer.nextToken();
                int index = item.indexOf("=");
                if (index != -1) {
                    parameters.put(item.substring(0, index), item.substring(index+1, item.length()));
                }
            }

            String eventTypeName = parameters.get("stream");

            WriterCacheEntry cacheEntry = streamCache.get(eventTypeName);
            if (cacheEntry == null) {
                cacheEntry = makeCacheEntry(eventTypeName);
                streamCache.put(eventTypeName, cacheEntry);
            }

            if (cacheEntry == null) {
                return;
            }

            Object[] values = new Object[cacheEntry.getParsers().length];
            for (int i = 0; i < cacheEntry.getParsers().length; i++) {
                String value = parameters.get(cacheEntry.getWritableProperties()[i].getPropertyName());
                if (value == null) {
                    continue;
                }
                values[i] = cacheEntry.getParsers()[i].parse(value);
            }

            EventBean event = cacheEntry.getEventBeanManufacturer().make(values);
            engine.getEPRuntime().sendEvent(event);
        }
        catch (Throwable t) {
            log.error("Unexpected exception encountered sending event " + input + " service '" + serviceName + "' :" + t.getMessage(), t);
        }
    }
    private WriterCacheEntry makeCacheEntry(String eventTypeName) {

        EventType eventType = engine.getEventAdapterService().getExistsTypeByName(eventTypeName);
        if (eventType == null) {
            log.info("Event type by name '" + eventTypeName + "' not found.");
            return null;
        }

        if (!(eventType instanceof EventTypeSPI)) {
            log.info("Event type by name '" + eventTypeName + "' is not writable.");
            return null;
        }

        EventTypeSPI eventTypeSPI = (EventTypeSPI) eventType;

        Set<WriteablePropertyDescriptor> writablesSet = engine.getEventAdapterService().getWriteableProperties(eventTypeSPI);
        List<WriteablePropertyDescriptor> writablePropertiesList = new ArrayList<WriteablePropertyDescriptor>();
        List<SimpleTypeParser> parserList = new ArrayList<SimpleTypeParser>();

        for (WriteablePropertyDescriptor writableDesc : writablesSet)
        {
            SimpleTypeParser parser = SimpleTypeParserFactory.getParser(writableDesc.getType());
            if (parser == null) {
                log.debug("No parser found for type '" + writableDesc.getType() + "'");
                continue;
            }

            writablePropertiesList.add(writableDesc);
            parserList.add(parser);
        }

        WriteablePropertyDescriptor[] writableProperties = writablePropertiesList.toArray(new WriteablePropertyDescriptor[writablePropertiesList.size()]);
        SimpleTypeParser[] parsers = parserList.toArray(new SimpleTypeParser[parserList.size()]);

        EventBeanManufacturer eventBeanManufacturer;
        try {
            eventBeanManufacturer = engine.getEventAdapterService().getManufacturer(eventType, writableProperties, methods);
        }
        catch (EventBeanManufactureException e) {
            log.info("Unable to create manufacturer for event type: " + e.getMessage(), e);
            return null;
        }

        return new WriterCacheEntry(eventBeanManufacturer, writableProperties, parsers);
    }
}
