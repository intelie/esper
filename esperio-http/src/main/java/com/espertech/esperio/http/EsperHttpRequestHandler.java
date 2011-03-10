package com.espertech.esperio.http;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.epl.core.MethodResolutionServiceImpl;
import com.espertech.esper.event.EventBeanManufactureException;
import com.espertech.esper.event.EventBeanManufacturer;
import com.espertech.esper.event.EventTypeSPI;
import com.espertech.esper.event.WriteablePropertyDescriptor;
import com.espertech.esper.util.SimpleTypeParser;
import com.espertech.esper.util.SimpleTypeParserFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class EsperHttpRequestHandler implements HttpRequestHandler {

    private static Log log = LogFactory.getLog(EsperHttpRequestHandler.class);

    private final EPServiceProviderSPI engineSPI;
    private final MethodResolutionServiceImpl methods;
    private final Map<String, EsperHttpRequestCacheEntry> streamCache = new HashMap<String, EsperHttpRequestCacheEntry>();

    public EsperHttpRequestHandler(final EPServiceProviderSPI engineSPI) {
        super();
        this.engineSPI = engineSPI;
        this.methods = new MethodResolutionServiceImpl(engineSPI.getEngineImportService(), engineSPI.getTimeProvider());
    }

    public void handle(
            final HttpRequest request,
            final HttpResponse response,
            final HttpContext context) throws HttpException, IOException {

        String method = request.getRequestLine().getMethod().toUpperCase(Locale.ENGLISH);
        if (!method.equals("GET") && !method.equals("HEAD") && !method.equals("POST")) {
            throw new MethodNotSupportedException(method + " method not supported");
        }

        response.setStatusCode(HttpStatus.SC_OK);

        String target = request.getRequestLine().getUri();
        try {
            Map<String, String> parameters = parseTarget(target);
            handle(parameters);
        }
        catch (Throwable t) {
            log.error("Error processing Http GET request target '" + target + "' :" + t.getMessage(), t);
        }
    }

    private void handle(Map<String, String> parameters) {

        String eventTypeName = parameters.get("stream");

        EsperHttpRequestCacheEntry cacheEntry = streamCache.get(eventTypeName);
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
        engineSPI.getEPRuntime().sendEvent(event);
    }

    private EsperHttpRequestCacheEntry makeCacheEntry(String eventTypeName) {

        EventType eventType = engineSPI.getEventAdapterService().getExistsTypeByName(eventTypeName);
        if (eventType == null) {
            log.info("Event type by name '" + eventTypeName + "' not found.");
            return null;
        }

        if (!(eventType instanceof EventTypeSPI)) {
            log.info("Event type by name '" + eventTypeName + "' is not writable.");
            return null;
        }

        EventTypeSPI eventTypeSPI = (EventTypeSPI) eventType;

        Set<WriteablePropertyDescriptor> writablesSet = engineSPI.getEventAdapterService().getWriteableProperties(eventTypeSPI);
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
            eventBeanManufacturer = engineSPI.getEventAdapterService().getManufacturer(eventType, writableProperties, methods);
        }
        catch (EventBeanManufactureException e) {
            log.info("Unable to create manufacturer for event type: " + e.getMessage(), e);
            return null;
        }

        return new EsperHttpRequestCacheEntry(eventBeanManufacturer, writableProperties, parsers);
    }

    private HashMap<String, String> parseTarget(String search) throws UnsupportedEncodingException {

        int start = search.indexOf('?');
        if (start == -1) {
            return new HashMap<String, String>();
        }
        String params = search.substring(start + 1, search.length());
        return splitParams(params);
    }

    private HashMap<String, String> splitParams(String search) throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<String, String>();
        String params[] = search.split("&");

        for (String param : params) {
            String temp[] = param.split("=");
            if (temp.length != 2) {
                continue;
            }
            map.put(temp[0], java.net.URLDecoder.decode(temp[1], "UTF-8"));
        }
        return map;
    }
}
