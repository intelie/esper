///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using net.esper.core;

namespace net.esper.schedule
{
	/// <summary>
	/// Interface for scheduled callbacks.
	/// </summary>
	public interface ScheduleHandleCallback : ScheduleHandle
	{
	    /// <summary>
	    /// Callback that is invoked as indicated by a schedule added to the scheduling service.
	    /// </summary>
	    /// <param name="extensionServicesContext">
	    /// is a marker interface for providing custom extension services
	    /// passed to the triggered class
	    /// </param>
	    void ScheduledTrigger(ExtensionServicesContext extensionServicesContext);
	}

    /// <summary>
    /// Delegate that is invoked as indicated by a schedule added to the scheduling service.
    /// </summary>
    /// <param name="extensionServicesContext"></param>

    public delegate void ScheduleHandleDelegate(ExtensionServicesContext extensionServicesContext);

    /// <summary>
    /// Proxy implementation that allows CLR to use delegates to facilitate the implementation
    /// of the interface.
    /// </summary>

    public class ScheduleHandleCallbackImpl : ScheduleHandleCallback
    {
        private ScheduleHandleDelegate m_delegate;

        /// <summary>
        /// Initializes a new instance of the <see cref="ScheduleHandleCallbackImpl"/> class.
        /// </summary>
        /// <param name="dg">The dg.</param>
        public ScheduleHandleCallbackImpl( ScheduleHandleDelegate dg )
        {
            m_delegate = dg;
        }

        /// <summary>
        /// Callback that is invoked as indicated by a schedule added to the scheduling service.
        /// </summary>
        /// <param name="extensionServicesContext">is a marker interface for providing custom extension services
        /// passed to the triggered class</param>
        public void ScheduledTrigger(ExtensionServicesContext extensionServicesContext)
        {
            m_delegate(extensionServicesContext);
        }
    }

} // End of namespace
