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
using net.esper.events;
using net.esper.support.events;

using org.apache.commons.logging;

namespace net.esper.pattern
{
	[TestFixture]
	public class TestEvalAndStateNode
	{
	    private EDictionary<String, EventBean> events;

	    [SetUp]
	    public void SetUp()
	    {
	        events = new HashDictionary<String, EventBean>();
	        String[] ids = { "0", "a", "b", "c", "d", "e", "f" };
	        foreach (String id in ids)
	        {
	            events.Put(id, SupportEventBeanFactory.CreateObject(id));
	        }
	    }

	    [Test]
	    public void testGenerate()
	    {
	        MatchedEventMap beginState = new MatchedEventMapImpl();
	        beginState.Add("0", events.Fetch("0"));

	        IList<IList<MatchedEventMap>> listArray = new List<IList<MatchedEventMap>>();
	        listArray.Insert(0, MakeList("a", "b"));
            listArray.Insert(1, MakeList("c", "d"));
            listArray.Insert(2, MakeList("e", "f"));

	        IList<MatchedEventMap> result = new List<MatchedEventMap>();
	        EvalAndStateNode.GenerateMatchEvents(listArray, 0, result, beginState);

	        Assert.IsTrue(result.Count == 8);
	        for (int i = 0; i < 2; i++)
	        {
	            for (int j = 0; j < 2; j++)
	            {
	                for (int k = 0; k < 2; k++)
	                {
	                    int index = i * 4 + j * 2 + k;
	                    MatchedEventMap _event = result[index];

	                    log.Debug(".testGenerate index=" + index + "  event=" + _event);
	                }
	            }
	        }

	        Assert.IsTrue(result[0].GetMatchingEvent("0") == events.Fetch("0"));

	        Assert.IsTrue(result[0].GetMatchingEvent("a") == events.Fetch("a"));
	        Assert.IsTrue(result[0].GetMatchingEvent("c") == events.Fetch("c"));
	        Assert.IsTrue(result[0].GetMatchingEvent("e") == events.Fetch("e"));

	        Assert.IsTrue(result[1].GetMatchingEvent("0") == events.Fetch("0"));
	        Assert.IsTrue(result[1].GetMatchingEvent("a") == events.Fetch("a"));
	        Assert.IsTrue(result[1].GetMatchingEvent("c") == events.Fetch("c"));
	        Assert.IsTrue(result[1].GetMatchingEvent("f") == events.Fetch("f"));

	        Assert.IsTrue(result[7].GetMatchingEvent("0") == events.Fetch("0"));
	        Assert.IsTrue(result[7].GetMatchingEvent("b") == events.Fetch("b"));
	        Assert.IsTrue(result[7].GetMatchingEvent("d") == events.Fetch("d"));
	        Assert.IsTrue(result[7].GetMatchingEvent("f") == events.Fetch("f"));
	    }

	    /**
	     * Make a list of MatchEvents for testing each containing 2 entries in the list
	     */
	    private IList<MatchedEventMap> MakeList(String valueOne, String valueTwo)
	    {
	        IList<MatchedEventMap> list = new List<MatchedEventMap>();

	        MatchedEventMap event1 = new MatchedEventMapImpl();
	        event1.Add(valueOne, events.Fetch(valueOne));
	        list.Add(event1);

	        MatchedEventMap event2 = new MatchedEventMapImpl();
	        event2.Add(valueTwo, events.Fetch(valueTwo));
	        list.Add(event2);

	        return list;
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
