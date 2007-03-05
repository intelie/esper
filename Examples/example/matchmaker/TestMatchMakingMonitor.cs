using System;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.events;
using net.esper.support;
using net.esper.support.util;

using net.esper.example.matchmaker.eventbean;
using net.esper.example.matchmaker.monitor;

using org.apache.commons.logging;

namespace net.esper.example.matchmaker
{
	[TestFixture]
	public class TestMatchMakingMonitor
	{
		private const int USER_ID_1 = 1;
	    private const int USER_ID_2 = 2;
	
	    private MatchAlertListener listener;
	    private EPServiceProvider epService = null;
	
	    [SetUp]
	    protected void setUp()
	    {
	        listener = new MatchAlertListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        epService.EPRuntime.AddEmittedListener(listener.Emitted, null);
	
	        new MatchMakingMonitor();
	    }
	    
	    [Test]	
	    public void testLocationChanges()
	    {
	        MobileUserBean user_1 = new MobileUserBean(USER_ID_1, 10, 10,
	                Gender.MALE, HairColor.BLONDE, AgeRange.AGE_4,
	                Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_1);
	        epService.EPRuntime.SendEvent(user_1);
	
	        MobileUserBean user_2 = new MobileUserBean(USER_ID_2, 10, 10,
	                Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_1,
	                Gender.MALE, HairColor.BLONDE, AgeRange.AGE_4);
	        epService.EPRuntime.SendEvent(user_2);
	
	        Assert.AreEqual(1, listener.GetAndClearEmittedCount());
	
	        user_1.SetLocation(8.99999, 10);
	        epService.EPRuntime.SendEvent(user_1);
	        Assert.AreEqual(0, listener.GetAndClearEmittedCount());
	
	        user_1.SetLocation(9, 10);
	        epService.EPRuntime.SendEvent(user_1);
	        Assert.AreEqual(1, listener.GetAndClearEmittedCount());
	
	        user_1.SetLocation(11, 10);
	        epService.EPRuntime.SendEvent(user_1);
	        Assert.AreEqual(1, listener.GetAndClearEmittedCount());
	
	        user_1.SetLocation(11.0000001, 10);
	        epService.EPRuntime.SendEvent(user_1);
	        Assert.AreEqual(0, listener.GetAndClearEmittedCount());
	
	        user_2.SetLocation(10.0000001, 9);
	        epService.EPRuntime.SendEvent(user_2);
	        Assert.AreEqual(1, listener.GetAndClearEmittedCount());
	    }
	
	    [Test]
	    public void testPreferredMatching()
	    {
	        MobileUserBean user_1 = new MobileUserBean(USER_ID_1, 10, 10,
	                Gender.MALE, HairColor.RED, AgeRange.AGE_6,
	                Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_5);
	        epService.EPRuntime.SendEvent(user_1);
	
	        // Test all combinations
	        foreach (Gender gender in Enum.GetValues(typeof(Gender)))
	        {
	        	foreach (HairColor color in Enum.GetValues(typeof(HairColor)))
	            {
	                foreach (AgeRange age in Enum.GetValues(typeof(AgeRange)))
	                {
	                    // Try user preferences
	                    MobileUserBean userA = new MobileUserBean(USER_ID_2, 10, 10,
	                            Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_5,
	                            gender, color, age);
	                    epService.EPRuntime.SendEvent(userA);
	
	                    if (listener.EmittedList.Count == 1)
	                    {
	                        Assert.AreEqual(gender, Gender.MALE);
	                        Assert.AreEqual(color, HairColor.RED);
	                        Assert.AreEqual(age, AgeRange.AGE_6);
	                    }
	                    else
	                    {
	                        Assert.AreEqual(0, listener.GetAndClearEmittedCount());
	                    }
	                }
	            }
	        }
	    }

	    [Test]
	    public void testPreferredMatchingBackwards()
	    {
	        MobileUserBean user_1 = new MobileUserBean(USER_ID_1, 10, 10,
	                Gender.MALE, HairColor.RED, AgeRange.AGE_6,
	                Gender.FEMALE, HairColor.BLACK, AgeRange.AGE_5);
	        epService.EPRuntime.SendEvent(user_1);
	
	        // Test all combinations
	        foreach (Gender gender in Enum.GetValues(typeof(Gender)))
	        {
	        	foreach (HairColor color in Enum.GetValues(typeof(HairColor)))
	            {
	                foreach (AgeRange age in Enum.GetValues(typeof(AgeRange)))
	                {
	                    // Try user preferences backwards
	                    MobileUserBean userB = new MobileUserBean(USER_ID_2, 10, 10,
	                            gender, color, age,
	                            Gender.MALE, HairColor.RED, AgeRange.AGE_6);
	                    epService.EPRuntime.SendEvent(userB);
	
	                    if (listener.EmittedList.Count == 1)
	                    {
	                        Assert.AreEqual(gender, Gender.FEMALE);
	                        Assert.AreEqual(color, HairColor.BLACK);
	                        Assert.AreEqual(age, AgeRange.AGE_5);
	                    }
	                    else
	                    {
	                        Assert.AreEqual(0, listener.GetAndClearEmittedCount());
	                    }
	                }
	            }
	        }
	    }
	}
}
