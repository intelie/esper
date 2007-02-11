using System;

using net.esper.support.bean;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.util
{
    [TestFixture]
    public class TestTypeHelper
    {
        [Test]
        public void testCoerceNumber()
        {
            Assert.AreEqual(1d, TypeHelper.CoerceNumber(1d, typeof(double?)));
            Assert.AreEqual(5d, TypeHelper.CoerceNumber(5, typeof(double?)));
            Assert.AreEqual(6d, TypeHelper.CoerceNumber((sbyte)6, typeof(double?)));
            Assert.AreEqual(3f, TypeHelper.CoerceNumber((long)3, typeof(float?)));
            Assert.AreEqual((short)2, TypeHelper.CoerceNumber((long)2, typeof(short?)));
            Assert.AreEqual(4, TypeHelper.CoerceNumber((long)4, typeof(int?)));
            Assert.AreEqual((sbyte)5, TypeHelper.CoerceNumber((long)5, typeof(sbyte?)));
            Assert.AreEqual(8l, TypeHelper.CoerceNumber((long)8, typeof(long?)));

            try
            {
                TypeHelper.CoerceNumber(10, typeof(int));
                Assert.Fail();
            }
            catch (ArgumentException)
            {
                // Expected
            }
        }

        [Test]
        public void testIsNumeric()
        {
            Type[] numericClasses = {
                typeof(float), typeof(float?), typeof(double), typeof(double?),
                typeof(sbyte), typeof(sbyte?), typeof(short), typeof(short?), typeof(int), typeof(int?),
                typeof(long), typeof(long?)
            };

            Type[] nonnumericClasses = {
                typeof(string), typeof(bool), typeof(bool?), typeof(TestFixture)
            };

            foreach (Type type in numericClasses)
            {
                Assert.IsTrue(TypeHelper.IsNumeric(type));
            }

            foreach (Type type in nonnumericClasses)
            {
                Assert.IsFalse(TypeHelper.IsNumeric(type));
            }
        }

        [Test]
        public void testGetBoxed()
        {
            Type[] primitiveClasses = {
                typeof(bool), typeof(float), typeof(double), typeof(sbyte), typeof(short), typeof(int), typeof(long), typeof(char)
            };

            Type[] boxedClasses = {
                typeof(bool?), typeof(float?), typeof(double?), typeof(sbyte?), typeof(short?), typeof(int?), typeof(long?), typeof(char?)
            };

            Type[] otherClasses = {
                typeof(string), typeof(TestFixture)
            };

            for (int i = 0; i < primitiveClasses.Length; i++)
            {
                Type boxed = TypeHelper.GetBoxedType(primitiveClasses[i]);
                Assert.AreEqual(boxed, boxedClasses[i]);
            }

            for (int i = 0; i < boxedClasses.Length; i++)
            {
                Type boxed = TypeHelper.GetBoxedType(boxedClasses[i]);
                Assert.AreEqual(boxed, boxedClasses[i]);
            }

            for (int i = 0; i < otherClasses.Length; i++)
            {
                Type boxed = TypeHelper.GetBoxedType(otherClasses[i]);
                Assert.AreEqual(boxed, otherClasses[i]);
            }
        }

        [Test]
        public void testIsAssignmentCompatible()
        {
            Assert.IsTrue(TypeHelper.IsAssignmentCompatible(typeof(bool), typeof(bool?)));
            Assert.IsFalse(TypeHelper.IsAssignmentCompatible(typeof(string), typeof(bool?)));
            Assert.IsFalse(TypeHelper.IsAssignmentCompatible(typeof(int), typeof(long?)));
            Assert.IsTrue(TypeHelper.IsAssignmentCompatible(typeof(long), typeof(long?)));
            Assert.IsTrue(TypeHelper.IsAssignmentCompatible(typeof(double), typeof(double)));
        }

        [Test]
        public void testIsBoolean()
        {
            Assert.IsTrue(TypeHelper.IsBoolean(typeof(bool?)));
            Assert.IsTrue(TypeHelper.IsBoolean(typeof(bool)));
            Assert.IsFalse(TypeHelper.IsBoolean(typeof(string)));
        }

        [Test]
        public void testGetCoercionType()
        {
            Assert.AreEqual(typeof(double?), TypeHelper.GetArithmaticCoercionType(typeof(double?), typeof(int)));
            Assert.AreEqual(typeof(double?), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(double)));
            Assert.AreEqual(typeof(long?), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(long)));
            Assert.AreEqual(typeof(long?), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(long)));
            Assert.AreEqual(typeof(float?), TypeHelper.GetArithmaticCoercionType(typeof(float), typeof(long)));
            Assert.AreEqual(typeof(float?), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(float)));
            Assert.AreEqual(typeof(int?), TypeHelper.GetArithmaticCoercionType(typeof(sbyte), typeof(int)));
            Assert.AreEqual(typeof(int?), TypeHelper.GetArithmaticCoercionType(typeof(int?), typeof(int)));

            try
            {
                TypeHelper.GetArithmaticCoercionType(typeof(string), typeof(float));
                Assert.Fail();
            }
            catch (CoercionException ex)
            {
                // Expected
            }

            try
            {
                TypeHelper.GetArithmaticCoercionType(typeof(int), typeof(bool));
                Assert.Fail();
            }
            catch (CoercionException ex)
            {
                // Expected
            }
        }

        [Test]
        public void testIsFloatingPointNumber()
        {
            double? testValueDouble = 1.0;
            float? testValueFloat = 1.0f;
            int? testValueInt = 1;

            Assert.IsTrue(TypeHelper.IsFloatingPointNumber(1d));
            Assert.IsTrue(TypeHelper.IsFloatingPointNumber(1f));
            Assert.IsTrue(TypeHelper.IsFloatingPointNumber(testValueDouble));
            Assert.IsTrue(TypeHelper.IsFloatingPointNumber(testValueFloat));

            Assert.IsFalse(TypeHelper.IsFloatingPointNumber(1));
            Assert.IsFalse(TypeHelper.IsFloatingPointNumber(testValueInt));
        }

        [Test]
        public void testIsFloatingPointClass()
        {
            Assert.IsTrue(TypeHelper.IsFloatingPointClass(typeof(double)));
            Assert.IsTrue(TypeHelper.IsFloatingPointClass(typeof(float)));
            Assert.IsTrue(TypeHelper.IsFloatingPointClass(typeof(double?)));
            Assert.IsTrue(TypeHelper.IsFloatingPointClass(typeof(float?)));

            Assert.IsFalse(TypeHelper.IsFloatingPointClass(typeof(string)));
            Assert.IsFalse(TypeHelper.IsFloatingPointClass(typeof(int)));
            Assert.IsFalse(TypeHelper.IsFloatingPointClass(typeof(int?)));
        }

        [Test]
        public void testGetCompareToCoercionType()
        {
            Assert.AreEqual(typeof(string), TypeHelper.GetCompareToCoercionType(typeof(string), typeof(string)));
            Assert.AreEqual(typeof(bool?), TypeHelper.GetCompareToCoercionType(typeof(bool?), typeof(bool?)));
            Assert.AreEqual(typeof(bool?), TypeHelper.GetCompareToCoercionType(typeof(bool?), typeof(bool)));
            Assert.AreEqual(typeof(bool?), TypeHelper.GetCompareToCoercionType(typeof(bool), typeof(bool?)));
            Assert.AreEqual(typeof(bool?), TypeHelper.GetCompareToCoercionType(typeof(bool), typeof(bool)));

            Assert.AreEqual(typeof(double?), TypeHelper.GetCompareToCoercionType(typeof(int), typeof(float)));
            Assert.AreEqual(typeof(double?), TypeHelper.GetCompareToCoercionType(typeof(double), typeof(sbyte)));
            Assert.AreEqual(typeof(double?), TypeHelper.GetCompareToCoercionType(typeof(float), typeof(float)));
            Assert.AreEqual(typeof(double?), TypeHelper.GetCompareToCoercionType(typeof(float), typeof(double?)));

            Assert.AreEqual(typeof(long?), TypeHelper.GetCompareToCoercionType(typeof(int), typeof(int)));
            Assert.AreEqual(typeof(long?), TypeHelper.GetCompareToCoercionType(typeof(short?), typeof(int?)));

            tryInvalidGetRelational(typeof(string), typeof(int));
            tryInvalidGetRelational(typeof(long?), typeof(string));
            tryInvalidGetRelational(typeof(long?), typeof(bool?));
            tryInvalidGetRelational(typeof(bool), typeof(int));
        }

        [Test]
        public void testGetBoxedClassName()
        {
            String[,] tests = new String[,] {
                {typeof(int?).FullName, typeof(int).FullName},
                {typeof(long?).FullName, typeof(long).FullName},
                {typeof(short?).FullName, typeof(short).FullName},
                {typeof(double?).FullName, typeof(double).FullName},
                {typeof(float?).FullName, typeof(float).FullName},
                {typeof(bool?).FullName, typeof(bool).FullName},
                {typeof(sbyte?).FullName, typeof(sbyte).FullName},
                {typeof(char?).FullName, typeof(char).FullName}
            };

            for (int i = 0; i < tests.Length; i++)
            {
                Assert.AreEqual(tests[i,0], TypeHelper.GetBoxedTypeName(tests[i,1]));
            }
        }

        [Test]
        public void testIsBuiltinDataType()
        {
            Type[] classesDataType = new Type[] {
                typeof(int), typeof(long?), typeof(double), typeof(bool), typeof(bool?),
                typeof(char), typeof(char?), typeof(string)
            };
            Type[] classesNotDataType = new Type[] {
                typeof(SupportBean), typeof(Math), typeof(Type)
            };

            for (int i = 0; i < classesDataType.Length; i++)
            {
                Assert.IsTrue(TypeHelper.IsBuiltinDataType(classesDataType[i]));
            }
            for (int i = 0; i < classesNotDataType.Length; i++)
            {
                Assert.IsFalse(TypeHelper.IsBuiltinDataType(classesNotDataType[i]));
            }
        }

        private void tryInvalidGetRelational(Type classOne, Type classTwo)
        {
            try
            {
                TypeHelper.GetCompareToCoercionType(classOne, classTwo);
                Assert.Fail();
            }
            catch (ArgumentException)
            {
                // Expected
            }
        }

        [Test]
        public void testGetCommonCoercionType()
        {
            Assert.AreEqual(typeof(string), TypeHelper.GetCommonCoercionType(new Type[] { typeof(string) }));
            Assert.AreEqual(typeof(bool?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool) }));
            Assert.AreEqual(typeof(long?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(long) }));

            Assert.AreEqual(typeof(string), TypeHelper.GetCommonCoercionType(new Type[] { typeof(string), null }));
            Assert.AreEqual(typeof(string), TypeHelper.GetCommonCoercionType(new Type[] { typeof(string), typeof(string) }));
            Assert.AreEqual(typeof(string), TypeHelper.GetCommonCoercionType(new Type[] { typeof(string), typeof(string), typeof(string) }));
            Assert.AreEqual(typeof(string), TypeHelper.GetCommonCoercionType(new Type[] { typeof(string), typeof(string), null }));
            Assert.AreEqual(typeof(string), TypeHelper.GetCommonCoercionType(new Type[] { null, typeof(string), null }));
            Assert.AreEqual(typeof(string), TypeHelper.GetCommonCoercionType(new Type[] { null, typeof(string), typeof(string) }));
            Assert.AreEqual(typeof(string), TypeHelper.GetCommonCoercionType(new Type[] { null, null, typeof(string), typeof(string) }));

            Assert.AreEqual(typeof(bool?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool?), typeof(bool?) }));
            Assert.AreEqual(typeof(bool?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool?), typeof(bool) }));
            Assert.AreEqual(typeof(bool?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool), typeof(bool?) }));
            Assert.AreEqual(typeof(bool?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool), typeof(bool) }));
            Assert.AreEqual(typeof(bool?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(bool?), typeof(bool), typeof(bool) }));
            Assert.AreEqual(typeof(int?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int), typeof(sbyte), typeof(int) }));
            Assert.AreEqual(typeof(int?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int?), typeof(sbyte?), typeof(short?) }));
            Assert.AreEqual(typeof(int?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(sbyte), typeof(short), typeof(short) }));
            Assert.AreEqual(typeof(double?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int?), typeof(sbyte?), typeof(double?) }));
            Assert.AreEqual(typeof(double?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(long?), typeof(double?), typeof(double?) }));
            Assert.AreEqual(typeof(double?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(double), typeof(sbyte) }));
            Assert.AreEqual(typeof(double?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(double), typeof(sbyte), null }));
            Assert.AreEqual(typeof(float?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(float), typeof(float) }));
            Assert.AreEqual(typeof(float?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(float), typeof(int) }));
            Assert.AreEqual(typeof(float?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int?), typeof(int), typeof(float?) }));
            Assert.AreEqual(typeof(long?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int?), typeof(int), typeof(long) }));
            Assert.AreEqual(typeof(long?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(long), typeof(int) }));
            Assert.AreEqual(typeof(long?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(long), typeof(int), typeof(int), typeof(int), typeof(sbyte), typeof(short) }));
            Assert.AreEqual(typeof(long?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(long), null, typeof(int), null, typeof(int), typeof(int), null, typeof(sbyte), typeof(short) }));
            Assert.AreEqual(typeof(long?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int?), typeof(int), typeof(long) }));
            Assert.AreEqual(typeof(char?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(char), typeof(char), typeof(char) }));
            Assert.AreEqual(typeof(long?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int), typeof(int), typeof(int), typeof(long), typeof(int), typeof(int) }));
            Assert.AreEqual(typeof(double?), TypeHelper.GetCommonCoercionType(new Type[] { typeof(int), typeof(long), typeof(int), typeof(double), typeof(int), typeof(int) }));
            Assert.AreEqual(null, TypeHelper.GetCommonCoercionType(new Type[] { null, null }));
            Assert.AreEqual(null, TypeHelper.GetCommonCoercionType(new Type[] { null, null, null }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { typeof(SupportBean), null, null }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { null, typeof(SupportBean), null }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { null, typeof(SupportBean) }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { null, null, typeof(SupportBean) }));
            Assert.AreEqual(typeof(SupportBean), TypeHelper.GetCommonCoercionType(new Type[] { typeof(SupportBean), null, typeof(SupportBean), typeof(SupportBean) }));

            tryInvalidGetCommonCoercionType(new Type[] { typeof(string), typeof(bool?) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(string), typeof(string), typeof(bool?) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(bool?), typeof(string), typeof(bool?) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(bool?), typeof(bool?), typeof(string) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(long), typeof(bool?), typeof(string) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(double), typeof(long), typeof(string) });
            tryInvalidGetCommonCoercionType(new Type[] { null, typeof(double), typeof(long), typeof(string) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(string), typeof(string), typeof(long) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(string), typeof(SupportBean) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(bool), null, null, typeof(string) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(int), null, null, typeof(string) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(SupportBean), typeof(bool?) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(string), typeof(SupportBean) });
            tryInvalidGetCommonCoercionType(new Type[] { typeof(SupportBean), typeof(string), typeof(SupportBean) });

            try
            {
                TypeHelper.GetCommonCoercionType(new Type[0]);
                Assert.Fail();
            }
            catch (ArgumentException)
            {
                // expected
            }
        }

        private void tryInvalidGetCommonCoercionType(Type[] types)
        {
            try
            {
                TypeHelper.GetCommonCoercionType(types);
                Assert.Fail();
            }
            catch (CoercionException)
            {
                // expected
            }
        }
    }
}
