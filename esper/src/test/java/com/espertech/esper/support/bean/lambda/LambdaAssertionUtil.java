package com.espertech.esper.support.bean.lambda;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.Assert;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class LambdaAssertionUtil {

    public static void assertValuesArrayScalar(SupportUpdateListener listener, String field, Object... expected) {
        Object result = listener.assertOneGetNew().get(field);
        if (expected == null) {
            Assert.assertNull(result);
            return;
        }
        Object[] arr = ((Collection) result).toArray();
        ArrayAssertionUtil.assertEqualsExactOrder(arr, expected);
    }

    public static void assertST0Id(SupportUpdateListener listener, String property, String expectedList) {
        SupportBean_ST0[] arr = toArray((Collection<SupportBean_ST0>) listener.assertOneGetNew().get(property));
        if (expectedList == null && arr == null) {
            return;
        }
        if (expectedList.isEmpty() && arr.length == 0) {
            return;
        }
        String[] expected = expectedList.split(",");
        Assert.assertEquals("Received: " + getIds(arr), expected.length, arr.length);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], arr[i].getId());
        }
    }

    public static String getIds(SupportBean_ST0[] arr) {
        String delimiter = "";
        StringWriter writer = new StringWriter();
        for (SupportBean_ST0 item : arr) {
            writer.append(delimiter);
            delimiter = ",";
            writer.append(item.getId());
        }
        return writer.toString();
    }

    private static SupportBean_ST0[] toArray(Collection<SupportBean_ST0> it) {
        if (it == null) {
            return null;
        }
        if (it.isEmpty()) {
            return new SupportBean_ST0[0];
        }
        return it.toArray(new SupportBean_ST0[it.size()]);
    }

    public static void assertTypes(EventType type, String[] fields, Class[] classes) {
        int count = 0;
        for (String field : fields) {
            Assert.assertEquals("position " + count, classes[count++], type.getPropertyType(field));
        }
    }

    public static void assertTypesAllSame(EventType type, String[] fields, Class clazz) {
        int count = 0;
        for (String field : fields) {
            Assert.assertEquals("position " + count, clazz, type.getPropertyType(field));
        }
    }
}
