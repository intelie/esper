package com.espertech.esper.support.bean.lambda;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_ST0;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class LambdaAssertionUtil {

    public static void assertValues(SupportUpdateListener listener, String field, Object... expected) {
        if (expected == null) {
            Assert.assertNull(listener.assertOneGetNew().get(field));
            return;
        }
        Object[] arr = ((Collection) listener.assertOneGetNew().get(field)).toArray();
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
        Assert.assertEquals(expected.length, arr.length);
        for (int i = 0; i < expected.length; i++) {
            Assert.assertEquals(expected[i], arr[i].getId());
        }
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
}
