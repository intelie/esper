package net.esper.eql.parse;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.support.eql.parse.SupportParserHelper;
import net.esper.support.bean.SupportBean;
import net.esper.support.event.SupportEventAdapterService;
import net.esper.eql.generated.EqlTokenTypes;
import antlr.collections.AST;

public class TestEQLParser extends TestCase implements EqlTokenTypes
{
    public void testDisplayAST() throws Exception
    {
        String className = SupportBean.class.getName();
        //String expression = "select case intPrimitive when 1 then null when 2 then 2 else 3 end " + "from " + className;
        String expression = "select case when 1 then null when 2 then 2 else 3 end from " + className;
        //String expression = "select case 1 when 1 then 2 end from " + className;

        log.debug(".testDisplayAST parsing: " + expression);
        AST ast = parse(expression);
        SupportParserHelper.displayAST(ast);

        log.debug(".testDisplayAST walking...");
        EQLTreeWalker walker = new EQLTreeWalker(SupportEventAdapterService.getService());
        walker.startEQLExpressionRule(ast);
    }

    public void testInvalidCases() throws Exception
    {
        String className = SupportBean.class.getName();

        assertIsInvalid(className + "(val=10000).");
        assertIsInvalid(className + "().a:someview");
        assertIsInvalid(className + "().a:someview(");
        assertIsInvalid(className + "().a:someview)");
        assertIsInvalid(className + "().lenght()");
        assertIsInvalid(className + "().:lenght()");
        assertIsInvalid(className + "().win:lenght(0,)");
        assertIsInvalid(className + "().win:lenght(,0)");
        assertIsInvalid(className + "().win:lenght(0,0,)");
        assertIsInvalid(className + "().win:lenght(0,0,\")");
        assertIsInvalid(className + "().win:lenght(\"\"5)");
        assertIsInvalid(className + "().win:lenght(,\"\")");
        assertIsInvalid(className + "().win:lenght.(,\"\")");
        assertIsInvalid(className + "().win:lenght().");
        assertIsInvalid(className + "().win:lenght().lenght");
        assertIsInvalid(className + "().win:lenght().lenght(");
        assertIsInvalid(className + "().win:lenght().lenght)");
        assertIsInvalid(className + "().win:lenght().lenght().");
        assertIsInvalid(className + "().win:lenght().lenght().lenght");
        assertIsInvalid(className + "().win:lenght({}))");
        assertIsInvalid(className + "().win:lenght({\"s\")");
        assertIsInvalid(className + "().win:lenght(\"s\"})");
        assertIsInvalid(className + "().win:lenght({{\"s\"})");
        assertIsInvalid(className + "().win:lenght({{\"s\"}})");
        assertIsInvalid(className + "().win:lenght({\"s\"}})");
        assertIsInvalid(className + "().win:lenght('s\"");
        assertIsInvalid(className + "().win:lenght(\"s')");

        assertIsInvalid("select MAX(intBoxed) from " + className + "().std:win(20)");
        assertIsInvalid("select * from com.xxx().std:win(3) where a not is null");
        assertIsInvalid("select * from com.xxx().std:win(3) where a = not null");
        assertIsInvalid("select * from com.xxx().std:win(3) where not not");
        assertIsInvalid("select * from com.xxx().std:win(3) where not ||");
        assertIsInvalid("select * from com.xxx().std:win(3) where a ||");
        assertIsInvalid("select * from com.xxx().std:win(3) where || a");

        assertIsInvalid("select a] from com.xxx().std:win(3)");
        assertIsInvalid("select * from com.xxx().std:win(3) where b('aaa)=5");

        assertIsInvalid("select sum() from b.win:length(1)");
        assertIsInvalid("select sum(?) from b.win:length(1)");
        assertIsInvalid("select sum(1+) from b.win:length(1)");
        assertIsInvalid("select sum(distinct) from b.win:length(1)");
        assertIsInvalid("select sum(distinct distinct a) from b.win:length(1)");
        assertIsInvalid("select avg() from b.win:length(1)");
        assertIsInvalid("select count() from b.win:length(1)");
        assertIsInvalid("select count(* *) from b.win:length(1)");
        assertIsInvalid("select count(*2) from b.win:length(1)");
        assertIsInvalid("select count(distinct *) from b.win:length(1)");
        assertIsInvalid("select max(distinct *) from b.win:length(1)");
        assertIsInvalid("select min() from b.win:length(1)");
        assertIsInvalid("select median() from b.win:length(1)");
        assertIsInvalid("select stddev() from b.win:length(1)");
        assertIsInvalid("select stddev(distinct) from b.win:length(1)");
        assertIsInvalid("select avedev() from b.win:length(1)");
        assertIsInvalid("select avedev(distinct) from b.win:length(1)");

        // group-by
        assertIsInvalid("select 1 from b.win:length(1) group by");
        assertIsInvalid("select 1 from b.win:length(1) group by group");
        assertIsInvalid("select 1 from b.win:length(1) group a");
        assertIsInvalid("select 1 from b.win:length(1) group by a group by b");
        assertIsInvalid("select 1 from b.win:length(1) by a ");
        assertIsInvalid("select 1 from b.win:length(1) group by a a");
        assertIsInvalid("select 1 from b.win:length(1) group by a as dummy");

        // having
        assertIsInvalid("select 1 from b.win:length(1) group by a having a>5,b<4");

        // insert into
        assertIsInvalid("insert into select 1 from b.win:length(1)");
        assertIsInvalid("insert into 38484 select 1 from b.win:length(1)");
        assertIsInvalid("insert into A B select 1 from b.win:length(1)");
        assertIsInvalid("insert into A () select 1 from b.win:length(1)");
        assertIsInvalid("insert into A (a,) select 1 from b.win:length(1)");
        assertIsInvalid("insert into A (,) select 1 from b.win:length(1)");
        assertIsInvalid("insert into A(,a) select 1 from b.win:length(1)");
        assertIsInvalid("insert xxx into A(,a) select 1 from b.win:length(1)");

        assertIsInvalid("select coalesce(tick.price) from x");
    }

    public void testValidCases() throws Exception
    {
        String className = SupportBean.class.getName();
        String preFill = "select * from " + className;

        assertIsValid(preFill + "(string='test',intPrimitive=20).win:lenght(100)");
        assertIsValid(preFill + "().win:lenght()");
        assertIsValid(preFill + "().win:lenght(4,5)");
        assertIsValid(preFill + "().win:lenght(4)");
        assertIsValid(preFill + "().win:lenght(\"\",5)");
        assertIsValid(preFill + "().win:lenght(10.9,1E30,-4.4,\"\",5)");
        assertIsValid(preFill + "().win:lenght(4).n:c(3.3, -3.3).n:d(\"price\")");
        assertIsValid(preFill + "().win:lenght().n:c().n:d().n:e().n:f().n:g().n:h(2.0)");
        assertIsValid(preFill + "().win:lenght({\"s\"})");
        assertIsValid(preFill + "().win:lenght({\"a\",\"b\"})");
        assertIsValid(preFill + "().win:lenght({\"a\",\"b\",\"c\"})");
        assertIsValid(preFill + "().win:lenght('')");
        assertIsValid(preFill + "().win:lenght('s')");
        assertIsValid(preFill + "().win:lenght('s',5)");
        assertIsValid(preFill + "().win:lenght({'s','t'},5)");
        assertIsValid(preFill + "().win:some_window('count','l','a').std:lastevent('s','tyr')");
        assertIsValid(preFill + "().win:some_view({'count'},'l','a')");
        assertIsValid(preFill + "().win:some_view({})");

        assertIsValid("select max(intPrimitive, intBoxed) from " + className + "().std:win(20)");
        assertIsValid("select max(intPrimitive, intBoxed, longBoxed) from " + className + "().std:win(20)");
        assertIsValid("select min(intPrimitive, intBoxed) from " + className + "().std:win(20)");
        assertIsValid("select min(intPrimitive, intBoxed, longBoxed) from " + className + "().std:win(20)");

        assertIsValid(preFill + "().win:lenght(3) where a = null");
        assertIsValid(preFill + "().win:lenght(3) where a is null");
        assertIsValid(preFill + "().win:lenght(3) where 10 is a");
        assertIsValid(preFill + "().win:lenght(3) where 10 is not a");
        assertIsValid(preFill + "().win:lenght(3) where 10 <> a");
        assertIsValid(preFill + "().win:lenght(3) where a <> 10");
        assertIsValid(preFill + "().win:lenght(3) where a != 10");
        assertIsValid(preFill + "().win:lenght(3) where 10 != a");
        assertIsValid(preFill + "().win:lenght(3) where not (a = 5)");
        assertIsValid(preFill + "().win:lenght(3) where not (a = 5 or b = 3)");
        assertIsValid(preFill + "().win:lenght(3) where not 5 < 4");
        assertIsValid(preFill + "().win:lenght(3) where a or (not b)");
        assertIsValid(preFill + "().win:lenght(3) where a % 3 + 6 * (c%d)");
        assertIsValid(preFill + "().win:lenght(3) where a || b = 'a'");
        assertIsValid(preFill + "().win:lenght(3) where a || b || c = 'a'");
        assertIsValid(preFill + "().win:lenght(3) where a + b + c = 'a'");

        assertIsValid("select not a, not (b), not (a > 5) from " +
                        className + "(a=1).win:lenght(10) as win1," +
                        className + "(a=2).win:lenght(10) as win2 " +
                        "where win1.f1 = win2.f2"
                        );

        assertIsValid("select intPrimitive from " +
                        className + "(a=1).win:lenght(10) as win1," +
                        className + "(a=2).win:lenght(10) as win2 " +
                        "where win1.f1 = win2.f2"
                        );

        // outer joins
        tryJoin("left");
        tryJoin("right");
        tryJoin("full");

        // complex property access
        assertIsValid("select array[1], map('a'), map(\"b\"), nested.nested " +
                      "from a.b(string='test',intPrimitive=20).win:lenght(100) " +
                      "where array[1].map('a').nested = 5");
        assertIsValid("select array[1] as b " +
                      "from a.b(string[0]='test').win:lenght(100) as x " +
                      "left outer join " +
                      "a.b(string[0]='test').win:lenght(100) as y " +
                      "on y.array[1].map('a').nested = x.nested2");
        assertIsValid("select a and b from b.win:length(1)");
        assertIsValid("select a or b from b.win:length(1)");
        assertIsValid("select a = b from b.win:length(1)");
        assertIsValid("select a != b from b.win:length(1)");

        assertIsValid("select sum(a), avg(b) from b.win:length(1)");
        assertIsValid("select sum(all a), avg(all b), avg(all b/c) from b.win:length(1)");
        assertIsValid("select sum(distinct a), avg(distinct b) from b.win:length(1)");
        assertIsValid("select sum(sum(a)) from b.win:length(1)");
        assertIsValid("select sum(3*a), sum(a - b - c) from b.win:length(1)");
        assertIsValid("select count(*), count(a), count(all b), count(distinct 2*a), count(5*a/2) from b.win:length(1)");
        assertIsValid("select max(volume), min(volume), min(all volume/44), min(distinct 2*a), max(distinct 5*a/2) from b.win:length(1)");
        assertIsValid("select median(volume), median(all volume*2/3), median(distinct 2*a) from b.win:length(1)");
        assertIsValid("select stddev(volume), stddev(all volume), stddev(distinct 2*a) from b.win:length(1)");
        assertIsValid("select avedev(volume), avedev(all volume), avedev(distinct 2*a) from b.win:length(1)");

        // group-by
        assertIsValid("select sum(a), x, y from b.win:length(1) group by a");
        assertIsValid("select 1 from b.win:length(1) where a=b and b=d group by a,b,3*x,max(4, 3),'a', \"a\", true, 5*(1+a+y/2)");
        assertIsValid("select 1 from b.win:length(1) where a"); // since a could be a boolean
        assertIsValid("select sum(distinct a), x, y from b.win:length(1) group by a");

        // having
        assertIsValid("select sum(a), x, y from b.win:length(1) group by a having x > y");
        assertIsValid("select 1 from b.win:length(1) where a=b and b=d group by a having (max(3*b - 2, 5) > 1) or 'a'=b");
        assertIsValid("select 1 from b.win:length(1) group by a having a");   // a could be boolean
        assertIsValid("select 1 from b.win:length(1) having a>5");

        // insert into
        assertIsValid("insert into MyEvent select 1 from b.win:length(1)");
        assertIsValid("insert into MyEvent (a) select 1 from b.win:length(1)");
        assertIsValid("insert into MyEvent (a, b) select 1 from b.win:length(1)");
        assertIsValid("insert into MyEvent (a, b, c) select 1 from b.win:length(1)");
        assertIsValid("insert istream into MyEvent select 1 from b.win:length(1)");
        assertIsValid("insert rstream into MyEvent select 1 from b.win:length(1)");

        // pattern inside EQL
        assertIsValid("select * from pattern [a=" + SupportBean.class.getName() + "]");
        assertIsValid("select * from pattern [a=" + SupportBean.class.getName() + "] as xyz");
        assertIsValid("select * from pattern [a=" + SupportBean.class.getName() + "].win:length(100) as xyz");
        assertIsValid("select * from pattern [a=" + SupportBean.class.getName() + "].win:length(100).std:someview() as xyz");
        assertIsValid("select * from xxx");

        // coalesce
        assertIsValid("select coalesce(tick.price, 0) from x");
        assertIsValid("select coalesce(tick.price, null, -1) from x");
        assertIsValid("select coalesce(tick.price, tick.price, tick.price, tick.price) from x");
    }

    public void testBitWiseCases() throws Exception
    {
        String className = SupportBean.class.getName();
        String eqlSmt = "select (intPrimitive & intBoxed) from " + className;
        assertIsValid(eqlSmt + ".win:lenght()");
        eqlSmt = "select boolPrimitive|boolBoxed from " + className;
        assertIsValid(eqlSmt + "().std:win(20)");
        eqlSmt = "select bytePrimitive^byteBoxed from " + className;
        assertIsValid(eqlSmt + "().win:some_view({})");
    }

    public void testIfThenElseCase() throws Exception
     {
         String className = SupportBean.class.getName();
         String eqlSmt = "select case when 1 then (a + 1) when 2 then (a*2) end from " + className;
         assertIsValid(eqlSmt + ".win:lenght()");
         eqlSmt = "select case a when 1 then (a + 1) end from " + className;
         assertIsValid(eqlSmt + ".win:lenght()");
         eqlSmt = "select case count(*) when 10 then sum(a) when 20 then max(a*b) end from " +  className;
         assertIsValid(eqlSmt + ".win:lenght()");
         eqlSmt = "select case (a>b) when true then a when false then b end from " +  className;
         assertIsValid(eqlSmt + ".win:lenght()");
         eqlSmt = "select case a when true then a when false then b end from " +  className;
         assertIsValid(eqlSmt + ".win:lenght()");
         eqlSmt = "select case when (a=b) then (a+b) when false then b end as p1 from " +  className;
         assertIsValid(eqlSmt + ".win:lenght()");
         eqlSmt = "select case (a+b) when (a*b) then count(a+b) when false then a ^ b end as p1 from " +  className;
         assertIsValid(eqlSmt + ".win:lenght()");
     }

    private void tryJoin(String joinType) throws Exception
    {
        String className = SupportBean.class.getName();
        assertIsValid("select intPrimitive from " +
                        className + "(a=1).win:lenght(10) as win1 " +
                        joinType + " outer join " +
                        className + "(a=2).win:lenght(10) as win2 " +
                        "on win1.f1 = win2.f2"
                        );

        assertIsValid("select intPrimitive from " +
                        className + "(a=1).win:lenght(10) as win1 " +
                        joinType + " outer join " +
                        className + "(a=2).win:lenght(10) as win2 " +
                        "on win1.f1 = win2.f2 " +
                        joinType + " outer join " +
                        className + "(a=2).win:lenght(10) as win3 " +
                        "on win1.f1 = win3.f3"
                        );
    }

    private void assertIsValid(String text) throws Exception
    {
        log.debug(".assertIsValid Trying text=" + text);
        AST ast = parse(text);
        log.debug(".assertIsValid success, tree walking...");

        SupportParserHelper.displayAST(ast);
        log.debug(".assertIsValid done");
    }

    private void assertIsInvalid(String text) throws Exception
    {
        log.debug(".assertIsInvalid Trying text=" + text);

        try
        {
            parse(text);
            assertFalse(true);
        }
        catch (Exception ex)
        {
            log.debug(".assertIsInvalid Expected ParseException exception was thrown and ignored, message=" + ex.getMessage());
        }
    }

    private AST parse(String expression) throws Exception
    {
        return SupportParserHelper.parseEQL(expression);
    }

    static Log log = LogFactory.getLog(TestEQLParser.class);
}