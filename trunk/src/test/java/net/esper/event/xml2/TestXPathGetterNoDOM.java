package net.esper.event.xml2;

import junit.framework.TestCase;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class TestXPathGetterNoDOM extends TestCase
{
    public void testIt() throws Exception
    {
        String xml = "<simpleEvent>\n" +
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

        XPathFactory factory = XPathFactory.newInstance();
        XPathExpression expression = factory.newXPath().compile("count(/simpleEvent/nested3/nested4)");

        // 6.2 seconds for 1000
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            StringReader reader = new StringReader(xml);
            InputSource source = new InputSource(reader);
            String value = expression.evaluate(source);
            assertEquals("3", value);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("delta from string=" + (endTime - startTime));

        //
        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		Document simpleDoc = builderFactory.newDocumentBuilder().parse(source);

        // 2.3 seconds for 1000
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++)
        {
            String value = expression.evaluate(simpleDoc);
            assertEquals("3", value);
        }
        endTime = System.currentTimeMillis();
        System.out.println("delta from document=" + (endTime - startTime));
    }

}
