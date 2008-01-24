package net.esper.core;

import net.esper.event.EventBean;
import net.esper.event.NaturalEventBean;
import net.esper.collection.Pair;
import net.sf.cglib.reflect.FastMethod;
import net.sf.cglib.reflect.FastClass;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DeliveryConvertorObjectArr implements DeliveryConvertor
{
    public Object[] convertRow(Object[] columns) {
        return new Object[] {columns};
    }
}
