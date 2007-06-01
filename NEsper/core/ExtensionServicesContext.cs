///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

namespace net.esper.core
{
	/// <summary>
	/// Marker interface for extension services that provide additional engine or statement-level extensions,
	/// such as views backed by a write-behind store.
	/// </summary>
	public interface ExtensionServicesContext
	{
	    /// <summary>
	    /// Invoked to destroy the extension services, when an existing engine is initialized.
	    /// </summary>
	    void Destroy();
	}
} // End of namespace
