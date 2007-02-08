using System;
using System.IO;
using System.Resources;

using EPException = net.esper.client.EPException;

namespace net.esper.util
{
	/// <summary>
	/// Utility class for loading or resolving external resources via URL and class path.
	/// </summary>

	public class ResourceLoader
	{
		/// <summary>
		/// Resolve a resource into a URL using the URL string or relative filename and
		/// using a name for any exceptions thrown.
		/// </summary>
		/// <param name="resourceName">is the name for use in exceptions</param>
		/// <param name="urlResource">is a URL string or relative filename</param>
		/// <returns>
		/// URL or null if resolution was unsuccessful
		/// </returns>
		
//		public static Uri resolveURLResource(String resourceName, String urlResource)
//		{
//			try
//			{
//				//UPGRADE_TODO: Class 'java.net.URL' was converted to a 'Uri' which does not throw an exception if a URL specifies an unknown protocol. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1132'"
//				url = new Uri(urlOrClasspathResource);
//			}
//			catch (UriFormatException ex)
//			{
//				url = getClasspathResourceAsURL(resourceName, urlOrClasspathResource);
//			}
//			return url;
//		}
	}
}
