package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.annotation.Description;
import com.espertech.esper.client.annotation.Name;
import com.espertech.esper.client.annotation.Tag;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportEnum;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.core.EPStatementSPI;
import junit.framework.TestCase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TestStatementAnnotation extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventType("Bean", SupportBean.class.getName());
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
    }

    public void testInvalid() throws Exception
    {
        epService.getEPAdministrator().getConfiguration().addImport("com.espertech.esper.regression.client.*");

        tryInvalid("@MyAnnotationNested(nestableSimple=@MyAnnotationNestableSimple, nestableValues=@MyAnnotationNestableValues, nestableNestable=@MyAnnotationNestableNestable) select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationNestableNestable' requires a value for attribute 'value' [@MyAnnotationNested(nestableSimple=@MyAnnotationNestableSimple, nestableValues=@MyAnnotationNestableValues, nestableNestable=@MyAnnotationNestableNestable) select * from Bean]");

        tryInvalid("@MyAnnotationNested(nestableNestable=@MyAnnotationNestableNestable('A'), nestableSimple=1) select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationNested' requires a MyAnnotationNestableSimple-typed value for attribute 'nestableSimple' but received a Integer-typed value [@MyAnnotationNested(nestableNestable=@MyAnnotationNestableNestable('A'), nestableSimple=1) select * from Bean]");

        tryInvalid("@MyAnnotationValuePair(stringVal='abc') select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationValuePair' requires a value for attribute 'booleanVal' [@MyAnnotationValuePair(stringVal='abc') select * from Bean]");

        tryInvalid("MyAnnotationValueArray(value=5) select * from Bean", true,
                   "Incorrect syntax near 'MyAnnotationValueArray' [MyAnnotationValueArray(value=5) select * from Bean]");

        tryInvalid("@MyAnnotationValueArray(value=null) select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationValueArray' requires a value for attribute 'doubleArray' [@MyAnnotationValueArray(value=null) select * from Bean]");

        tryInvalid("@MyAnnotationValueArray(intArray={},doubleArray={},stringArray={null},value={}) select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationValueArray' requires a non-null value for array elements for attribute 'stringArray' [@MyAnnotationValueArray(intArray={},doubleArray={},stringArray={null},value={}) select * from Bean]");

        tryInvalid("@MyAnnotationValueArray(intArray={},doubleArray={},stringArray={1},value={}) select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationValueArray' requires a String-typed value for array elements for attribute 'stringArray' but received a Integer-typed value [@MyAnnotationValueArray(intArray={},doubleArray={},stringArray={1},value={}) select * from Bean]");

        tryInvalid("@MyAnnotationValue(value='a', value='a') select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationValue' has duplicate attribute values for attribute 'value' [@MyAnnotationValue(value='a', value='a') select * from Bean]");
        tryInvalid("@ABC select * from Bean", false,
                   "Failed to process statement annotations: Failed to resolve @-annotation class: Could not load class by name 'ABC', please check imports [@ABC select * from Bean]");

        tryInvalid("@MyAnnotationSimple(5) select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationSimple' does not have an attribute 'value' [@MyAnnotationSimple(5) select * from Bean]");
        tryInvalid("@MyAnnotationSimple(null) select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationSimple' does not have an attribute 'value' [@MyAnnotationSimple(null) select * from Bean]");
        
        tryInvalid("@MyAnnotationValue select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationValue' requires a value for attribute 'value' [@MyAnnotationValue select * from Bean]");

        tryInvalid("@MyAnnotationValue(5) select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationValue' requires a String-typed value for attribute 'value' but received a Integer-typed value [@MyAnnotationValue(5) select * from Bean]");
        tryInvalid("@MyAnnotationValueArray(value=\"ABC\", intArray={}, doubleArray={}, stringArray={}) select * from Bean", false,
                   "Failed to process statement annotations: Annotation 'MyAnnotationValueArray' requires a long[]-typed value for attribute 'value' but received a String-typed value [@MyAnnotationValueArray(value=\"ABC\", intArray={}, doubleArray={}, stringArray={}) select * from Bean]");
        tryInvalid("@MyAnnotationValueEnum(a.b.CC) select * from Bean", false,
                   "Annotation enumeration value 'a.b.CC' not recognized as an enumeration class, please check imports or type used [@MyAnnotationValueEnum(a.b.CC) select * from Bean]");

        tryInvalid("@Hint('XXX') select * from Bean", false,
                   "Failed to process statement annotations: Hint annotation value 'XXX' is not one of the known values [@Hint('XXX') select * from Bean]");
        tryInvalid("@Hint('ITERATE_ONLY,XYZ') select * from Bean", false,
                   "Failed to process statement annotations: Hint annotation value 'XYZ' is not one of the known values [@Hint('ITERATE_ONLY,XYZ') select * from Bean]");
    }

    private void tryInvalid(String stmtText, boolean isSyntax, String message)
    {
        try
        {
            epService.getEPAdministrator().createEPL(stmtText);
            fail();
        }
        catch (EPStatementSyntaxException ex)
        {
            assertTrue(isSyntax);
            assertEquals(message, ex.getMessage());
        }
        catch (EPStatementException ex)
        {
            assertFalse(isSyntax);
            assertEquals(message, ex.getMessage());
        }
    }

    public void testBuiltin()
    {
        String stmtText = "@Name('MyTestStmt') @Description('MyTestStmt description') @Tag(name=\"UserId\", value=\"value\") select * from Bean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        runAssertion(stmt);
        stmt.destroy();

        stmtText = "@Name('MyTestStmt') @Description('MyTestStmt description') @Tag(name='UserId', value='value') every Bean";
        stmt = epService.getEPAdministrator().createPattern(stmtText);
        runAssertion(stmt);
        stmt.destroy();

        stmtText = "@" + Name.class.getName() + "('MyTestStmt') @Description('MyTestStmt description') @Tag(name=\"UserId\", value=\"value\") every Bean";
        stmt = epService.getEPAdministrator().createPattern(stmtText);
        runAssertion(stmt);

        epService.getEPAdministrator().createEPL("@Hint('ITERATE_ONLY') select * from Bean");
        epService.getEPAdministrator().createEPL("@Hint('ITERATE_ONLY,DISABLE_RECLAIM_GROUP') select * from Bean");
        epService.getEPAdministrator().createEPL("@Hint('ITERATE_ONLY,DISABLE_RECLAIM_GROUP,ITERATE_ONLY') select * from Bean");
        epService.getEPAdministrator().createEPL("@Hint('  iterate_only ') select * from Bean");
    }

    private void runAssertion(EPStatement stmt)
    {
        Annotation[] annotations = stmt.getAnnotations();
        annotations = sortAlpha(annotations);
        assertEquals(3, annotations.length);

        assertEquals(Description.class, annotations[0].annotationType());
        assertEquals("MyTestStmt description", ((Description)annotations[0]).value());
        assertEquals("@Description(\"MyTestStmt description\")", annotations[0].toString());

        assertEquals(Name.class, annotations[1].annotationType());
        assertEquals("MyTestStmt", ((Name)annotations[1]).value());
        assertEquals("MyTestStmt", stmt.getName());
        assertEquals("@Name(\"MyTestStmt\")", annotations[1].toString());

        assertEquals(Tag.class, annotations[2].annotationType());
        assertEquals("UserId", ((Tag)annotations[2]).name());
        assertEquals("value", ((Tag)annotations[2]).value());
        assertEquals("@Tag(value=\"value\", name=\"UserId\")", annotations[2].toString());

        assertFalse(annotations[2].equals(annotations[1]));
        assertTrue(annotations[1].equals(annotations[1]));
        assertTrue(annotations[1].hashCode() != 0);
    }

    @MyAnnotationSimple
    @MyAnnotationValue("abc")
    @MyAnnotationValuePair(stringVal="a", intVal=-1, longVal=2, booleanVal=true, charVal='x', byteVal=10, shortVal=20, doubleVal=2.5)
    @MyAnnotationValueDefaulted
    @MyAnnotationValueArray(value={1, 2, 3}, intArray={4, 5}, doubleArray={}, stringArray={"X"})
    @MyAnnotationValueEnum(supportEnum = SupportEnum.ENUM_VALUE_3)
    public void testClientAppAnnotationSimple()
    {
        epService.getEPAdministrator().getConfiguration().addImport("com.espertech.esper.regression.client.*");
        epService.getEPAdministrator().getConfiguration().addImport(SupportEnum.class);

        String stmtText =
                "@MyAnnotationSimple " +
                "@MyAnnotationValue('abc') " +
                "@MyAnnotationValueDefaulted " +
                "@MyAnnotationValueEnum(supportEnum = SupportEnum.ENUM_VALUE_3) " +
                "@MyAnnotationValuePair(stringVal='a', intVal=-1, longVal=2, booleanVal=true, charVal='x', byteVal=10, shortVal=20, doubleVal=2.5) " +
                "select * from Bean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        EPStatementSPI spi = (EPStatementSPI) stmt;
        assertEquals("select * from Bean", spi.getExpressionNoAnnotations());

        Annotation[] annotations = stmt.getAnnotations();
        annotations = sortAlpha(annotations);
        assertEquals(5, annotations.length);

        assertEquals(MyAnnotationSimple.class, annotations[0].annotationType());
        assertEquals("abc", ((MyAnnotationValue)annotations[1]).value());
        assertEquals("XYZ", ((MyAnnotationValueDefaulted)annotations[2]).value());

        MyAnnotationValueEnum enumval = (MyAnnotationValueEnum) annotations[3];
        assertEquals(SupportEnum.ENUM_VALUE_2, enumval.supportEnumDef());
        assertEquals(SupportEnum.ENUM_VALUE_3, enumval.supportEnum());

        MyAnnotationValuePair pair = (MyAnnotationValuePair) annotations[4];
        assertEquals("a", pair.stringVal());
        assertEquals(-1, pair.intVal());
        assertEquals(2l, pair.longVal());
        assertEquals(true, pair.booleanVal());
        assertEquals('x', pair.charVal());
        assertEquals(10, pair.byteVal());
        assertEquals(20, pair.shortVal());
        assertEquals(2.5, pair.doubleVal());
        assertEquals("def", pair.stringValDef());
        assertEquals(100, pair.intValDef());
        assertEquals(200l, pair.longValDef());
        assertEquals(true, pair.booleanValDef());
        assertEquals('D', pair.charValDef());
        assertEquals(1.1, pair.doubleValDef());

        // test array
        stmtText =
                "@MyAnnotationValueArray(value={1, 2, 3}, intArray={4, 5}, doubleArray={}, \nstringArray={\"X\"})\n" +
                "/* Test */ select * \nfrom Bean";
        stmt = epService.getEPAdministrator().createEPL(stmtText);

        annotations = stmt.getAnnotations();
        annotations = sortAlpha(annotations);
        assertEquals(1, annotations.length);

        MyAnnotationValueArray array = (MyAnnotationValueArray) annotations[0];
        assertTrue(Arrays.deepEquals(toObjectArray(array.value()), new Object[] {1L, 2L, 3L}));
        assertTrue(Arrays.deepEquals(toObjectArray(array.intArray()), new Object[] {4, 5}));
        assertTrue(Arrays.deepEquals(toObjectArray(array.doubleArray()), new Object[] {}));
        assertTrue(Arrays.deepEquals(toObjectArray(array.stringArray()), new Object[] {"X"}));
        assertTrue(Arrays.deepEquals(toObjectArray(array.stringArrayDef()), new Object[] {"XYZ"}));
    }

    public void testSPI()
    {
        epService.getEPAdministrator().getConfiguration().addImport("com.espertech.esper.regression.client.*");

        String[][] testdata = new String[][] {
                {"@MyAnnotationSimple /* test */ select * from Bean",
                 "/* test */ select * from Bean"},
                {"/* test */ select * from Bean",
                 null},
                {"@MyAnnotationValueArray(value={1, 2, 3}, intArray={4, 5}, doubleArray={}, stringArray={\"X\"})    select * from Bean",
                 "select * from Bean"},
                {"@MyAnnotationSimple\nselect * from Bean",
                 "select * from Bean"},
                {"@MyAnnotationSimple\n@MyAnnotationSimple\nselect * from Bean",
                 "select * from Bean"},
                {"@MyAnnotationValueArray(value={1, 2, 3}, intArray={4, 5}, doubleArray={}, \nstringArray={\"X\"})\n" +
                "/* Test */ select * \nfrom Bean",
                "/* Test */ select * \r\nfrom Bean"},
        };

        for (int i = 0; i < testdata.length; i++)
        {
            EPStatement stmt = epService.getEPAdministrator().createEPL(testdata[i][0]);
            EPStatementSPI spi = (EPStatementSPI) stmt;
            assertEquals("Error on " + testdata[i][0], testdata[i][1], spi.getExpressionNoAnnotations());
        }
    }

    @MyAnnotationNested(
            nestableSimple=@MyAnnotationNestableSimple,
            nestableValues=@MyAnnotationNestableValues(val=999, arr={2, 1}),
            nestableNestable=@MyAnnotationNestableNestable("CDF")
    )
    public void testClientAppAnnotationNested()
    {
        epService.getEPAdministrator().getConfiguration().addImport("com.espertech.esper.regression.client.*");

        String stmtText =
                "@MyAnnotationNested(\n" +
                        "            nestableSimple=@MyAnnotationNestableSimple,\n" +
                        "            nestableValues=@MyAnnotationNestableValues(val=999, arr={2, 1}),\n" +
                        "            nestableNestable=@MyAnnotationNestableNestable(\"CDF\")\n" +
                        "    ) " +
                "select * from Bean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);

        Annotation[] annotations = stmt.getAnnotations();
        annotations = sortAlpha(annotations);
        assertEquals(1, annotations.length);

        MyAnnotationNested nested = (MyAnnotationNested) annotations[0];
        assertNotNull(nested.nestableSimple());
        assertTrue(Arrays.deepEquals(toObjectArray((nested.nestableValues().arr())), new Object[] {2, 1}));
        assertEquals(999, nested.nestableValues().val());
        assertEquals("CDF", nested.nestableNestable().value());
    }

    private Annotation[] sortAlpha(Annotation[] annotations)
    {
        if (annotations == null)
        {
            return null;
        }
        ArrayList<Annotation> sorted = new ArrayList<Annotation>();
        sorted.addAll(Arrays.asList(annotations));
        Collections.sort(sorted, new Comparator<Annotation>()
        {
            public int compare(Annotation o1, Annotation o2)
            {
                return o1.annotationType().getSimpleName().compareTo(o2.annotationType().getSimpleName());
            }
        });
        return sorted.toArray(new Annotation[sorted.size()]);
    }

    private Object[] toObjectArray(Object array)
    {
        if (!array.getClass().isArray())
        {
            throw new RuntimeException("Parameter passed is not an array");
        }
        Object[] result = new Object[Array.getLength(array)];
        for (int i = 0; i < Array.getLength(array); i++)
        {
            result[i] = Array.get(array, i);
        }
        return result;
    }
}
