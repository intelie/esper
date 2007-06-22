using System;

namespace net.esper.compat
{
    /// <summary>
    /// An extended hash dictionary that maps a string to an object.
    /// </summary>

	public class DataDictionary : HashDictionary<String,Object>, IDataDictionary
	{
	}
}
