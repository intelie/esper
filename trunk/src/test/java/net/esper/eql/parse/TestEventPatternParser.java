package net.esper.eql.parse;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.esper.support.eql.parse.SupportParserHelper;
import net.esper.support.bean.SupportBean;
import net.esper.eql.generated.EqlTokenTypes;
import antlr.collections.AST;

public class TestEventPatternParser extends TestCase implements EqlTokenTypes
{
    public void testDisplayAST() throws Exception
    {
        String expression = "B(a('aa').b.c[1].d.e(\"ee\")=2)";

        log.debug(".testDisplayAST parsing: " + expression);
        AST ast = parse(expression);
        SupportParserHelper.displayAST(ast);
    }

    public void testInvalidCases() throws Exception
    {
        // Base vocabulary
        assertIsInvalid("");
        assertIsInvalid("a(");
        assertIsInvalid("a)");
        assertIsInvalid("a((");
        assertIsInvalid("a())");
        assertIsInvalid("a(a=)");
        assertIsInvalid("a(a=2 3)");
        assertIsInvalid("a(a=2a)");
        assertIsInvalid("a(a=\"2)");
        assertIsInvalid("a(a=1EE5)");
        assertIsInvalid("a(a=)");
        assertIsInvalid("a(=2)");
        assertIsInvalid("a(a=2b=3)");
        assertIsInvalid("a(a=2,)");
        assertIsInvalid("a(a=2,b=)");
        assertIsInvalid("a(a=2,b3)");
        assertIsInvalid("e(a2>b3)");
        assertIsInvalid("e(a2>)");
        assertIsInvalid("e(a2<d4)");
        assertIsInvalid("e(a2<=<4)");
        assertIsInvalid("e(a2>=>6)");
        assertIsInvalid("e(a2<>8)");
        assertIsInvalid("e(a in [])");
        assertIsInvalid("e(a in [1:])");
        assertIsInvalid("e(a in [:5])");
        assertIsInvalid("e(a in [:5)))");
        assertIsInvalid("e(a in ((:5)))");
        assertIsInvalid("e(a in [[:5)");
        assertIsInvalid("e(a in [1:5:8]");
        assertIsInvalid("e(a in [1,2])");
        assertIsInvalid("e(a in [1:2]");
        assertIsInvalid("e(a in [1:2] b=3)");

        // Followed by
        assertIsInvalid("every e() ->");
        assertIsInvalid("every e() -> b() ->");
        assertIsInvalid("e() -> b)");
        assertIsInvalid("e() -> -> b()");
        assertIsInvalid("e() -> every -> b()");
        assertIsInvalid("-> every e()");
        assertIsInvalid("e() -> f() -> g() -> every");

        // Or
        assertIsInvalid("e() or");
        assertIsInvalid("or e()");
        assertIsInvalid("a() or or e()");
        assertIsInvalid("a() or e() or");
        assertIsInvalid("f() or f() or f() o");

        // And
        assertIsInvalid("e() and");
        assertIsInvalid("and e()");
        assertIsInvalid("a() and and e()");
        assertIsInvalid("a() and e() and");
        assertIsInvalid("f() and f() and f() an");

        // Not
        assertIsInvalid("not e(");
        assertIsInvalid("n o t e()");
        assertIsInvalid("note a()");
        assertIsInvalid("a() not");
        assertIsInvalid("not f() not");
        assertIsInvalid("not not f()");
        assertIsInvalid("every not f()");
        assertIsInvalid("not every not f()");
        assertIsInvalid("every every not f()");

        // where a:b
        assertIsInvalid("a() where a:b(a=b)");
        assertIsInvalid("a() where a:b(A)");
        assertIsInvalid("a() where a:b(g=5l)");
        assertIsInvalid("a() where a:b(33s)");
        assertIsInvalid("a() where a:b(m=3.3)");
        assertIsInvalid("a() where a:b('+1E4)");
        assertIsInvalid("a() where a:b(o22L2)");
        assertIsInvalid("a() where a:b(x=2)");
        assertIsInvalid("a() where a:b(2) or b() every where a:b(3)");
        assertIsInvalid("a() where a:b(2) or b() not where a:b(3)");
        assertIsInvalid("where a:b(5) a()");
        assertIsInvalid("every where a:b(5) a()");
        assertIsInvalid("every a() where a:b(1) every");
        assertIsInvalid("(every a()) (where a:b(1))");
        assertIsInvalid("every ((every a(id=\"A1\")) where a:b(10l) and c()");

        assertIsInvalid("timer:interval(m=10)");
        assertIsInvalid("timer:interval(10) timer:interval(20)");
        assertIsInvalid("a() timer:interval(10)");
        assertIsInvalid("timer:interval(10) b()");
        assertIsInvalid("timer:interval(10) -> b() or timer:interval(20))");
        assertIsInvalid("timer:interval(x)");
        assertIsInvalid("a() or timer:interval(10) b()");
        assertIsInvalid("unmatched timer:interval()");

        // At
        assertIsInvalid("timer:at(,*,*,*,*)");
        assertIsInvalid("timer:at(,*,*,*,*,*)");
        assertIsInvalid("timer:at(*,,*,*,*)");
        assertIsInvalid("timer:at(*,*,*,*,)");
        assertIsInvalid("timer:at(*,*,*,*,*,)");
        assertIsInvalid("timer:at(*,*,*,*,*,)");
        assertIsInvalid("timer:at(*,*,*,*,G)");
        assertIsInvalid("timer:at(a,*,*,*,*)");
        assertIsInvalid("timer:at(*,*,=2*,*,*)");
        assertIsInvalid("timer:at(*,*,*,*,a=3) or b()");
        assertIsInvalid("timer:at(*,[],*,*,*)");
        assertIsInvalid("timer:at(*,[a],*,*,*)");
        assertIsInvalid("timer:at(*,[E1],*,*,*)");
        assertIsInvalid("timer:at(*,[a=3],*,*,*)");
        assertIsInvalid("timer:at(*,[2,,5],*,*,*)");
        assertIsInvalid("timer:at(*,[,2,5,7],*,*,*)");
        assertIsInvalid("timer:at(*,[2,5,7,],*,*,*)");
        assertIsInvalid("timer:at(*,[2,5][2,4],*,*,*)");
        assertIsInvalid("timer:at(*,*,x:y,*,*)");
        assertIsInvalid("timer:at(*,*,1:y,*,*)");
        assertIsInvalid("timer:at(*,*,1.0:2,*,*)");
        assertIsInvalid("timer:at(*,*,x:2,*,*)");
        assertIsInvalid("timer:at(*,*,:2,*,*)");
        assertIsInvalid("timer:at(*,*,2:,*,*)");
        assertIsInvalid("timer:at(*,*,:,*,*)");
        assertIsInvalid("timer:at(*,*,*/a,*,*)");
        assertIsInvalid("timer:at(*,*,0/*,*,*)");
        assertIsInvalid("timer:at(*,*,*/*,*,*)");
        assertIsInvalid("timer:at(*,*,5/5,*,*)");
        assertIsInvalid("timer:at([2:2x],*,*,*,*)");
        assertIsInvalid("timer:at(3:3],*,*,*,*)");
        assertIsInvalid("timer:at(3:3:3,*,*,*,*)");
        assertIsInvalid("timer:at([3,*,*,*,*)");
        assertIsInvalid("timer:at([3,*,*,*,*],q)");
        assertIsInvalid("timer:at(3,3,3,*,*,*])");

        // Custom EventObserver
        assertIsInvalid("a:b(");
        assertIsInvalid("a:b)");
        assertIsInvalid("a:b('a',, 10)");

        // Use of results
        assertIsInvalid("a=A() -> b=B(pb1=a.)");
        assertIsInvalid("a=A() -> b=B(pb1=.sss)");
        assertIsInvalid("a=A() -> b=B(pb1=1.sss)");
        assertIsInvalid("a=A() -> b=B(pb1=s.1)");
        assertIsInvalid("a=A() -> b=B(pb1=s.1)");
        assertIsInvalid("a=A() -> b=B(pb1 in [s.1:a.s2])");
        assertIsInvalid("a=A() -> b=B(pb1 in [s.x:a.1])");

        // Use of index, mapped, nested and combined properties
        assertIsInvalid("A(x[0 = 1)");
        assertIsInvalid("A(x 0] = 1)");
        assertIsInvalid("A(x(b = 1)");
        assertIsInvalid("A(x y) = 1)");
        assertIsInvalid("A(x[0][1] = 1)");
        assertIsInvalid("A(x[0]. = 1)");
        assertIsInvalid("A(x. = 1)");
        assertIsInvalid("A(x.y. = 1)");
        assertIsInvalid("A(.y = 1)");
        assertIsInvalid("A(y[0](g) = 1)");
        assertIsInvalid("A(y(g)..z = 1)");
        assertIsInvalid("A(x[aaa] = 1)");
        assertIsInvalid("A(x(1) = 1)");
        assertIsInvalid("A(x(aaa) = 1)");
    }

    public void testValidCases() throws Exception
    {
        String className = SupportBean.class.getName();

        // Base vocabulary
        assertIsValid(className);
        assertIsValid(className + "(intPrimitive=444)");
        assertIsValid(className + "(string=\"\")");
        assertIsValid(className + "(string=\"ddddd\")");
        assertIsValid(className + "(doubleBoxed=444E43)");
        assertIsValid(className + "(doubleBoxed=1.2345)");
        assertIsValid(className + "(doubleBoxed=0)");
        assertIsValid(className + "(doubleBoxed=-394847575)");
        assertIsValid(className + "(doublePrimitive=37374)");
        assertIsValid(className + "(doublePrimitive=+2)");
        assertIsValid(className + "(boolPrimitive=false)");
        assertIsValid(className + "(boolPrimitive=true)");
        assertIsValid(className + "(boolPrimitive=true,intPrimitive=4,string=\"d\")");
        assertIsValid(className + "(string=\"b\",\nbyteBoxed=3)");
        assertIsValid(className + "(intPrimitive<5,intBoxed>3,doublePrimitive<=2,doubleBoxed>=5)");
        assertIsValid(className + " (  intPrimitive<5, \n intBoxed>3, \n doublePrimitive<=2,\t doubleBoxed>=5)");
        assertIsValid(className + "(boolBoxed=true)");
        assertIsValid(className + "(intPrimitive in [1:2])");
        assertIsValid(className + "(intPrimitive in (1:2])");
        assertIsValid(className + "(intPrimitive in (1:2))");
        assertIsValid(className + "(intPrimitive in [1:2))");
        assertIsValid(className + "(intPrimitive in (-34243232:+342342343])");
        assertIsValid(className + "(longPrimitive in (-34243232L:+342342343l])");
        assertIsValid(className + "(doublePrimitive in [1E20:-1])");
        assertIsValid(className + "(doublePrimitive in [45775.2244502:1.345674))");
        assertIsValid(className + "(longPrimitive in [   1   :  2  ])");
        assertIsValid(className + "(intPrimitive in [1:2], longPrimitive=3)");
        assertIsValid(className + "(intPrimitive=3, string=\"a2\", longPrimitive in [1:2], doubleBoxed=3)");
        assertIsValid(className + "(intPrimitive in [2:10])");
        assertIsValid(className + "(doubleBoxed in [-0.00001:-0.1E2])");
        assertIsValid(className + "(doubleBoxed in [60.0:61.0])");

        // With name
        assertIsValid("se1=" + className);
        assertIsValid("er2=" + className + "(intPrimitive=444)");
        assertIsValid("x3=" + className + "(intPrimitive in [1:2])");
        assertIsValid("gamma=" + className + "(doubleBoxed in [-0.00001:-0.1E2])");

        // Every
        assertIsValid("every " + className);
        assertIsValid("every " + className + "(string=\"b\",\nintPrimitive=3)");
        assertIsValid("(every " + className + "())");
        assertIsValid("every(" + className + "())");
        assertIsValid("((every " + className + "()))");
        assertIsValid("every(every((every " + className + "())))");

        // Followed by
        assertIsValid(className + "() -> " + className);
        assertIsValid("every " + className + "() -> every " + className);
        assertIsValid(className + "() -> every " + className);
        assertIsValid("every " + className + "() -> " + className);
        assertIsValid("every " + className + "() -> every " + className);
        assertIsValid(className + "() -> " + className + "() -> " + className);

        // Or
        assertIsValid(className + "() or " + className);
        assertIsValid(className + "() or " + className + "() or " + className);
        assertIsValid(className + "() -> " + className + "() or " + className);
        assertIsValid(className + "() -> " + className + "() -> " + className + "() or " + className);
        assertIsValid("every " + className + "() -> every " + className + "() or every " + className);

        // And
        assertIsValid(className + " and " + className);
        assertIsValid(className + " and " + className + " and " + className);
        assertIsValid(className + " -> " + className + " and " + className);
        assertIsValid(className + " -> " + className + " -> " + className + "() and " + className);
        assertIsValid("every "  + className + "() -> every " + className + "() and every " + className);

        // Not
        assertIsValid("not " + className);
        assertIsValid("not (" + className + "())");
        assertIsValid("every(not (" + className + "()))");
        assertIsValid(className + "() and not " + className);
        assertIsValid(className + "() and " + className + "() and not " + className);
        assertIsValid("not " + className + "(intPrimitive=3) and " + className + "(doubleBoxed=3)");
        assertIsValid("(" + className + "() -> " + className + "()) and not " + className);
        assertIsValid("((" + className + "() and not " + className + "()) and not " + className + "())");
        assertIsValid(className + "() and not (" + className + "() -> " + className + "())");
        assertIsValid("every " + className + "() -> every " + className + "() and not " + className);
        assertIsValid("(not " + className + "())");
        assertIsValid("not (" + className + "())");

        // where
        assertIsValid(className + "() where a:b(5)");
        assertIsValid(className + "() where a:b(100354)");
        assertIsValid(className + "() where a:b(1595950)");
        assertIsValid("(" + className + "()) where a:b(5)");
        assertIsValid("every " + className + "() where a:b(45)");
        assertIsValid("not (" + className + "()) where a:b(5)");
        assertIsValid("every ((" + className + "())) where a:b(5)");
        assertIsValid("(every " + className + "()) where a:b(1000000)");
        assertIsValid("every ((every " + className + "(string=\"A1\")) where a:b(10))");
        assertIsValid(className + "() -> " + className + "() where a:b(10)");
        assertIsValid(className + "() -> (" + className + "() where a:b(10))");
        assertIsValid(className + "() or " + className + "() where a:b(10)");
        assertIsValid(className + "() or (" + className + "() where a:b(10))");
        assertIsValid("((" + className + "() where a:b(10)) or (" + className + "() where a:b(5))) where a:b(15)");

        // timer:interval
        assertIsValid("timer:interval(5)");
        assertIsValid("timer:interval(100354)");
        assertIsValid("timer:interval(15959500354)");
        assertIsValid("timer:interval(1595950)");
        assertIsValid("(timer:interval(5))");
        assertIsValid("every timer:interval(5)");
        assertIsValid("not timer:interval(5)");
        assertIsValid("every (timer:interval(10) where a:b(5))");
        assertIsValid("every timer:interval(10) where a:b(10)");
        assertIsValid("every ((every timer:interval(4)) where a:b(10))");
        assertIsValid(className + "() -> timer:interval(10)");
        assertIsValid("timer:interval(20) -> timer:interval(10)");
        assertIsValid("timer:interval(20) -> " + className + "(intPrimitive=3)");
        assertIsValid(className + "() -> every (timer:interval(10))");
        assertIsValid("timer:interval(30) or " + className + "() where a:b(10)");
        assertIsValid("timer:interval(30) or every timer:interval(40) where a:b(10)");
        assertIsValid(className + "() or (timer:interval(30) where a:b(10))");
        assertIsValid(className + "() and timer:interval(30)");
        assertIsValid("((" + className + "() where a:b(10)) or (" + className + "() where a:b(5))) where a:b(15)");

        // timer:at
        assertIsValid("timer:at(*,*,*,*,*)");
        assertIsValid("timer:at(*,*,*,*,*,*)");
        assertIsValid("timer:at(10,20,10,10,1)");
        assertIsValid("timer:at(0,00,1,1,0)");
        assertIsValid("timer:at(*,1,*,1,*)");
        assertIsValid("timer:at(1,0,*,11,1,0)");
        assertIsValid("timer:at([1],*,*,*,*,[2,3])");
        assertIsValid("timer:at([0,0,0,0,0,  0,  1],[3,5,7],[1],[1,2],[3,4],[2,3])");
        assertIsValid("timer:at(6:10,*,*,*,*)");
        assertIsValid("timer:at(6:10,1:2,1:4,2:3,0:0)");
        assertIsValid("timer:at(00000:00000,*,   1 : 2  ,*,*,9:10)");
        assertIsValid("timer:at(*/1, *, *, *, *)");
        assertIsValid("timer:at(*/1, * / 5 , */ 1, */ 1 , */1010210993,*/7)");
        assertIsValid("timer:at(40,3:12,[2,3],*,0:3)");
        assertIsValid("timer:at([2, */1, 1:5], [6:6, 6:6], [*/2, */3], [2,*/2,2,2:2], *, *)");

        // Custom EventObserver
        assertIsValid("a:b()");
        assertIsValid("a:b('a', 10, false, true, 20, 0.111, 1E6)");

        // Use of results
        assertIsValid("a=A() -> b=B(pb1=a.pa1)");
        assertIsValid("a=A() -> b=B(pb1 = a.pa1)");
        assertIsValid("a=A(x=y.a) -> b=B(pb1 = a.pa1, o=5, p=q.a)");
        assertIsValid("a=A() -> b=B(pb1 in [a.p1 : a.p2])");
        assertIsValid("a=A() -> b=B(pb1 in [100.1 : a.p2])");
        assertIsValid("a=A() -> b=B(pb1 in [a.xx : 200])");

        // Use of index, mapped, nested and combined properties
        assertIsValid("A(x[0] = 1)");
        assertIsValid("A(x[565656] = 1)");
        assertIsValid("A(x[565656] = 1, y[2]=2)");
        assertIsValid("A(x('home') = 1)");
        assertIsValid("A(x.y = 1)");
        assertIsValid("A(x.y.z = 1)");
        assertIsValid("A(x[1].y('g').z('r').zz = 1)");
        assertIsValid("A(x.y[11111].b.b = 1)");
        assertIsValid("A(x('1') = 1)");
        assertIsValid("A(x(\"1\") = 1)");
        assertIsValid("B(a('aa').b.c[1].d.e(\"ee\")=2)");
        assertIsValid("a=X -> b=Y(id=a.indexed[0])");

        // intervals specs
        assertIsValid("timer:interval(5 seconds)");
        assertIsValid("timer:interval(5 seconds 3.3 milliseconds)");
        assertIsValid("timer:interval(1 days 5 seconds 3.3 milliseconds)");
        assertIsValid("timer:interval(1 days 3.3 milliseconds)");
        assertIsValid("timer:interval(1 day 1E1 minute 3.3 milliseconds)");
        assertIsValid("timer:interval(1.0001 hours 1E1 minute)");
        assertIsValid("timer:interval(1.1 minute 2.2 seconds)");
        assertIsValid("A where timer:within(5 seconds)");
        assertIsValid("A where timer:within(1 days 1 milliseconds)");
        assertIsValid("A where timer:within(100 days 0.00001 millisecond)");
        assertIsValid("A where timer:within(100 hours 3 minutes 1.00001 millisecond)");
    }

    public void testParserNodeGeneration() throws Exception
    {
        String expression = "a(m=1) -> not b() or every c() and d() where a:b (5) and timer:interval(10)";

        log.debug(".testParserNodeGeneration parsing: " + expression);
        AST ast = parse(expression);
        SupportParserHelper.displayAST(ast);

        assertTrue(ast.getType() == FOLLOWED_BY_EXPR);

        // 2 Children: filter a  and  or-subexpression with the rest
        assertTrue(ast.getNumberOfChildren() == 2);
        assertTrue(ast.getFirstChild().getType() == EVENT_FILTER_EXPR);

        // Assert on or-subexpression
        AST orExpr = ast.getFirstChild().getNextSibling();
        assertTrue(orExpr.getType() == OR_EXPR);
        assertTrue(orExpr.getNumberOfChildren() == 2);
        assertTrue(orExpr.getFirstChild().getType() == NOT_EXPR);
        assertTrue(orExpr.getFirstChild().getNumberOfChildren() == 1);
        assertTrue(orExpr.getFirstChild().getFirstChild().getType() == EVENT_FILTER_EXPR);

        // Assert on and-subexpression
        AST andExpr = orExpr.getFirstChild().getNextSibling();
        assertTrue(andExpr.getType() == AND_EXPR);
        assertTrue(andExpr.getNumberOfChildren() == 3);
        assertTrue(andExpr.getFirstChild().getType() == EVERY_EXPR);
        assertTrue(andExpr.getFirstChild().getNumberOfChildren() == 1);
        assertTrue(andExpr.getFirstChild().getFirstChild().getType() == EVENT_FILTER_EXPR);

        // Assert on where a:b and timer:interval sub-expressions
        AST guardPostFix = andExpr.getFirstChild().getNextSibling();
        assertTrue(guardPostFix.getNumberOfChildren() == 4);
        assertTrue(guardPostFix.getFirstChild().getType() == EVENT_FILTER_EXPR);
        assertTrue(guardPostFix.getFirstChild().getNextSibling().getType() == IDENT);

        AST timerIntervalExpr = guardPostFix.getNextSibling();
        assertTrue(timerIntervalExpr.getNumberOfChildren() == 3);
        assertTrue(timerIntervalExpr.getFirstChild().getType() == IDENT);

        // The tree generated....
        /*
followedByExpression [18]
   filterExpression [15]
      a [17]
      = [49]
         m [41]
         1 [24]
   orExpression [6]
      not [8]
         filterExpression [15]
            b [17]
      andExpression [7]
         every [9]
            filterExpression [15]
               c [17]
         guardPostFix [19]
            filterExpression [15]
               d [17]
            a [41]
            b [41]
            5 [24]
         observerExpression [22]
            timer [41]
            interval [41]
            10 [24]
        */
    }

    //
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
        return SupportParserHelper.parsePattern(expression);
    }

    static Log log = LogFactory.getLog(TestEventPatternParser.class);
}
