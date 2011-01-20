package com.espertech.esperio.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.params.HttpParams;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.ServerSocket;
import java.util.Map;
import java.util.LinkedHashMap;

import com.espertech.esperio.http.config.Service;
import com.espertech.esperio.http.config.GetHandler;
import com.espertech.esperio.http.core.EsperHttpServiceClassicRunnable;
import com.espertech.esper.core.EPServiceProviderSPI;

public class SupportHTTPServer {
    private static Log log = LogFactory.getLog(SupportHTTPServer.class);

    private final int port;
    private ServerSocket serversocket;
    private HttpParams params;
    private HttpService httpService;
    private EsperHttpServiceClassicRunnable runnable;
    private Thread socketThread;

    public SupportHTTPServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        if (serversocket != null) {
            throw new RuntimeException("Server socket already initialized");
        }

        this.serversocket = new ServerSocket(port);
        this.params = new BasicHttpParams();
        this.params
            .setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000)
            .setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 8 * 1024)
            .setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, false)
            .setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true)
            .setParameter(CoreProtocolPNames.ORIGIN_SERVER, "HttpComponents/1.1");

        // Set up the HTTP protocol processor
        BasicHttpProcessor httpproc = new BasicHttpProcessor();
        httpproc.addInterceptor(new ResponseDate());
        httpproc.addInterceptor(new ResponseServer());
        httpproc.addInterceptor(new ResponseContent());
        httpproc.addInterceptor(new ResponseConnControl());

        // Set up request handlers
        HttpRequestHandlerRegistry registery = new HttpRequestHandlerRegistry();
        registery.register("*", new SupportHTTPServerReqestHandler());

        // Set up the HTTP service
        this.httpService = new HttpService(
                httpproc,
                new DefaultConnectionReuseStrategy(),
                new DefaultHttpResponseFactory());
        this.httpService.setParams(this.params);
        this.httpService.setHandlerResolver(registery);

        runnable = new EsperHttpServiceClassicRunnable("regressionTestService", serversocket, params, httpService);
        socketThread = new Thread(runnable);
        socketThread.setDaemon(true);
        socketThread.start();
    }

    public void stop() {

        log.info("Closing existing workers");
        runnable.destroy();

        log.info("Closing server socket for port " + port);
        try {
            serversocket.close();
        } catch (IOException e) {
            log.debug("Error closing server socket: " + e.getMessage(), e);
        }

        log.info("Stopping socket thread");
        socketThread.interrupt();
        try {
            socketThread.join(10000);
        } catch (InterruptedException e) {
            log.debug("Interrupted", e);
        }
    }

}