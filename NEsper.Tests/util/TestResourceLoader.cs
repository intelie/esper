using System;

using net.esper.client;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.util
{
    [TestFixture]
    public class TestResourceLoader
    {
        private const String TEST_RESOURCE = "regression/esper.test.readconfig.cfg.xml";

        //public virtual void testResolveResourceAsURL()
        //{
        //    Uri url = ResourceLoader.getClasspathResourceAsURL("somefile", TEST_RESOURCE);
        //    Assert.IsNotNull(url);

        //    try
        //    {
        //        ResourceLoader.getClasspathResourceAsURL("somefile", "xxx");
        //        Assert.Fail();
        //    }
        //    catch (EPException ex)
        //    {
        //        // expected
        //    }
        //}

        //public virtual void testClasspathOrURL()
        //{
        //    Uri url = this.GetType().ClassLoader.getResource(TEST_RESOURCE);
        //    Uri urlAfterResolve = ResourceLoader.ResolveClassPathOrURLResource("a", url.ToString());
        //    Assert.AreEqual(url, urlAfterResolve);

        //    Uri url3 = ResourceLoader.ResolveClassPathOrURLResource("a", "file:///xxx/a.b");
        //    Assert.AreEqual("file:/xxx/a.b", url3.ToString());

        //    try
        //    {
        //        ResourceLoader.ResolveClassPathOrURLResource("a", "b");
        //        Assert.Fail();
        //    }
        //    catch (EPException ex)
        //    {
        //        // expected
        //    }
        //}
    }
}
