package com.espertech.esper.util;

import com.espertech.esper.support.bean.SupportEnum;
import junit.framework.TestCase;

public class TestSerializableObjectCopier extends TestCase
{
    public void testCopyEnum() throws Exception
    {
        SupportEnum enumOne = SupportEnum.ENUM_VALUE_2;
        Object result = SerializableObjectCopier.copy(enumOne);
        assertEquals(result, enumOne);
        assertTrue(result == enumOne);
    }
}
