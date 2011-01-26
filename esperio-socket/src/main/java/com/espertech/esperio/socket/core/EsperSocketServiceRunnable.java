package com.espertech.esperio.socket.core;

import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esperio.socket.config.DataType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EsperSocketServiceRunnable implements Runnable {
    private static Log log = LogFactory.getLog(EsperSocketServiceRunnable.class);

    private String serviceName;
    private DataType dataType;
    private ServerSocket serversocket;
    private EPServiceProviderSPI engine;
    private List<WorkerThread> workers = new CopyOnWriteArrayList<WorkerThread>();
    private boolean shutdown;

    public EsperSocketServiceRunnable(String serviceName, DataType dataType, ServerSocket serversocket, EPServiceProviderSPI engine) {
        this.serviceName = serviceName;
        this.serversocket = serversocket;
        this.dataType = dataType;
        this.engine = engine;
    }

    public void run() {
        log.info("For service '" + serviceName + "' listening on port " + this.serversocket.getLocalPort() + " expecting data type " + (dataType == null ? DataType.OBJECT : dataType));
        while (!Thread.interrupted()) {
            try {
                // Set up Socket connection
                Socket socket = this.serversocket.accept();
                log.info("Incoming connection service '" + serviceName + "' from " + socket.getInetAddress());

                // Start worker thread
                WorkerThread t = new WorkerThread(serviceName, engine, this, socket, dataType);
                t.setDaemon(true);
                t.start();
                workers.add(t);
            }
            catch (InterruptedIOException ex) {
                break;
            }
            catch (IOException e) {
                if (!shutdown) {
                    log.error("I/O error initialising connection thread for service '" + serviceName + "' : " + e.getMessage());
                }
                break;
            }
        }
        log.info("For service '" + serviceName + "' listening on port " + this.serversocket.getLocalPort() + " ended socket thread.");
    }

    public void destroy() {
        log.info("Stopping worker threads for service '" + serviceName + "'");
        shutdown = true;
        for (WorkerThread worker : workers) {
            worker.setShutdown(true);

            if (!worker.isAlive()) {
                worker.interrupt();
            }
            try {
                worker.join(1000);
            }
            catch (InterruptedException e) {
            }
        }
        workers.clear();
    }

    public void remove(WorkerThread workerThread) {
        workers.remove(workerThread);
    }
}
