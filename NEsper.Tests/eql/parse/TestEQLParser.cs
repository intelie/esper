using System;

using antlr.collections;

using net.esper.eql.generated;
using net.esper.support.bean;
using net.esper.support.eql.parse;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
    // EqlTokenTypes	
    [TestFixture]
    public class TestEQLParser
    {
        [Test]
        public virtual void testDisplayAST()
        {
            String className = typeof(SupportBean).FullName;
            String expression = "select myclass.googlex(1) from " + className;
            //String expression = "select googlex(1) from " + className;

            log.Debug(".testDisplayAST parsing: " + expression);
            AST ast = parse(expression);
            SupportParserHelper.displayAST(ast);

            log.Debug(".testDisplayAST walking...");
            EQLTreeWalker walker = new EQLTreeWalker(SupportEventAdapterService.Service);
            walker.startEQLExpressionRule(ast);
        }

        [Test]
        public virtual void testInvalidCases()
        {
            String className = typeof(SupportBean).FullName;

            assertIsInvalid(className + "(val=10000).");
            assertIsInvalid(className + "().a:someview");
            assertIsInvalid(className + "().a:someview(");
            assertIsInvalid(className + "().a:someview)");
            assertIsInvalid(className + "().length()");
            assertIsInvalid(className + "().:length()");
            assertIsInvalid(className + "().win:length(0,)");
            assertIsInvalid(className + "().win:length(,0)");
            assertIsInvalid(className + "().win:length(0,0,)");
            assertIsInvalid(className + "().win:length(0,0,\")");
            assertIsInvalid(className + "().win:length(\"\"5)");
            assertIsInvalid(className + "().win:length(,\"\")");
            assertIsInvalid(className + "().win:length.(,\"\")");
            assertIsInvalid(className + "().win:length().");
            assertIsInvalid(className + "().win:length().length");
            assertIsInvalid(className + "().win:length().length(");
            assertIsInvalid(className + "().win:length().length)");
            assertIsInvalid(className + "().win:length().length().");
            assertIsInvalid(className + "().win:length().length().length");
            assertIsInvalid(className + "().win:length({}))");
            assertIsInvalid(className + "().win:length({\"s\")");
            assertIsInvalid(className + "().win:length(\"s\"})");
            assertIsInvalid(className + "().win:length({{\"s\"})");
            assertIsInvalid(className + "().win:length({{\"s\"}})");
            assertIsInvalid(className + "().win:length({\"s\"}})");
            assertIsInvalid(className + "().win:length('s\"");
            assertIsInvalid(className + "().win:length(\"s')");

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

            // time periods
            assertIsInvalid("select * from x.win:time(sec 99)");
            assertIsInvalid("select * from x.win:time(99 min min)");
            assertIsInvalid("select * from x.win:time(88 sec day)");
            assertIsInvalid("select * from x.win:time(1 sec 88 days)");
            assertIsInvalid("select * from x.win:time(1 day 2 hours 1 day)");

            // in
            assertIsInvalid("select * from x where a in()");
            assertIsInvalid("select * from x where a in(a,)");
            assertIsInvalid("select * from x where a in(,a)");
            assertIsInvalid("select * from x where a in(, ,)");
            assertIsInvalid("select * from x where a in not(1,2)");

            // between
            assertIsInvalid("select * from x where between a");
            assertIsInvalid("select * from x where between and b");
            assertIsInvalid("select * from x where between in and b");
            assertIsInvalid("select * from x where between");

            // like and regexp
            assertIsInvalid("select * from x where like");
            assertIsInvalid("select * from x where like escape");
            assertIsInvalid("select * from x where like a escape");
            assertIsInvalid("select * from x where escape");
            assertIsInvalid("select * from x where field rlike 'aa' escape '!'");
            assertIsInvalid("select * from x where field regexp 'aa' escape '!'");
            assertIsInvalid("select * from x where regexp 'aa'");
            assertIsInvalid("select * from x where a like b escape c");

            // database join
            assertIsInvalid("select * from x, sql ");
            assertIsInvalid("select * from x, sql:xx ");
            assertIsInvalid("select * from x, sql:xx ");
            assertIsInvalid("select * from x, sql:xx [' dsfsdf \"]");
            assertIsInvalid("select * from x, sql:xx [\"sfsf ']");
        }

        [Test]
        public virtual void testValidCases()
        {
            String className = typeof(SupportBean).FullName;
            String preFill = "select * from " + className;

            assertIsValid(preFill + "(string='test',intPrimitive=20).win:length(100)");
            assertIsValid(preFill + "().win:length()");
            assertIsValid(preFill + "().win:length(4,5)");
            assertIsValid(preFill + "().win:length(4)");
            assertIsValid(preFill + "().win:length(\"\",5)");
            assertIsValid(preFill + "().win:length(10.9,1E30,-4.4,\"\",5)");
            assertIsValid(preFill + "().win:length(4).n:c(3.3, -3.3).n:some(\"price\")");
            assertIsValid(preFill + "().win:length().n:c().n:da().n:e().n:f().n:g().n:xh(2.0)");
            assertIsValid(preFill + "().win:length({\"s\"})");
            assertIsValid(preFill + "().win:length({\"a\",\"b\"})");
            assertIsValid(preFill + "().win:length({\"a\",\"b\",\"c\"})");
            assertIsValid(preFill + "().win:length('')");
            assertIsValid(preFill + "().win:length('s')");
            assertIsValid(preFill + "().win:length('s',5)");
            assertIsValid(preFill + "().win:length({'s','t'},5)");
            assertIsValid(preFill + "().win:some_window('count','l','a').std:lastevent('s','tyr')");
            assertIsValid(preFill + "().win:some_view({'count'},'l','a')");
            assertIsValid(preFill + "().win:some_view({})");
            assertIsValid(preFill + "(string != 'test').win:length(100)");

            assertIsValid("select max(intPrimitive, intBoxed) from " + className + "().std:win(20)");
            assertIsValid("select max(intPrimitive, intBoxed, longBoxed) from " + className + "().std:win(20)");
            assertIsValid("select min(intPrimitive, intBoxed) from " + className + "().std:win(20)");
            assertIsValid("select min(intPrimitive, intBoxed, longBoxed) from " + className + "().std:win(20)");

            assertIsValid(preFill + "().win:length(3) where a = null");
            assertIsValid(preFill + "().win:length(3) where a is null");
            assertIsValid(preFill + "().win:length(3) where 10 is a");
            assertIsValid(preFill + "().win:length(3) where 10 is not a");
            assertIsValid(preFill + "().win:length(3) where 10 <> a");
            assertIsValid(preFill + "().win:length(3) where a <> 10");
            assertIsValid(preFill + "().win:length(3) where a != 10");
            assertIsValid(preFill + "().win:length(3) where 10 != a");
            assertIsValid(preFill + "().win:length(3) where not (a = 5)");
            assertIsValid(preFill + "().win:length(3) where not (a = 5 or b = 3)");
            assertIsValid(preFill + "().win:length(3) where not 5 < 4");
            assertIsValid(preFill + "().win:length(3) where a or (not b)");
            assertIsValid(preFill + "().win:length(3) where a % 3 + 6 * (c%d)");
            assertIsValid(preFill + "().win:length(3) where a || b = 'a'");
            assertIsValid(preFill + "().win:length(3) where a || b || c = 'a'");
            assertIsValid(preFill + "().win:length(3) where a + b + c = 'a'");

            assertIsValid("select not a, not (b), not (a > 5) from " + className + "(a=1).win:length(10) as win1," + className + "(a=2).win:length(10) as win2 " + "where win1.f1 = win2.f2");

            assertIsValid("select intPrimitive from " + className + "(a=1).win:length(10) as win1," + className + "(a=2).win:length(10) as win2 " + "where win1.f1 = win2.f2");

            // outer joins
            tryJoin("left");
            tryJoin("right");
            tryJoin("full");

            // complex property access
            assertIsValid(
                "select array[1], map('a'), map(\"b\"), nested.nested " +
                "from a.b(string='test',intPrimitive=20).win:length(100) " +
                "where array[1].map('a').nested = 5");

            assertIsValid(
                "select array[1] as b " + "from a.b(string[0]='test').win:length(100) as x " +
                "left outer join " + "a.b(string[0]='test').win:length(100) as y " +
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
            assertIsValid("select 1 from b.win:length(1) group by a having a"); // a could be boolean
            assertIsValid("select 1 from b.win:length(1) having a>5");

            // insert into
            assertIsValid("insert into MyEvent select 1 from b.win:length(1)");
            assertIsValid("insert into MyEvent (a) select 1 from b.win:length(1)");
            assertIsValid("insert into MyEvent (a, b) select 1 from b.win:length(1)");
            assertIsValid("insert into MyEvent (a, b, c) select 1 from b.win:length(1)");
            assertIsValid("insert istream into MyEvent select 1 from b.win:length(1)");
            assertIsValid("insert rstream into MyEvent select 1 from b.win:length(1)");

            // pattern inside EQL
            assertIsValid("select * from pattern [a=" + typeof(SupportBean).FullName + "]");
            assertIsValid("select * from pattern [a=" + typeof(SupportBean).FullName + "] as xyz");
            assertIsValid("select * from pattern [a=" + typeof(SupportBean).FullName + "].win:length(100) as xyz");
            assertIsValid("select * from pattern [a=" + typeof(SupportBean).FullName + "].win:length(100).std:someview() as xyz");
            assertIsValid("select * from xxx");
            assertIsValid("select rstream * from xxx");
            assertIsValid("select istream * from xxx");
            assertIsValid("select rstream 1, 2 from xxx");
            assertIsValid("select istream 1, 2 from xxx");

            // coalesce
            assertIsValid("select coalesce(tick.price, 0) from x");
            assertIsValid("select coalesce(tick.price, null, -1) from x");
            assertIsValid("select coalesce(tick.price, tick.price, tick.price, tick.price) from x");

            // time intervals
            assertIsValid("select * from x.win:time(1 seconds)");
            assertIsValid("select * from x.win:time(1.5 second)");
            assertIsValid("select * from x.win:time(120230L sec)");
            assertIsValid("select * from x.win:time(1.5d milliseconds)");
            assertIsValid("select * from x.win:time(1E30 millisecond)");
            assertIsValid("select * from x.win:time(1.0 msec)");
            assertIsValid("select * from x.win:time(0001 minutes)");
            assertIsValid("select * from x.win:time(.1 minute)");
            assertIsValid("select * from x.win:time(1.1111001 min)");
            assertIsValid("select * from x.win:time(5 hours)");
            assertIsValid("select * from x.win:time(5 hour)");
            assertIsValid("select * from x.win:time(5 days)");
            assertIsValid("select * from x.win:time(5 day)");
            assertIsValid("select * from x.win:time(5 days 2 hours 88 minutes 1 seconds 9.8 milliseconds)");
            assertIsValid("select * from x.win:time(5 day 2 hour 88 minute 1 second 9.8 millisecond)");
            assertIsValid("select * from x.win:time(5 days 2 hours 88 minutes 1 seconds)");
            assertIsValid("select * from x.win:time(5 days 2 hours 88 minutes)");
            assertIsValid("select * from x.win:time(5 days 2 hours)");
            assertIsValid("select * from x.win:time(2 hours 88 minutes 1 seconds 9.8 milliseconds)");
            assertIsValid("select * from x.win:time(2 hours 88 minutes 1 seconds)");
            assertIsValid("select * from x.win:time(2 hours 88 minutes)");
            assertIsValid("select * from x.win:time(88 minutes 1 seconds 9.8 milliseconds)");
            assertIsValid("select * from x.win:time(88 minutes 1 seconds)");
            assertIsValid("select * from x.win:time(1 seconds 9.8 milliseconds)");
            assertIsValid("select * from x.win:time(1 seconds 9.8 milliseconds).win:goodie(1 sec)");
            assertIsValid("select * from x.win:time(1 seconds 9.8 milliseconds).win:goodie(1 sec).win:otto(1.1 days 1.1 msec)");

            // in
            assertIsValid("select * from x where a in('a')");
            assertIsValid("select * from x where abc in ('a', 'b')");
            assertIsValid("select * from x where abc in (8*2, 1.001, 'a' || 'b', coalesce(0,null), null)");
            assertIsValid("select * from x where abc in (sum(x), max(2,2), true)");
            assertIsValid("select * from x where abc in (y,z, y+z)");
            assertIsValid("select * from x where abc not in (1)");
            assertIsValid("select * from x where abc not in (1, 2, 3)");
            assertIsValid("select * from x where abc*2/dog not in (1, 2, 3)");

            // between
            assertIsValid("select * from x where abc between 1 and 10");
            assertIsValid("select * from x where abc between 'a' and 'x'");
            assertIsValid("select * from x where abc between 1.1 and 1E1000");
            assertIsValid("select * from x where abc between a and b");
            assertIsValid("select * from x where abc between a*2 and sum(b)");
            assertIsValid("select * from x where abc*3 between a*2 and sum(b)");

            // custom aggregation func
            assertIsValid("select myfunc(price) from x");

            // like and regexp
            assertIsValid("select * from x where abc like 'dog'");
            assertIsValid("select * from x where abc like '_dog'");
            assertIsValid("select * from x where abc like '%dog'");
            assertIsValid("select * from x where abc like null");
            assertIsValid("select * from x where abc like '%dog' escape '\\\\'");
            assertIsValid("select * from x where abc like '%dog%' escape '!'");
            assertIsValid("select * from x where abc like '%dog' escape \"a\"");
            assertIsValid("select * from x where abc||'hairdo' like 'dog'");
            assertIsValid("select * from x where abc not like 'dog'");
            assertIsValid("select * from x where abc not regexp '[a-z]'");
            assertIsValid("select * from x where abc regexp '[a-z]'");
            assertIsValid("select * from x where a like b escape 'aa'");

            // database joins
            assertIsValid("select * from x, sql:mydb [\"whetever SQL $x.id google\"]");
            assertIsValid("select * from x, sql:mydb ['whetever SQL $x.id google']");
            assertIsValid("select * from x, sql:mydb ['']");
            assertIsValid("select * from x, sql:mydb ['   ']");
        }

        [Test]
        public virtual void testBitWiseCases()
        {
            String className = typeof(SupportBean).FullName;
            String eqlSmt = "select (intPrimitive & intBoxed) from " + className;
            assertIsValid(eqlSmt + ".win:length()");
            eqlSmt = "select boolPrimitive|boolBoxed from " + className;
            assertIsValid(eqlSmt + "().std:win(20)");
            eqlSmt = "select bytePrimitive^byteBoxed from " + className;
            assertIsValid(eqlSmt + "().win:some_view({})");
        }

        [Test]
        public virtual void testIfThenElseCase()
        {
            String className = typeof(SupportBean).FullName;
            String eqlSmt = "select case when 1 then (a + 1) when 2 then (a*2) end from " + className;
            assertIsValid(eqlSmt + ".win:length()");
            eqlSmt = "select case a when 1 then (a + 1) end from " + className;
            assertIsValid(eqlSmt + ".win:length()");
            eqlSmt = "select case count(*) when 10 then sum(a) when 20 then max(a*b) end from " + className;
            assertIsValid(eqlSmt + ".win:length()");
            eqlSmt = "select case (a>b) when true then a when false then b end from " + className;
            assertIsValid(eqlSmt + ".win:length()");
            eqlSmt = "select case a when true then a when false then b end from " + className;
            assertIsValid(eqlSmt + ".win:length()");
            eqlSmt = "select case when (a=b) then (a+b) when false then b end as p1 from " + className;
            assertIsValid(eqlSmt + ".win:length()");
            eqlSmt = "select case (a+b) when (a*b) then count(a+b) when false then a ^ b end as p1 from " + className;
            assertIsValid(eqlSmt + ".win:length()");
        }

        private void tryJoin(String joinType)
        {
            String className = typeof(SupportBean).FullName;
            assertIsValid("select intPrimitive from " + className + "(a=1).win:length(10) as win1 " + joinType + " outer join " + className + "(a=2).win:length(10) as win2 " + "on win1.f1 = win2.f2");

            assertIsValid("select intPrimitive from " + className + "(a=1).win:length(10) as win1 " + joinType + " outer join " + className + "(a=2).win:length(10) as win2 " + "on win1.f1 = win2.f2 " + joinType + " outer join " + className + "(a=2).win:length(10) as win3 " + "on win1.f1 = win3.f3");
        }

        private void assertIsValid(String text)
        {
            log.Debug(".assertIsValid Trying text=" + text);
            AST ast = parse(text);
            log.Debug(".assertIsValid success, tree walking...");

            SupportParserHelper.displayAST(ast);
            log.Debug(".assertIsValid done");
        }

        private void assertIsInvalid(String text)
        {
            log.Debug(".assertIsInvalid Trying text=" + text);

            try
            {
                parse(text);
                Assert.IsFalse(true);
            }
            catch (System.Exception ex)
            {
                log.Debug(".assertIsInvalid Expected ParseException exception was thrown and ignored, message=" + ex.Message);
            }
        }

        private AST parse(String expression)
        {
            return SupportParserHelper.parseEQL(expression);
        }

        internal static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}