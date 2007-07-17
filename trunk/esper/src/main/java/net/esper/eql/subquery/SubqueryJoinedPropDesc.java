package net.esper.eql.subquery;

import java.util.Collection;

/**
 * Holds property information for joined properties in a subquery.
 */
public class SubqueryJoinedPropDesc
{
    private String indexPropName;
    private Class coercionType;
    private String keyPropName;
    private Integer keyStreamId;

    /**
     * Ctor.
     * @param indexPropName is the property name of the indexed field
     * @param coercionType is the type to coerce to
     * @param keyPropName is the property name of the key field
     * @param keyStreamId is the stream number of the key field
     */
    public SubqueryJoinedPropDesc(String indexPropName, Class coercionType, String keyPropName, Integer keyStreamId)
    {
        this.indexPropName = indexPropName;
        this.coercionType = coercionType;
        this.keyPropName = keyPropName;
        this.keyStreamId = keyStreamId;
    }

    /**
     * Returns the property name of the indexed field.
     * @return property name of indexed field
     */
    public String getIndexPropName()
    {
        return indexPropName;
    }

    /**
     * Returns the coercion type of key to index field.
     * @return type to coerce to
     */
    public Class getCoercionType()
    {
        return coercionType;
    }

    /**
     * Returns the property name of the key field.
     * @return property name of key field
     */
    public String getKeyPropName()
    {
        return keyPropName;
    }

    /**
     * Returns the stream id of the key field.
     * @return stream id
     */
    public Integer getKeyStreamId()
    {
        return keyStreamId;
    }

    /**
     * Returns the key stream numbers.
     * @param descList a list of descriptors
     * @return key stream numbers
     */
    public static int[] getKeyStreamNums(Collection<SubqueryJoinedPropDesc> descList)
    {
        int[] streamIds = new int[descList.size()];
        int count = 0;
        for (SubqueryJoinedPropDesc desc : descList)
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
    public static String[] getKeyProperties(Collection<SubqueryJoinedPropDesc> descList)
    {
        String[] result = new String[descList.size()];
        int count = 0;
        for (SubqueryJoinedPropDesc desc : descList)
        {
            result[count++] = desc.getKeyPropName();
        }
        return result;
    }

    /**
     * Returns the key coercion types.
     * @param descList a list of descriptors
     * @return key coercion types
     */
    public static Class[] getCoercionTypes(Collection<SubqueryJoinedPropDesc> descList)
    {
        Class[] result = new Class[descList.size()];
        int count = 0;
        for (SubqueryJoinedPropDesc desc : descList)
        {
            result[count++] = desc.getCoercionType();
        }
        return result;
    }
}
