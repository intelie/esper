using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.collection
{

	[TestFixture]
public class TestMultiKeyUntyped 
{
    internal MultiKeyUntyped keys1 = new MultiKeyUntyped("a", "b");
    internal MultiKeyUntyped keys2 = new MultiKeyUntyped("a", "b");
    internal MultiKeyUntyped keys3 = new MultiKeyUntyped("a", null);
    internal MultiKeyUntyped keys4 = new MultiKeyUntyped(null, "b");
    internal MultiKeyUntyped keys5 = new MultiKeyUntyped(null, null);
    internal MultiKeyUntyped keys6 = new MultiKeyUntyped("a");
    internal MultiKeyUntyped keys7 = new MultiKeyUntyped("a", "b", "c");
    internal MultiKeyUntyped keys8 = new MultiKeyUntyped("a", "b", null);
    internal MultiKeyUntyped keys9 = new MultiKeyUntyped("a", "b", "c", "d");
    internal MultiKeyUntyped keys10 = new MultiKeyUntyped("a", "b", "c", "d");

    [Test]
    public virtual void  testHashCode()
    {
        Assert.IsTrue(keys1.GetHashCode() == ("a".GetHashCode() ^ "b".GetHashCode()));
        Assert.IsTrue(keys10.GetHashCode() == ("a".GetHashCode() ^ "b".GetHashCode() ^ "c".GetHashCode() ^ "d".GetHashCode()));
        Assert.IsTrue(keys3.GetHashCode() == "a".GetHashCode());
        Assert.IsTrue(keys4.GetHashCode() == "b".GetHashCode());
        Assert.IsTrue(keys5.GetHashCode() == 0);

        Assert.IsTrue(keys8.GetHashCode() == keys1.GetHashCode());
        Assert.IsTrue(keys1.GetHashCode() == keys2.GetHashCode());
        Assert.IsTrue(keys1.GetHashCode() != keys3.GetHashCode());
        Assert.IsTrue(keys1.GetHashCode() != keys4.GetHashCode());
        Assert.IsTrue(keys1.GetHashCode() != keys5.GetHashCode());

        Assert.IsTrue(keys7.GetHashCode() != keys8.GetHashCode());
        Assert.IsTrue(keys9.GetHashCode() == keys10.GetHashCode());
    }

    [Test]
    public virtual void  testEquals()
    {
        Assert.AreEqual(keys2, keys1);
        Assert.AreEqual(keys1, keys2);

        Assert.IsFalse(keys1.Equals(keys3));
        Assert.IsFalse(keys3.Equals(keys1));
        Assert.IsFalse(keys1.Equals(keys4));
        Assert.IsFalse(keys2.Equals(keys5));
        Assert.IsFalse(keys3.Equals(keys4));
        Assert.IsFalse(keys4.Equals(keys5));

        Assert.IsTrue(keys1.Equals(keys1));
        Assert.IsTrue(keys2.Equals(keys2));
        Assert.IsTrue(keys3.Equals(keys3));
        Assert.IsTrue(keys4.Equals(keys4));
        Assert.IsTrue(keys5.Equals(keys5));

        Assert.IsFalse(keys1.Equals(keys7));
        Assert.IsFalse(keys1.Equals(keys8));
        Assert.IsFalse(keys1.Equals(keys9));
        Assert.IsFalse(keys1.Equals(keys10));
        Assert.IsTrue(keys9.Equals(keys10));
    }

    [Test]
    public virtual void  testGet()
    {
        Assert.AreEqual(1, keys6.Count);
        Assert.AreEqual(2, keys1.Count);
        Assert.AreEqual(3, keys8.Count);
        Assert.AreEqual(4, keys9.Count);

        Assert.AreEqual("a", keys1[0]);
        Assert.AreEqual("b", keys1[1]);
        Assert.IsTrue(null == keys4[0]);
        Assert.IsTrue((Object) "d" == keys10[3]);
    }

    [Test]
    public virtual void  testWithSet()
    {
        EventBean[][] testEvents = new EventBean[][]{
			SupportEventBeanFactory.MakeEvents(new String[]{"a", "b"}),
			SupportEventBeanFactory.MakeEvents(new String[]{"a"}),
			SupportEventBeanFactory.MakeEvents(new String[]{"a", "b", "c"}), 
			SupportEventBeanFactory.MakeEvents(new String[]{"a", "b"})
		};

        Set<MultiKeyUntyped> mapSet = new HashSet<MultiKeyUntyped>();

        // Test contains
        mapSet.Add(new MultiKeyUntyped(testEvents[0]));
        Assert.IsTrue(mapSet.Contains(new MultiKeyUntyped(testEvents[0])));
        Assert.IsFalse(mapSet.Contains(new MultiKeyUntyped(testEvents[1])));
        Assert.IsFalse(mapSet.Contains(new MultiKeyUntyped(testEvents[2])));
        Assert.IsFalse(mapSet.Contains(new MultiKeyUntyped(testEvents[3])));

        // Test unique
        mapSet.Add(new MultiKeyUntyped(testEvents[0]));
        Assert.AreEqual(1, mapSet.Count);

        mapSet.Add(new MultiKeyUntyped(testEvents[1]));
        mapSet.Add(new MultiKeyUntyped(testEvents[2]));
        mapSet.Add(new MultiKeyUntyped(testEvents[3]));
        Assert.AreEqual(4, mapSet.Count);

        mapSet.Remove(new MultiKeyUntyped(testEvents[0]));
        Assert.AreEqual(3, mapSet.Count);
        Assert.IsFalse(mapSet.Contains(new MultiKeyUntyped(testEvents[0])));

        mapSet.Remove(new MultiKeyUntyped(testEvents[1]));
        mapSet.Remove(new MultiKeyUntyped(testEvents[2]));
        mapSet.Remove(new MultiKeyUntyped(testEvents[3]));
        Assert.AreEqual(0, mapSet.Count);
    }
}
}

