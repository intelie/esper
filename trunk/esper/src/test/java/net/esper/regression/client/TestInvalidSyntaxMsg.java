package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.eql.parse.EPStatementSyntaxException;

public class TestInvalidSyntaxMsg extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider();
        epService.initialize();
    }

    public void testSyntaxError()
    {
        // This could be a better exception - outlining viable alternatives in ANTLR 2.7.5 cannot be done
        // Use ANTLR version 3 once out.
        tryCompile("select * from pattern[A -> B - C]",
                   "unexpected token: B near line 1, column 28 [select * from pattern[A -> B - C]]");

        tryCompile("insert into A (a",
                   "end of input when expecting a closing parenthesis ')' near line 1, column 17 [insert into A (a]");

        tryCompile("select case when 1>2 from A",
                   "expecting \"then\", found 'from' near line 1, column 22 [select case when 1>2 from A]");

        tryCompile("select * from A full outer join B on A.field < B.field",
                   "expecting an equals '=', found '<' near line 1, column 46 [select * from A full outer join B on A.field < B.field]");

        tryCompile("select a.b('aa\") from A",
                   "end of input when expecting a singe quote \"'\" near line 1, column 24 [select a.b('aa\") from A]");

        tryCompile("select a.b('aa\") from A",
                   "end of input when expecting a singe quote \"'\" near line 1, column 24 [select a.b('aa\") from A]");

        tryCompile("select * from A, sql:mydb [\"",
                   "end of input when expecting '\"' near line 1, column 29 [select * from A, sql:mydb [\"]");

        tryCompile("select prior(A, x) from A",
                   "expecting a numeric literal, found 'A' near line 1, column 14 [select prior(A, x) from A]");

        tryCompile("select * from A, into",
                   "unexpected token: into near line 1, column 18 [select * from A, into]");

        tryCompile("select * from pattern [",
                   "end of input near line 1, column 24 [select * from pattern []");

        tryCompile("select * google",
                   "expecting \"from\", found 'google' near line 1, column 10 [select * google]");

        tryCompile("insert into into",
                   "expecting an identifier, found 'into' near line 1, column 13 [insert into into]");
    }

    private void tryCompile(String expression, String expectedMsg)
    {
        try
        {
            epService.getEPAdministrator().createEQL(expression);
            fail();
        }
        catch (EPStatementSyntaxException ex)
        {
            assertEquals(expectedMsg, ex.getMessage());
            // expected
        }
    }

}
