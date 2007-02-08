using System;
using System.Text;

namespace net.esper.compat
{
	public class ArrayHelper
	{
		/// <summary>
		/// Renders the array as a string
		/// </summary>
		/// <param name="array1"></param>
		/// <returns></returns>
		
		public static String Render( Array array )
		{
			string fieldDelimiter = String.Empty ;
			
			StringBuilder builder = new StringBuilder() ;
			builder.Append( '{' ) ;
			
			int length = array.Length ;
			for( int ii = 0 ; ii < length ; ii++ )
			{
				builder.Append( Convert.ToString( array.GetValue( ii ) ) ) ;
				builder.Append( fieldDelimiter ) ;
				fieldDelimiter = "," ;
			}
			
			builder.Append( '}' ) ;
			return builder.ToString() ;
		}
		
		/// <summary>
		/// Compares two arrays for equality
		/// </summary>
		/// <param name="array1"></param>
		/// <param name="array2"></param>
		/// <returns></returns>
		
		public static bool AreEqual( Array array1, Array array2 )
		{
			if ( array1 == null ) {
				throw new ArgumentNullException( "array1" ) ;
			}
			if ( array2 == null ) {
				throw new ArgumentNullException( "array2" ) ;
			}
			if ( array1.Length != array2.Length ) {
				return false ;
			}
			
			for( int ii = array1.Length - 1 ; ii >= 0 ; ii-- ) {
				if ( ! Object.Equals( array1.GetValue(ii), array2.GetValue(ii) ) ) {
					return false ;
				}
			}
			
			return true;
		}
	}
}
