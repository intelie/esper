///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using antlr.collections;

using NUnit.Framework;

using net.esper.eql.generated;
using net.esper.support.bean;
using net.esper.support.eql.parse;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
	[TestFixture]
	public class TestEQLParser : EqlTokenTypes
	{
	    [Test]
	    public void TestDisplayAST()
	    {
	        String className = typeof(SupportBean).FullName;
	        String expression = "select b in (select * from A) from " + className;

	        log.Debug(".testDisplayAST parsing: " + expression);
	        AST ast = Parse(expression);
	        SupportParserHelper.DisplayAST(ast);

	        log.Debug(".testDisplayAST walking...");
	        EQLTreeWalker walker = SupportEQLTreeWalkerFactory.MakeWalker();
	        walker.startEQLExpressionRule(ast);
	    }

	    [Test]
	    public void TestInvalidCases()
	    {
	        String className = typeof(SupportBean).FullName;

	        AssertIsInvalid(className + "(val=10000).");
	        AssertIsInvalid(className + "().a:someview");
	        AssertIsInvalid(className + "().a:someview(");
	        AssertIsInvalid(className + "().a:someview)");
	        AssertIsInvalid(className + "().Lenght()");
	        AssertIsInvalid(className + "().:lenght()");
	        AssertIsInvalid(className + "().win:lenght(0,)");
	        AssertIsInvalid(className + "().win:lenght(,0)");
	        AssertIsInvalid(className + "().win:lenght(0,0,)");
	        AssertIsInvalid(className + "().win:lenght(0,0,\")");
	        AssertIsInvalid(className + "().win:lenght(\"\"5)");
	        AssertIsInvalid(className + "().win:lenght(,\"\")");
	        AssertIsInvalid(className + "().win:lenght.(,\"\")");
	        AssertIsInvalid(className + "().win:lenght().");
	        AssertIsInvalid(className + "().win:lenght().lenght");
	        AssertIsInvalid(className + "().win:Lenght().Lenght(");
	        AssertIsInvalid(className + "().win:lenght().lenght)");
	        AssertIsInvalid(className + "().win:Lenght().Lenght().");
	        AssertIsInvalid(className + "().win:Lenght().Lenght().lenght");
	        AssertIsInvalid(className + "().win:lenght({}))");
	        AssertIsInvalid(className + "().win:lenght({\"s\")");
	        AssertIsInvalid(className + "().win:lenght(\"s\"})");
	        AssertIsInvalid(className + "().win:lenght({{\"s\"})");
	        AssertIsInvalid(className + "().win:lenght({{\"s\"}})");
	        AssertIsInvalid(className + "().win:lenght({\"s\"}})");
	        AssertIsInvalid(className + "().win:lenght('s\"");
	        AssertIsInvalid(className + "().win:lenght(\"s')");

	        AssertIsInvalid("select * from com.Xxx().std:win(3) where a not is null");
	        AssertIsInvalid("select * from com.Xxx().std:win(3) where a = not null");
	        AssertIsInvalid("select * from com.Xxx().std:win(3) where not not");
	        AssertIsInvalid("select * from com.Xxx().std:win(3) where not ||");
	        AssertIsInvalid("select * from com.Xxx().std:win(3) where a ||");
	        AssertIsInvalid("select * from com.Xxx().std:win(3) where || a");

	        AssertIsInvalid("select a] from com.Xxx().std:win(3)");
	        AssertIsInvalid("select * from com.Xxx().std:win(3) where b('aaa)=5");

	        AssertIsInvalid("select Sum() from b.win:length(1)");
	        AssertIsInvalid("select Sum(?) from b.win:length(1)");
	        AssertIsInvalid("select Sum(1+) from b.win:length(1)");
	        AssertIsInvalid("select Sum(distinct) from b.win:length(1)");
	        AssertIsInvalid("select Sum(distinct distinct a) from b.win:length(1)");
	        AssertIsInvalid("select Avg() from b.win:length(1)");
	        AssertIsInvalid("select Count() from b.win:length(1)");
	        AssertIsInvalid("select Count(* *) from b.win:length(1)");
	        AssertIsInvalid("select Count(*2) from b.win:length(1)");
	        AssertIsInvalid("select Count(distinct *) from b.win:length(1)");
	        AssertIsInvalid("select Max(distinct *) from b.win:length(1)");
	        AssertIsInvalid("select Median() from b.win:length(1)");
	        AssertIsInvalid("select Stddev() from b.win:length(1)");
	        AssertIsInvalid("select Stddev(distinct) from b.win:length(1)");
	        AssertIsInvalid("select Avedev() from b.win:length(1)");
	        AssertIsInvalid("select Avedev(distinct) from b.win:length(1)");

	        // group-by
	        AssertIsInvalid("select 1 from b.win:length(1) group by");
	        AssertIsInvalid("select 1 from b.win:length(1) group by group");
	        AssertIsInvalid("select 1 from b.win:length(1) group a");
	        AssertIsInvalid("select 1 from b.win:length(1) group by a group by b");
	        AssertIsInvalid("select 1 from b.win:length(1) by a ");
	        AssertIsInvalid("select 1 from b.win:length(1) group by a a");
	        AssertIsInvalid("select 1 from b.win:length(1) group by a as dummy");

	        // having
	        AssertIsInvalid("select 1 from b.win:length(1) group by a having a>5,b<4");

	        // insert into
	        AssertIsInvalid("insert into select 1 from b.win:length(1)");
	        AssertIsInvalid("insert into 38484 select 1 from b.win:length(1)");
	        AssertIsInvalid("insert into A B select 1 from b.win:length(1)");
	        AssertIsInvalid("insert into A () select 1 from b.win:length(1)");
	        AssertIsInvalid("insert into A (a,) select 1 from b.win:length(1)");
	        AssertIsInvalid("insert into A (,) select 1 from b.win:length(1)");
	        AssertIsInvalid("insert into A(,a) select 1 from b.win:length(1)");
	        AssertIsInvalid("insert xxx into A(,a) select 1 from b.win:length(1)");

	        AssertIsInvalid("select Coalesce(tick.price) from x");

	        // time periods
	        AssertIsInvalid("select * from x.win:time(sec 99)");
	        AssertIsInvalid("select * from x.win:time(99 min min)");
	        AssertIsInvalid("select * from x.win:time(88 sec day)");
	        AssertIsInvalid("select * from x.win:time(1 sec 88 days)");
	        AssertIsInvalid("select * from x.win:time(1 day 2 hours 1 day)");

	        // in
	        AssertIsInvalid("select * from x where a In()");
	        AssertIsInvalid("select * from x where a In(a,)");
	        AssertIsInvalid("select * from x where a In(,a)");
	        AssertIsInvalid("select * from x where a In(, ,)");
	        AssertIsInvalid("select * from x where a in Not(1,2)");

	        // between
	        AssertIsInvalid("select * from x where between a");
	        AssertIsInvalid("select * from x where between and b");
	        AssertIsInvalid("select * from x where between in and b");
	        AssertIsInvalid("select * from x where between");

	        // like and regexp
	        AssertIsInvalid("select * from x where like");
	        AssertIsInvalid("select * from x where like escape");
	        AssertIsInvalid("select * from x where like a escape");
	        AssertIsInvalid("select * from x where escape");
	        AssertIsInvalid("select * from x where field rlike 'aa' escape '!'");
	        AssertIsInvalid("select * from x where field regexp 'aa' escape '!'");
	        AssertIsInvalid("select * from x where regexp 'aa'");
	        AssertIsInvalid("select * from x where a like b escape c");

	        // database join
	        AssertIsInvalid("select * from x, sql ");
	        AssertIsInvalid("select * from x, sql:xx ");
	        AssertIsInvalid("select * from x, sql:xx ");
	        AssertIsInvalid("select * from x, sql:xx [' dsfsdf \"]");
	        AssertIsInvalid("select * from x, sql:xx [\"sfsf ']");

	        // Previous and prior function
	        AssertIsInvalid("select Prev(10, a*b) from x");
	        AssertIsInvalid("select Prev(price, a*b) from x");
	        AssertIsInvalid("select Prior(10) from x");
	        AssertIsInvalid("select Prior(price, a*b) from x");

	        // subqueries
	        AssertIsInvalid("select (select a) from x");
	        AssertIsInvalid("select (select a from X group by b) from x");
	        AssertIsInvalid("select (select a from X, Y) from x");
	        AssertIsInvalid("select (select a,b from X) from x");
	        AssertIsInvalid("select (select a from ) from x");
	        AssertIsInvalid("select (select from X) from x");
	        AssertIsInvalid("select * from x where (select q from pattern [A->B])");
	        AssertIsInvalid("select c from A where q*9 in in (select g*5 from C.win:length(100)) and r=6");
	        AssertIsInvalid("select c from A in (select g*5 from C.win:length(100)) and r=6");
	        AssertIsInvalid("select c from A where a in (select g*5 from C.win:length(100)) 9");
	    }

	    [Test]
	    public void TestValidCases()
	    {
	        String className = typeof(SupportBean).FullName;
	        String preFill = "select * from " + className;

	        AssertIsValid(preFill + "(string='test',intPrimitive=20).win:lenght(100)");
	        AssertIsValid(preFill + "(string in ('b', 'a'))");
	        AssertIsValid(preFill + "(string in ('b'))");
	        AssertIsValid(preFill + "(string in ('b', 'c', 'x'))");
	        AssertIsValid(preFill + "(string in [1:2))");
	        AssertIsValid(preFill + "(string in [1:2])");
	        AssertIsValid(preFill + "(string in (1:2))");
	        AssertIsValid(preFill + "(string in (1:2])");
	        AssertIsValid(preFill + "(intPrimitive between 1 and 2)");
	        AssertIsValid(preFill + "(intPrimitive not between 1 and 2)");
	        AssertIsValid(preFill + "(intPrimitive not in [1:2])");
	        AssertIsValid(preFill + "(intPrimitive not in (1, 2, 3))");
	        AssertIsValid(preFill + "().win:lenght()");
	        AssertIsValid(preFill + "().win:lenght(4,5)");
	        AssertIsValid(preFill + "().win:lenght(4)");
	        AssertIsValid(preFill + "().win:lenght(\"\",5)");
	        AssertIsValid(preFill + "().win:lenght(10.9,1E30,-4.4,\"\",5)");
	        AssertIsValid(preFill + "().win:lenght(4).n:c(3.3, -3.3).n:some(\"price\")");
	        AssertIsValid(preFill + "().win:lenght().n:c().n:da().n:e().n:f().n:g().n:xh(2.0)");
	        AssertIsValid(preFill + "().win:lenght({\"s\"})");
	        AssertIsValid(preFill + "().win:lenght({\"a\",\"b\"})");
	        AssertIsValid(preFill + "().win:lenght({\"a\",\"b\",\"c\"})");
	        AssertIsValid(preFill + "().win:lenght('')");
	        AssertIsValid(preFill + "().win:lenght('s')");
	        AssertIsValid(preFill + "().win:lenght('s',5)");
	        AssertIsValid(preFill + "().win:lenght({'s','t'},5)");
	        AssertIsValid(preFill + "().win:some_window('count','l','a').std:lastevent('s','tyr')");
	        AssertIsValid(preFill + "().win:some_view({'count'},'l','a')");
	        AssertIsValid(preFill + "().win:some_view({})");
	        AssertIsValid(preFill + "(string != 'test').win:lenght(100)");
	        AssertIsValid(preFill + "(string in (1:2) and dodo=3 and lax like '%e%')");
	        AssertIsValid(preFill + "(string in (1:2) and dodo=3, lax like '%e%' and oppol / yyy = 5, Yunc(3))");

	        AssertIsValid("select Max(intPrimitive, intBoxed) from " + className + "().std:win(20)");
	        AssertIsValid("select Max(intPrimitive, intBoxed, longBoxed) from " + className + "().std:win(20)");
	        AssertIsValid("select Min(intPrimitive, intBoxed) from " + className + "().std:win(20)");
	        AssertIsValid("select Min(intPrimitive, intBoxed, longBoxed) from " + className + "().std:win(20)");

	        AssertIsValid(preFill + "().win:lenght(3) where a = null");
	        AssertIsValid(preFill + "().win:lenght(3) where a is null");
	        AssertIsValid(preFill + "().win:lenght(3) where 10 is a");
	        AssertIsValid(preFill + "().win:lenght(3) where 10 is not a");
	        AssertIsValid(preFill + "().win:lenght(3) where 10 <> a");
	        AssertIsValid(preFill + "().win:lenght(3) where a <> 10");
	        AssertIsValid(preFill + "().win:lenght(3) where a != 10");
	        AssertIsValid(preFill + "().win:lenght(3) where 10 != a");
	        AssertIsValid(preFill + "().win:lenght(3) where not (a = 5)");
	        AssertIsValid(preFill + "().win:lenght(3) where not (a = 5 or b = 3)");
	        AssertIsValid(preFill + "().win:lenght(3) where not 5 < 4");
	        AssertIsValid(preFill + "().win:lenght(3) where a or (not b)");
	        AssertIsValid(preFill + "().win:lenght(3) where a % 3 + 6 * (c%d)");
	        AssertIsValid(preFill + "().win:lenght(3) where a || b = 'a'");
	        AssertIsValid(preFill + "().win:lenght(3) where a || b || c = 'a'");
	        AssertIsValid(preFill + "().win:lenght(3) where a + b + c = 'a'");

	        AssertIsValid("select not a, not (b), not (a > 5) from " +
	                        className + "(a=1).win:lenght(10) as win1," +
	                        className + "(a=2).win:lenght(10) as win2 " +
	                        "where win1.f1 = win2.f2"
	                        );

	        AssertIsValid("select intPrimitive from " +
	                        className + "(a=1).win:lenght(10) as win1," +
	                        className + "(a=2).win:lenght(10) as win2 " +
	                        "where win1.f1 = win2.f2"
	                        );

	        // outer joins
	        TryJoin("left");
	        TryJoin("right");
	        TryJoin("full");

	        // complex property access
	        AssertIsValid("select array[1], Map('a'), Map(\"b\"), nested.nested " +
	                      "from a.b(string='test',intPrimitive=20).win:lenght(100) " +
	                      "where array[1].Map('a').nested = 5");
	        AssertIsValid("select array[1] as b " +
	                      "from a.b(string[0]='test').win:lenght(100) as x " +
	                      "left outer join " +
	                      "a.b(string[0]='test').win:lenght(100) as y " +
	                      "on y.array[1].Map('a').nested = x.nested2");
	        AssertIsValid("select a and b from b.win:length(1)");
	        AssertIsValid("select a or b from b.win:length(1)");
	        AssertIsValid("select a = b from b.win:length(1)");
	        AssertIsValid("select a != b from b.win:length(1)");

	        AssertIsValid("select Sum(a), Avg(b) from b.win:length(1)");
	        AssertIsValid("select Sum(all a), Avg(all b), Avg(all b/c) from b.win:length(1)");
	        AssertIsValid("select Sum(distinct a), Avg(distinct b) from b.win:length(1)");
	        AssertIsValid("select Sum(Sum(a)) from b.win:length(1)");
	        AssertIsValid("select Sum(3*a), Sum(a - b - c) from b.win:length(1)");
	        AssertIsValid("select Count(*), Count(a), Count(all b), Count(distinct 2*a), Count(5*a/2) from b.win:length(1)");
	        AssertIsValid("select Max(volume), Min(volume), Min(all volume/44), Min(distinct 2*a), Max(distinct 5*a/2) from b.win:length(1)");
	        AssertIsValid("select Median(volume), Median(all volume*2/3), Median(distinct 2*a) from b.win:length(1)");
	        AssertIsValid("select Stddev(volume), Stddev(all volume), Stddev(distinct 2*a) from b.win:length(1)");
	        AssertIsValid("select Avedev(volume), Avedev(all volume), Avedev(distinct 2*a) from b.win:length(1)");

	        // group-by
	        AssertIsValid("select Sum(a), x, y from b.win:length(1) group by a");
	        AssertIsValid("select 1 from b.win:length(1) where a=b and b=d group by a,b,3*x,max(4, 3),'a', \"a\", true, 5*(1+a+y/2)");
	        AssertIsValid("select 1 from b.win:length(1) where a"); // since a could be a bool
	        AssertIsValid("select Sum(distinct a), x, y from b.win:length(1) group by a");

	        // having
	        AssertIsValid("select Sum(a), x, y from b.win:length(1) group by a having x > y");
	        AssertIsValid("select 1 from b.win:length(1) where a=b and b=d group by a having (max(3*b - 2, 5) > 1) or 'a'=b");
	        AssertIsValid("select 1 from b.win:length(1) group by a having a");   // a could be bool
	        AssertIsValid("select 1 from b.win:length(1) having a>5");
	        AssertIsValid("SELECT 1 FROM b.win:length(1) WHERE a=b AND b=d GROUP BY a HAVING (max(3*b - 2, 5) > 1) OR 'a'=b");

	        // insert into
	        AssertIsValid("insert into MyEvent select 1 from b.win:length(1)");
	        AssertIsValid("insert into MyEvent (a) select 1 from b.win:length(1)");
	        AssertIsValid("insert into MyEvent (a, b) select 1 from b.win:length(1)");
	        AssertIsValid("insert into MyEvent (a, b, c) select 1 from b.win:length(1)");
	        AssertIsValid("insert istream into MyEvent select 1 from b.win:length(1)");
	        AssertIsValid("insert rstream into MyEvent select 1 from b.win:length(1)");

	        // pattern inside EQL
	        AssertIsValid("select * from pattern [a=" + typeof(SupportBean).FullName + "]");
	        AssertIsValid("select * from pattern [a=" + typeof(SupportBean).FullName + "] as xyz");
	        AssertIsValid("select * from pattern [a=" + typeof(SupportBean).FullName + "].win:length(100) as xyz");
	        AssertIsValid("select * from pattern [a=" + typeof(SupportBean).FullName + "].win:length(100).std:someview() as xyz");
	        AssertIsValid("select * from xxx");
	        AssertIsValid("select rstream * from xxx");
	        AssertIsValid("select istream * from xxx");
	        AssertIsValid("select rstream 1, 2 from xxx");
	        AssertIsValid("select istream 1, 2 from xxx");

	        // coalesce
	        AssertIsValid("select Coalesce(tick.price, 0) from x");
	        AssertIsValid("select Coalesce(tick.price, null, -1) from x");
	        AssertIsValid("select Coalesce(tick.price, tick.price, tick.price, tick.price) from x");

	        // time intervals
	        AssertIsValid("select * from x.win:time(1 seconds)");
	        AssertIsValid("select * from x.win:time(1.5 second)");
	        AssertIsValid("select * from x.win:time(120230L sec)");
	        AssertIsValid("select * from x.win:time(1.5d milliseconds)");
	        AssertIsValid("select * from x.win:time(1E30 millisecond)");
	        AssertIsValid("select * from x.win:time(1.0 msec)");
	        AssertIsValid("select * from x.win:time(0001 minutes)");
	        AssertIsValid("select * from x.win:time(.1 minute)");
	        AssertIsValid("select * from x.win:time(1.1111001 min)");
	        AssertIsValid("select * from x.win:time(5 hours)");
	        AssertIsValid("select * from x.win:time(5 hour)");
	        AssertIsValid("select * from x.win:time(5 days)");
	        AssertIsValid("select * from x.win:time(5 day)");
	        AssertIsValid("select * from x.win:time(5 days 2 hours 88 minutes 1 seconds 9.8 milliseconds)");
	        AssertIsValid("select * from x.win:time(5 day 2 hour 88 minute 1 second 9.8 millisecond)");
	        AssertIsValid("select * from x.win:time(5 days 2 hours 88 minutes 1 seconds)");
	        AssertIsValid("select * from x.win:time(5 days 2 hours 88 minutes)");
	        AssertIsValid("select * from x.win:time(5 days 2 hours)");
	        AssertIsValid("select * from x.win:time(2 hours 88 minutes 1 seconds 9.8 milliseconds)");
	        AssertIsValid("select * from x.win:time(2 hours 88 minutes 1 seconds)");
	        AssertIsValid("select * from x.win:time(2 hours 88 minutes)");
	        AssertIsValid("select * from x.win:time(88 minutes 1 seconds 9.8 milliseconds)");
	        AssertIsValid("select * from x.win:time(88 minutes 1 seconds)");
	        AssertIsValid("select * from x.win:time(1 seconds 9.8 milliseconds)");
	        AssertIsValid("select * from x.win:time(1 seconds 9.8 milliseconds).win:goodie(1 sec)");
	        AssertIsValid("select * from x.win:time(1 seconds 9.8 milliseconds).win:goodie(1 sec).win:otto(1.1 days 1.1 msec)");

	        // in
	        AssertIsValid("select * from x where a In('a')");
	        AssertIsValid("select * from x where abc in ('a', 'b')");
	        AssertIsValid("select * from x where abc in (8*2, 1.001, 'a' || 'b', Coalesce(0,null), null)");
	        AssertIsValid("select * from x where abc in (sum(x), Max(2,2), true)");
	        AssertIsValid("select * from x where abc in (y,z, y+z)");
	        AssertIsValid("select * from x where abc not in (1)");
	        AssertIsValid("select * from x where abc not in (1, 2, 3)");
	        AssertIsValid("select * from x where abc*2/dog not in (1, 2, 3)");

	        // between
	        AssertIsValid("select * from x where abc between 1 and 10");
	        AssertIsValid("select * from x where abc between 'a' and 'x'");
	        AssertIsValid("select * from x where abc between 1.1 and 1E1000");
	        AssertIsValid("select * from x where abc between a and b");
	        AssertIsValid("select * from x where abc between a*2 and Sum(b)");
	        AssertIsValid("select * from x where abc*3 between a*2 and Sum(b)");

	        // custom aggregation func
	        AssertIsValid("select Myfunc(price) from x");

	        // like and regexp
	        AssertIsValid("select * from x where abc like 'dog'");
	        AssertIsValid("select * from x where abc like '_dog'");
	        AssertIsValid("select * from x where abc like '%dog'");
	        AssertIsValid("select * from x where abc like null");
	        AssertIsValid("select * from x where abc like '%dog' escape '\\\\'");
	        AssertIsValid("select * from x where abc like '%dog%' escape '!'");
	        AssertIsValid("select * from x where abc like '%dog' escape \"a\"");
	        AssertIsValid("select * from x where abc||'hairdo' like 'dog'");
	        AssertIsValid("select * from x where abc not like 'dog'");
	        AssertIsValid("select * from x where abc not regexp '[a-z]'");
	        AssertIsValid("select * from x where abc regexp '[a-z]'");
	        AssertIsValid("select * from x where a like b escape 'aa'");

	        // database joins
	        AssertIsValid("select * from x, sql:mydb [\"whetever SQL $x.id google\"]");
	        AssertIsValid("select * from x, sql:mydb ['whetever SQL $x.id google']");
	        AssertIsValid("select * from x, sql:mydb ['']");
	        AssertIsValid("select * from x, sql:mydb ['   ']");

	        // Previous and prior function
	        AssertIsValid("select Prev(10, price) from x");
	        AssertIsValid("select Prev(0, price) from x");
	        AssertIsValid("select Prev(1000, price) from x");
	        AssertIsValid("select Prev(index, price) from x");
	        AssertIsValid("select Prior(10, price) from x");
	        AssertIsValid("select Prior(0, price) from x");
	        AssertIsValid("select Prior(1000, price) from x");
	        AssertIsValid("select Prior(2, symbol) from x");

	        // array constants and expressions
	        AssertIsValid("select {'a', 'b'} from x");
	        AssertIsValid("select {'a'} from x");
	        AssertIsValid("select {} from x");
	        AssertIsValid("select {'a', 'b'} as yyy from x");
	        AssertIsValid("select * from x where MyFunc.Func({1,2}, xx)");
	        AssertIsValid("select {1,2,3} from x");
	        AssertIsValid("select {1.1,'2',3E5, 7L} from x");
	        AssertIsValid("select * from x where oo = {1,2,3}");
	        AssertIsValid("select {a, b}, {c, d} from x");

	        // subqueries
	        AssertIsValid("select (select a from B) from x");
	        AssertIsValid("select (select a||b||c from B) from x");
	        AssertIsValid("select (select 3*222 from B) from x");
	        AssertIsValid("select (select 3*222 from B.win:length(100)) from x");
	        AssertIsValid("select (select x from B.win:length(100) where a=b) from x");
	        AssertIsValid("select (select x from B.win:length(100) where a=b), (select y from C.w:g().e:o(11)) from x");
	        AssertIsValid("select 3 + (select a from B) from x");
	        AssertIsValid("select (select x from B) / 100, 9 * (select y from C.w:g().e:o(11))/2 from x");
	        AssertIsValid("select * from x where id = (select a from B)");
	        AssertIsValid("select * from x where id = -1 * (select a from B)");
	        AssertIsValid("select * from x where id = (5-(select a from B))");
	        AssertIsValid("select * from X where (select a from B where X.f = B.a) or (select a from B where X.f = B.c)");
	        AssertIsValid("select * from X where exists (select * from B where X.f = B.a)");
	        AssertIsValid("select * from X where exists (select * from B)");
	        AssertIsValid("select * from X where not exists (select * from B where X.f = B.a)");
	        AssertIsValid("select * from X where not exists (select * from B)");
	        AssertIsValid("select exists (select * from B where X.f = B.a) from A");
	        AssertIsValid("select B or exists (select * from B) from A");
	        AssertIsValid("select c in (select * from C) from A");
	        AssertIsValid("select c from A where b in (select * from C)");
	        AssertIsValid("select c from A where b not in (select b from C)");
	        AssertIsValid("select c from A where q*9 not in (select g*5 from C.win:length(100)) and r=6");
	    }

	    [Test]
	    public void TestBitWiseCases()
	    {
	        String className = typeof(SupportBean).FullName;
	        String eqlSmt = "select (intPrimitive & intBoxed) from " + className;
	        AssertIsValid(eqlSmt + ".win:lenght()");
	        eqlSmt = "select boolPrimitive|boolBoxed from " + className;
	        AssertIsValid(eqlSmt + "().std:win(20)");
	        eqlSmt = "select bytePrimitive^byteBoxed from " + className;
	        AssertIsValid(eqlSmt + "().win:some_view({})");
	    }

	    [Test]
	    public void TestIfThenElseCase()
	     {
	         String className = typeof(SupportBean).FullName;
	         String eqlSmt = "select case when 1 then (a + 1) when 2 then (a*2) end from " + className;
	         AssertIsValid(eqlSmt + ".win:lenght()");
	         eqlSmt = "select case a when 1 then (a + 1) end from " + className;
	         AssertIsValid(eqlSmt + ".win:lenght()");
	         eqlSmt = "select case Count(*) when 10 then Sum(a) when 20 then Max(a*b) end from " +  className;
	         AssertIsValid(eqlSmt + ".win:lenght()");
	         eqlSmt = "select case (a>b) when true then a when false then b end from " +  className;
	         AssertIsValid(eqlSmt + ".win:lenght()");
	         eqlSmt = "select case a when true then a when false then b end from " +  className;
	         AssertIsValid(eqlSmt + ".win:lenght()");
	         eqlSmt = "select case when (a=b) then (a+b) when false then b end as p1 from " +  className;
	         AssertIsValid(eqlSmt + ".win:lenght()");
	         eqlSmt = "select case (a+b) when (a*b) then Count(a+b) when false then a ^ b end as p1 from " +  className;
	         AssertIsValid(eqlSmt + ".win:lenght()");
	     }

	    private void TryJoin(String joinType)
	    {
	        String className = typeof(SupportBean).FullName;
	        AssertIsValid("select intPrimitive from " +
	                        className + "(a=1).win:lenght(10) as win1 " +
	                        joinType + " outer join " +
	                        className + "(a=2).win:lenght(10) as win2 " +
	                        "on win1.f1 = win2.f2"
	                        );

	        AssertIsValid("select intPrimitive from " +
	                        className + "(a=1).win:lenght(10) as win1 " +
	                        joinType + " outer join " +
	                        className + "(a=2).win:lenght(10) as win2 " +
	                        "on win1.f1 = win2.f2 " +
	                        joinType + " outer join " +
	                        className + "(a=2).win:lenght(10) as win3 " +
	                        "on win1.f1 = win3.f3"
	                        );
	    }

	    private void AssertIsValid(String text)
	    {
	        log.Debug(".assertIsValid Trying text=" + text);
	        AST ast = Parse(text);
	        log.Debug(".assertIsValid success, tree walking...");

	        SupportParserHelper.DisplayAST(ast);
	        log.Debug(".assertIsValid done");
	    }

	    private void AssertIsInvalid(String text)
	    {
	        log.Debug(".assertIsInvalid Trying text=" + text);

	        try
	        {
	            Parse(text);
	            Assert.IsFalse(true);
	        }
	        catch (Exception ex)
	        {
	            log.Debug(".assertIsInvalid Expected ParseException exception was thrown and ignored, message=" + ex.Message);
	        }
	    }

	    private AST Parse(String expression)
	    {
	        return SupportParserHelper.ParseEQL(expression);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
