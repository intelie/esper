package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.event.EventTypeAssertionUtil;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;

public class TestNoSchemaXMLEventTranspose extends TestCase
{
    private static String CLASSLOADER_SCHEMA_URI = "regression/simpleSchema.xsd";

    private EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testXPathConfigured() throws Exception
    {
        ConfigurationEventTypeXMLDOM rootMeta = new ConfigurationEventTypeXMLDOM();
        rootMeta.setRootElementName("simpleEvent");
        rootMeta.addNamespacePrefix("ss", "samples:schemas:simpleSchema");
        rootMeta.addXPathPropertyFragment("nested1simple", "/ss:simpleEvent/ss:nested1", XPathConstants.NODE, "MyNestedEvent");
        rootMeta.addXPathPropertyFragment("nested4array", "//ss:nested4", XPathConstants.NODESET, "MyNestedArrayEvent");
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MyXMLEvent", rootMeta);

        ConfigurationEventTypeXMLDOM metaNested = new ConfigurationEventTypeXMLDOM();
        metaNested.setRootElementName("nested1");
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MyNestedEvent", metaNested);

        ConfigurationEventTypeXMLDOM metaNestedArray = new ConfigurationEventTypeXMLDOM();
        metaNestedArray.setRootElementName("nested4");
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MyNestedArrayEvent", metaNestedArray);

        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into Nested3Stream select nested1simple, nested4array from MyXMLEvent");
        EPStatement stmtWildcard = epService.getEPAdministrator().createEPL("select * from MyXMLEvent");
        EventTypeAssertionUtil.assertConsistency(stmtInsert.getEventType());
        EventTypeAssertionUtil.assertConsistency(stmtWildcard.getEventType());
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("nested1simple", Node.class, false, false, false, false, true),
            new EventPropertyDescriptor("nested4array", Node[].class, false, false, true, false, true),
           }, stmtInsert.getEventType().getPropertyDescriptors());

        FragmentEventType fragmentTypeNested1 = stmtInsert.getEventType().getFragmentType("nested1simple");
        assertFalse(fragmentTypeNested1.isIndexed());
        assertEquals(0, fragmentTypeNested1.getFragmentType().getPropertyDescriptors().length);
        EventTypeAssertionUtil.assertConsistency(fragmentTypeNested1.getFragmentType());

        FragmentEventType fragmentTypeNested4 = stmtInsert.getEventType().getFragmentType("nested4array");
        assertTrue(fragmentTypeNested4.isIndexed());
        assertEquals(0, fragmentTypeNested4.getFragmentType().getPropertyDescriptors().length);
        EventTypeAssertionUtil.assertConsistency(fragmentTypeNested4.getFragmentType());

        SupportXML.sendEvent(epService.getEPRuntime(), "ABC");

        EventBean received = stmtInsert.iterator().next();
        ArrayAssertionUtil.assertProps(received, "nested1simple.prop1,nested1simple.prop2,nested1simple.attr1,nested1simple.nested2.prop3[1]".split(","), new Object[] {"SAMPLE_V1", "true", "SAMPLE_ATTR1", "4"});
        ArrayAssertionUtil.assertProps(received, "nested4array[0].id,nested4array[0].prop5[1],nested4array[1].id".split(","), new Object[] {"a","SAMPLE_V8","b"});

        // assert event and fragments alone
        EventBean wildcardStmtEvent = stmtWildcard.iterator().next();
        EventTypeAssertionUtil.assertConsistency(wildcardStmtEvent);

        FragmentEventType eventType = wildcardStmtEvent.getEventType().getFragmentType("nested1simple");
        assertFalse(eventType.isIndexed());
        assertFalse(eventType.isNative());
        assertEquals("MyNestedEvent", eventType.getFragmentType().getName());
        assertTrue(wildcardStmtEvent.get("nested1simple") instanceof Node);
        assertEquals("SAMPLE_V1", ((EventBean)wildcardStmtEvent.getFragment("nested1simple")).get("prop1"));

        eventType = wildcardStmtEvent.getEventType().getFragmentType("nested4array");
        assertTrue(eventType.isIndexed());
        assertFalse(eventType.isNative());
        assertEquals("MyNestedArrayEvent", eventType.getFragmentType().getName());
        EventBean[] eventsArray = (EventBean[])wildcardStmtEvent.getFragment("nested4array");
        assertEquals(3, eventsArray.length);
        assertEquals("SAMPLE_V8", eventsArray[0].get("prop5[1]"));
        assertEquals("SAMPLE_V9", eventsArray[1].get("prop5[0]"));
        assertEquals(NodeList.class, wildcardStmtEvent.getEventType().getPropertyType("nested4array"));
        assertTrue(wildcardStmtEvent.get("nested4array") instanceof NodeList);
    }

    public void testExpressionSimpleDOMGetter() throws Exception
    {
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("simpleEvent");
        // eventTypeMeta.setPropertyExprXPath(false); <== the default
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("TestXMLSchemaType", eventTypeMeta);

        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into MyNestedStream select nested1 from TestXMLSchemaType");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("nested1", String.class, false, false, false, false, false),
           }, stmtInsert.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtInsert.getEventType());

        EPStatement stmtSelectWildcard = epService.getEPAdministrator().createEPL("select * from TestXMLSchemaType");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[0], stmtSelectWildcard.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtSelectWildcard.getEventType());

        SupportXML.sendEvent(epService.getEPRuntime(), "test");
        EventBean stmtInsertWildcardBean = stmtInsert.iterator().next();
        EventBean stmtSelectWildcardBean = stmtInsert.iterator().next();
        ArrayAssertionUtil.assertProps(stmtInsertWildcardBean, "nested1".split(","),
                new Object[] {"SAMPLE_V1"});
        EventTypeAssertionUtil.assertConsistency(stmtSelectWildcardBean);
        EventTypeAssertionUtil.assertConsistency(stmtInsert.iterator().next());
        
        assertEquals(0, stmtSelectWildcardBean.getEventType().getPropertyNames().length);
    }

    // Note that XPath Node results when transposed must be queried by XPath that is also absolute.
    // For example: "nested1" => "/n0:simpleEvent/n0:nested1" results in a Node.
    // That result Node's "prop1" =>  "/n0:simpleEvent/n0:nested1/n0:prop1" and "/n0:nested1/n0:prop1" does NOT result in a value.
    // Therefore property transposal is disabled for Property-XPath expressions.
    public void testExpressionSimpleXPathGetter() throws Exception
    {
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("simpleEvent");
        String schemaUri = TestNoSchemaXMLEventTranspose.class.getClassLoader().getResource(CLASSLOADER_SCHEMA_URI).toString();
        eventTypeMeta.setSchemaResource(schemaUri);
        eventTypeMeta.setPropertyExprXPath(true);       // <== note this
        eventTypeMeta.addNamespacePrefix("ss", "samples:schemas:simpleSchema");
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("TestXMLSchemaType", eventTypeMeta);

        // note class not a fragment
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into MyNestedStream select nested1 from TestXMLSchemaType");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("nested1", Node.class, false, false, false, false, false),
           }, stmtInsert.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtInsert.getEventType());

        EventType type = ((EPServiceProviderSPI)epService).getEventAdapterService().getExistsTypeByAlias("TestXMLSchemaType");
        EventTypeAssertionUtil.assertConsistency(type);
        assertNull(type.getFragmentType("nested1"));
        assertNull(type.getFragmentType("nested1.nested2"));

        SupportXML.sendEvent(epService.getEPRuntime(), "ABC");
        EventTypeAssertionUtil.assertConsistency(stmtInsert.iterator().next());
    }

    public void testExpressionNodeArray() throws Exception
    {
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("simpleEvent");
        String schemaUri = TestNoSchemaXMLEventTranspose.class.getClassLoader().getResource(CLASSLOADER_SCHEMA_URI).toString();
        eventTypeMeta.setSchemaResource(schemaUri);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("TestXMLSchemaType", eventTypeMeta);

        // try array property insert
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("select nested3.nested4 as narr from TestXMLSchemaType");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("narr", Node[].class, false, false, true, false, true),
           }, stmtInsert.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtInsert.getEventType());

        SupportXML.sendEvent(epService.getEPRuntime(), "test");

        EventBean result = stmtInsert.iterator().next();
        EventTypeAssertionUtil.assertConsistency(result);
        EventBean[] fragments = (EventBean[]) result.getFragment("narr");
        assertEquals(3, fragments.length);
        assertEquals("SAMPLE_V8", fragments[0].get("prop5[1]"));
        assertEquals("SAMPLE_V11", fragments[2].get("prop5[1]"));

        EventBean fragmentItem = (EventBean) result.getFragment("narr[2]");
        assertEquals("TestXMLSchemaType.nested3.nested4", fragmentItem.getEventType().getName());
        assertEquals("SAMPLE_V10", fragmentItem.get("prop5[0]"));

        // try array index property insert
        EPStatement stmtInsertItem = epService.getEPAdministrator().createEPL("select nested3.nested4[1] as narr from TestXMLSchemaType");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("narr", Node.class, false, false, false, false, true),
           }, stmtInsertItem.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtInsertItem.getEventType());

        SupportXML.sendEvent(epService.getEPRuntime(), "test");

        EventBean resultItem = stmtInsertItem.iterator().next();
        assertEquals("b", resultItem.get("narr.id"));
        EventTypeAssertionUtil.assertConsistency(resultItem);
        EventBean fragmentsInsertItem = (EventBean) resultItem.getFragment("narr");
        EventTypeAssertionUtil.assertConsistency(fragmentsInsertItem);
        assertEquals("b", fragmentsInsertItem.get("id"));
        assertEquals("SAMPLE_V9", fragmentsInsertItem.get("prop5[0]"));
    }

    public void testExpressionPrimitiveArray() throws Exception
    {
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("simpleEvent");
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("ABCType", eventTypeMeta);

        eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("//nested2");
        eventTypeMeta.setEventSenderValidatesRoot(false);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("TestNested2", eventTypeMeta);

        // try array property in select
        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("select * from TestNested2");
        stmtInsert.addListener(listener);
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("prop3", Integer[].class, false, false, true, false, false),
           }, stmtInsert.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtInsert.getEventType());

        SupportXML.sendEvent(epService.getEPRuntime(), "test");
        assertFalse(listener.isInvoked());

        EventSender sender = epService.getEPRuntime().getEventSender("TestNested2");
        sender.sendEvent(SupportXML.getDocument("<nested2><prop3>2</prop3><prop3></prop3><prop3>4</prop3></nested2>"));
        EventBean event = stmtInsert.iterator().next();
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {2,null,4}, (Integer[]) event.get("prop3"));
        EventTypeAssertionUtil.assertConsistency(event);

        // try array property nested
        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select nested3.* from ABCType");
        SupportXML.sendEvent(epService.getEPRuntime(), "test");
        EventBean stmtSelectResult = stmtSelect.iterator().next();
        EventTypeAssertionUtil.assertConsistency(stmtSelectResult);
        assertEquals(String[].class, stmtSelectResult.getEventType().getPropertyType("nested4[2].prop5"));
        assertEquals("SAMPLE_V8", stmtSelectResult.get("nested4[0].prop5[1]"));
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {"SAMPLE_V10", "SAMPLE_V11"}, (String[]) stmtSelectResult.get("nested4[2].prop5"));

        EventBean fragmentNested4 = (EventBean) stmtSelectResult.getFragment("nested4[2]");
        ArrayAssertionUtil.assertEqualsExactOrder(new Object[] {"SAMPLE_V10", "SAMPLE_V11"}, (String[]) fragmentNested4.get("prop5"));
        assertEquals("SAMPLE_V11", fragmentNested4.get("prop5[1]"));
        EventTypeAssertionUtil.assertConsistency(fragmentNested4);
    }
}
