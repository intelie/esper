using System;
using System.Reflection;

using net.esper.eql.core;
using net.esper.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{
    [TestFixture]
    public class TestExprStaticMethodNode
    {
        internal StaticMethodResolver staticMethodResolver;
        internal StreamTypeService streamTypeService;
        internal AutoImportService autoImportService;
        internal ExprNode intThree;
        internal ExprNode intFive;
        internal ExprNode shortNine;
        internal ExprNode doubleFour;
        internal ExprNode doubleEight;
        internal ExprNode stringTen;
        internal System.Reflection.MethodInfo maxInt;
        internal System.Reflection.MethodInfo maxDouble;

        [SetUp]
        public virtual void setUp()
        {
            streamTypeService = null;
            autoImportService = new AutoImportServiceImpl(new String[] { "System" });
            staticMethodResolver = new StaticMethodResolver();
            intThree = new ExprConstantNode(3);
            intFive = new ExprConstantNode(5);
            short nine = 9;
            shortNine = new ExprConstantNode(nine);
            doubleFour = new ExprConstantNode(4d);
            doubleEight = new ExprConstantNode(8d);
            stringTen = new ExprConstantNode("10");
            maxInt = typeof(Math).GetMethod("Max", new Type[] { typeof(int), typeof(int) });
            maxDouble = typeof(Math).GetMethod("Max", new Type[]{ typeof(double), typeof(double) });
        }

        [Test]
        public virtual void testMaxIntInt()
        {
            ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max");
            root.AddChildNode(intThree);
            root.AddChildNode(intFive);
            validate(root);

            Assert.AreEqual(maxInt, root.StaticMethod);
            Int32 result = Math.Max(3, 5);
            Assert.AreEqual(result, root.Evaluate(null));
        }

        [Test]
        public virtual void testIntegerInt()
		{
            Type thisType = GetType() ;
            MethodInfo staticMethod = thisType.GetMethod("staticIntMethod", new Type[] { typeof(int) });
			ExprStaticMethodNode parent = new ExprStaticMethodNode(thisType.Name, "staticIntMethod");
			ExprNode child = new ExprStaticMethodNode("Math", "max");
			child.AddChildNode(intThree);
			child.AddChildNode(intFive);
			parent.AddChildNode(child);
			validate(parent);
			
			Assert.AreEqual(staticMethod, parent.StaticMethod);
			int result = Math.Max(3, 5);
			Assert.AreEqual(result, parent.Evaluate(null));
		}

        [Test]
        public virtual void testMaxIntShort()
        {
            ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "Max");
            root.AddChildNode(intThree);
            root.AddChildNode(shortNine);
            validate(root);

            Assert.AreEqual(maxInt, root.StaticMethod);
            short nine = 9;
            Int32 result = Math.Max((short) 3, nine);
            Assert.AreEqual(result, root.Evaluate(null));
        }

        [Test]
        public virtual void testMaxDoubleInt()
        {
            ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max");
            root.AddChildNode(doubleEight);
            root.AddChildNode(intFive);
            validate(root);

            Assert.AreEqual(maxDouble, root.StaticMethod);
            Double result = Math.Max(8d, 5);
            Assert.AreEqual(result, root.Evaluate(null));
        }

        [Test]
        public virtual void testMaxDoubleDouble()
        {
            ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "max");
            root.AddChildNode(doubleEight);
            root.AddChildNode(doubleFour);
            validate(root);

            Assert.AreEqual(maxDouble, root.StaticMethod);
            Double result = Math.Max(8d, 4d);
            Assert.AreEqual(result, root.Evaluate(null));
        }

        [Test]
        public virtual void testPowDoubleDouble()
        {
            MethodInfo pow = typeof(Math).GetMethod("pow", new Type[] { typeof(double), typeof(double) });
            ExprStaticMethodNode root = new ExprStaticMethodNode("Math", "pow");
            root.AddChildNode(doubleEight);
            root.AddChildNode(doubleFour);
            validate(root);

            Assert.AreEqual(pow, root.StaticMethod);
            Double result = Math.Pow(8d, 4d);
            Assert.AreEqual(result, root.Evaluate(null));
        }

        private void validate(ExprNode node)
        {
            node.GetValidatedSubtree(streamTypeService, autoImportService);
        }

        public virtual void nonstaticMethod()
        {
        }

        public static void staticMethod()
        {
        }

        public static int staticIntMethod(int param)
        {
            return param;
        }
    }
}
