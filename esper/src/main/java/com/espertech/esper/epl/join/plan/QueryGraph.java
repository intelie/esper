/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.join.plan;

import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.expression.ExprIdentNode;
import com.espertech.esper.epl.expression.ExprIdentNodeImpl;
import com.espertech.esper.epl.expression.ExprNode;
import com.espertech.esper.type.RelationalOpEnum;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Model of relationships between streams based on properties in both streams that are
 * specified as equal in a filter expression.
 */
public class QueryGraph
{
    private final int numStreams;
    private final Map<QueryGraphKey, QueryGraphValue> streamJoinMap;

    /**
     * Ctor.
     * @param numStreams - number of streams
     */
    public QueryGraph(int numStreams)
    {
        this.numStreams = numStreams;
        streamJoinMap = new HashMap<QueryGraphKey, QueryGraphValue>();
    }

    /**
     * Returns the number of streams.
     * @return number of streams
     */
    public int getNumStreams()
    {
        return numStreams;
    }

    /**
     * Add properties for 2 streams that are equal.
     * @param streamLeft - left hand stream
     * @param propertyLeft - left hand stream property
     * @param streamRight - right hand stream
     * @param propertyRight - right hand stream property
     * @return true if added and did not exist, false if already known
     */
    public boolean addStrictEquals(int streamLeft, String propertyLeft, ExprIdentNode nodeLeft, int streamRight, String propertyRight, ExprIdentNode nodeRight)
    {
        check(streamLeft, streamRight);
        if (propertyLeft == null || propertyRight == null)
        {
            throw new IllegalArgumentException("Null property names supplied");
        }

        if (streamLeft == streamRight)
        {
            throw new IllegalArgumentException("Streams supplied are the same");
        }

        boolean addedLeft = addInteral(streamLeft, propertyLeft, nodeLeft, streamRight, propertyRight, nodeRight);
        boolean addedRight = addInteral(streamRight, propertyRight, nodeRight, streamLeft, propertyLeft, nodeLeft);
        return addedLeft || addedRight;
    }

    private boolean addInteral(int streamLookup, String propertyLookup, ExprIdentNode propertyLookupNode, int streamIndexed, String propertyIndexed, ExprIdentNode propertyIndexedNode) {
        QueryGraphKey key = new QueryGraphKey(streamLookup, streamIndexed);
        QueryGraphValue value = streamJoinMap.get(key);

        if (value == null)
        {
            value = new QueryGraphValue();
            streamJoinMap.put(key, value);
        }

        return value.addStrictCompare(propertyLookup, propertyLookupNode, propertyIndexed, propertyIndexedNode);
    }

    public boolean isNavigableAtAll(int streamFrom, int streamTo)
    {
        QueryGraphKey key = new QueryGraphKey(streamFrom, streamTo);
        QueryGraphValue value = streamJoinMap.get(key);
        if (value == null) {
            return false;
        }
        return !value.getEntries().isEmpty();
    }

    /**
     * Returns set of streams that the given stream is navigable to.
     * @param streamFrom - from stream number
     * @return set of streams related to this stream, or empty set if none
     */
    public Set<Integer> getNavigableStreams(int streamFrom)
    {
        Set<Integer> result = new HashSet<Integer>();
        for (int i = 0; i < numStreams; i++)
        {
            if (isNavigableAtAll(streamFrom, i))
            {
                result.add(i);
            }
        }
        return result;
    }

    private QueryGraphValue getCreateValue(int streamKey, int streamValue) {
        check(streamValue, streamKey);
        QueryGraphKey key = new QueryGraphKey(streamKey, streamValue);
        QueryGraphValue value = streamJoinMap.get(key);
        if (value == null) {
            value = new QueryGraphValue();
            streamJoinMap.put(key, value);
        }
        return value;
    }

    public QueryGraphValue getGraphValue(int streamLookup, int streamIndexed) {
        QueryGraphKey key = new QueryGraphKey(streamLookup, streamIndexed);
        QueryGraphValue value = streamJoinMap.get(key);
        if (value != null) {
            return value;
        }
        return new QueryGraphValue();
    }

    /**
     * Returns index properties.
     * @param streamLookup - stream to serve as source for looking up events
     * @param streamIndexed - stream to look up in
     * @return index property names
    public String[] getIndexProperties(int streamLookup, int streamIndexed)
    {
        QueryGraphKey key = new QueryGraphKey(streamLookup, streamIndexed);
        QueryGraphValue value = streamJoinMap.get(key);

        if (value == null)
        {
            return null;
        }

        return value.getPropertiesValue().toArray(new String[value.getPropertiesValue().size()]);
    }
     */

    /*
    public String[] getRangeProperties(int streamLookup, int streamIndexed)
    {
        QueryGraphKey key = new QueryGraphKey(streamLookup, streamIndexed);
        QueryGraphValue value = streamJoinMap.get(key);

        if (value == null)
        {
            return null;
        }

        return value.getRangeEntriesValueProperties();
    }
    */

    /**
     * Returns key properties.
     * @param streamLookup - stream to serve as source for looking up events
     * @param streamIndexed - stream to look up in
     * @return key property names
    public String[] getKeyProperties(int streamLookup, int streamIndexed)
    {
        QueryGraphKey key = new QueryGraphKey(streamLookup, streamIndexed);
        QueryGraphValue value = streamJoinMap.get(key);

        if (value == null)
        {
            return null;
        }

        return value.getPropertiesKey().toArray(new String[value.getPropertiesKey().size()]);
    }
     */

    /**
     * Fill in equivalent key properties (navigation entries) on all streams.
     * For example, if  a=b and b=c  then addRelOpInternal a=c. The method adds new equalivalent key properties
     * until no additional entries to be added are found, ie. several passes can be made.
     * @param queryGraph - navigablity info between streamss
     */
    public static void fillEquivalentNav(EventType[] typesPerStream, QueryGraph queryGraph)
    {
        boolean addedEquivalency;

        // Repeat until no more entries were added
        do
        {
            addedEquivalency = false;

            // For each stream-to-stream combination
            for (int lookupStream = 0; lookupStream < queryGraph.numStreams; lookupStream++)
            {
                for (int indexedStream = 0; indexedStream < queryGraph.numStreams; indexedStream++)
                {
                    if (lookupStream == indexedStream)
                    {
                        continue;
                    }

                    boolean added = fillEquivalentNav(typesPerStream, queryGraph, lookupStream, indexedStream);
                    if (added)
                    {
                        addedEquivalency = true;
                    }
                }
            }
        }
        while (addedEquivalency);
    }

    /*
     * Looks at the key and index (aka. left and right) properties of the 2 streams and checks
     * for each property if any equivalent index properties exist for other streams.
     */
    private static boolean fillEquivalentNav(EventType[] typesPerStream, QueryGraph queryGraph, int lookupStream, int indexedStream)
    {
        boolean addedEquivalency = false;

        QueryGraphValue value = queryGraph.getGraphValue(lookupStream, indexedStream);
        if (value.getEntries().isEmpty()) {
            return false;
        }

        QueryGraphValuePairHashKeyIndex hashKeys = value.getHashKeyProps();
        String[] strictKeyProps = hashKeys.getStrictKeys();
        String[] indexProps = hashKeys.getIndexed();

        if (strictKeyProps.length == 0)
        {
            return false;
        }
        if (strictKeyProps.length != indexProps.length)
        {
            throw new IllegalStateException("Unexpected key and index property number mismatch");
        }

        for (int i = 0; i < strictKeyProps.length; i++)
        {
            if (strictKeyProps[i] == null) {
                continue;   // not a strict key
            }

            boolean added = fillEquivalentNav(typesPerStream, queryGraph, lookupStream, strictKeyProps[i], indexedStream, indexProps[i]);
            if (added)
            {
                addedEquivalency = true;
            }
        }

        return addedEquivalency;
    }

    /*
     * Looks at the key and index (aka. left and right) properties of the 2 streams and checks
     * for each property if any equivalent index properties exist for other streams.
     *
     * Example:  s0.p0 = s1.p1  and  s1.p1 = s2.p2  ==> therefore s0.p0 = s2.p2
     * ==> look stream s0, property p0; indexed stream s1, property p1
     * Is there any other lookup stream that has stream 1 and property p1 as index property? ==> this is stream s2, p2
     * Add navigation entry between stream s0 and property p0 to stream s2, property p2
     */
    private static boolean fillEquivalentNav(EventType[] typesPerStream, QueryGraph queryGraph, int lookupStream, String keyProp, int indexedStream, String indexProp)
    {
        boolean addedEquivalency = false;

        for (int otherStream = 0; otherStream < queryGraph.numStreams; otherStream++)
        {
            if ((otherStream == lookupStream) || (otherStream == indexedStream))
            {
                continue;
            }

            QueryGraphValue value = queryGraph.getGraphValue(otherStream, indexedStream);
            QueryGraphValuePairHashKeyIndex hashKeys = value.getHashKeyProps();

            String[] otherStrictKeyProps = hashKeys.getStrictKeys();
            String[] otherIndexProps = hashKeys.getIndexed();
            int otherPropertyNum = -1;

            if (otherIndexProps == null)
            {
                continue;
            }

            for (int i = 0; i < otherIndexProps.length; i++)
            {
                if (otherIndexProps[i].equals(indexProp))
                {
                    otherPropertyNum = i;
                    break;
                }
            }

            if (otherPropertyNum != -1)
            {
                if (otherStrictKeyProps[otherPropertyNum] != null) {
                    ExprIdentNode identNodeLookup = new ExprIdentNodeImpl(typesPerStream[lookupStream], keyProp, lookupStream);
                    ExprIdentNode identNodeOther = new ExprIdentNodeImpl(typesPerStream[otherStream], otherStrictKeyProps[otherPropertyNum], otherStream);
                    boolean added = queryGraph.addStrictEquals(lookupStream, keyProp, identNodeLookup, otherStream, otherStrictKeyProps[otherPropertyNum], identNodeOther);
                    if (added)
                    {
                        addedEquivalency = true;
                    }
                }
            }
        }

        return addedEquivalency;
    }

    public String toString()
    {
        StringWriter buf = new StringWriter();
        PrintWriter writer = new PrintWriter(buf);

        int count = 0;
        for (Map.Entry<QueryGraphKey, QueryGraphValue> entry : streamJoinMap.entrySet())
        {
            count++;
            writer.println("Entry " + count + ": key=" + entry.getKey());
            writer.println("  value=" + entry.getValue());
        }

        return buf.toString();
    }

    public void addRangeStrict(int streamNumStart, String propertyStart, ExprIdentNode propertyStartExpr,
                               int streamNumEnd, String propertyEnd, ExprIdentNode propertyEndExpr,
                               int streamNumValue, String propertyValue, ExprIdentNode propertyValueExpr,
                               boolean includeStart, boolean includeEnd, boolean isInverted) {
        check(streamNumStart, streamNumValue);
        check(streamNumEnd, streamNumValue);

        // add as a range if the endpoints are from the same stream
        if (streamNumStart == streamNumEnd && streamNumStart != streamNumValue) {
            QueryGraphRangeEnum rangeOp = QueryGraphRangeEnum.getRangeOp(includeStart, includeEnd, isInverted);
            QueryGraphValue valueLeft = getCreateValue(streamNumStart, streamNumValue);
            valueLeft.addRange(rangeOp, propertyStartExpr, propertyEndExpr, propertyValue);

            QueryGraphValue valueRight = getCreateValue(streamNumValue, streamNumStart);
            valueRight.addRelOp(propertyValueExpr, QueryGraphRangeEnum.GREATER_OR_EQUAL, propertyEnd, false);
            valueRight.addRelOp(propertyValueExpr, QueryGraphRangeEnum.LESS_OR_EQUAL, propertyStart, false);
        }
        else {
            // endpoints from a different stream, add individually
            if (streamNumValue != streamNumStart) {
                QueryGraphValue valueStart = getCreateValue(streamNumStart, streamNumValue);
                valueStart.addRelOp(propertyStartExpr, QueryGraphRangeEnum.GREATER_OR_EQUAL, propertyValue, true); // read propertyValue >= propertyStart

                QueryGraphValue valueStartReversed = getCreateValue(streamNumValue, streamNumStart);
                valueStartReversed.addRelOp(propertyValueExpr, QueryGraphRangeEnum.LESS_OR_EQUAL, propertyStart, true);  // read propertyStart <= propertyValue
            }

            if (streamNumValue != streamNumEnd) {
                QueryGraphValue valueEnd = getCreateValue(streamNumEnd, streamNumValue);
                valueEnd.addRelOp(propertyEndExpr, QueryGraphRangeEnum.LESS_OR_EQUAL, propertyValue, true);   // read propertyValue <= propertyEnd

                QueryGraphValue valueEndReversed = getCreateValue(streamNumValue, streamNumEnd);
                valueEndReversed.addRelOp(propertyValueExpr, QueryGraphRangeEnum.GREATER_OR_EQUAL, propertyEnd, true); // read propertyEnd >= propertyValue
            }
        }
    }

    public void addRelationalOpStrict(int streamIdLeft, String propertyLeft, ExprNode propertyLeftExpr,
                                      int streamIdRight, String propertyRight, ExprNode propertyRightExpr,
                                      RelationalOpEnum relationalOpEnum) {
        check(streamIdLeft, streamIdRight);
        QueryGraphValue valueLeft = getCreateValue(streamIdLeft, streamIdRight);
        valueLeft.addRelOp(propertyLeftExpr, QueryGraphRangeEnum.mapFrom(relationalOpEnum.reversed()), propertyRight, false);

        QueryGraphValue valueRight = getCreateValue(streamIdRight, streamIdLeft);
        valueRight.addRelOp(propertyRightExpr, QueryGraphRangeEnum.mapFrom(relationalOpEnum), propertyLeft, false);
    }

    public void addUnkeyedExpression(int indexedStream, String indexedProp, ExprNode exprNodeNoIdent) {
        if (indexedStream < 0 || indexedStream >= numStreams) {
            throw new IllegalArgumentException("Invalid indexed stream " + indexedStream);
        }

        for (int i = 0; i < numStreams; i++) {
            if (i != indexedStream) {
                QueryGraphValue value = getCreateValue(i, indexedStream);
                value.addUnkeyedExpr(indexedProp, exprNodeNoIdent);
            }
        }
    }

    public void addKeyedExpression(int indexedStream, String indexedProp, int keyExprStream, ExprNode exprNodeNoIdent) {
        check(indexedStream, keyExprStream);
        QueryGraphValue value = getCreateValue(keyExprStream, indexedStream);
        value.addKeyedExpr(indexedProp, exprNodeNoIdent);
    }

    private void check(int indexedStream, int keyStream) {
        if (indexedStream < 0 || indexedStream >= numStreams) {
            throw new IllegalArgumentException("Invalid indexed stream " + indexedStream);
        }
        if (keyStream < 0 || keyStream >= numStreams) {
            throw new IllegalArgumentException("Invalid key stream " + keyStream);
        }
        if (keyStream == indexedStream) {
            throw new IllegalArgumentException("Invalid key stream equals indexed stream " + keyStream);
        }
    }

    public void addRangeExpr(int indexedStream, String indexedProp, ExprNode startNode, Integer optionalStartStreamNum, ExprNode endNode, Integer optionalEndStreamNum) {
        if (optionalStartStreamNum == null && optionalEndStreamNum == null) {
            for (int i = 0; i < numStreams; i++) {
                if (i == indexedStream) {
                    continue;
                }
                QueryGraphValue value = getCreateValue(i, indexedStream);
                value.addRange(QueryGraphRangeEnum.RANGE_CLOSED, startNode, endNode, indexedProp);
            }
            return;
        }

        optionalStartStreamNum = optionalStartStreamNum != null ? optionalStartStreamNum : -1;
        optionalEndStreamNum = optionalEndStreamNum != null ? optionalEndStreamNum : -1;

            // add for a specific stream only
        if (optionalStartStreamNum.equals(optionalEndStreamNum) || optionalEndStreamNum.equals(-1)) {
            QueryGraphValue value = getCreateValue(optionalStartStreamNum, indexedStream);
            value.addRange(QueryGraphRangeEnum.RANGE_CLOSED, startNode, endNode, indexedProp);
        }
        if (optionalStartStreamNum.equals(-1)) {
            QueryGraphValue value = getCreateValue(optionalEndStreamNum, indexedStream);
            value.addRange(QueryGraphRangeEnum.RANGE_CLOSED, startNode, endNode, indexedProp);
        }
    }

    public void addRelationalOp(int indexedStream, String indexedProp, Integer keyStreamNum, ExprNode exprNodeNoIdent, RelationalOpEnum relationalOpEnum) {
        if (keyStreamNum == null) {
            for (int i = 0; i < numStreams; i++) {
                if (i == indexedStream) {
                    continue;
                }
                QueryGraphValue value = getCreateValue(i, indexedStream);
                value.addRelOp(exprNodeNoIdent, QueryGraphRangeEnum.mapFrom(relationalOpEnum), indexedProp, false);
            }
            return;
        }

        // add for a specific stream only
        QueryGraphValue value = getCreateValue(keyStreamNum, indexedStream);
        value.addRelOp(exprNodeNoIdent, QueryGraphRangeEnum.mapFrom(relationalOpEnum), indexedProp, false);
    }
}
