// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.support.eql;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestEngineImportServiceImpl
	{
	    EngineImportServiceImpl engineImportService;

	    [SetUp]
	    public void SetUp()
	    {
	        this.engineImportService = new EngineImportServiceImpl();
	    }

	    [Test]
	    public void testAddAggregation()
	    {
	        engineImportService.AddAggregation("abc", "abcdef.G");
	        engineImportService.AddAggregation("abcDefGhk", "ab");
	        engineImportService.AddAggregation("a", "Yh");

	        TryInvalidAddAggregation("g h", "");
	        TryInvalidAddAggregation("gh", "j j");
	        TryInvalidAddAggregation("abc", "hhh");
	    }

	    [Test]
	    public void testResolveAggregationMethod()
	    {
	    	engineImportService.AddAggregation("abc", typeof(SupportPluginAggregationMethodOne).FullName);
	        Assert.IsTrue(engineImportService.ResolveAggregation("abc") is SupportPluginAggregationMethodOne);
	    }

	    [Test]
	    public void testInvalidResolveAggregation(String funcName)
	    {
	        try
	        {
	            engineImportService.ResolveAggregation("abc");
	        }
	        catch (EngineImportUndefinedException ex)
	        {
	            // expected
	        }

	        engineImportService.AddAggregation("abc", "abcdef.G");
	        try
	        {
	            engineImportService.ResolveAggregation("abc");
	        }
	        catch (EngineImportException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testResolveClass()
	    {
	        String className = "System.Math";
	        Type expected = typeof(Math);
            Assert.AreEqual(expected, engineImportService.ResolveType(className));

	        engineImportService.AddImport("System");
	        className = "String";
	        expected = typeof(string);
            Assert.AreEqual(expected, engineImportService.ResolveType(className));
	    }

	    [Test]
	    public void testResolveClassInvalid()
	    {
	        String className = "Math";
	        try
	        {
	            engineImportService.ResolveType(className);
	            Assert.Fail();
	        }
	        catch (TypeLoadException e)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testAddImport()
	    {
	        engineImportService.AddImport("System.Math");
	        Assert.AreEqual(1, engineImportService.Imports.Length);
	        Assert.AreEqual("System.Math", engineImportService.Imports[0]);

	        engineImportService.AddImport("System");
	        Assert.AreEqual(2, engineImportService.Imports.Length);
	        Assert.AreEqual("System.Math", engineImportService.Imports[0]);
	        Assert.AreEqual("System", engineImportService.Imports[1]);
	    }

	    [Test]
	    public void testAddImportInvalid()
	    {
	        try
	        {
	            engineImportService.AddImport("System.*.*");
	            Assert.Fail();
	        }
	        catch (EngineImportException e)
	        {
	            // Expected
	        }

	        try
	        {
	            engineImportService.AddImport("System..Math");
	            Assert.Fail();
	        }
	        catch (EngineImportException e)
	        {
	            // Expected
	        }
	    }

	    private void TryInvalidAddAggregation(String funcName, String className)
	    {
	        try
	        {
	            engineImportService.AddAggregation(funcName, className);
	        }
	        catch (EngineImportException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
