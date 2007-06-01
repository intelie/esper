using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;
using net.esper.core;
using net.esper.schedule;

using org.apache.commons.logging;

namespace net.esper.adapter
{
	/// <summary>
	/// An implementation of AdapterCoordinator.
	/// </summary>
	public class AdapterCoordinatorImpl : AbstractCoordinatedAdapter, AdapterCoordinator
	{
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    private readonly EDictionary<SendableEvent, CoordinatedAdapter> eventsFromAdapters = new EHashDictionary<SendableEvent, CoordinatedAdapter>();
		private readonly ISet<CoordinatedAdapter> emptyAdapters = new EHashSet<CoordinatedAdapter>();
		private readonly bool usingEngineThread;
		private readonly ScheduleBucket scheduleBucket;
		private readonly EPServiceProvider epService;

		/// <summary>
		/// Ctor.
		/// </summary>
		/// <param name="epService">the EPServiceProvider for the engine services and runtime</param>
		/// <param name="usingEngineThread">true if the coordinator should set time by the scheduling service in the engine,
		///                           false if it should set time externally through the calling thread
		/// </param>
		public AdapterCoordinatorImpl(EPServiceProvider epService, bool usingEngineThread)
			: base(epService, usingEngineThread)
		{
			if(epService == null)
			{
				throw new NullPointerException("epService cannot be null");
			}
			if(!(epService is EPServiceProviderSPI))
			{
				throw new IllegalArgumentException("Illegal type of EPServiceProvider");
			}
			this.epService = epService;
			this.scheduleBucket = ((EPServiceProviderSPI)epService).getSchedulingService().allocateBucket();
			this.usingEngineThread = usingEngineThread;
		}

		/// <summary>
		///@see net.esper.adapter.ReadableAdapter#read()
		/// </summary>
		public SendableEvent Read()
		{
			log.Debug(".read");
			PollEmptyAdapters();

			log.Debug(".read eventsToSend.isEmpty==" + eventsToSend.isEmpty());
			log.Debug(".read eventsFromAdapters.isEmpty==" + eventsFromAdapters.isEmpty());
			log.Debug(".read emptyAdapters.isEmpty==" + emptyAdapters.isEmpty());

			if(eventsToSend.isEmpty() && eventsFromAdapters.isEmpty() && emptyAdapters.isEmpty())
			{
				stop();
			}

			if(stateManager.State == AdapterState.DESTROYED || eventsToSend.isEmpty())
			{
				return null;
			}

			SendableEvent result = eventsToSend.first();

			replaceFirstEventToSend();

			return result;
		}

		/// <summary>
		///@see net.esper.adapter.AdapterCoordinator#add(net.esper.adapter.Adapter)
		/// </summary>
		public void Coordinate(InputAdapter inputAdapter)
		{
			if(inputAdapter == null)
			{
				throw new NullPointerException("AdapterSpec cannot be null");
			}

			if(!(inputAdapter is CoordinatedAdapter))
			{
				throw new IllegalArgumentException("Cannot coordinate a Adapter of type " + inputAdapter.getClass());
			}
			CoordinatedAdapter adapter = (CoordinatedAdapter)inputAdapter;
			if(eventsFromAdapters.values().contains(adapter) || emptyAdapters.contains(adapter))
			{
				return;
			}
			adapter.disallowStateTransitions();
			adapter.EPService = epService;
			adapter.UsingEngineThread = usingEngineThread;
			adapter.ScheduleSlot = scheduleBucket.AllocateSlot();
			AddNewEvent(adapter);
		}

		/// <summary>
		/// Does nothing.
		/// </summary>
		protected void Close()
		{
			// Do nothing
		}

		/// <summary>
		/// Replace the first member of eventsToSend with the next
		/// event returned by the read() method of the same Adapter that
		/// provided the first event.
		/// </summary>
		protected void ReplaceFirstEventToSend()
		{
			log.Debug(".replaceFirstEventToSend");
			SendableEvent _event = eventsToSend.First;
			eventsToSend.remove(_event);
			addNewEvent(eventsFromAdapters.Fetch(_event));
			pollEmptyAdapters();
		}

		/// <summary>
		/// Reset all the changeable state of this ReadableAdapter, as if it were just created.
		/// </summary>
		protected void Reset()
		{
			eventsFromAdapters.Clear();
			emptyAdapters.Clear();
		}

		private void AddNewEvent(CoordinatedAdapter adapter)
		{
			log.Debug(".addNewEvent eventsFromAdapters==" + eventsFromAdapters);
			SendableEvent _event = adapter.read();
			if(_event != null)
			{
				log.Debug(".addNewEvent event==" + _event);
				eventsToSend.Add(_event);
				eventsFromAdapters[_event] = adapter;
			}
			else
			{
				if(adapter.State == AdapterState.DESTROYED)
				{
					eventsFromAdapters.values().removeAll(Collections.singleton(adapter));
				}
				else
				{
					emptyAdapters.Add(adapter);
				}
			}
		}

		private void PollEmptyAdapters()
		{
			log.Debug(".pollEmptyAdapters emptyAdapters.size==" + emptyAdapters.Count);

			List<CoordinatedAdapter> tempList = new List<CoordinatedAdapter>() ;

			foreach( CoordinatedAdapter adapter in emptyAdapters )
			{
				if(adapter.State == AdapterState.DESTROYED)
				{
					tempList.Add( adapter ) ;
					continue;
				}

				SendableEvent _event = adapter.Read();
				if(_event != null)
				{
					eventsToSend.Add(_event);
					eventsFromAdapters[_event] = adapter;
				}
			}
			
			foreach( CoordinatedAdapter adapter in tempList )
			{
				emptyAdapters.Remove( adapter ) ;
			}
		}
	}
}
