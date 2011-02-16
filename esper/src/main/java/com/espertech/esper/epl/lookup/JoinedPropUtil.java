package com.espertech.esper.epl.lookup;

import java.util.Collection;

public class JoinedPropUtil {

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
