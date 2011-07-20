/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.util;


import junit.framework.TestCase;

import java.util.Map;
import java.util.HashMap;
import java.sql.Types;
import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestSQLTypeMapUtil extends TestCase
{
    public void testMapping()
    {
        Map<Integer, Class> testData = new HashMap<Integer, Class>();
        testData.put(Types.CHAR, String.class);
        testData.put(Types.VARCHAR, String.class);
        testData.put(Types.LONGVARCHAR, String.class);
        testData.put(Types.NUMERIC, BigDecimal.class);
        testData.put(Types.DECIMAL, BigDecimal.class);
        testData.put(Types.BIT, Boolean.class);
        testData.put(Types.BOOLEAN, Boolean.class);
        testData.put(Types.TINYINT, Byte.class);
        testData.put(Types.SMALLINT, Short.class);
        testData.put(Types.INTEGER, Integer.class);
        testData.put(Types.BIGINT, Long.class);
        testData.put(Types.REAL, Float.class);
        testData.put(Types.FLOAT, Double.class);
        testData.put(Types.DOUBLE, Double.class);
        testData.put(Types.BINARY, byte[].class );
        testData.put(Types.VARBINARY, byte[].class);
        testData.put(Types.LONGVARBINARY, byte[].class);
        testData.put(Types.DATE, java.sql.Date.class);
        testData.put(Types.TIMESTAMP, java.sql.Timestamp.class);
        testData.put(Types.TIME, java.sql.Time.class);
        testData.put(Types.CLOB, java.sql.Clob.class);
        testData.put(Types.BLOB, java.sql.Blob.class );
        testData.put(Types.ARRAY, java.sql.Array.class);
        testData.put(Types.STRUCT, java.sql.Struct.class);
        testData.put(Types.REF, java.sql.Ref.class);
        testData.put(Types.DATALINK, java.net.URL.class);

        for (int type : testData.keySet())
        {
            Class result = SQLTypeMapUtil.sqlTypeToClass(type, null);
            log.debug(".testMapping Mapping " + type + " to " + result.getSimpleName());
            assertEquals(testData.get(type), result);
        }

        assertEquals(String.class, SQLTypeMapUtil.sqlTypeToClass(Types.JAVA_OBJECT, "java.lang.String"));
        assertEquals(String.class, SQLTypeMapUtil.sqlTypeToClass(Types.DISTINCT, "java.lang.String"));
    }

    public void testMappingInvalid()
    {
        tryInvalid(Types.JAVA_OBJECT, null);
        tryInvalid(Types.JAVA_OBJECT, "xx");
        tryInvalid(Types.DISTINCT, null);
        tryInvalid(Integer.MAX_VALUE, "yy");
    }

    private void tryInvalid(int type, String classname)
    {
        try
        {
            SQLTypeMapUtil.sqlTypeToClass(type, classname);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            // expected
        }
    }

    private static final Log log = LogFactory.getLog(TestSQLTypeMapUtil.class);
}
