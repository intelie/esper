package com.espertech.esperio.socket.core;

import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esperio.socket.config.SocketConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.ServerSocket;

public class EsperSocketService {

    private static Log log = LogFactory.getLog(EsperSocketService.class);

    private final String serviceName;
    private final SocketConfig serviceConfig;
    private ServerSocket serverSocket;
    private EsperSocketServiceRunnable runnable;
    private Thread socketThread;

    public EsperSocketService(String serviceName, SocketConfig serviceConfig) {
        this.serviceName = serviceName;
        this.serviceConfig = serviceConfig;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void start(String serviceName, EPServiceProviderSPI engineSPI) throws IOException  {
        log.info("Esper socket adapter accepting connections on port " + serviceConfig.getPort() + " for socket named '" + serviceName + "'");

        serverSocket = new ServerSocket(serviceConfig.getPort());

        runnable = new EsperSocketServiceRunnable(this.getServiceName(), this.serviceConfig.getDataType(), serverSocket, engineSPI);
        socketThread = new Thread(runnable);
        socketThread.setDaemon(true);
        socketThread.start();
    }

    public void destroy() {
        log.info("Closing existing workers for service '" + this.getServiceName() + "'");
        runnable.destroy();

        log.info("Closing server socket for service '" + this.getServiceName() + "' and port " + serviceConfig.getPort());
        try {
            serverSocket.close();
        } catch (IOException e) {
            log.debug("Error closing server socket: " + e.getMessage(), e);
        }

        log.info("Stopping socket thread for service '" + this.getServiceName() + "'");
        socketThread.interrupt();
        try {
            socketThread.join(10000);
        }
        catch (InterruptedException e) {
            log.debug("Interrupted", e);
        }        
    }
}
