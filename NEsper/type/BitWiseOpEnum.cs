using System;
using System.Collections.Generic;

using net.esper.collection;

namespace net.esper.type
{
	/// <summary>
	/// Enum representing relational types of operation.
	/// </summary>

	public class BitWiseOpEnum
	{
		/// <summary>
		/// Bitwise and.
		/// </summary>

		public static readonly BitWiseOpEnum BAND = new BitWiseOpEnum( "&" );

		/// <summary>
		/// Bitwise or.
		/// </summary>

		public static readonly BitWiseOpEnum BOR = new BitWiseOpEnum( "|" );

		/// <summary>
		/// Bitwise xor.
		/// </summary>

		public static readonly BitWiseOpEnum BXOR = new BitWiseOpEnum( "^" );

		private static IDictionary<MultiKey<Object>, BitWiseOpEnum.Computer> computers;

		private String expressionText;

		private BitWiseOpEnum( String expressionText )
		{
			this.expressionText = expressionText;
		}

        /// <summary>
        /// Initializes the <see cref="BitWiseOpEnum"/> class.
        /// </summary>
		
        static BitWiseOpEnum()
		{
			computers = new Dictionary<MultiKey<Object>, BitWiseOpEnum.Computer>();
			computers.Add( new MultiKey<Object>( new Object[] { typeof( sbyte? ),  BAND } ), BAndByte );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( short? ), BAND } ), BAndShort );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( int? ),   BAND } ), BAndInt );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( long? ),  BAND } ), BAndLong );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( bool? ),  BAND } ), BAndBoolean );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( sbyte? ),  BOR } ),  BOrByte );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( short? ), BOR } ),  BOrShort );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( int? ),   BOR } ),  BOrInt );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( long? ),  BOR } ),  BOrLong );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( bool? ),  BOR } ),  BOrBoolean );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( sbyte? ),  BXOR } ), BXorByte );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( short? ), BXOR } ), BXorShort );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( int? ),   BXOR } ), BXorInt );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( long? ),  BXOR } ), BXorLong );
			computers.Add( new MultiKey<Object>( new Object[] { typeof( bool? ),  BXOR } ), BXorBoolean );
		}

        /// <summary>Returns number or bool computation for the target coercion type.</summary>
        /// <param name="coercedType">target type</param>
        /// <returns>number cruncher</returns>

		public Computer GetComputer( Type coercedType )
		{
			if ( ( coercedType != typeof( sbyte? ) ) &&
				 ( coercedType != typeof( short? ) ) &&
				 ( coercedType != typeof( int? ) ) &&
				 ( coercedType != typeof( long? ) ) &&
				 ( coercedType != typeof( bool? ) ) )
			{
				throw new ArgumentException( "Expected base numeric or bool type for computation result but got type " + coercedType );
			}

			MultiKey<Object> key = new MultiKey<Object>( new Object[] { coercedType, this } );
            return computers[key];
		}

        /// <summary>Computer for relational op.</summary>
        
		public delegate Object Computer( Object objOne, Object objTwo );

        /// <summary>
        /// Bit Wise And.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>

		public static Object BAndByte( Object objOne, Object objTwo )
		{
			sbyte? n1 = (sbyte?) objOne;
			sbyte? n2 = (sbyte?) objTwo;
			sbyte? result = (sbyte?) ( n1 & n2 );
			return result;
		}

        /// <summary>
        /// Bit Wise Or.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>

        public static Object BOrByte(Object objOne, Object objTwo)
		{
			sbyte? n1 = (sbyte?) objOne;
			sbyte? n2 = (sbyte?) objTwo;
			sbyte? result = (sbyte?) ( n1 | n2);
			return result;
		}

        /// <summary>
        /// Bit Wise Xor.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>

        public static Object BXorByte(Object objOne, Object objTwo)
		{
			sbyte? n1 = (sbyte?) objOne;
			sbyte? n2 = (sbyte?) objTwo;
			sbyte? result = (sbyte?) ( n1 ^ n2 );
			return result;
		}

        /// <summary>
        /// Bit Wise And.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>

        public static Object BAndShort(Object objOne, Object objTwo)
		{
			short? n1 = (short?) objOne;
			short? n2 = (short?) objTwo;
			short? result = (short?) ( n1 & n2 );
			return result;
		}

        /// <summary>
        /// Bit Wise Or.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>

        public static Object BOrShort(Object objOne, Object objTwo)
		{
			short? n1 = (short?) objOne;
			short? n2 = (short?) objTwo;
			short? result = (short?) ( n1 | n2 );
			return result;
		}

        /// <summary>
        /// Bit Wise Xor.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>
        
        public static Object BXorShort(Object objOne, Object objTwo)
		{
			short? n1 = (short?) objOne;
			short? n2 = (short?) objTwo;
			short? result = (short?) ( n1 ^ n2 );
			return result;
		}

        /// <summary>
        /// Bit Wise And.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>
        public static Object BAndInt(Object objOne, Object objTwo)
		{
			int? n1 = (int?) objOne;
			int? n2 = (int?) objTwo;
			int? result = n1 & n2;
			return result;
		}

        /// <summary>
        /// Bit Wise Or.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>

        public static Object BOrInt(Object objOne, Object objTwo)
		{
			int? n1 = (int?) objOne;
			int? n2 = (int?) objTwo;
			int? result = n1 | n2;
			return result;
		}

        /// <summary>
        /// Bit Wise Xor.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>
        public static Object BXorInt(Object objOne, Object objTwo)
		{
			int? n1 = (int?) objOne;
			int? n2 = (int?) objTwo;
			int? result = n1 ^ n2;
			return result;
		}

        /// <summary>
        /// Bit Wise And.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>
        public static Object BAndLong(Object objOne, Object objTwo)
		{
			long? n1 = (long?) objOne;
			long? n2 = (long?) objTwo;
			long? result = n1 & n2;
			return result;
		}

        /// <summary>
        /// Bit Wise Or.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>

        public static Object BOrLong(Object objOne, Object objTwo)
		{
			long? n1 = (long?) objOne;
			long? n2 = (long?) objTwo;
			long? result = n1 | n2;
			return result;
		}

        /// <summary>
        /// Bit Wise Xor.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>
        public static Object BXorLong(Object objOne, Object objTwo)
		{
			long? n1 = (long?) objOne;
			long? n2 = (long?) objTwo;
			long? result = n1 ^ n2;
			return result;
		}

        /// <summary>
        /// Bit Wise And.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>
        public static Object BAndBoolean(Object objOne, Object objTwo)
		{
			bool? b1 = (bool?) objOne;
			bool? b2 = (bool?) objTwo;
			bool? result = b1 & b2;
			return result;
		}

        /// <summary>
        /// Bit Wise Or.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>

        public static Object BOrBoolean(Object objOne, Object objTwo)
		{
			bool? b1 = (bool?) objOne;
			bool? b2 = (bool?) objTwo;
			bool? result = b1 | b2;
			return result;
		}

        /// <summary>
        /// Bit Wise Xor.
        /// </summary>
        /// <param name="objOne">The obj one.</param>
        /// <param name="objTwo">The obj two.</param>
        /// <returns></returns>

        public static Object BXorBoolean(Object objOne, Object objTwo)
		{
			bool? b1 = (bool?) objOne;
			bool? b2 = (bool?) objTwo;
			bool? result = b1 ^ b2;
			return result;
		}

        /// <summary>Returns string rendering of enum.</summary>
        /// <returns>bitwise operator string</returns>

		public String ComputeDescription
		{
            get { return expressionText; }
		}
	}
}
