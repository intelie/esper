using System;
using System.IO;
using System.Net;
using System.Text;
using System.Xml;

using net.esper.client;
using net.esper.compat;

namespace net.esper.example.autoid
{
	public class AutoIdSimMain {
	
	    private static readonly Random RANDOM = new Random();
	    private static readonly string[] SENSOR_IDS = {"urn:epc:1:4.16.30", "urn:epc:1:4.16.32", "urn:epc:1:4.16.36", "urn:epc:1:4.16.38" };
	    private const string XML_ROOT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	            "<pmlcore:Sensor \n" +
	            "  xmlns=\"urn:autoid:specification:interchange:PMLCore:xml:schema:1\" \n" +
	            "  xmlns:pmlcore=\"urn:autoid:specification:interchange:PMLCore:xml:schema:1\" \n" +
	            "  xmlns:autoid=\"http://www.autoidcenter.org/2003/xml\" \n" +
	            "  xmlns:pmluid=\"urn:autoid:specification:universal:Identifier:xml:schema:1\" \n" +
	            "  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
	            "  xsi:schemaLocation=\"urn:autoid:specification:interchange:PMLCore:xml:schema:1 AutoIdPmlCore.xsd\">\n";
	
	    private readonly int numEvents;
	
	    public static void Main(string[] args) 
	    {
	        if (args.Length < 1) {
	            Console.Out.WriteLine("Arguments are: <numberOfEvents>");
	            Environment.Exit(-1);
	        }
	
	        int events = 0;
	        try {
	            events = Int32.Parse(args[0]);
	        } catch (NullReferenceException) {
	            Console.Out.WriteLine("Invalid numberOfEvents:" + args[0]);
	            Environment.Exit(-2);
	            return;
	        }
	
	        // Run the sample
	        AutoIdSimMain autoIdSimMain = new AutoIdSimMain(events);
	        autoIdSimMain.Run();
	    }
	
	    public AutoIdSimMain(int numEvents)
	    {
	        this.numEvents = numEvents;
	    }
	
	    public void Run()
	    {
	        // load config - this defines the XML event types to be processed
	        Uri url = ResourceManager.ResolveResourceURL("esper.examples.cfg.xml");
	        Configuration config = new Configuration();
	        config.Configure(url);
	
	        // get engine instance
	        EPServiceProvider epService = EPServiceProviderManager.GetProvider("AutoIdSim", config);
	
	        // set up statement
	        RFIDTagsPerSensorStmt rfidStmt = new RFIDTagsPerSensorStmt(epService.EPAdministrator);
	        rfidStmt.AddListener((new RFIDTagsPerSensorListener()).Update);
	
	        // Send events
	        int eventCount = 0;
	        while(eventCount < numEvents) {
	            SendEvent(epService.EPRuntime);
	            eventCount++;
	        }
	    }
	
	    private void SendEvent(EPRuntime epRuntime)
	    {
	        String eventXMLText = GenerateEvent();
	        XmlDocument simpleDoc = new XmlDocument() ;
	        simpleDoc.LoadXml(eventXMLText) ;
	        epRuntime.SendEvent(simpleDoc);
	    }
	
	    private String GenerateEvent()
	    {
	        StringBuilder buffer = new StringBuilder();
	        buffer.Append(XML_ROOT);
	
	        String sensorId = SENSOR_IDS[RANDOM.Next(SENSOR_IDS.Length)];
	        buffer.Append("<pmluid:ID>");
	        buffer.Append(sensorId);
	        buffer.Append("</pmluid:ID>");
	
	        buffer.Append("<pmlcore:Observation>");
	        buffer.Append("<pmlcore:Command>READ_PALLET_TAGS_ONLY</pmlcore:Command>");
	
	        for (int i = 0; i < RANDOM.Next(6) + 1; i++)
	        {
	            buffer.Append("<pmlcore:Tag><pmluid:ID>urn:epc:1:2.24.400</pmluid:ID></pmlcore:Tag>");
	        }
	
	        buffer.Append("</pmlcore:Observation>");
	        buffer.Append("</pmlcore:Sensor>");
	
	        return buffer.ToString();
	    }
	}
}
