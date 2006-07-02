package net.esper.eql.expression;

import junit.framework.TestCase;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportBeanComplexProps;
import net.esper.support.eql.SupportStreamTypeSvc3Stream;
import net.esper.support.event.SupportEventBeanFactory;
import net.esper.regression.eql.TestPerfPropertyAccess;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestExprIdentNode extends TestCase
{
    private ExprIdentNode identNodes[];
    private StreamTypeService streamTypeService;

    public void setUp()
    {
        identNodes = new ExprIdentNode[4];
        identNodes[0] = new ExprIdentNode("mapped('a')");
        identNodes[1] = new ExprIdentNode("nestedValue", "nested");
        identNodes[2] = new ExprIdentNode("indexed[1]", "s2");
        identNodes[3] = new ExprIdentNode("intPrimitive", "s0");

        streamTypeService = new SupportStreamTypeSvc3Stream();
    }

    public void testValidateInvalid() throws Exception
    {
        try
        {
            identNodes[0].getStreamId();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }

        try
        {
            identNodes[0].getType();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }

        try
        {
            identNodes[0].getResolvedStreamName();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }

        try
        {
            identNodes[0].getResolvedPropertyName();
            fail();
        }
        catch (IllegalStateException ex)
        {
            // expected
        }
    }

    public void testValidate() throws Exception
    {
        identNodes[0].validate(streamTypeService);
        assertEquals(2, identNodes[0].getStreamId());
        assertEquals(String.class, identNodes[0].getType());
        assertEquals("mapped('a')", identNodes[0].getResolvedPropertyName());

        identNodes[1].validate(streamTypeService);
        assertEquals(2, identNodes[1].getStreamId());
        assertEquals(String.class, identNodes[1].getType());
        assertEquals("nested.nestedValue", identNodes[1].getResolvedPropertyName());

        identNodes[2].validate(streamTypeService);
        assertEquals(2, identNodes[2].getStreamId());
        assertEquals(int.class, identNodes[2].getType());
        assertEquals("indexed[1]", identNodes[2].getResolvedPropertyName());

        identNodes[3].validate(streamTypeService);
        assertEquals(0, identNodes[3].getStreamId());
        assertEquals(int.class, identNodes[3].getType());
        assertEquals("intPrimitive", identNodes[3].getResolvedPropertyName());

        tryInvalidValidate(new ExprIdentNode(""));
        tryInvalidValidate(new ExprIdentNode("dummy"));
        tryInvalidValidate(new ExprIdentNode("nested", "s0"));
        tryInvalidValidate(new ExprIdentNode("dummy", "s2"));
        tryInvalidValidate(new ExprIdentNode("intPrimitive", "s2"));
        tryInvalidValidate(new ExprIdentNode("intPrimitive", "s3"));
    }

    public void testGetType() throws Exception
    {
        // test success
        identNodes[0].validate(streamTypeService);
        assertEquals(String.class, identNodes[0].getType());
    }

    public void testEvaluate() throws Exception
    {
        EventBean[] events = new EventBean[] {makeEvent(10)};

        identNodes[3].validate(streamTypeService);
        assertEquals(10, identNodes[3].evaluate(events));
        assertNull(identNodes[3].evaluate(new EventBean[2]));
    }

    public void testEvaluatePerformance() throws Exception
    {
        // test performance of evaluate for indexed events
        // fails if the getter is not in place

        EventBean[] events = SupportStreamTypeSvc3Stream.getSampleEvents();
        identNodes[2].validate(streamTypeService);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
        {
            identNodes[2].evaluate(events);
        }
        long endTime = System.currentTimeMillis();
        long delta = endTime - startTime;
        log.info(".testEvaluate delta=" + delta);
        assertTrue(delta < 200);
    }

    public void testToExpressionString() throws Exception
    {
        for (int i = 0; i < identNodes.length; i++)
        {
            identNodes[i].validate(streamTypeService);
        }
        assertEquals("mapped('a')", identNodes[0].toExpressionString());
        assertEquals("nested.nestedValue", identNodes[1].toExpressionString());
        assertEquals("s2.indexed[1]", identNodes[2].toExpressionString());
        assertEquals("s0.intPrimitive", identNodes[3].toExpressionString());
    }

    public void testEqualsNode() throws Exception
    {
        identNodes[0].validate(streamTypeService);
        identNodes[2].validate(streamTypeService);
        identNodes[3].validate(streamTypeService);
        assertTrue(identNodes[3].equalsNode(identNodes[3]));
        assertFalse(identNodes[0].equalsNode(identNodes[2]));
    }

    private EventBean makeEvent(int intPrimitive)
    {
        SupportBean event = new SupportBean();
        event.setIntPrimitive(intPrimitive);
        return SupportEventBeanFactory.createObject(event);
    }

    private void tryInvalidValidate(ExprIdentNode identNode)
    {
        try
        {
            identNode.validate(streamTypeService);
            fail();
        }
        catch(ExprValidationException ex)
        {
            // expected
        }
    }

    private static final Log log = LogFactory.getLog(TestExprIdentNode.class);
}
