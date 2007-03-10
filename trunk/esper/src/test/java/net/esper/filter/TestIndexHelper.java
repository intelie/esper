package net.esper.filter;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.TestCase;
import net.esper.collection.Pair;
import net.esper.event.EventType;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventTypeFactory;

public class TestIndexHelper extends TestCase
{
    private EventType eventType;
    private SortedSet<FilterValueSetParam> parameters;
    private FilterValueSetParam parameterOne;
    private FilterValueSetParam parameterTwo;
    private FilterValueSetParam parameterThree;

    public void setUp()
    {
        eventType = SupportEventTypeFactory.createBeanType(SupportBean.class);
        parameters = new TreeSet<FilterValueSetParam>(new FilterValueSetParamComparator());

        // Create parameter test list
        parameterOne = new FilterValueSetParamImpl("intPrimitive", FilterOperator.GREATER, 10);
        parameters.add(parameterOne);
        parameterTwo = new FilterValueSetParamImpl("doubleBoxed", FilterOperator.GREATER, 20d);
        parameters.add(parameterTwo);
        parameterThree = new FilterValueSetParamImpl("string", FilterOperator.EQUAL, "sometext");
        parameters.add(parameterThree);
    }

    public void testFindIndex()
    {
        List<FilterParamIndexBase> indexes = new LinkedList<FilterParamIndexBase>();

        // Create index list wity index that doesn't match
        FilterParamIndexBase indexOne = IndexFactory.createIndex(eventType, "boolPrimitive", FilterOperator.EQUAL);
        indexes.add(indexOne);
        assertTrue(IndexHelper.findIndex(parameters, indexes) == null);

        // Create index list wity index that doesn't match
        indexOne = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.GREATER_OR_EQUAL);
        indexes.clear();
        indexes.add(indexOne);
        assertTrue(IndexHelper.findIndex(parameters, indexes) == null);

        // Add an index that does match a parameter
        FilterParamIndexBase indexTwo = IndexFactory.createIndex(eventType, "doubleBoxed", FilterOperator.GREATER);
        indexes.add(indexTwo);
        Pair<FilterValueSetParam, FilterParamIndexBase> pair = IndexHelper.findIndex(parameters, indexes);
        assertTrue(pair != null);
        assertEquals(parameterTwo, pair.getFirst());
        assertEquals(indexTwo, pair.getSecond());

        // Add another index that does match a parameter, should return first match however which is doubleBoxed
        FilterParamIndexBase indexThree = IndexFactory.createIndex(eventType, "intPrimitive", FilterOperator.GREATER);
        indexes.add(indexThree);
        pair = IndexHelper.findIndex(parameters, indexes);
        assertEquals(parameterTwo, pair.getFirst());
        assertEquals(indexTwo, pair.getSecond());

        // Try again removing one index
        indexes.remove(indexTwo);
        pair = IndexHelper.findIndex(parameters, indexes);
        assertEquals(parameterOne, pair.getFirst());
        assertEquals(indexThree, pair.getSecond());
    }

    public void testFindParameter()
    {
        FilterParamIndexBase indexOne = IndexFactory.createIndex(eventType, "boolPrimitive", FilterOperator.EQUAL);
        assertNull(IndexHelper.findParameter(parameters, indexOne));

        FilterParamIndexBase indexTwo = IndexFactory.createIndex(eventType, "string", FilterOperator.EQUAL);
        assertEquals(parameterThree, IndexHelper.findParameter(parameters, indexTwo));

        FilterParamIndexBase indexThree = IndexFactory.createIndex(eventType, "intPrimitive", FilterOperator.GREATER);
        assertEquals(parameterOne, IndexHelper.findParameter(parameters, indexThree));
    }
}
