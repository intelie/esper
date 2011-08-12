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

package com.espertech.esper.regression.view;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;

public class TestOutputLimitAfter extends TestCase
 {
     private EPServiceProvider epService;
     private long currentTime;
     private SupportUpdateListener listener;
     private String[] fields;

     public void setUp()
     {
         fields = new String[] {"string"};
         Configuration config = SupportConfigFactory.getConfiguration();
         config.addEventType("SupportBean", SupportBean.class);
         epService = EPServiceProviderManager.getDefaultProvider(config);
         epService.initialize();
         listener = new SupportUpdateListener();
     }

     public void testEveryPolicy()
     {
         sendTimer(0);
         String stmtText = "select string from SupportBean.win:keepall() output after 0 days 0 hours 0 minutes 20 seconds 0 milliseconds every 0 days 0 hours 0 minutes 5 seconds 0 milliseconds";
         EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
         stmt.addListener(listener);

         runAssertion();
         
         EPStatementObjectModel model = new EPStatementObjectModel();
         model.setSelectClause(SelectClause.create("string"));
         model.setFromClause(FromClause.create(FilterStream.create("SupportBean").addView("win", "keepall")));
         model.setOutputLimitClause(OutputLimitClause.create(Expressions.timePeriod(0, 0, 0, 5, 0)).afterTimePeriodExpression(Expressions.timePeriod(0, 0, 0, 20, 0)));
         assertEquals(stmtText, model.toEPL());
     }

     private void runAssertion()
     {
         sendTimer(1);
         sendEvent("E1");

         sendTimer(6000);
         sendEvent("E2");
         sendTimer(16000);
         sendEvent("E3");
         assertFalse(listener.isInvoked());

         sendTimer(20000);
         sendEvent("E4");
         assertFalse(listener.isInvoked());

         sendTimer(24999);
         sendEvent("E5");

         sendTimer(25000);
         ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E4"}, {"E5"}});
         listener.reset();

         sendTimer(27000);
         sendEvent("E6");

         sendTimer(29999);
         assertFalse(listener.isInvoked());

         sendTimer(30000);
         ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E6"});
     }

     public void testDirectNumberOfEvents()
     {
         String stmtText = "select string from SupportBean.win:keepall() output after 3 events";
         EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
         stmt.addListener(listener);

         sendEvent("E1");
         sendEvent("E2");
         sendEvent("E3");
         assertFalse(listener.isInvoked());

         sendEvent("E4");
         ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E4"});

         sendEvent("E5");
         ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E5"});

         stmt.destroy();
         
         EPStatementObjectModel model = new EPStatementObjectModel();
         model.setSelectClause(SelectClause.create("string"));
         model.setFromClause(FromClause.create(FilterStream.create("SupportBean").addView("win", "keepall")));
         model.setOutputLimitClause(OutputLimitClause.createAfter(3));
         assertEquals("select string from SupportBean.win:keepall() output after 3 events ", model.toEPL());

         stmt = epService.getEPAdministrator().create(model);
         stmt.addListener(listener);

         sendEvent("E1");
         sendEvent("E2");
         sendEvent("E3");
         assertFalse(listener.isInvoked());

         sendEvent("E4");
         ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E4"});

         sendEvent("E5");
         ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E5"});
         
         model = epService.getEPAdministrator().compileEPL("select string from SupportBean.win:keepall() output after 3 events");
         assertEquals("select string from SupportBean.win:keepall() output after 3 events ", model.toEPL());
     }

     public void testDirectTimePeriod()
     {
         sendTimer(0);
         String stmtText = "select string from SupportBean.win:keepall() output after 20 seconds ";
         EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
         stmt.addListener(listener);

         sendTimer(1);
         sendEvent("E1");

         sendTimer(6000);
         sendEvent("E2");

         sendTimer(19999);
         sendEvent("E3");
         assertFalse(listener.isInvoked());

         sendTimer(20000);
         sendEvent("E4");
         ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E4"});

         sendTimer(21000);
         sendEvent("E5");
         ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E5"});
     }

     public void testSnapshotVariable()
     {
         epService.getEPAdministrator().createEPL("create variable int myvar = 1");

         sendTimer(0);
         String stmtText = "select string from SupportBean.win:keepall() output after 20 seconds snapshot when myvar = 1";
         EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
         stmt.addListener(listener);

         runAssertionSnapshotVar();
         
         stmt.destroy();
         EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(stmtText);
         assertEquals(stmtText, model.toEPL());
         stmt = epService.getEPAdministrator().create(model);
         assertEquals(stmtText, stmt.getText());
     }

     public void testOutputWhenThen()
     {
         epService.getEPAdministrator().createEPL("create variable boolean myvar0 = false");
         epService.getEPAdministrator().createEPL("create variable boolean myvar1 = false");
         epService.getEPAdministrator().createEPL("create variable boolean myvar2 = false");

         String epl = "@Name(\"select-streamstar+outputvar\")\n" +
                 "select a.* from SupportBean.win:time(10) a output after 3 events when myvar0=true then set myvar1=true, myvar2=true";

         EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
         stmt.addListener(listener);

         sendEvent("E1");
         sendEvent("E2");
         sendEvent("E3");
         assertFalse(listener.isInvoked());

         epService.getEPRuntime().setVariableValue("myvar0", true);
         sendEvent("E4");
         assertTrue(listener.isInvoked());
         
         assertEquals(true, epService.getEPRuntime().getVariableValue("myvar1"));
         assertEquals(true, epService.getEPRuntime().getVariableValue("myvar2"));
     }

     private void runAssertionSnapshotVar()
     {
         sendTimer(6000);
         sendEvent("E1");
         sendEvent("E2");

         sendTimer(19999);
         sendEvent("E3");
         assertFalse(listener.isInvoked());

         sendTimer(20000);
         sendEvent("E4");
         ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1"},{"E2"},{"E3"},{"E4"}});
         listener.reset();

         sendTimer(21000);
         sendEvent("E5");
         ArrayAssertionUtil.assertPropsPerRow(listener.getLastNewData(), fields, new Object[][] {{"E1"},{"E2"},{"E3"},{"E4"},{"E5"}});
         listener.reset();
     }

     private void sendTimer(long time)
     {
         epService.getEPRuntime().sendEvent(new CurrentTimeEvent(time));
     }

     private void sendEvent(String string)
     {
         epService.getEPRuntime().sendEvent(new SupportBean(string, 0));
     }
 }
