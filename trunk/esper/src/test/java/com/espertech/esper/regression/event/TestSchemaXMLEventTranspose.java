package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.event.EventTypeAssertionUtil;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.xpath.XPathConstants;

public class TestSchemaXMLEventTranspose extends TestCase
{
    private static String CLASSLOADER_SCHEMA_URI = "regression/simpleSchema.xsd";

    private String schemaURI;

    public void setUp()
    {
        schemaURI = TestSchemaXMLEventTranspose.class.getClassLoader().getResource(CLASSLOADER_SCHEMA_URI).toString();
    }

    // TODO: transpose two different XML docs
    public void testTransposeXPath() throws Exception
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();

        ConfigurationEventTypeXMLDOM rootMeta = new ConfigurationEventTypeXMLDOM();
        rootMeta.setRootElementName("simpleEvent");
        rootMeta.setSchemaResource(schemaURI);
        rootMeta.addNamespacePrefix("ss", "samples:schemas:simpleSchema");
        rootMeta.addXPathPropertyFragment("nested1simple", "/ss:simpleEvent/ss:nested1", XPathConstants.NODE, "MyNestedEvent");
        rootMeta.addXPathPropertyFragment("nested4array", "//ss:nested4", XPathConstants.NODESET, "MyNestedArrayEvent");
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MyXMLEvent", rootMeta);

        ConfigurationEventTypeXMLDOM metaNested = new ConfigurationEventTypeXMLDOM();
        metaNested.setRootElementName("//nested1");
        metaNested.setSchemaResource(schemaURI);
        epService.getEPAdministrator().getConfiguration().addEventTypeAlias("MyNestedEvent", metaNested);

        ConfigurationEventTypeXMLDOM metaNestedArray = new ConfigurationEventTypeXMLDOM();
        metaNestedArray.setRootElementName("//nested4");
        metaNestedArray.setSchemaResource(schemaURI);
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
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("prop1", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("prop2", Boolean.class, false, false, false, false, false),
            new EventPropertyDescriptor("attr1", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("nested2", Node.class, false, false, false, false, false),
           }, fragmentTypeNested1.getFragmentType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(fragmentTypeNested1.getFragmentType());

        FragmentEventType fragmentTypeNested4 = stmtInsert.getEventType().getFragmentType("nested4array");
        assertTrue(fragmentTypeNested4.isIndexed());
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("prop5", String.class, false, false, true, false, false),
            new EventPropertyDescriptor("id", String.class, false, false, false, false, false),
           }, fragmentTypeNested4.getFragmentType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(fragmentTypeNested4.getFragmentType());

        SupportXML.sendEvent(epService.getEPRuntime(), "ABC");

        EventBean received = stmtInsert.iterator().next();
        ArrayAssertionUtil.assertProps(received, "nested1simple.prop1,nested1simple.prop2,nested1simple.attr1,nested1simple.nested2.prop3[1]".split(","), new Object[] {"SAMPLE_V1", true, "SAMPLE_ATTR1", 4});
        ArrayAssertionUtil.assertProps(received, "nested4array[0].id,nested4array[0].prop5[1],nested4array[1].id".split(","), new Object[] {"a","SAMPLE_V8","b"});

        // assert fragments alone
        FragmentEventType eventType = stmtWildcard.iterator().next().getEventType().getFragmentType("nested1simple");
        assertFalse(eventType.isIndexed());
        assertFalse(eventType.isNative());
        assertEquals("MyNestedEvent", eventType.getFragmentType().getName());

        eventType = stmtWildcard.iterator().next().getEventType().getFragmentType("nested4array");
        assertTrue(eventType.isIndexed());
        assertFalse(eventType.isNative());
        assertEquals("MyNestedArrayEvent", eventType.getFragmentType().getName());
    }

    public void testSchemaXMLTranspose() throws Exception
    {
        EPServiceProvider epService = EPServiceProviderManager.getProvider("TestSchemaXML", getConfig());
        epService.initialize();

        EPStatement stmtInsert = epService.getEPAdministrator().createEPL("insert into MyNestedStream select nested1 from TestXMLSchemaType");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("nested1", Node.class, false, false, false, false, true),
           }, stmtInsert.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtInsert.getEventType());

        EPStatement stmtSelect = epService.getEPAdministrator().createEPL("select nested1.attr1 as attr1, nested1.prop1 as prop1, nested1.prop2 as prop2, nested1.nested2.prop3 as prop3, nested1.nested2 as nested2 from MyNestedStream");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("prop1", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("prop2", Boolean.class, false, false, false, false, false),
            new EventPropertyDescriptor("attr1", String.class, false, false, false, false, false),
            new EventPropertyDescriptor("prop3", Integer.class, false, false, false, false, false),
            new EventPropertyDescriptor("nested2", Node.class, false, false, false, false, true),
           }, stmtSelect.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtSelect.getEventType());

        EPStatement stmtSelectWildcard = epService.getEPAdministrator().createEPL("select * from MyNestedStream");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
            new EventPropertyDescriptor("nested1", Node.class, false, false, false, false, true),
           }, stmtSelectWildcard.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtSelectWildcard.getEventType());

        EPStatement stmtInsertWildcard = epService.getEPAdministrator().createEPL("insert into MyNestedStreamTwo select nested1.* from TestXMLSchemaType");
        ArrayAssertionUtil.assertEqualsAnyOrder(new Object[] {
                new EventPropertyDescriptor("prop1", String.class, false, false, false, false, false),
                new EventPropertyDescriptor("prop2", Boolean.class, false, false, false, false, false),
                new EventPropertyDescriptor("attr1", String.class, false, false, false, false, false),
                new EventPropertyDescriptor("nested2", Node.class, false, false, false, false, true),
           }, stmtInsertWildcard.getEventType().getPropertyDescriptors());
        EventTypeAssertionUtil.assertConsistency(stmtInsert.getEventType());

        Document eventDoc = SupportXML.sendEvent(epService.getEPRuntime(), "test");
        ArrayAssertionUtil.assertProps(stmtSelect.iterator().next(), "prop1,prop2,attr1,prop3".split(","),
                new Object[] {"SAMPLE_V1", "", true, ""});
        EventTypeAssertionUtil.assertConsistency(stmtSelect.iterator().next());
    }

    private Configuration getConfig()
    {
        ConfigurationEventTypeXMLDOM eventTypeMeta = new ConfigurationEventTypeXMLDOM();
        eventTypeMeta.setRootElementName("simpleEvent");
        String schemaUri = TestSchemaXMLEventTranspose.class.getClassLoader().getResource(CLASSLOADER_SCHEMA_URI).toString();
        eventTypeMeta.setSchemaResource(schemaUri);
        eventTypeMeta.addNamespacePrefix("ss", "samples:schemas:simpleSchema");
        eventTypeMeta.addXPathProperty("customProp", "count(/ss:simpleEvent/ss:nested3/ss:nested4)", XPathConstants.NUMBER);

        ConfigurationEventTypeXMLDOM eventTypeMetaNested = new ConfigurationEventTypeXMLDOM();
        eventTypeMetaNested.setRootElementName("nested1");
        eventTypeMetaNested.setSchemaResource(schemaUri);
        eventTypeMetaNested.addNamespacePrefix("ss", "samples:schemas:simpleSchema");
        eventTypeMetaNested.addXPathProperty("customProp", "count(ss:nested3/ss:nested4)", XPathConstants.NUMBER);

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventTypeAlias("TestXMLRoot", eventTypeMeta);
        configuration.addEventTypeAlias("TestXMLNested", eventTypeMetaNested);

        return configuration;
    }

    private static final Log log = LogFactory.getLog(TestSchemaXMLEventTranspose.class);
}
