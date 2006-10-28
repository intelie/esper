package net.esper.example.jmsoutputadapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import junit.framework.TestCase;

import org.apache.xbean.server.spring.loader.SpringLoader;
import org.apache.xbean.kernel.Kernel;
import org.apache.xbean.kernel.KernelFactory;
import org.apache.xbean.kernel.ServiceName;
import org.apache.xbean.kernel.StringServiceName;
import org.apache.xbean.classloader.MultiParentClassLoader;
import org.apache.xbean.server.repository.FileSystemRepository;
import org.apache.xbean.server.spring.configuration.ClassLoaderXmlPreprocessor;
import org.apache.xbean.spring.context.SpringApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: MYSELF
 * Date: Oct 21, 2006
 * Time: 5:43:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpringLoaderTest extends TestCase  {
    
    private static final String basedir = System.getProperties().getProperty("basedir", "F:\\Esper\\jmsoutputadapter\\trunk\\eg\\etc");
    //private static final String basedir = System.getProperties().getProperty("basedir", ".");
    private static final String CLASS_NAME = "TestClass";
    private static final String ENTRY_NAME = "foo";
    private static final String ENTRY_VALUE = "bar";
    private File jarFile;

    public void testLoad() throws Exception{
        Kernel kernel = KernelFactory.newInstance().createKernel("test");

        try {
            //File xbeanDir = new File(basedir, "src/test/resources/org/apache/xbean/server/spring/loader/").getAbsoluteFile();
            File xbeanDir = new File(basedir, "xbean\\").getAbsoluteFile();
            System.setProperty("xbean.base.dir", xbeanDir.getAbsolutePath());

            FileSystemRepository repository = new FileSystemRepository(new File(basedir).getAbsoluteFile());
            ClassLoaderXmlPreprocessor classLoaderXmlPreprocessor = new ClassLoaderXmlPreprocessor(repository);
            List xmlPreprocessors = Collections.singletonList(classLoaderXmlPreprocessor);

            SpringLoader springLoader = new SpringLoader();
            springLoader.setKernel(kernel);
            springLoader.setBaseDir(xbeanDir);
            springLoader.setXmlPreprocessors(xmlPreprocessors);
            ServiceName configurationName = springLoader.load("classpath-xbean");

            kernel.startService(configurationName);

            Object testService = kernel.getService(new StringServiceName("test"));
            //("TestClass", testService.getClass().getName());
            //assertTrue(testService instanceof SortedSet);
        }
        finally {
            kernel.destroy();
        }
    }
}
