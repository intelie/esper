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

package com.espertech.esper.regression.rowrecog;

import com.espertech.esper.client.*;
import com.espertech.esper.client.soda.EPStatementObjectModel;
import com.espertech.esper.client.soda.MatchRecognizeRegEx;
import com.espertech.esper.core.soda.SODAAnalyzer;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.List;

public class TestRowPatternRecognitionDataSet extends TestCase
{
    private static final Log log = LogFactory.getLog(TestRowPatternRecognitionDataSet.class);

    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventType("MyEvent", SupportRecogBean.class);
        config.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testExampleFinancialWPattern()
    {
        String text = "select * " +
                "from SupportBean " +
                "match_recognize (" +
                " measures A.string as beginA, last(Z.string) as lastZ" +
                " all matches" +
                " after match skip to current row" +
                " pattern (A W+ X+ Y+ Z+)" +
                " define" +
                " W as W.intPrimitive < prev(W.intPrimitive)," +
                " X as X.intPrimitive > prev(X.intPrimitive)," +
                " Y as Y.intPrimitive < prev(Y.intPrimitive)," +
                " Z as Z.intPrimitive > prev(Z.intPrimitive)" +
                ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(text);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        Object[][] data = new Object[][] {
                {"E1",8},   // 0
                {"E2",8},
                {"E3",8},       // A
                {"E4",6},       // W
                {"E5",3},       // W
                {"E6",7},
                {"E7",6},
                {"E8",2},
                {"E9",6,        // Z
                  new String[] {"beginA=E3,lastZ=E9", "beginA=E4,lastZ=E9"}},
                {"E10",2},
                {"E11",9,  // 10
                  new String[] {"beginA=E6,lastZ=E11", "beginA=E7,lastZ=E11"}},
                {"E12",9},
                {"E13",8},
                {"E14",5},
                {"E15",0},
                {"E16",9},
                {"E17",2},
                {"E18",0},
                {"E19",2,
                  new String[] {"beginA=E12,lastZ=E19", "beginA=E13,lastZ=E19", "beginA=E14,lastZ=E19"}},
                {"E20",3,
                  new String[] {"beginA=E12,lastZ=E20", "beginA=E13,lastZ=E20", "beginA=E14,lastZ=E20"}},
                {"E21",8,
                  new String[] {"beginA=E12,lastZ=E21", "beginA=E13,lastZ=E21", "beginA=E14,lastZ=E21"}},
                {"E22",5},
                {"E23",9,
                  new String[] {"beginA=E16,lastZ=E23", "beginA=E17,lastZ=E23"}},
                {"E24",9},
                {"E25",4},
                {"E26",7},
                {"E27",2},
                {"E28",8,
                  new String[] {"beginA=E24,lastZ=E28"}},
                {"E29",0},
                {"E30",4,
                  new String[] {"beginA=E26,lastZ=E30"}},
                {"E31",4},
                {"E32",7},
                {"E33",8},
                {"E34",6},
                {"E35",4},
                {"E36",5},
                {"E37",1},
                {"E38",7,
                  new String[] {"beginA=E33,lastZ=E38", "beginA=E34,lastZ=E38"}},
                {"E39",5},
                {"E40",8,
                  new String[] {"beginA=E36,lastZ=E40"}},
                {"E41",6},
                {"E42",6},
                {"E43",0},
                {"E44",6},
                {"E45",8},
                {"E46",4},
                {"E47",3},
                {"E48",8,
                  new String[] {"beginA=E42,lastZ=E48"}},
                {"E49",2},
                {"E50",5,
                  new String[] {"beginA=E45,lastZ=E50", "beginA=E46,lastZ=E50"}},
                {"E51",3},
                {"E52",3},
                {"E53",9},
                {"E54",8},
                {"E55",5},
                {"E56",5},
                {"E57",9},
                {"E58",7},
                {"E59",3},
                {"E60",3}
        };

        int rowCount = 0;
        for (Object[] row : data)
        {
            SupportBean event = new SupportBean((String) row[0], (Integer) row[1]);
            epService.getEPRuntime().sendEvent(event);

            compare(row, rowCount, event, listener);
            rowCount++;
        }

        stmt.destroy();
        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(text);
        assertEquals(text, model.toEPL());
        stmt = epService.getEPAdministrator().create(model);
        stmt.addListener(listener);
        assertEquals(text, stmt.getText());
        List<MatchRecognizeRegEx> regexs = SODAAnalyzer.analyzeModelMatchRecogRegexs(model);
        assertEquals(1, regexs.size());
        
        for (Object[] row : data)
        {
            SupportBean event = new SupportBean((String) row[0], (Integer) row[1]);
            epService.getEPRuntime().sendEvent(event);

            compare(row, rowCount, event, listener);
            rowCount++;
        }
    }

    public void testExampleWithPREV()
    {
        String query = "SELECT * " +
            "FROM MyEvent.win:keepall()" +
            "   MATCH_RECOGNIZE (" +
            "       MEASURES A.string AS a_string," +
            "         A.value AS a_value," +
            "         B.string AS b_string," +
            "         B.value AS b_value," +
            "         C[0].string AS c0_string," +
            "         C[0].value AS c0_value," +
            "         C[1].string AS c1_string," +
            "         C[1].value AS c1_value," +
            "         C[2].string AS c2_string," +
            "         C[2].value AS c2_value," +
            "         D.string AS d_string," +
            "         D.value AS d_value," +
            "         E[0].string AS e0_string," +
            "         E[0].value AS e0_value," +
            "         E[1].string AS e1_string," +
            "         E[1].value AS e1_value," +
            "         F[0].string AS f0_string," +
            "         F[0].value AS f0_value," +
            "         F[1].string AS f1_string," +
            "         F[1].value AS f1_value" +
            "       ALL MATCHES" +
            "       after match skip to current row" +
            "       PATTERN ( A B C* D E* F+ )" +
            "       DEFINE /* A is unspecified, defaults to TRUE, matches any row */" +
            "            B AS (B.value < PREV (B.value))," +
            "            C AS (C.value <= PREV (C.value))," +
            "            D AS (D.value < PREV (D.value))," +
            "            E AS (E.value >= PREV (E.value))," +
            "            F AS (F.value >= PREV (F.value) and F.value > A.value)" +
            ")";

        EPStatement stmt = epService.getEPAdministrator().createEPL(query);
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        Object[][] data = new Object[][] {
                {"E1", 100, null},
                {"E2", 98, null},
                {"E3", 75, null},
                {"E4", 61, null},
                {"E5", 50, null},
                {"E6", 49, null},
                {"E7", 64,
                  new String[] {"a_string=E4,a_value=61,b_string=E5,b_value=50,c0_string=null,c0_value=null,c1_string=null,c1_value=null,c2_string=null,c2_value=null,d_string=E6,d_value=49,e0_string=null,e0_value=null,e1_string=null,e1_value=null,f0_string=E7,f0_value=64,f1_string=null,f1_value=null"}},
                {"E8", 78,
                  new String[] {"a_string=E3,a_value=75,b_string=E4,b_value=61,c0_string=E5,c0_value=50,c1_string=null,c1_value=null,c2_string=null,c2_value=null,d_string=E6,d_value=49,e0_string=E7,e0_value=64,e1_string=null,e1_value=null,f0_string=E8,f0_value=78,f1_string=null,f1_value=null",
                                "a_string=E4,a_value=61,b_string=E5,b_value=50,c0_string=null,c0_value=null,c1_string=null,c1_value=null,c2_string=null,c2_value=null,d_string=E6,d_value=49,e0_string=E7,e0_value=64,e1_string=null,e1_value=null,f0_string=E8,f0_value=78,f1_string=null,f1_value=null",
                                "a_string=E4,a_value=61,b_string=E5,b_value=50,c0_string=null,c0_value=null,c1_string=null,c1_value=null,c2_string=null,c2_value=null,d_string=E6,d_value=49,e0_string=null,e0_value=null,e1_string=null,e1_value=null,f0_string=E7,f0_value=64,f1_string=E8,f1_value=78"}},
                {"E9", 84,
                  new String[] {"a_string=E3,a_value=75,b_string=E4,b_value=61,c0_string=E5,c0_value=50,c1_string=null,c1_value=null,c2_string=null,c2_value=null,d_string=E6,d_value=49,e0_string=E7,e0_value=64,e1_string=null,e1_value=null,f0_string=E8,f0_value=78,f1_string=E9,f1_value=84",
                                "a_string=E3,a_value=75,b_string=E4,b_value=61,c0_string=E5,c0_value=50,c1_string=null,c1_value=null,c2_string=null,c2_value=null,d_string=E6,d_value=49,e0_string=E7,e0_value=64,e1_string=E8,e1_value=78,f0_string=E9,f0_value=84,f1_string=null,f1_value=null",                                
                                "a_string=E4,a_value=61,b_string=E5,b_value=50,c0_string=null,c0_value=null,c1_string=null,c1_value=null,c2_string=null,c2_value=null,d_string=E6,d_value=49,e0_string=E7,e0_value=64,e1_string=E8,e1_value=78,f0_string=E9,f0_value=84,f1_string=null,f1_value=null",
                                "a_string=E4,a_value=61,b_string=E5,b_value=50,c0_string=null,c0_value=null,c1_string=null,c1_value=null,c2_string=null,c2_value=null,d_string=E6,d_value=49,e0_string=E7,e0_value=64,e1_string=null,e1_value=null,f0_string=E8,f0_value=78,f1_string=E9,f1_value=84",
                                "a_string=E4,a_value=61,b_string=E5,b_value=50,c0_string=null,c0_value=null,c1_string=null,c1_value=null,c2_string=null,c2_value=null,d_string=E6,d_value=49,e0_string=null,e0_value=null,e1_string=null,e1_value=null,f0_string=E7,f0_value=64,f1_string=E8,f1_value=78"
                  }},
                };

        int rowCount = 0;
        for (Object[] row : data)
        {
            rowCount++;
            SupportRecogBean event = new SupportRecogBean((String) row[0], (Integer) row[1]);
            epService.getEPRuntime().sendEvent(event);

            compare(row, rowCount, event, listener);
            rowCount++;
        }

        stmt.destroy();
    }

    private static void compare(Object[] row, int rowCount, Object event, SupportUpdateListener listener)
    {
        if (row.length < 3 || row[2] == null)
        {
            if (listener.isInvoked())
            {
                EventBean[] matches = listener.getLastNewData();
                if (matches != null)
                {
                    for (int i = 0; i < matches.length; i++)
                    {
                        log.info("Received matches: " + getProps(matches[i]));
                    }
                }
            }
            assertFalse("For event " +event + " row " + rowCount, listener.isInvoked());
            return;
        }

        String[] expected = (String[]) row[2];

        EventBean[] matches = listener.getLastNewData();
        String[] matchesText = null;
        if (matches != null)
        {
            matchesText = new String[matches.length];
            for (int i = 0; i < matches.length; i++)
            {
                matchesText[i] = getProps(matches[i]);
                log.debug(getProps(matches[i]));
            }
        }
        else
        {
            if (expected != null)
            {
                log.info("Received no matches but expected: ");
                for (int i = 0; i < expected.length; i++)
                {
                    log.info(expected[i]);
                }
                Assert.fail();
            }
        }

        Arrays.sort(expected);
        Arrays.sort(matchesText);

        assertEquals("For event " + event, matches.length, expected.length);
        for (int i = 0; i < expected.length; i++)
        {
            if (!expected[i].equals(matchesText[i]))
            {
                log.info("expected:" + expected[i]);
                log.info("  actual:" + expected[i]);
                assertEquals("Sending event " + event + " row " + rowCount, expected[i], matchesText[i]);
            }
        }

        listener.reset();
    }

    private static String getProps(EventBean event)
    {
        StringBuilder buf = new StringBuilder();
        String delimiter = "";
        for (EventPropertyDescriptor prop : event.getEventType().getPropertyDescriptors())
        {
            buf.append(delimiter);
            buf.append(prop.getPropertyName());
            buf.append("=");
            buf.append(event.get(prop.getPropertyName()));
            delimiter = ",";
        }
        return buf.toString();
    }
}
