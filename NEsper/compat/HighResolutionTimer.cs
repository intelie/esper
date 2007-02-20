using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Threading;

namespace net.esper.compat
{
    /// <summary>
    /// Windows timers are based on the system timer.  The system timer runs at a
    /// frequency of about 50-60 hz depending on your machine.  This presents a 
    /// problem for applications that require finer granularity.  The HighRes timer
    /// allows us to get better granularity, but currently it only works on Windows.
    /// 
    /// Thanks to Luc Pattyn for clarifying some of the issues with high resolution
    /// timers with the post on CodeProject.
    /// </summary>

    public class HighResolutionTimer : IDisposable
    {
        public delegate void TimerEventHandler(uint id, uint msg, IntPtr userCtx, uint rsv1, uint rsv2);

        [DllImport("winmm.dll", SetLastError = true)]
        static extern uint timeGetDevCaps(ref TimeCaps timeCaps, uint sizeTimeCaps);

        [DllImport("WinMM.dll", SetLastError = true)]
        private static extern uint timeSetEvent(uint msDelay, uint msResolution, TimerEventHandler handler, IntPtr userCtx, uint eventType);

        [DllImport("WinMM.dll", SetLastError = true)]
        private static extern uint timeKillEvent(uint timerEventId);

        private const int TIME_ONESHOT    = 0x0000   ; /* program timer for single event */
        private const int TIME_PERIODIC   = 0x0001   ; /* program for continuous periodic event */

        /// <summary>
        /// Callback is a function
        /// </summary>
        private const uint TIME_CALLBACK_FUNCTION = 0x0000 ;
        /// <summary>
        /// Callback is an event -- use SetEvent
        /// </summary>
        private const uint TIME_CALLBACK_EVENT_SET = 0x0010 ;
        /// <summary>
        /// Callback is an event -- use PulseEvent
        /// </summary>
        private const uint TIME_CALLBACK_EVENT_PULSE = 0x0020 ;
        /// <summary>
        /// This flag prevents the event from occurring after the user calls timeKillEvent() to
        /// destroy it.
        /// </summary>
        private const uint TIME_KILL_SYNCHRONOUS = 0x0100 ;

        private TimerCallback m_timerCallback;
        private Object m_state;
        private uint m_dueTime;
        private uint m_period;
        private uint? m_timer;
        private IntPtr m_data;

        private static TimerEventHandler m_delegate;

        /// <summary>
        /// Initializes a new instance of the <see cref="HighResolutionTimer"/> class.
        /// </summary>
        /// <param name="timerCallback">The timer callback.</param>
        /// <param name="state">The state.</param>
        /// <param name="dueTime">The due time.</param>
        /// <param name="period">The period.</param>
        
        public HighResolutionTimer( 
            TimerCallback timerCallback,
            Object state,
            int dueTime,
            int period )
        {
            m_timerCallback = timerCallback;
            m_state = state;
            m_dueTime = (uint) dueTime;
            m_period = (uint) period;
            m_data = Marshal.GetIUnknownForObject( this ) ;
            m_timer = null;

            Start();
        }

        /// <summary>
        /// Destructor
        /// </summary>
        /// <param name="id"></param>
        /// <param name="msg"></param>
        /// <param name="userCtx"></param>
        /// <param name="rsv1"></param>
        /// <param name="rsv2"></param>
        
        ~HighResolutionTimer()
        {
        	Dispose() ;
        }
        
        /// <summary>
        /// Called when timer event occurs.
        /// </summary>
        /// <param name="id">The id.</param>
        /// <param name="msg">The MSG.</param>
        /// <param name="userCtx">The user CTX.</param>
        /// <param name="rsv1">The RSV1.</param>
        /// <param name="rsv2">The RSV2.</param>
        
        private static void OnTimerEvent(uint id, uint msg, IntPtr userCtx, uint rsv1, uint rsv2)
        {
        	// Check for an invalid pointer.  Appears that this condition
        	// occurs when the GC moves memory around.  Because we use the
        	// IntPtr to the data that is provided to use through the Marshaller
        	// we expect that this value should not change.
        	
            long ptrLongValue = userCtx.ToInt64();
            if ( ptrLongValue <= 0 )
            {
                return;
            }
            
            // Convert the IntPtr back into its object form.  Once in its object form, the
            // object resolution timer can be called.

            try
            {
                HighResolutionTimer timer = Marshal.GetObjectForIUnknown(userCtx) as HighResolutionTimer;
                if (timer != null)
                {
                    timer.m_timerCallback(timer.m_state);
                }
            }
            catch (Exception xx)
            {
            }
        }

        /// <summary>
        /// Starts this instance.
        /// </summary>
        
        private void Start()
        {
            if (m_dueTime == 0)
            {
                m_timer = timeSetEvent(
                    m_period,
                    m_period,
                    m_delegate,
                    m_data,
                    TIME_PERIODIC | TIME_KILL_SYNCHRONOUS);
                m_timerTable[m_timer.Value] = m_timer.Value;
                m_timerCallback(m_state);
            }
            else
            {
                m_timer = timeSetEvent(
                    m_dueTime,
                    m_period,
                    m_delegate,
                    m_data,
                    TIME_PERIODIC | TIME_KILL_SYNCHRONOUS);
                m_timerTable[m_timer.Value] = m_timer.Value;
            }
        }

        /// <summary>
        /// Performs application-defined tasks associated with freeing, releasing, or resetting unmanaged resources.
        /// </summary>
        
        public void Dispose()
        {
            if (m_timer.HasValue)
            {
                timeKillEvent(m_timer.Value);
                m_timerTable.Remove(m_timer.Value);
                m_timer = null;
            }
        }

        /// <summary>
        /// Reference to the appDomain for this instance
        /// </summary>

        static AppDomain m_appDomain;
        static Dictionary<uint, uint> m_timerTable;

        static HighResolutionTimer()
        {
            m_timerTable = new Dictionary<uint, uint>();
            m_appDomain = AppDomain.CurrentDomain;
            m_appDomain.DomainUnload += new EventHandler(OnAppDomainUnload);
            m_delegate = new TimerEventHandler(OnTimerEvent);
        }

        /// <summary>
        /// Called when an AppDomain is unloaded.  Our goal here is to ensure that
        /// all timers created by this class under the banner of this AppDomain
        /// are cleaned up prior to the AppDomain unloading.  Failure to do so will
        /// cause applications to crash due to exceptions outside of the AppDomain.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>

        static void OnAppDomainUnload(object sender, EventArgs e)
        {
            if (sender == m_appDomain)
            {
                // Current AppDomain is about to unload.  It is vital that any
                // multimedia timers that were tied to this domain be killed
                // immediately so that they do not attempt to make invocations
                // back into this domain.

                foreach (uint timerId in m_timerTable.Keys)
                {
                    timeKillEvent(timerId);
                }
                
                m_timerTable.Clear() ;

                // Give the timers a brief amount of time to recover

                Thread.Sleep(100);
            }
        }

        [StructLayout(LayoutKind.Sequential)]
        public struct TimeCaps
        {
            public UInt32 wPeriodMin;
            public UInt32 wPeriodMax;
        };
    }
}
