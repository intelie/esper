using System;
using System.IO;
using System.Net;
using System.Xml;

using net.esper.client;
using net.esper.compat;
using net.esper.support.util;
using net.esper.events;

using NUnit.Framework;

namespace net.esper.example.autoid
{
	public class TestRFIDTagsPerSensorStmt
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener listener;
	
	    [SetUp]
	    public void setUp()
	    {
	    	Uri url = ResourceManager.ResolveResourceURL("esper.examples.cfg.xml");
	        Configuration config = new Configuration();
	        config.Configure(url);
	
	        epService = EPServiceProviderManager.GetProvider("AutoIdSim", config);
	        epService.Initialize();
	
	        listener = new SupportUpdateListener();
	        RFIDTagsPerSensorStmt rfidStmt = new RFIDTagsPerSensorStmt(epService.EPAdministrator);
	        rfidStmt.AddListener(listener.Update);
	    }
	
	    [Test]
	    public void testEvents()
	    {
	    	XmlDocument sensorlDoc = new XmlDocument() ;
	
	        using( Stream stream = ResourceManager.GetResourceAsStream("data/AutoIdSensor1.xml") ) {
	        	sensorlDoc.Load( stream ) ;
	        }
	    	
	        epService.EPRuntime.SendEvent(sensorlDoc);
	        assertReceived("urn:epc:1:4.16.36", 5);
	    }
	
	    private void assertReceived(string sensorId, double numTags)
	    {
	        Assert.IsTrue(listener.Invoked);
	        Assert.AreEqual(1, listener.LastNewData.Length);
	        EventBean eventBean = listener.LastNewData[0];
	        Assert.AreEqual(sensorId, eventBean["SensorId"]);
	        Assert.AreEqual(numTags, eventBean["NumTagsPerSensor"]);
	        listener.reset();
	    }
	}
}
