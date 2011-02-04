package com.espertech.esper.regression.client;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.deploy.EPDeploymentAdmin;
import com.espertech.esper.client.deploy.Module;
import com.espertech.esper.client.deploy.ParseException;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

import java.io.IOException;

public class TestDeployParse extends TestCase
{
    private static String newline = System.getProperty("line.separator");

    private EPServiceProvider epService;
    private EPDeploymentAdmin deploySvc;

    public void setUp() {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
        deploySvc = epService.getEPAdministrator().getDeploymentAdmin();
    }

    public void testParse() throws Exception {
        Module module = deploySvc.read("regression/test_module_4.epl");
        assertModule(module, null, "abd", null, new String[] {
                "select * from ABC",
                "/* Final comment */"
            }, new boolean[] {false, true},
                new int[] {3, 8},
                new int[] {12, 0},
                new int[] {37, 0}
        );

        module = deploySvc.read("regression/test_module_1.epl");
        assertModule(module, "abc", "def,jlk", null, new String[] {
                "select * from A",
                "select * from B" + newline +
                    "where C=d",
                "/* Test ; Comment */" + newline +
                        "update ';' where B=C",
                "update D"
            }
        );

        module = deploySvc.read("regression/test_module_2.epl");
        assertModule(module, "abc.def.hij", "def.hik,jlk.aja", null, new String[] {
                "// Note 4 white spaces after * and before from" + newline + "select * from A",
                "select * from B",
                "select *    " + newline + "    from C",
            }
        );

        module = deploySvc.read("regression/test_module_3.epl");
        assertModule(module, null, null, null, new String[] {
                "create window ABC",
                "select * from ABC"
            }
        );

        module = deploySvc.read("regression/test_module_5.epl");
        assertModule(module, "abd.def", null, null, new String[0]);

        module = deploySvc.read("regression/test_module_6.epl");
        assertModule(module, null, null, null, new String[0]);

        module = deploySvc.read("regression/test_module_7.epl");
        assertModule(module, null, null, null, new String[0]);

        module = deploySvc.read("regression/test_module_8.epl");
        assertModule(module, "def.jfk", null, null, new String[0]);
        
        module = deploySvc.parse("module mymodule; uses mymodule2; import abc; select * from MyEvent;");
        assertModule(module, "mymodule", "mymodule2", "abc", new String[] {
                "select * from MyEvent"
            });

        module = deploySvc.read("regression/test_module_11.epl");
        assertModule(module, null, null, "com.mycompany.pck1", new String[0]);

        module = deploySvc.read("regression/test_module_10.epl");
        assertModule(module, "abd.def", "one.use,two.use", "com.mycompany.pck1,com.mycompany.*", new String[] {
                "select * from A",
            }
        );
    }

    public void testParseFail() throws Exception {
        tryInvalidIO("regression/dummy_not_there.epl",
                   "Failed to find resource 'regression/dummy_not_there.epl' in classpath");

        tryInvalidParse("regression/test_module_1_fail.epl",
                   "Keyword 'module' must be followed by a name or package name (set of names separated by dots) for resource 'regression/test_module_1_fail.epl'");

        tryInvalidParse("regression/test_module_2_fail.epl",
                   "Keyword 'uses' must be followed by a name or package name (set of names separated by dots) for resource 'regression/test_module_2_fail.epl'");

        tryInvalidParse("regression/test_module_3_fail.epl",
                   "Keyword 'module' must be followed by a name or package name (set of names separated by dots) for resource 'regression/test_module_3_fail.epl'");

        tryInvalidParse("regression/test_module_4_fail.epl",
                   "Keyword 'uses' must be followed by a name or package name (set of names separated by dots) for resource 'regression/test_module_4_fail.epl'");

        tryInvalidParse("regression/test_module_5_fail.epl",
                   "Keyword 'import' must be followed by a name or package name (set of names separated by dots) for resource 'regression/test_module_5_fail.epl'");

        tryInvalidParse("regression/test_module_6_fail.epl",
                   "The 'module' keyword must be the first declaration in the module file for resource 'regression/test_module_6_fail.epl'");

        tryInvalidParse("regression/test_module_7_fail.epl",
                   "Duplicate use of the 'module' keyword for resource 'regression/test_module_7_fail.epl'");

        tryInvalidParse("regression/test_module_8_fail.epl",
                   "The 'uses' and 'import' keywords must be the first declaration in the module file or follow the 'module' declaration");

        tryInvalidParse("regression/test_module_9_fail.epl",
                   "The 'uses' and 'import' keywords must be the first declaration in the module file or follow the 'module' declaration");
    }

    private void tryInvalidIO(String resource, String message) throws ParseException {
        try {
            deploySvc.read(resource);
            fail();
        }
        catch (IOException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    private void tryInvalidParse(String resource, String message) throws IOException {
        try {
            deploySvc.read(resource);
            fail();
        }
        catch (ParseException ex) {
            assertEquals(message, ex.getMessage());
        }
    }

    private void assertModule(Module module, String name, String usesCSV, String importsCSV, String[] statements) {
        assertModule(module, name, usesCSV, importsCSV, statements, new boolean[statements.length], new int[statements.length], new int[statements.length], new int[statements.length]);
    }

    private void assertModule(Module module, String name, String usesCSV, String importsCSV, String[] statementsExpected,
                              boolean[] commentsExpected,
                              int[] lineNumsExpected,
                              int[] charStartsExpected,
                              int[] charEndsExpected) {
        assertEquals(name, module.getName());

        String[] expectedUses = usesCSV == null ? new String[0] : usesCSV.split(",");
        ArrayAssertionUtil.assertEqualsExactOrder(module.getUses().toArray(), expectedUses);

        String[] expectedImports = importsCSV == null ? new String[0] : importsCSV.split(",");
        ArrayAssertionUtil.assertEqualsExactOrder(module.getImports().toArray(), expectedImports);

        String[] stmtsFound = new String[module.getItems().size()];
        boolean[] comments = new boolean[module.getItems().size()];
        int[] lineNumsFound = new int[module.getItems().size()];
        int[] charStartsFound = new int[module.getItems().size()];
        int[] charEndsFound = new int[module.getItems().size()];

        for (int i = 0; i < module.getItems().size(); i++) {
            stmtsFound[i] = module.getItems().get(i).getExpression();
            comments[i] = module.getItems().get(i).isCommentOnly();
            lineNumsFound[i] = module.getItems().get(i).getLineNumber();
            charStartsFound[i] = module.getItems().get(i).getCharPosStart();
            charEndsFound[i] = module.getItems().get(i).getCharPosEnd();
        }
                
        ArrayAssertionUtil.assertEqualsExactOrder(stmtsFound, statementsExpected);
        ArrayAssertionUtil.assertEqualsExactOrder(comments, commentsExpected);

        boolean isCompareLineNums = false;
        for (int l : lineNumsExpected) {
            if (l > 0) {
                isCompareLineNums = true;
            }
        }
        if (isCompareLineNums) {
            ArrayAssertionUtil.assertEqualsExactOrder(lineNumsFound, lineNumsExpected);
            ArrayAssertionUtil.assertEqualsExactOrder(charStartsFound, charStartsExpected);
            ArrayAssertionUtil.assertEqualsExactOrder(charEndsFound, charEndsExpected);
        }
    }
}
