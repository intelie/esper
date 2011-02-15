package com.espertech.esper.epl.lookup;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.join.plan.RangeKeyDesc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JoinedPropUtil {

    public static int[] getKeyStreamNumsRange(Collection<Pair<Integer, RangeKeyDesc>> pairs) {
        int[] streamIds = new int[pairs.size()];
        int count = 0;
        for (Pair<Integer, RangeKeyDesc> pair : pairs)
        {
            streamIds[count++] = pair.getFirst();
        }
        return streamIds;
    }

    public static List<RangeKeyDesc> getRanges(Collection<Pair<Integer, RangeKeyDesc>> pairs) {
        List<RangeKeyDesc> ranges = new ArrayList<RangeKeyDesc>();
        for (Pair<Integer, RangeKeyDesc> pair : pairs)
        {
            ranges.add(pair.getSecond());
        }
        return ranges;
    }

    /**
     * Returns the key stream numbers.
     * @param descList a list of descriptors
     * @return key stream numbers
     */
    public static int[] getKeyStreamNums(Collection<JoinedPropDesc> descList)
    {
        int[] streamIds = new int[descList.size()];
        int count = 0;
        for (JoinedPropDesc desc : descList)
        {
            streamIds[count++] = desc.getKeyStreamId();
        }
        return streamIds;
    }

    /**
     * Returns the key stream numbers.
     * @param descList a list of descriptors
     * @return key stream numbers
     */
    public static int[] getKeyStreamNums(JoinedPropDesc[] descList)
    {
        int[] streamIds = new int[descList.length];
        int count = 0;
        for (JoinedPropDesc desc : descList)
        {
            streamIds[count++] = desc.getKeyStreamId();
        }
        return streamIds;
    }

    /**
     * Returns the key property names.
     * @param descList a list of descriptors
     * @return key property names
     */
    public static String[] getKeyProperties(Collection<JoinedPropDesc> descList)
    {
        String[] result = new String[descList.size()];
        int count = 0;
        for (JoinedPropDesc desc : descList)
        {
            result[count++] = desc.getKeyPropName();
        }
        return result;
    }

    /**
     * Returns the key property names.
     * @param descList a list of descriptors
     * @return key property names
     */
    public static String[] getKeyProperties(JoinedPropDesc[] descList)
    {
        String[] result = new String[descList.length];
        int count = 0;
        for (JoinedPropDesc desc : descList)
        {
            result[count++] = desc.getKeyPropName();
        }
        return result;
    }

    /**
     * Returns the index property names given an array of descriptors.
     * @param descList descriptors of joined properties
     * @return array of index property names
     */
    public static String[] getIndexProperties(JoinedPropDesc[] descList)
    {
        String[] result = new String[descList.length];
        int count = 0;
        for (JoinedPropDesc desc : descList)
        {
            result[count++] = desc.getIndexPropName();
        }
        return result;
    }

    /**
     * Returns the key coercion types.
     * @param descList a list of descriptors
     * @return key coercion types
     */
    public static Class[] getCoercionTypes(Collection<JoinedPropDesc> descList)
    {
        Class[] result = new Class[descList.size()];
        int count = 0;
        for (JoinedPropDesc desc : descList)
        {
            result[count++] = desc.getCoercionType();
        }
        return result;
    }

    /**
     * Returns the key coercion types.
     * @param descList a list of descriptors
     * @return key coercion types
     */
    public static Class[] getCoercionTypes(JoinedPropDesc[] descList)
    {
        Class[] result = new Class[descList.length];
        int count = 0;
        for (JoinedPropDesc desc : descList)
        {
            result[count++] = desc.getCoercionType();
        }
        return result;
    }
}
