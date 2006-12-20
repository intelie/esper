package net.esper.filter;

import net.esper.support.filter.SupportFilterSpecBuilder;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;
import net.esper.event.EventType;
import net.esper.event.BeanEventAdapter;
import net.esper.eql.parse.ASTFilterSpecValidationException;
import junit.framework.TestCase;

public class TestFilterSpecValidator extends TestCase
{
    private EventType eventType;

    public void setUp()
    {
        eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
    }

    public void testValidate()
    {
        assertInvalid("boolPrimitive", FilterOperator.GREATER, false);
        assertInvalid("boolPrimitive", FilterOperator.RANGE_CLOSED, 1,1);
        assertValid("boolPrimitive", FilterOperator.EQUAL, false);
        assertValid("boolPrimitive", FilterOperator.EQUAL, true);

        assertInvalid("string", FilterOperator.LESS, "a");
        assertInvalid("string", FilterOperator.RANGE_CLOSED, 10,20);
        assertInvalid("string", FilterOperator.EQUAL, null);
        assertValid("string", FilterOperator.EQUAL, "a");

        assertInvalid("dummy", FilterOperator.EQUAL, "a");

        assertInvalid("doubleBoxed", FilterOperator.EQUAL, "a");
        assertValid("doubleBoxed", FilterOperator.RANGE_CLOSED, 2,2);
        assertInvalid("doubleBoxed", FilterOperator.GREATER, 2);
        assertValid("doubleBoxed", FilterOperator.GREATER, 2d);
    }

    private void assertValid(Object ... filterParameters)
    {
        FilterSpec spec = SupportFilterSpecBuilder.build(eventType, filterParameters);
        FilterSpecValidator.validate(spec, null);
    }

    private void assertInvalid(Object ... filterParameters)
    {
        try
        {
            FilterSpec spec = SupportFilterSpecBuilder.build(eventType, filterParameters);
            FilterSpecValidator.validate(spec, null);
            fail();
        }
        catch (ASTFilterSpecValidationException ex)
        {
            // Expected exception
        }
    }
}
