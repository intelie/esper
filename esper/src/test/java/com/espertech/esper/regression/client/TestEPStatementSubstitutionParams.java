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

package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;

public class TestEPStatementSubstitutionParams extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listenerOne;
    private SupportUpdateListener listenerTwo;

    public void setUp()
    {
        listenerOne = new SupportUpdateListener();
        listenerTwo = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testPattern()
    {
        String stmt = SupportBean.class.getName() + "(string=?)";
        EPPreparedStatement prepared = epService.getEPAdministrator().preparePattern(stmt);

        prepared.setObject(1, "e1");
        EPStatement statement = epService.getEPAdministrator().create(prepared);
        statement.addListener(listenerOne);
        assertEquals("select * from pattern [com.espertech.esper.support.bean.SupportBean(string = \"e1\")]", statement.getText());

        prepared.setObject(1, "e2");
        statement = epService.getEPAdministrator().create(prepared);
        statement.addListener(listenerTwo);
        assertEquals("select * from pattern [com.espertech.esper.support.bean.SupportBean(string = \"e2\")]", statement.getText());

        epService.getEPRuntime().sendEvent(new SupportBean("e2", 10));
        assertFalse(listenerOne.isInvoked());
        assertTrue(listenerTwo.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("e1", 10));
        assertFalse(listenerTwo.isInvoked());
        assertTrue(listenerOne.getAndClearIsInvoked());

        statement.destroy();
        prepared = epService.getEPAdministrator().prepareEPL("create window MyWindow.win:time(?) as " + SupportBean.class.getName());
        prepared.setObject(1, 300);
        statement = epService.getEPAdministrator().create(prepared);
        assertEquals("create window MyWindow.win:time(300) as select * from com.espertech.esper.support.bean.SupportBean", statement.getText());
    }

    public void testSubselect()
    {
        String stmtText = "select (" +
           "select symbol from " + SupportMarketDataBean.class.getName() + "(symbol=?).std:lastevent()) as mysymbol from " +
                SupportBean.class.getName();

        EPPreparedStatement preparedStmt = epService.getEPAdministrator().prepareEPL(stmtText);

        preparedStmt.setObject(1, "S1");
        EPStatement stmtS1 = epService.getEPAdministrator().create(preparedStmt);
        stmtS1.addListener(listenerOne);

        preparedStmt.setObject(1, "S2");
        EPStatement stmtS2 = epService.getEPAdministrator().create(preparedStmt);
        stmtS2.addListener(listenerTwo);

        // test no event, should return null
        epService.getEPRuntime().sendEvent(new SupportBean("e1", -1));
        assertEquals(null, listenerOne.assertOneGetNewAndReset().get("mysymbol"));
        assertEquals(null, listenerTwo.assertOneGetNewAndReset().get("mysymbol"));

        // test one non-matching event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("XX", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportBean("e1", -1));
        assertEquals(null, listenerOne.assertOneGetNewAndReset().get("mysymbol"));
        assertEquals(null, listenerTwo.assertOneGetNewAndReset().get("mysymbol"));

        // test S2 matching event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("S2", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportBean("e1", -1));
        assertEquals(null, listenerOne.assertOneGetNewAndReset().get("mysymbol"));
        assertEquals("S2", listenerTwo.assertOneGetNewAndReset().get("mysymbol"));

        // test S1 matching event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("S1", 0, 0L, ""));
        epService.getEPRuntime().sendEvent(new SupportBean("e1", -1));
        assertEquals("S1", listenerOne.assertOneGetNewAndReset().get("mysymbol"));
        assertEquals("S2", listenerTwo.assertOneGetNewAndReset().get("mysymbol"));
    }

    public void testSimpleOneParameter()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string=?)";
        EPPreparedStatement prepared = epService.getEPAdministrator().prepareEPL(stmt);

        prepared.setObject(1, "e1");
        EPStatement statement = epService.getEPAdministrator().create(prepared);
        statement.addListener(listenerOne);
        assertEquals("select * from com.espertech.esper.support.bean.SupportBean(string = \"e1\")", statement.getText());

        prepared.setObject(1, "e2");
        statement = epService.getEPAdministrator().create(prepared);
        statement.addListener(listenerTwo);
        assertEquals("select * from com.espertech.esper.support.bean.SupportBean(string = \"e2\")", statement.getText());

        epService.getEPRuntime().sendEvent(new SupportBean("e2", 10));
        assertFalse(listenerOne.isInvoked());
        assertTrue(listenerTwo.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("e1", 10));
        assertFalse(listenerTwo.isInvoked());
        assertTrue(listenerOne.getAndClearIsInvoked());

        // Test substitution parameter and inheritance in key matching
        epService.getEPAdministrator().getConfiguration().addEventType("MyEventOne", MyEventOne.class);
        String epl = "select * from MyEventOne(key = ?)";
        EPPreparedStatement preparedStatement = epService.getEPAdministrator().prepareEPL(epl);
        MyObjectKeyInterface lKey = new MyObjectKeyInterface();
        preparedStatement.setObject(1, lKey);
        statement = epService.getEPAdministrator().create(preparedStatement);
        statement.addListener(listenerOne);

        epService.getEPRuntime().sendEvent(new MyEventOne(lKey));
        assertTrue(listenerOne.getAndClearIsInvoked());

        // Test substitution parameter and concrete subclass in key matching 
        epService.getEPAdministrator().getConfiguration().addEventType("MyEventTwo", MyEventTwo.class);
        epl = "select * from MyEventTwo where key = ?";
        preparedStatement = epService.getEPAdministrator().prepareEPL(epl);
        MyObjectKeyConcrete cKey = new MyObjectKeyConcrete();
        preparedStatement.setObject(1, cKey);
        statement = epService.getEPAdministrator().create(preparedStatement);
        statement.addListener(listenerOne);

        epService.getEPRuntime().sendEvent(new MyEventTwo(cKey));
        assertTrue(listenerOne.getAndClearIsInvoked());
    }

    public void testSimpleTwoParameterFilter()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string=?, intPrimitive=?)";
        runSimpleTwoParameter(stmt, null, true);
    }

    public void testSimpleTwoParameterWhere()
    {
        String stmt = "select * from " + SupportBean.class.getName() + " where string=? and intPrimitive=?";
        runSimpleTwoParameter(stmt, null, false);
    }

    public void testSimpleTwoParameterWhereNamed()
    {
        String stmt = "select * from " + SupportBean.class.getName() + " where string=? and intPrimitive=?";
        runSimpleTwoParameter(stmt, "s1", false);
    }

    private void runSimpleTwoParameter(String stmtText, String statementName, boolean compareText)
    {
        EPPreparedStatement prepared = epService.getEPAdministrator().prepareEPL(stmtText);

        prepared.setObject(1, "e1");
        prepared.setObject(2, 1);
        EPStatement statement;
        if (statementName != null)
        {
            statement = epService.getEPAdministrator().create(prepared, statementName);
        }
        else
        {
            statement = epService.getEPAdministrator().create(prepared);
        }
        statement.addListener(listenerOne);
        if (compareText)
        {
            assertEquals("select * from com.espertech.esper.support.bean.SupportBean(string = \"e1\" and intPrimitive = 1)", statement.getText());
        }

        prepared.setObject(1, "e2");
        prepared.setObject(2, 2);
        if (statementName != null)
        {
            statement = epService.getEPAdministrator().create(prepared, statementName + "_1");
        }
        else
        {
            statement = epService.getEPAdministrator().create(prepared);
        }
        statement.addListener(listenerTwo);
        if (compareText)
        {
            assertEquals("select * from com.espertech.esper.support.bean.SupportBean(string = \"e2\" and intPrimitive = 2)", statement.getText());
        }

        epService.getEPRuntime().sendEvent(new SupportBean("e2", 2));
        assertFalse(listenerOne.isInvoked());
        assertTrue(listenerTwo.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("e1", 1));
        assertFalse(listenerTwo.isInvoked());
        assertTrue(listenerOne.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("e1", 2));
        assertFalse(listenerOne.isInvoked());
        assertFalse(listenerTwo.isInvoked());
    }

    public void testSimpleNoParameter()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string=\"e1\")";
        EPPreparedStatement prepared = epService.getEPAdministrator().prepareEPL(stmt);

        EPStatement statement = epService.getEPAdministrator().create(prepared);
        statement.addListener(listenerOne);
        assertEquals("select * from com.espertech.esper.support.bean.SupportBean(string = \"e1\")", statement.getText());

        statement = epService.getEPAdministrator().create(prepared);
        statement.addListener(listenerTwo);
        assertEquals("select * from com.espertech.esper.support.bean.SupportBean(string = \"e1\")", statement.getText());

        epService.getEPRuntime().sendEvent(new SupportBean("e2", 10));
        assertFalse(listenerOne.isInvoked());
        assertFalse(listenerTwo.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("e1", 10));
        assertTrue(listenerOne.getAndClearIsInvoked());
        assertTrue(listenerTwo.getAndClearIsInvoked());
    }

    public void testInvalidParameterNotSet()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string=?)";
        EPPreparedStatement prepared = epService.getEPAdministrator().prepareEPL(stmt);

        try
        {
            epService.getEPAdministrator().create(prepared);
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Substitution parameter value for index 1 not set, please provide a value for this parameter", ex.getMessage());
        }

        stmt = "select * from " + SupportBean.class.getName() + "(string in (?, ?))";
        prepared = epService.getEPAdministrator().prepareEPL(stmt);

        try
        {
            epService.getEPAdministrator().create(prepared);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }

        try
        {
            prepared.setObject(1, "");
            epService.getEPAdministrator().create(prepared);
            fail();
        }
        catch (EPException ex)
        {
            // expected
        }

        // success
        prepared.setObject(2, "");
        epService.getEPAdministrator().create(prepared);
    }

    public void testInvalidParameterType()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string=?)";
        EPPreparedStatement prepared = epService.getEPAdministrator().prepareEPL(stmt);

        try
        {
            prepared.setObject(1, -1);
            epService.getEPAdministrator().create(prepared);
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Implicit conversion from datatype 'Integer' to 'String' is not allowed [select * from com.espertech.esper.support.bean.SupportBean(string = -1)]", ex.getMessage());
        }
    }

    public void testInvalidNoParameters()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string='ABC')";
        EPPreparedStatement prepared = epService.getEPAdministrator().prepareEPL(stmt);

        try
        {
            prepared.setObject(1, -1);
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            assertEquals("Statement does not have substitution parameters indicated by the '?' character", ex.getMessage());
        }
    }

    public void testInvalidSetObject()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string=?)";
        EPPreparedStatement prepared = epService.getEPAdministrator().prepareEPL(stmt);

        try
        {
            prepared.setObject(0, "");
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            assertEquals("Substitution parameter index starts at 1", ex.getMessage());
        }

        try
        {
            prepared.setObject(2, "");
            fail();
        }
        catch (IllegalArgumentException ex)
        {
            assertEquals("Invalid substitution parameter index of 2 supplied, the maximum for this statement is 1", ex.getMessage());
        }
    }

    public void testInvalidCreateEPL()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string=?)";
        try
        {
            epService.getEPAdministrator().createEPL(stmt);
        }
        catch (EPException ex)
        {
            assertEquals("Invalid use of substitution parameters marked by '?' in statement, use the prepare method to prepare statements with substitution parameters [select * from com.espertech.esper.support.bean.SupportBean(string=?)]", ex.getMessage());
        }
    }

    public void testInvalidCreatePattern()
    {
        String stmt = SupportBean.class.getName() + "(string=?)";
        try
        {
            epService.getEPAdministrator().createPattern(stmt);
        }
        catch (EPException ex)
        {
            assertEquals("Invalid use of substitution parameters marked by '?' in statement, use the prepare method to prepare statements with substitution parameters [com.espertech.esper.support.bean.SupportBean(string=?)]", ex.getMessage());
        }
    }

    public void testInvalidCompile()
    {
        String stmt = "select * from " + SupportBean.class.getName() + "(string=?)";
        try
        {
            epService.getEPAdministrator().compileEPL(stmt);
        }
        catch (EPException ex)
        {
            assertEquals("Invalid use of substitution parameters marked by '?' in statement, use the prepare method to prepare statements with substitution parameters", ex.getMessage());
        }
    }

    public void testInvalidViewParameter()
    {
        String stmt = "select * from " + SupportBean.class.getName() + ".win:length(?)";
        try
        {
            epService.getEPAdministrator().prepareEPL(stmt);
        }
        catch (EPException ex)
        {
            assertEquals("Incorrect syntax near '?' expecting a closing parenthesis ')' but found a questionmark '?' at line 1 column 70, please check the view specifications within the from clause [select * from com.espertech.esper.support.bean.SupportBean.win:length(?)]", ex.getMessage());
        }
    }

    public interface IKey {
    }

    public class MyObjectKeyInterface implements IKey {
    }

    public class MyEventOne {
        private IKey key;

        public MyEventOne(IKey key) {
            this.key = key;
        }

        public IKey getKey() {
            return key;
        }
    }

    public class MyObjectKeyConcrete {
    }

    public class MyEventTwo {
        private MyObjectKeyConcrete key;

        public MyEventTwo(MyObjectKeyConcrete key) {
            this.key = key;
        }

        public MyObjectKeyConcrete getKey() {
            return key;
        }
    }
}
