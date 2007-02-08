using System;
using System.Collections.Generic;

using net.esper.compat;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.util
{
	[TestFixture]
	public class TestSQLTypeMapUtil
	{
		[Test]
		public virtual void testMapping()
		{
			EDictionary<Int32, Type> testData = new EHashDictionary<Int32, Type>();
			testData.Put((int) System.Data.OleDb.OleDbType.Char, typeof(String));
			testData.Put((int) System.Data.OleDb.OleDbType.VarChar, typeof(String));
			testData.Put((int) System.Data.OleDb.OleDbType.LongVarChar, typeof(String));
			testData.Put((int) System.Data.OleDb.OleDbType.Numeric, typeof(System.Decimal));
			testData.Put((int) System.Data.OleDb.OleDbType.Decimal, typeof(System.Decimal));
			testData.Put((int) System.Data.OleDb.OleDbType.Boolean, typeof(bool));
			testData.Put(Types.BOOLEAN, typeof(bool));
			testData.Put((int) System.Data.OleDb.OleDbType.TinyInt, typeof(System.SByte));
			testData.Put((int) System.Data.OleDb.OleDbType.SmallInt, typeof(Int16));
			testData.Put((int) System.Data.OleDb.OleDbType.Integer, typeof(Int32));
			testData.Put((int) System.Data.OleDb.OleDbType.BigInt, typeof(Int64));
			testData.Put((int) System.Data.OleDb.OleDbType.Double, typeof(System.Single));
			testData.Put((int) System.Data.OleDb.OleDbType.Single, typeof(Double));
			testData.Put((int) System.Data.OleDb.OleDbType.Double, typeof(Double));
			testData.Put((int) System.Data.OleDb.OleDbType.Binary, typeof(sbyte[]));
			testData.Put((int) System.Data.OleDb.OleDbType.VarBinary, typeof(sbyte[]));
			testData.Put((int) System.Data.OleDb.OleDbType.LongVarBinary, typeof(sbyte[]));
			testData.Put((int) System.Data.OleDb.OleDbType.Date, typeof(DateTime));
			testData.Put((int) System.Data.OleDb.OleDbType.DBTimeStamp, typeof(DateTime));
			testData.Put((int) System.Data.OleDb.OleDbType.DBTime, typeof(DateTime));
			testData.Put((int) System.Data.OleDb.OleDbType.LongVarChar, typeof(System.Char[]));
			testData.Put((int) System.Data.OleDb.OleDbType.LongVarBinary, typeof(System.Byte[]));
			testData.Put(Types.ARRAY, typeof(java.sql.Array));
			testData.Put(Types.STRUCT, typeof(java.sql.Struct));
			testData.Put(Types.REF, typeof(java.sql.Ref));
			testData.Put(Types.DATALINK, typeof(Uri));
			
			foreach(int type in testData.Keys)
			{
				Type result = SQLTypeMapUtil.sqlTypeToClass(type, null);
				log.Debug(".testMapping Mapping " + type + " to " + result.getSimpleName());
				Assert.AreEqual(testData[type], result);
			}
			
			Assert.AreEqual(typeof(String), SQLTypeMapUtil.sqlTypeToClass(Types.JAVA_OBJECT, "System.String"));
			Assert.AreEqual(typeof(String), SQLTypeMapUtil.sqlTypeToClass(Types.DISTINCT, "System.String"));
		}

		[Test]
		public virtual void testMappingInvalid()
		{
			tryInvalid( Types.JAVA_OBJECT, null );
			tryInvalid( Types.JAVA_OBJECT, "xx" );
			tryInvalid( Types.DISTINCT, null );
			tryInvalid( Int32.MaxValue, "yy" );
		}

		private void tryInvalid( int type, String classname )
		{
			try
			{
				SQLTypeMapUtil.sqlTypeToClass( type, classname );
				Assert.Fail();
			}
			catch ( ArgumentException ex )
			{
				// expected
			}
		}

		private static readonly Log log = LogFactory.GetLog( typeof( TestSQLTypeMapUtil ) );
	}
}
