package com.espertech.esper.regression.client;

import com.espertech.esper.client.annotation.Tag;
import com.espertech.esper.client.annotation.Name;
import com.espertech.esper.client.annotation.Description;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.util.SupportStmtAwareUpdateListener;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.SupportBean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestStatementAnnotation extends TestCase
{
    private static Log log = LogFactory.getLog(TestStatementAnnotation.class);

    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    // TODO - test patterns
    // test statement name injected
    
    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventType("Bean", SupportBean.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        listener = new SupportUpdateListener();
    }

    public void testStmtAware()
    {
        String stmtText = "@Name('MyTestStmt') @Description('MyTestStmt description') @Tag(name=\"UserId\", value=\"value\") select * from Bean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        Annotation[] annotations = stmt.getAnnotations();
        annotations = sortAlpha(annotations);
        assertEquals(3, annotations.length);

        assertEquals(Description.class, annotations[0].annotationType());
        assertEquals("MyTestStmt description", ((Description)annotations[0]).value());
        assertEquals(Name.class, annotations[1].annotationType());
        assertEquals("MyTestStmt", ((Name)annotations[1]).value());
        assertEquals(Tag.class, annotations[2].annotationType());
        assertEquals("UserId", ((Tag)annotations[2]).name());
        assertEquals("value", ((Tag)annotations[2]).value());

        assertFalse(annotations[2].equals(annotations[1]));
        assertTrue(annotations[1].equals(annotations[1]));
        assertTrue(annotations[1].hashCode() != 0);
    }

    private Annotation[] sortAlpha(Annotation[] annotations)
    {
        if (annotations == null)
        {
            return null;
        }
        ArrayList<Annotation> sorted = new ArrayList<Annotation>();
        sorted.addAll(Arrays.asList(annotations));
        Collections.sort(sorted, new Comparator<Annotation>()
        {
            public int compare(Annotation o1, Annotation o2)
            {
                return o1.annotationType().getSimpleName().compareTo(o2.annotationType().getSimpleName());
            }
        });
        return sorted.toArray(new Annotation[sorted.size()]);
    }
}
