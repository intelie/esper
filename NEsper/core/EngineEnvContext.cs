///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

namespace net.esper.core
{
	/// <summary>
	/// Implements a JNDI context for providing a directory for engine-external resources such as adapters.
	/// </summary>
	public class EngineEnvContext : Context
	{
	    private IDictionary<String, Object> context;

	    /// <summary>Ctor.</summary>
	    public EngineEnvContext()
	    {
	        context = new EHashDictionary<String, Object>();
	    }

	    public Object Lookup(Name name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public Object Lookup(String name)
	    {
	        return context.Get(name);
	    }

	    public void Bind(Name name, Object obj)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public void Bind(String name, Object obj)
	    {
	        if (context.ContainsKey(name))
	        {
	            throw new NamingException("Already in context: " + name);
	        }
	        context.Put(name, obj);
	    }

	    public void Rebind(Name name, Object obj)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public void Rebind(String name, Object obj)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public void Unbind(Name name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public void Unbind(String name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public void Rename(Name oldName, Name newName)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public void Rename(String oldName, String newName)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public NamingEnumeration<NameClassPair> List(Name name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public NamingEnumeration<NameClassPair> List(String name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public NamingEnumeration<Binding> ListBindings(Name name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public NamingEnumeration<Binding> ListBindings(String name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public void DestroySubcontext(Name name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public void DestroySubcontext(String name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public Context CreateSubcontext(Name name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public Context CreateSubcontext(String name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public Object LookupLink(Name name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public Object LookupLink(String name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public NameParser GetNameParser(Name name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public NameParser GetNameParser(String name)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public Name ComposeName(Name name, Name prefix)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public String ComposeName(String name, String prefix)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public Object AddToEnvironment(String propName, Object propVal)
	    {
	        throw new UnsupportedOperationException();
	    }

	    public Object RemoveFromEnvironment(String propName)
	    {
	        throw new UnsupportedOperationException();
	    }

        //public Hashtable<?, ?> GetEnvironment()
        //{
        //    throw new UnsupportedOperationException();
        //}

	    public void Close()
	    {
	        throw new UnsupportedOperationException();
	    }

	    public String GetNameInNamespace()
	    {
	        throw new UnsupportedOperationException();
	    }
	}
} // End of namespace
