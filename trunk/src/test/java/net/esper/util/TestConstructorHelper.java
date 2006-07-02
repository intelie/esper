package net.esper.util;

import junit.framework.TestCase;
import net.esper.support.util.SupportCtorNone;
import net.esper.support.util.SupportCtorObjectArray;
import net.esper.support.util.SupportCtorInt;
import net.esper.support.util.SupportCtorIntObjectArray;

public class TestConstructorHelper extends TestCase
{
    public void testValidInvokeConstructor() throws Exception
    {
        Object[] params = new Object[] { "test", 1 };
        SupportCtorObjectArray objOne = (SupportCtorObjectArray) ConstructorHelper.invokeConstructor(SupportCtorObjectArray.class, params);
        assertEquals(params, objOne.getArguments());

        SupportCtorInt objTwo = (SupportCtorInt) ConstructorHelper.invokeConstructor(SupportCtorInt.class, new Object[] { 99 });
        assertEquals(99, objTwo.getSomeValue());
        objTwo = (SupportCtorInt) ConstructorHelper.invokeConstructor(SupportCtorInt.class, new Object[] { new Integer(13) });
        assertEquals(13, objTwo.getSomeValue());

        SupportCtorIntObjectArray objThree = (SupportCtorIntObjectArray) ConstructorHelper.invokeConstructor(SupportCtorIntObjectArray.class, new Object[] { 1 });
        assertEquals(1, objThree.getSomeValue());
        objThree = (SupportCtorIntObjectArray) ConstructorHelper.invokeConstructor(SupportCtorIntObjectArray.class, params);
        assertEquals(params, objThree.getArguments());
    }

    public void testInvalidInvokeConstructor() throws Exception
    {
        // No Ctor
        try
        {
            ConstructorHelper.invokeConstructor(SupportCtorNone.class, new Object[0]);
            fail();
        }
        catch (NoSuchMethodException ex)
        {
            // Expected
        }

        // Not matching Ctor - number of params
        try
        {
            ConstructorHelper.invokeConstructor(SupportCtorInt.class, new Object[0]);
            fail();
        }
        catch (NoSuchMethodException ex)
        {
            // Expected
        }

        // Type not matching
        try
        {
            ConstructorHelper.invokeConstructor(SupportCtorInt.class, new Object[] { "a" });
            fail();
        }
        catch (NoSuchMethodException ex)
        {
            // Expected
        }
    }
}
