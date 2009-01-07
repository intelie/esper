package com.espertech.esper.example.springbean;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.time.CurrentTimeEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SpringBeanMain
{
    private static final Log log = LogFactory.getLog(SpringBeanMain.class);

    public static void main(String[] args)
    {
        log.info("Setting up EPL");

        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"/sample/espertest.xml"});
        EsperBean esperBean = (EsperBean) appContext.getBean("esperBean", EsperBean.class);
        esperBean.sendEvent("test EsperSerivce");
        appContext.destroy();

    }
}

