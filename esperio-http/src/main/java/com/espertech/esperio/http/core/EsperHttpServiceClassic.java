package com.espertech.esperio.http.core;

import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esperio.http.config.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.*;

import java.io.IOException;
import java.net.ServerSocket;

public class EsperHttpServiceClassic extends EsperHttpServiceBase {
    private static Log log = LogFactory.getLog(EsperHttpServiceClassic.class);

    private ServerSocket serversocket;
    private HttpParams params;
    private HttpService httpService;
    private EsperHttpServiceClassicRunnable runnable;
    private Thread socketThread;

    public EsperHttpServiceClassic(String serviceName, Service value) {
        super(serviceName, value);
    }

    public void start(EPServiceProviderSPI engine) throws IOException {
        this.serversocket = new ServerSocket(this.getServiceConfig().getPort());
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
        HttpRequestHandlerRegistry reqistry = setupRegistry(engine);

        // Set up the HTTP service
        this.httpService = new HttpService(
                httpproc,
                new DefaultConnectionReuseStrategy(),
                new DefaultHttpResponseFactory());
        this.httpService.setParams(this.params);
        this.httpService.setHandlerResolver(reqistry);

        runnable = new EsperHttpServiceClassicRunnable(this.getServiceName(), serversocket, params, httpService);
        socketThread = new Thread(runnable);
        socketThread.setDaemon(true);
        socketThread.start();
    }

    public void destroy() {
        log.info("Closing existing workers for service '" + this.getServiceName() + "'");
        runnable.destroy();

        log.info("Closing server socket for service '" + this.getServiceName() + "' and port " + this.getServiceConfig().getPort());
        try {
            serversocket.close();
        } catch (IOException e) {
            log.debug("Error closing server socket: " + e.getMessage(), e);
        }

        log.info("Stopping socket thread for service '" + this.getServiceName() + "'");
        socketThread.interrupt();
        try {
            socketThread.join(10000);
        } catch (InterruptedException e) {
            log.debug("Interrupted", e);
        }
    }
}
