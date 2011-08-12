/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.event.xml;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.ConfigurationEventTypeXMLDOM;
import com.espertech.esper.support.event.SupportEventAdapterService;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import junit.framework.TestCase;

public class TestSimpleXMLEventType extends TestCase {

    private static final String xml =
            "<simpleEvent>\n" +
            "\t<nested1 attr1=\"SAMPLE_ATTR1\">\n" +
            "\t\t<prop1>SAMPLE_V1</prop1>\n" +
            "\t\t<prop2>true</prop2>\n" +
            "\t\t<nested2>\n" +
            "\t\t\t<prop3>3</prop3>\n" +
            "\t\t\t<prop3>4</prop3>\n" +
            "\t\t\t<prop3>5</prop3>\n" +
            "\t\t</nested2>\n" +
            "\t</nested1>\n" +
            "\t<prop4 attr2=\"true\">SAMPLE_V6</prop4>\n" +
            "\t<nested3>\n" +
            "\t\t<nested4 id=\"a\">\n" +
            "\t\t\t<prop5>SAMPLE_V7</prop5>\n" +
            "\t\t\t<prop5>SAMPLE_V8</prop5>\n" +
            "\t\t</nested4>\n" +
            "\t\t<nested4 id=\"b\">\n" +
            "\t\t\t<prop5>SAMPLE_V9</prop5>\n" +
            "\t\t</nested4>\n" +
            "\t\t<nested4 id=\"c\">\n" +
            "\t\t\t<prop5>SAMPLE_V10</prop5>\n" +
            "\t\t\t<prop5>SAMPLE_V11</prop5>\n" +
            "\t\t</nested4>\n" +
            "\t</nested3>\n" +
            "</simpleEvent>";

    private EventBean event;
	
	protected void setUp() throws Exception
    {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		Document simpleDoc = builderFactory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));

        ConfigurationEventTypeXMLDOM config = new ConfigurationEventTypeXMLDOM();
        config.setRootElementName("simpleEvent");
        config.addXPathProperty("customProp", "count(/simpleEvent/nested3/nested4)", XPathConstants.NUMBER);

        SimpleXMLEventType eventType = new SimpleXMLEventType(null, 1, config, SupportEventAdapterService.getService());
		event = new XMLEventBean(simpleDoc.getDocumentElement(), eventType);
	}

	public void testSimpleProperies() {
		assertEquals("SAMPLE_V6", event.get("prop4"));
	}
	
	public void testNestedProperties() {
		assertEquals("true",event.get("nested1.prop2"));
	}
	
	public void testMappedProperties() {
		assertEquals("SAMPLE_V8",event.get("nested3.nested4('a').prop5[1]"));
		assertEquals("SAMPLE_V10",event.get("nested3.nested4('c').prop5[0]"));
	}
	
	public void testIndexedProperties() {
		assertEquals("5",event.get("nested1.nested2.prop3[2]"));
		assertEquals(String.class,event.getEventType().getPropertyType("nested1.nested2.prop3[2]"));
	}
	
	public void testCustomProperty() {
		assertEquals(Double.class,event.getEventType().getPropertyType("customProp"));
		assertEquals(new Double(3),event.get("customProp"));
	}
}
