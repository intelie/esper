///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.eql.spec;
using net.esper.events;
using net.esper.type;
using net.esper.view;

namespace net.esper.support.view
{
/**
 * Convenience class for making view specifications from class and string arrays.
 */
	public class SupportViewSpecFactory
	{
	    public static IList<ViewSpec> MakeSpecListOne()
	    {
	        List<ViewSpec> specifications = new List<ViewSpec>();

	        ViewSpec specOne = MakeSpec("win", "length",
                    new Type[] { typeof(int?) }, new String[] { "1000" });
	        ViewSpec specTwo = MakeSpec("stat", "uni",
                    new Type[] { typeof(String) }, new String[] { "\"price\"" });
	        ViewSpec specThree = MakeSpec("std", "lastevent", null, null);

	        specifications.Add(specOne);
	        specifications.Add(specTwo);
	        specifications.Add(specThree);

	        return specifications;
	    }

	    public static IList<ViewFactory> MakeFactoryListOne(EventType parentEventType)
	    {
	        return MakeFactories(parentEventType, MakeSpecListOne());
	    }

	    public static IList<ViewSpec> MakeSpecListTwo()
	    {
	        IList<ViewSpec> specifications = new List<ViewSpec>();

	        ViewSpec specOne = MakeSpec("std", "groupby",
                    new Type[] { typeof(String) }, new String[] { "\"symbol\"" });

	        specifications.Add(specOne);

	        return specifications;
	    }

	    public static IList<ViewFactory> MakeFactoryListTwo(EventType parentEventType)
	    {
	        return MakeFactories(parentEventType, MakeSpecListTwo());
	    }

	    public static IList<ViewSpec> MakeSpecListThree()
	    {
	        IList<ViewSpec> specifications = new List<ViewSpec>();

	        ViewSpec specOne = SupportViewSpecFactory.MakeSpec("win", "length",
                    new Type[] { typeof(int?) }, new String[] { "1000" });
	        ViewSpec specTwo = SupportViewSpecFactory.MakeSpec("std", "unique",
                    new Type[] { typeof(String) }, new String[] { "\"symbol\"" });

	        specifications.Add(specOne);
	        specifications.Add(specTwo);

	        return specifications;
	    }

	    public static IList<ViewFactory> MakeFactoryListThree(EventType parentEventType)
	    {
	        return MakeFactories(parentEventType, MakeSpecListThree());
	    }

	    public static IList<ViewSpec> MakeSpecListFour()
	    {
	        IList<ViewSpec> specifications = new List<ViewSpec>();

	        ViewSpec specOne = SupportViewSpecFactory.MakeSpec("win", "length",
                    new Type[] { typeof(int?) }, new String[] { "1000" });
	        ViewSpec specTwo = SupportViewSpecFactory.MakeSpec("stat", "uni",
                    new Type[] { typeof(String) }, new String[] { "\"price\"" });
	        ViewSpec specThree = SupportViewSpecFactory.MakeSpec("std", "size", null, null);

	        specifications.Add(specOne);
	        specifications.Add(specTwo);
	        specifications.Add(specThree);

	        return specifications;
	    }

	    public static IList<ViewFactory> MakeFactoryListFour(EventType parentEventType)
	    {
	        return MakeFactories(parentEventType, MakeSpecListFour());
	    }

	    public static List<ViewSpec> MakeSpecListFive()
	    {
	        List<ViewSpec> specifications = new List<ViewSpec>();

	        ViewSpec specOne = MakeSpec("win", "time",
	                new Type[] { typeof(int?)}, new String[] { "10000" } );
	        specifications.Add(specOne);

	        return specifications;
	    }

	    public static IList<ViewFactory> MakeFactoryListFive(EventType parentEventType)
	    {
	        return MakeFactories(parentEventType, MakeSpecListFive());
	    }

        public static ViewSpec MakeSpec(String _namespace, String viewName, Type[] paramTypes, String[] paramValues)
	    {
	        return new ViewSpec(_namespace, viewName, MakeParams(paramTypes, paramValues));
	    }

	    private static List<Object> MakeParams(Type[] type, String[] values)
	    {
	        List<Object> _params = new List<Object>();

	        if (type == null)
	        {
	            return _params;
	        }

	        for (int i = 0; i < type.Length; i++)
	        {
	            PrimitiveValue placeholder = PrimitiveValueFactory.Create(type[i]);
	            placeholder.Parse(values[i]);
	            Object value = placeholder.ValueObject;
	            _params.Add(value);
	        }

	        return _params;
	    }

	    private static IList<ViewFactory> MakeFactories(EventType parentEventType, IList<ViewSpec> viewSpecs)
	    {
	        ViewServiceImpl svc = new ViewServiceImpl();
	        ViewFactoryChain viewFactories = svc.CreateFactories(1, parentEventType, viewSpecs, SupportStatementContextFactory.MakeContext());
	        return viewFactories.FactoryChain;
	    }
	}
} // End of namespace
