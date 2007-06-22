// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;

namespace net.esper.support.util
{
	public class SerializableObjectCopier
	{
	    public static Object Copy(Object orig)
	    {
	        BinaryFormatter formatter = new BinaryFormatter();

            MemoryStream stream = new MemoryStream();
            formatter.Serialize(stream, orig);
            stream.Flush();

	        stream.Seek(0, SeekOrigin.Begin);
	        Object result = formatter.Deserialize(stream);

	        return result;
	    }
	}
} // End of namespace
