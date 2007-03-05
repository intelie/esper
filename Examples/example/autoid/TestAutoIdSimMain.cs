using NUnit.Framework;

namespace net.esper.example.autoid
{
	[TestFixture]
	public class TestAutoIdSimMain
	{
		[Test]
	    public void testRun() 
	    {
	        AutoIdSimMain main = new AutoIdSimMain(10);
	        main.Run();
	    }
	}
}
