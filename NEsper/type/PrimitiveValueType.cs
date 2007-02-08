using System;

namespace net.esper.type
{
	/// <summary>
	/// Enumeration of types of primitive values.
	/// </summary>

	public class PrimitiveValueType
	{
		/// <summary> Byte.</summary>
		public readonly static PrimitiveValueType BYTE = new PrimitiveValueType( "Byte" ) ;

		/// <summary> Short.</summary>
		public readonly static PrimitiveValueType SHORT = new PrimitiveValueType( "Short" );

		/// <summary> Integer.</summary>
		public readonly static PrimitiveValueType INTEGER = new PrimitiveValueType( "Int" ) ;

		/// <summary> Long.</summary>
		public readonly static PrimitiveValueType LONG = new PrimitiveValueType( "Long" ) ;

		/// <summary> Float.</summary>
		public readonly static PrimitiveValueType FLOAT = new PrimitiveValueType( "Single" );

		/// <summary> Double.</summary>
		public readonly static PrimitiveValueType DOUBLE = new PrimitiveValueType( "Double" ) ;

		/// <summary> Boolean.</summary>
		public readonly static PrimitiveValueType BOOL = new PrimitiveValueType( "Boolean" ) ;

		/// <summary> String.</summary>
		public readonly static PrimitiveValueType STRING = new PrimitiveValueType( "String" );

		private String typeName;

		private PrimitiveValueType( String typeName )
		{
			this.typeName = typeName;
		}

		/// <summary> Returns the name of the type.</summary>
		/// <returns> type name
		/// </returns>

		public String getTypeName()
		{
			return typeName;
		}

		public override String ToString()
		{
			return typeName;
		}
	}
}
