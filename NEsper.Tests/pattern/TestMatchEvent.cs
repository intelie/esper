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

namespace net.esper.pattern
{
	[TestFixture]
	public class TestMatchEvent
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
	    public void testPutAndGet()
	    {
	        MatchedEventMap _event = new MatchedEventMapImpl();
	        _event.Add("tag", events.Fetch("a"));
	        _event.Add("test", events.Fetch("b"));
	        Assert.IsTrue(_event.MatchingEvents.Fetch("tag") == events.Fetch("a"));
	        Assert.IsTrue(_event.GetMatchingEvent("tag") == events.Fetch("a"));
	        Assert.IsTrue(_event.GetMatchingEvent("test") == events.Fetch("b"));
	    }

	    [Test]
	    public void testEquals()
	    {
	        MatchedEventMap eventOne = new MatchedEventMapImpl();
	        MatchedEventMap eventTwo = new MatchedEventMapImpl();
	        Assert.IsTrue(eventOne.Equals(eventTwo));

	        eventOne.Add("test", events.Fetch("a"));
	        Assert.IsFalse(eventOne.Equals(eventTwo));
	        Assert.IsFalse(eventTwo.Equals(eventOne));

	        eventTwo.Add("test", events.Fetch("a"));
	        Assert.IsTrue(eventOne.Equals(eventTwo));

	        eventOne.Add("abc", events.Fetch("b"));
	        eventTwo.Add("abc", events.Fetch("c"));
	        Assert.IsFalse(eventOne.Equals(eventTwo));

	        eventOne.Add("abc", events.Fetch("c"));
	        Assert.IsTrue(eventOne.Equals(eventTwo));

	        eventOne.Add("1", events.Fetch("d"));
	        eventOne.Add("2", events.Fetch("e"));
	        eventTwo.Add("2", events.Fetch("e"));
	        eventTwo.Add("1", events.Fetch("d"));
	        Assert.IsTrue(eventOne.Equals(eventTwo));
	    }

	    [Test]
	    public void testClone()
	    {
	        MatchedEventMap _event = new MatchedEventMapImpl();

	        _event.Add("tag", events.Fetch("0"));
	        MatchedEventMap copy = _event.ShallowCopy();

	        Assert.IsTrue(copy.Equals(_event));
	        _event.Add("a", events.Fetch("a"));
	        Assert.IsFalse(copy.Equals(_event));
	        copy.Add("a", events.Fetch("a"));
	        Assert.IsTrue(copy.Equals(_event));

	        MatchedEventMap copyTwo = copy.ShallowCopy();
	        Assert.IsTrue(copy.Equals(copyTwo));
	        copyTwo.Add("b", events.Fetch("b"));

	        Assert.IsTrue(copyTwo.MatchingEvents.Count == 3);
	        Assert.IsTrue(copyTwo.GetMatchingEvent("tag") == events.Fetch("0"));
	        Assert.IsTrue(copyTwo.GetMatchingEvent("a") == events.Fetch("a"));
	        Assert.IsTrue(copyTwo.GetMatchingEvent("b") == events.Fetch("b"));

            Assert.IsTrue(copy.MatchingEvents.Count == 2);
	        Assert.IsTrue(copy.GetMatchingEvent("tag") == events.Fetch("0"));
	        Assert.IsTrue(copy.GetMatchingEvent("a") == events.Fetch("a"));

            Assert.IsTrue(_event.MatchingEvents.Count == 2);
	        Assert.IsTrue(_event.GetMatchingEvent("tag") == events.Fetch("0"));
	        Assert.IsTrue(_event.GetMatchingEvent("a") == events.Fetch("a"));
	    }

	    [Test]
	    public void testMerge()
	    {
	        MatchedEventMap eventOne = new MatchedEventMapImpl();
	        MatchedEventMap eventTwo = new MatchedEventMapImpl();

	        eventOne.Add("tagA", events.Fetch("a"));
	        eventOne.Add("tagB", events.Fetch("b"));

	        eventTwo.Add("tagB", events.Fetch("c"));
	        eventTwo.Add("xyz", events.Fetch("d"));

	        eventOne.Merge(eventTwo);
	        Assert.IsTrue(eventOne.GetMatchingEvent("tagA") == events.Fetch("a"));
	        Assert.IsTrue(eventOne.GetMatchingEvent("tagB") == events.Fetch("c"));
	        Assert.IsTrue(eventOne.GetMatchingEvent("xyz") == events.Fetch("d"));
	        Assert.IsTrue(eventOne.MatchingEvents.Count == 3);
	    }
	}
} // End of namespace
