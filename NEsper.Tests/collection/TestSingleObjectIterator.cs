// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

namespace net.esper.collection
{
	[TestFixture]
	public class TestSingleObjectIterator
	{
        public IEnumerator<String> CreateEnumerator(string value)
        {
            yield return value;
        }

	    [Test]
	    public void testNext()
	    {
	        IEnumerator<String> it = CreateEnumerator("a");
	        Assert.IsTrue(it.MoveNext());
	        Assert.AreEqual("a", it.Current);
	        Assert.IsFalse(it.MoveNext());
	    }
	}
} // End of namespace
