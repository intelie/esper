package net.esper.example.servershell;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.InputStream;
import java.rmi.registry.LocateRegistry;

import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;

public class ServerShellConstants
{
    public static final String CONFIG_FILENAME = "servershell_config.properties";
    
    public static final String JMS_CONTEXT_FACTORY = "jms-context-factory";
    public static final String JMS_PROVIDER_URL = "jms-provider-url";
    public static final String JMS_CONNECTION_FACTORY_NAME = "jms-connection-factory-name";
    public static final String JMS_USERNAME = "jms-user";
    public static final String JMS_PASSWORD = "jms-password";
    public static final String JMS_INCOMING_DESTINATION = "jms-incoming-destination";
    public static final String JMS_IS_TOPIC = "jms-is-topic";
    public static final String JMS_NUM_LISTENERS = "jms-num-listeners";

    public static final String MGMT_RMI_PORT = "rmi-port";
    public static final String MGMT_SERVICE_URL = "jmx-service-url";
    public static final String MGMT_MBEAN_NAME = "net.esper.mbean:type=EPServiceProviderJMXMBean";
}
