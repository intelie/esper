package com.espertech.esper.regression.client;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.deploy.DeploymentResult;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
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

    public void testNamedWindow() throws Exception {
        DeploymentResult result = deploySvc.parseDeploy("create window MyWindow.win:time(30) as (col1 int, col2 string)",
                null, null, null);
        deploySvc.undeployRemove(result.getDeploymentId());
        
        result = deploySvc.parseDeploy("create window MyWindow.win:time(30) as (col1 short, col2 long)",
                null, null, null);
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
