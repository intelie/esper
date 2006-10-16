package net.esper.example.jmsoutputadapter;

import junit.framework.TestCase;
import org.apache.servicemix.jms.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URL;
import java.net.URI;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 10, 2006
 * Time: 10:09:53 PM
 * To change this template use File | Settings | File Templates.
 */

public class JmsXBeanDeployerTest extends TestCase
{
    public void test() throws Exception {
        // JMS Component
        JmsComponent component = new JmsComponent();
        URL url = getClass().getClassLoader().getResource("xbean/xbean.xml");
        File path = new File(new URI(url.toString()));
        log.debug(path.getName());
        path = path.getParentFile();
        log.debug(path.getName());
        component.getServiceUnitManager().deploy("xbean", path.getAbsolutePath());
    }
    static Log log = LogFactory.getLog(JmsXBeanDeployerTest.class);
}
