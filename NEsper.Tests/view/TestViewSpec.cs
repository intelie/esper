///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.compat;
using net.esper.eql.spec;
using net.esper.support.view;

namespace net.esper.view
{
	[TestFixture]
	public class TestViewSpec
	{
	    [Test]
	    public void TestEquals()
	    {
	        Type[] c_0 = new Type[] { typeof(String)} ;
	        String[] s_0_0 = new String[] { "\"symbol\"" };
	        String[] s_0_1 = new String[] { "\"price\"" };

	        Type[] c_1 = new Type[] { typeof(String), typeof(long?)} ;
	        String[] s_1_0 = new String[] { "\"symbol\"", "1" };
	        String[] s_1_1 = new String[] { "\"price\"", "1" };
	        String[] s_1_2 = new String[] { "\"price\"", "2" };
	        String[] s_1_3 = new String[] { "\"price\"", "1" };

	        Type[] c_2 = new Type[] { typeof(Boolean), typeof(String), typeof(long?)} ;
	        String[] s_2_0 = new String[] { "true", "\"symbol\"", "1" };
	        String[] s_2_1 = new String[] { "true", "\"price\"", "1" };
	        String[] s_2_2 = new String[] { "true", "\"price\"", "2" };
	        String[] s_2_3 = new String[] { "false", "\"price\"", "1" };

            EDictionary<int, ViewSpec> specs = new HashDictionary<int, ViewSpec>();
	        specs.Put(1,  SupportViewSpecFactory.MakeSpec("ext", "sort", null, null));
	        specs.Put(2,  SupportViewSpecFactory.MakeSpec("std", "sum", null, null));
	        specs.Put(3,  SupportViewSpecFactory.MakeSpec("ext", "sort", null, null));
	        specs.Put(4,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_0, s_0_0));
	        specs.Put(5,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_0, s_0_0));
	        specs.Put(6,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_0, s_0_1));
	        specs.Put(7,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_1, s_1_0));
	        specs.Put(8,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_1, s_1_1));
	        specs.Put(9,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_1, s_1_2));
	        specs.Put(10,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_1, s_1_3));
	        specs.Put(11,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_2, s_2_0));
	        specs.Put(12,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_2, s_2_1));
	        specs.Put(13,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_2, s_2_2));
	        specs.Put(14,  SupportViewSpecFactory.MakeSpec("ext", "sort", c_2, s_2_3));

            EDictionary<int, int> matches = new HashDictionary<int, int>();
	        matches.Put(1,3);
	        matches.Put(3,1);
	        matches.Put(4,5);
	        matches.Put(5,4);
	        matches.Put(8,10);
	        matches.Put(10,8);

	        // Compare each against each
	        foreach (KeyValuePair<int, ViewSpec> entryOut in specs)
	        {
	            foreach (KeyValuePair<int, ViewSpec> entryIn in specs)
	            {
	                bool result = entryOut.Value.Equals(entryIn.Value);

	                if (Object.Equals(entryOut, entryIn))
	                {
	                    Assert.IsTrue(result);
	                    continue;
	                }


	                if ( (matches.ContainsKey(entryOut.Key)) &&
	                     (matches.Fetch(entryOut.Key) == entryIn.Key) )
	                {
	                    Assert.IsTrue(result);
	                }
	                else
	                {
	                    Assert.IsFalse(result);
	                }
	            }
	        }
	    }

	}
} // End of namespace
