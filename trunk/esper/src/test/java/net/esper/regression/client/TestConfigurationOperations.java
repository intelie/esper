package net.esper.regression.client;

import net.esper.client.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBean_A;
import net.esper.support.client.SupportConfigFactory;
import junit.framework.TestCase;

import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;

public class TestConfigurationOperations extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener testListener;
    private ConfigurationOperations configOps;

    public void setUp()
    {
        testListener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
        configOps = epService.getEPAdministrator().getConfiguration();
    }

    public void testAutoAliasPackage()
    {
        configOps.addEventTypeAutoAlias(this.getClass().getPackage().getName());

        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from " + MyAutoAliasEventType.class.getSimpleName());
        stmt.addListener(testListener);

        MyAutoAliasEventType eventOne = new MyAutoAliasEventType(10);
        epService.getEPRuntime().sendEvent(eventOne);
        assertSame(eventOne, testListener.assertOneGetNewAndReset().getUnderlying());
    }

    public void testAutoAliasPackageAmbigous()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAutoAlias(this.getClass().getPackage().getName());
        configOps.addEventTypeAutoAlias(this.getClass().getPackage().getName());
        configOps.addEventTypeAutoAlias(SupportBean.class.getPackage().getName());

        try
        {
            epService.getEPAdministrator().createEQL("select * from " + SupportAmbigousEventType.class.getSimpleName());
            fail();
        }
        catch (Exception ex)
        {
            assertEquals("Failed to resolve event type: Failed to resolve alias 'SupportAmbigousEventType', the class was ambigously found both in package 'net.esper.regression.client' and in package 'net.esper.support.bean' [select * from SupportAmbigousEventType]", ex.getMessage());
        }

        try
        {
            epService.getEPAdministrator().createEQL("select * from XXXX");
            fail();
        }
        catch (Exception ex)
        {
            assertEquals("Failed to resolve event type: Failed to load class XXXX [select * from XXXX]", ex.getMessage());
        }
    }

    public void testAddDOMType() throws Exception
    {
        tryInvalid("AddedDOMOne");

        // First statement with new name
        ConfigurationEventTypeXMLDOM domConfig = new ConfigurationEventTypeXMLDOM();
        domConfig.setRootElementName("RootAddedDOMOne");
        configOps.addEventTypeAlias("AddedDOMOne", domConfig);

        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from AddedDOMOne");
        stmt.addListener(testListener);

        Document eventOne = makeDOMEvent("RootAddedDOMOne");
        epService.getEPRuntime().sendEvent(eventOne);
        assertSame(eventOne, testListener.assertOneGetNewAndReset().getUnderlying());

        tryInvalid("AddedMapNameSecond");

        // Second statement using a new alias to the same type, should both receive
        domConfig = new ConfigurationEventTypeXMLDOM();
        domConfig.setRootElementName("RootAddedDOMOne");
        configOps.addEventTypeAlias("AddedDOMSecond", domConfig);

        configOps.addEventTypeAlias("AddedMapNameSecond", domConfig);
        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
        stmt = epService.getEPAdministrator().createEQL("select * from AddedMapNameSecond");
        stmt.addListener(testListenerTwo);

        Document eventTwo = makeDOMEvent("RootAddedDOMOne");
        epService.getEPRuntime().sendEvent(eventTwo);
        assertTrue(testListener.isInvoked());
        assertEquals(eventTwo, testListenerTwo.assertOneGetNewAndReset().getUnderlying());

        // Add the same alias and type again
        domConfig = new ConfigurationEventTypeXMLDOM();
        domConfig.setRootElementName("RootAddedDOMOne");
        configOps.addEventTypeAlias("AddedDOMSecond", domConfig);

        // Add the same alias and a different type
        try
        {
            domConfig = new ConfigurationEventTypeXMLDOM();
            domConfig.setRootElementName("RootAddedDOMXXX");
            configOps.addEventTypeAlias("AddedDOMSecond", domConfig);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    public void testAddMapByClass()
    {
        tryInvalid("AddedMapOne");

        // First statement with new name
        Map<String, Class> mapProps = new HashMap<String, Class>();
        mapProps.put("prop1", int.class);
        configOps.addEventTypeAlias("AddedMapOne", mapProps);

        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from AddedMapOne");
        stmt.addListener(testListener);

        Map<String, Object> eventOne = new HashMap<String, Object>();
        eventOne.put("prop1", 1);
        epService.getEPRuntime().sendEvent(eventOne, "AddedMapOne");
        assertEquals(eventOne, testListener.assertOneGetNewAndReset().getUnderlying());

        tryInvalid("AddedMapNameSecond");

        // Second statement using a new alias to the same type, should only one receive
        configOps.addEventTypeAlias("AddedMapNameSecond", mapProps);
        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
        stmt = epService.getEPAdministrator().createEQL("select * from AddedMapNameSecond");
        stmt.addListener(testListenerTwo);

        Map<String, Object> eventTwo = new HashMap<String, Object>();
        eventTwo.put("prop1", 1);
        epService.getEPRuntime().sendEvent(eventTwo, "AddedMapNameSecond");
        assertFalse(testListener.isInvoked());
        assertEquals(eventTwo, testListenerTwo.assertOneGetNewAndReset().getUnderlying());

        // Add the same alias and type again
        mapProps.clear();
        mapProps.put("prop1", int.class);
        configOps.addEventTypeAlias("AddedNameSecond", mapProps);

        // Add the same alias and a different type
        try
        {
            mapProps.put("XX", int.class);
            configOps.addEventTypeAlias("AddedNameSecond", mapProps);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    public void testAddMapProperties()
    {
        tryInvalid("AddedMapOne");

        // First statement with new name
        Properties mapProps = new Properties();
        mapProps.put("prop1", int.class.getName());
        configOps.addEventTypeAlias("AddedMapOne", mapProps);

        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from AddedMapOne");
        stmt.addListener(testListener);

        Map<String, Object> eventOne = new HashMap<String, Object>();
        eventOne.put("prop1", 1);
        epService.getEPRuntime().sendEvent(eventOne, "AddedMapOne");
        assertEquals(eventOne, testListener.assertOneGetNewAndReset().getUnderlying());

        tryInvalid("AddedMapNameSecond");

        // Second statement using a new alias to the same type, should only one receive
        configOps.addEventTypeAlias("AddedMapNameSecond", mapProps);
        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
        stmt = epService.getEPAdministrator().createEQL("select * from AddedMapNameSecond");
        stmt.addListener(testListenerTwo);

        Map<String, Object> eventTwo = new HashMap<String, Object>();
        eventTwo.put("prop1", 1);
        epService.getEPRuntime().sendEvent(eventTwo, "AddedMapNameSecond");
        assertFalse(testListener.isInvoked());
        assertEquals(eventTwo, testListenerTwo.assertOneGetNewAndReset().getUnderlying());

        // Add the same alias and type again
        mapProps.clear();
        mapProps.put("prop1", int.class.getName());
        configOps.addEventTypeAlias("AddedNameSecond", mapProps);

        // Add the same alias and a different type
        try
        {
            mapProps.put("XX", int.class.getName());
            configOps.addEventTypeAlias("AddedNameSecond", mapProps);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    public void testAddAliasClassName()
    {
        tryInvalid("AddedName");

        // First statement with new name
        configOps.addEventTypeAlias("AddedName", SupportBean.class.getName());
        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from AddedName");
        stmt.addListener(testListener);

        SupportBean eventOne = new SupportBean("a", 1);
        epService.getEPRuntime().sendEvent(eventOne);
        assertSame(eventOne, testListener.assertOneGetNewAndReset().getUnderlying());

        tryInvalid("AddedNameSecond");

        // Second statement using a new alias to the same type, should both receive
        configOps.addEventTypeAlias("AddedNameSecond", SupportBean.class.getName());
        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
        stmt = epService.getEPAdministrator().createEQL("select * from AddedNameSecond");
        stmt.addListener(testListenerTwo);

        SupportBean eventTwo = new SupportBean("b", 2);
        epService.getEPRuntime().sendEvent(eventTwo);
        assertSame(eventTwo, testListener.assertOneGetNewAndReset().getUnderlying());
        assertSame(eventTwo, testListenerTwo.assertOneGetNewAndReset().getUnderlying());
        
        // Add the same alias and type again
        configOps.addEventTypeAlias("AddedNameSecond", SupportBean.class.getName());

        // Add the same alias and a different type
        try
        {
            configOps.addEventTypeAlias("AddedNameSecond", SupportBean_A.class.getName());
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    public void testAddAliasClass()
    {
        tryInvalid("AddedName");

        // First statement with new name
        configOps.addEventTypeAlias("AddedName", SupportBean.class);
        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from AddedName");
        stmt.addListener(testListener);

        SupportBean eventOne = new SupportBean("a", 1);
        epService.getEPRuntime().sendEvent(eventOne);
        assertSame(eventOne, testListener.assertOneGetNewAndReset().getUnderlying());

        tryInvalid("AddedNameSecond");

        // Second statement using a new alias to the same type, should both receive
        configOps.addEventTypeAlias("AddedNameSecond", SupportBean.class);
        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
        stmt = epService.getEPAdministrator().createEQL("select * from AddedNameSecond");
        stmt.addListener(testListenerTwo);

        SupportBean eventTwo = new SupportBean("b", 2);
        epService.getEPRuntime().sendEvent(eventTwo);
        assertSame(eventTwo, testListener.assertOneGetNewAndReset().getUnderlying());
        assertSame(eventTwo, testListenerTwo.assertOneGetNewAndReset().getUnderlying());

        // Add the same alias and type again
        configOps.addEventTypeAlias("AddedNameSecond", SupportBean.class);

        // Add the same alias and a different type
        try
        {
            configOps.addEventTypeAlias("AddedNameSecond", SupportBean_A.class);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // expected
        }
    }

    private void tryInvalid(String alias)
    {
        try
        {
            epService.getEPAdministrator().createEQL("select * from " + alias);
            fail();
        }
        catch (EPStatementException ex)
        {
            // expected
        }
    }

    private Document makeDOMEvent(String rootElementName) throws Exception
    {
        String XML =
            "<VAL1>\n" +
            "  <someelement/>\n" +
            "</VAL1>";

        String xml = XML.replaceAll("VAL1", rootElementName);

        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        Document simpleDoc = builderFactory.newDocumentBuilder().parse(source);
        return simpleDoc;
    }
}
