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

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.deploy.DeploymentResult;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.filter.FilterService;
import com.espertech.esper.filter.FilterServiceSPI;
import junit.framework.TestCase;

public class TestDeployRedefinition extends TestCase
{
    private EPServiceProvider epService;
    private EPDeploymentAdmin deploySvc;

    public void setUp() {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        deploySvc = epService.getEPAdministrator().getDeploymentAdmin();
    }

    // Test EPL keyword in import and uses or module text

    public void testCreateSchemaNamedWindowInsert() throws Exception {

        String text = "module test.test1;\n" +
                "create schema MyTypeOne(col1 string, col2 int);" +
                "create window MyWindowOne.win:keepall() as select * from MyTypeOne;" +
                "insert into MyWindowOne select * from MyTypeOne;";

        DeploymentResult resultOne = deploySvc.parseDeploy(text, "uri1", "arch1", null);
        deploySvc.undeployRemove(resultOne.getDeploymentId());

        DeploymentResult resultTwo = deploySvc.parseDeploy(text, "uri2", "arch2", null);
        deploySvc.undeployRemove(resultTwo.getDeploymentId());
        text = "module test.test1;\n" +
                "create schema MyTypeOne(col1 string, col2 int, col3 long);" +
                "create window MyWindowOne.win:keepall() as select * from MyTypeOne;" +
                "insert into MyWindowOne select * from MyTypeOne;";

        DeploymentResult resultThree = deploySvc.parseDeploy(text, "uri1", "arch1", null);
        deploySvc.undeployRemove(resultThree.getDeploymentId());

        FilterService filterService = ((EPServiceProviderSPI) epService).getFilterService();
        FilterServiceSPI filterSPI = (FilterServiceSPI) filterService;
        assertEquals(0, filterSPI.getCountTypes());
    }

    public void testRedefDeployOrder() throws Exception {
        String eplClientA = "" +
                    "create schema InputEvent as (col1 string, col2 string);" +
                    "\n" +
                    "@Name('A') " +
                    "insert into OutOne select col1||col2 as outOneCol from InputEvent;\n" +
                    "\n" +
                    "@Name('B') " +
                    "insert into OutTwo select outOneCol||'x'||outOneCol as finalOut from OutOne;";
        DeploymentResult deploymentResultOne = deploySvc.parseDeploy(eplClientA);

        String eplClientB = "@Name('C') select * from OutTwo;";   // implicily bound to PN1
        DeploymentResult deploymentResultTwo = deploySvc.parseDeploy(eplClientB);

        deploySvc.undeploy(deploymentResultOne.getDeploymentId());
        deploySvc.undeploy(deploymentResultTwo.getDeploymentId());

        String eplClientC = "" +
                    "create schema InputEvent as (col1 string, col2 string);" +
                    "\n" +
                    "@Name('A') " +
                    "insert into OutOne select col1||col2 as outOneCol from InputEvent;" +
                    "\n" +
                    "@Name('B') " +
                    "insert into OutTwo select col2||col1 as outOneCol from InputEvent;";
        deploySvc.parseDeploy(eplClientC);

        String eplClientD = "@Name('C') select * from OutOne;" +
                              "@Name('D') select * from OutTwo;";
        deploySvc.parseDeploy(eplClientD);
    }

    public void testNamedWindow() throws Exception {
        DeploymentResult result = deploySvc.parseDeploy("create window MyWindow.win:time(30) as (col1 int, col2 string)",
                null, null, null);
        deploySvc.undeployRemove(result.getDeploymentId());
        
        result = deploySvc.parseDeploy("create window MyWindow.win:time(30) as (col1 short, col2 long)");
        deploySvc.undeployRemove(result.getDeploymentId());
    }

    public void testInsertInto() throws Exception {
        DeploymentResult result = deploySvc.parseDeploy("create schema MySchema (col1 int, col2 string);"
                + "insert into MyStream select * from MySchema;",
                null, null, null);
        deploySvc.undeployRemove(result.getDeploymentId());
        
        result = deploySvc.parseDeploy("create schema MySchema (col1 short, col2 long);"
                + "insert into MyStream select * from MySchema;",
                null, null, null);
        deploySvc.undeployRemove(result.getDeploymentId());
    }

    public void testVariables() throws Exception {
        DeploymentResult result = deploySvc.parseDeploy("create variable int MyVar;"
                + "create schema MySchema (col1 short, col2 long);"
                + "select MyVar from MySchema;",
                null, null, null);
        deploySvc.undeployRemove(result.getDeploymentId());

        result = deploySvc.parseDeploy("create variable string MyVar;"
                + "create schema MySchema (col1 short, col2 long);"
                + "select MyVar from MySchema;",
                null, null, null);
        deploySvc.undeployRemove(result.getDeploymentId());
    }
}
