package com.espertech.esper.regression.client;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.deploy.*;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.epl.SupportStaticMethodLib;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import java.util.Arrays;
import java.util.HashSet;

public class TestDeployAdmin extends TestCase
{
    private static String newline = System.getProperty("line.separator");
	private static final Log log = LogFactory.getLog(TestDeployAdmin.class);

    private EPServiceProvider epService;
    private DeploymentAdmin deploymentAdmin;
    private SupportUpdateListener listener;

    public void setUp() {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        deploymentAdmin = epService.getEPAdministrator().getDeploymentAdmin();
        listener = new SupportUpdateListener();
    }

    public void testDeployImports() throws Exception {
        Module module = makeModule("com.testit", "create schema S1 as SupportBean", "@Name('A') select SupportStaticMethodLib.plusOne(intPrimitive) as val from S1");
        module.getImports().add(SupportBean.class.getName());
        module.getImports().add(SupportStaticMethodLib.class.getPackage().getName() + ".*");
        deploymentAdmin.deploy(module, null);
        epService.getEPAdministrator().getStatement("A").addListener(listener);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 4));
        assertEquals(5, listener.assertOneGetNewAndReset().get("val"));
    }

    public void testDeploySingle() throws Exception {
        Module module = deploymentAdmin.read("regression/test_module_9.epl");
        DeploymentResult result = deploymentAdmin.deploy(module, new DeploymentOptions());

        assertNotNull(result.getDeploymentId());
        assertEquals(2, result.getStatements().size());
        assertEquals(2, epService.getEPAdministrator().getStatementNames().length);
        assertEquals("@Name(\"StmtOne\")" + newline +
                "create schema MyEvent(id String, val1 int, val2 int)", epService.getEPAdministrator().getStatement("StmtOne").getText());
        assertEquals("@Name(\"StmtTwo\")" + newline +
                "select * from MyEvent", epService.getEPAdministrator().getStatement("StmtTwo").getText());

        assertEquals(1, deploymentAdmin.getDeployments().length);
        assertEquals(result.getDeploymentId(), deploymentAdmin.getDeployments()[0]);
    }

    public void testDeployUndeploy() throws Exception {
        Module moduleOne = makeModule("mymodule.one", "create schema MySchemaOne (col1 int)", "select * from MySchemaOne");
        DeploymentResult resultOne = deploymentAdmin.deploy(moduleOne, new DeploymentOptions());
        assertEquals(2, resultOne.getStatements().size());

        Module moduleTwo = makeModule("mymodule.two", "create schema MySchemaTwo (col1 int)", "select * from MySchemaTwo");
        DeploymentResult resultTwo = deploymentAdmin.deploy(moduleTwo, new DeploymentOptions());
        assertEquals(2, resultTwo.getStatements().size());

        assertEquals(4, epService.getEPAdministrator().getStatementNames().length);
        
        deploymentAdmin.undeploy(resultTwo.getDeploymentId());
        assertEquals(2, epService.getEPAdministrator().getStatementNames().length);

        deploymentAdmin.undeploy(resultOne.getDeploymentId());
        assertEquals(0, epService.getEPAdministrator().getStatementNames().length);
    }

    public void testInvalidExceptionList() throws Exception {
        Module moduleOne = makeModule("mymodule.one", "create schema MySchemaOne (col1 Wrong)", "create schema MySchemaOne (col2 WrongTwo)");
        try {
            DeploymentOptions options = new DeploymentOptions();
            options.setFailFast(false);
            deploymentAdmin.deploy(moduleOne, options);
            fail();
        }
        catch (DeploymentException ex) {
            assertEquals("Deployment failed in module 'mymodule.one'", ex.getMessage());
            assertEquals(2,  ex.getExceptions().size());
            assertEquals("create schema MySchemaOne (col1 Wrong)", ex.getExceptions().get(0).getExpression());
            assertEquals("Error starting statement: Nestable map type configuration encountered an unexpected property type name 'Wrong' for property 'col1', expected java.lang.Class or java.util.Map or the name of a previously-declared Map type [create schema MySchemaOne (col1 Wrong)]", ex.getExceptions().get(0).getInner().getMessage());
            assertEquals("create schema MySchemaOne (col2 WrongTwo)", ex.getExceptions().get(1).getExpression());
            assertEquals("Error starting statement: Nestable map type configuration encountered an unexpected property type name 'WrongTwo' for property 'col2', expected java.lang.Class or java.util.Map or the name of a previously-declared Map type [create schema MySchemaOne (col2 WrongTwo)]", ex.getExceptions().get(1).getInner().getMessage());
        }
    }

    public void testFlagRollbackFailfastCompile() throws Exception {

        String textOne = "@Name('A') create schema MySchemaTwo (col1 int)";
        String textTwo = "@Name('B') create schema MySchemaTwo (col1 not_existing_type)";
        String errorTextTwo = "Error starting statement: Nestable map type configuration encountered an unexpected property type name 'not_existing_type' for property 'col1', expected java.lang.Class or java.util.Map or the name of a previously-declared Map type [@Name('B') create schema MySchemaTwo (col1 not_existing_type)]";
        String textThree = "@Name('C') create schema MySchemaTwo (col1 int)";
        Module module = makeModule("mymodule.two", textOne, textTwo, textThree);

        try {
            DeploymentOptions options = new DeploymentOptions();
            deploymentAdmin.deploy(module, options);
            fail();
        }
        catch (DeploymentException ex) {
            assertEquals(1, ex.getExceptions().size());
            DeploymentItemException first = ex.getExceptions().get(0);
            assertEquals(textTwo, first.getExpression());
            assertEquals(errorTextTwo, first.getInner().getMessage());
        }
        assertEquals(0, epService.getEPAdministrator().getStatementNames().length);

        try {
            DeploymentOptions options = new DeploymentOptions();
            options.setRollbackOnFail(false);
            deploymentAdmin.deploy(module, options);
            fail();
        }
        catch (DeploymentException ex) {
            assertEquals(1, ex.getExceptions().size());
            DeploymentItemException first = ex.getExceptions().get(0);
            assertEquals(textTwo, first.getExpression());
            assertEquals(errorTextTwo, first.getInner().getMessage());
            ArrayAssertionUtil.assertEqualsExactOrder(new String[] {"A"}, epService.getEPAdministrator().getStatementNames());
            epService.getEPAdministrator().getStatement("A").destroy();
        }

        try {
            DeploymentOptions options = new DeploymentOptions();
            options.setRollbackOnFail(false);
            options.setFailFast(false);
            deploymentAdmin.deploy(module, options);
            fail();
        }
        catch (DeploymentException ex) {
            assertEquals(1, ex.getExceptions().size());
            DeploymentItemException first = ex.getExceptions().get(0);
            assertEquals(textTwo, first.getExpression());
            assertEquals(errorTextTwo, first.getInner().getMessage());
            ArrayAssertionUtil.assertEqualsExactOrder(epService.getEPAdministrator().getStatementNames(), new String[] {"A", "C"});
        }
    }

    public void testFlagCompileOnly() throws Exception {
        String text = "create schema SomeSchema (col1 NotExists)";
        String error = "Error starting statement: Nestable map type configuration encountered an unexpected property type name 'NotExists' for property 'col1', expected java.lang.Class or java.util.Map or the name of a previously-declared Map type [create schema SomeSchema (col1 NotExists)]";

        try {
            deploymentAdmin.deploy(makeModule("test", text), null);
            fail();
        }
        catch (DeploymentException ex) {
            assertEquals(1, ex.getExceptions().size());
            DeploymentItemException first = ex.getExceptions().get(0);
            assertEquals(error, first.getInner().getMessage());
        }

        DeploymentOptions options = new DeploymentOptions();
        options.setCompileOnly(true);
        assertNull(deploymentAdmin.deploy(makeModule("test", text), options));
    }

    private Module makeModule(String name, String... statements) {

        ModuleItem[] items = new ModuleItem[statements.length];
        for (int i = 0; i < statements.length; i++) {
            items[i] = new ModuleItem(statements[i], false);
        }
        return new Module(name, null, new HashSet<String>(), new HashSet<String>(), Arrays.asList(items));
    }
}
