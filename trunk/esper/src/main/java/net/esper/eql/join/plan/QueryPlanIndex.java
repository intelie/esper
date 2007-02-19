package net.esper.eql.join.plan;

import java.util.Arrays;

/**
 * Specifies an index to build as part of an overall query plan.
 */
public class QueryPlanIndex
{
    private String[][] indexProps;

    /**
     * Ctor.
     * @param indexProps - array of property names with the first dimension suplying the number of
     * distinct indexes. The second dimension can be empty and indicates a full table scan.
     */
    public QueryPlanIndex(String[][] indexProps)
    {
        if ((indexProps == null) || (indexProps.length == 0))
        {
            throw new IllegalArgumentException("Null or empty index properites parameter is supplied, expecting at least one entry");
        }
        this.indexProps = indexProps;
    }

    /**
     * Returns property names of all indexes.
     * @return property names array
     */
    public String[][] getIndexProps()
    {
        return indexProps;
    }

    /**
     * Find a matching index for the property names supplied.
     * @param indexFields - property names to search for
     * @return -1 if not found, or offset within indexes if found
     */
    protected int getIndexNum(String[] indexFields)
    {
        for (int i = 0; i < indexProps.length; i++)
        {
            if (Arrays.equals(indexFields, indexProps[i]))
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Add an index specification element.
     * @param indexProperties - list of property names to index
     * @return number indicating position of index that was added
     */
    public int addIndex(String[] indexProperties)
    {
        int numElements = indexProps.length;
        String[][] newProps = new String[numElements + 1][];
        System.arraycopy(indexProps, 0, newProps, 0, numElements);
        newProps[numElements] = indexProperties;

        indexProps = newProps;

        return numElements;
    }

    public String toString()
    {
        if (indexProps == null)
        {
            return "indexProperties=null";
        }

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < indexProps.length; i++)
        {
            buf.append("indexProperties(").append(i).append(")=").append(Arrays.toString(indexProps[i])).append(' ');
        }

        return buf.toString();
    }

    /**
     * Print index specifications in readable format.
     * @param indexSpecs - define indexes
     * @return readable format of index info
     */
    @SuppressWarnings({"StringConcatenationInsideStringBufferAppend"})
    public static String print(QueryPlanIndex[] indexSpecs)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("QueryPlanIndex[]\n");

        for (int i = 0; i < indexSpecs.length; i++)
        {
            buffer.append("  index spec " + i + " : " + indexSpecs[i].toString() + '\n');
        }

        return buffer.toString();
    }
}
