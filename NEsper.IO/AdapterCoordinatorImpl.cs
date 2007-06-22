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
	public class AdapterCoordinatorImpl
		: AbstractCoordinatedAdapter
		, AdapterCoordinator
	{
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

	    private readonly EDictionary<SendableEvent, CoordinatedAdapter> eventsFromAdapters = new HashDictionary<SendableEvent, CoordinatedAdapter>();
		private readonly Set<CoordinatedAdapter> emptyAdapters = new HashSet<CoordinatedAdapter>();
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
				throw new ArgumentException("epService cannot be null");
			}
			if(!(epService is EPServiceProviderSPI))
			{
				throw new ArgumentException("Illegal type of EPServiceProvider");
			}
			this.epService = epService;
			this.scheduleBucket = ((EPServiceProviderSPI)epService).SchedulingService.AllocateBucket();
			this.usingEngineThread = usingEngineThread;
		}

		/// <summary>
		///@see net.esper.adapter.ReadableAdapter#read()
		/// </summary>
		public override SendableEvent Read()
		{
			log.Debug(".read");
			PollEmptyAdapters();

			log.Debug(".read eventsToSend.isEmpty==" + eventsToSend.IsEmpty);
			log.Debug(".read eventsFromAdapters.isEmpty==" + (eventsFromAdapters.Count == 0));
			log.Debug(".read emptyAdapters.isEmpty==" + emptyAdapters.IsEmpty);

			if(eventsToSend.IsEmpty && (eventsFromAdapters.Count == 0) && emptyAdapters.IsEmpty)
			{
				Stop();
			}

			if(stateManager.State == AdapterState.DESTROYED || eventsToSend.IsEmpty)
			{
				return null;
			}

			SendableEvent result = eventsToSend.First;

			ReplaceFirstEventToSend();

			return result;
		}

		/// <summary>
		///@see net.esper.adapter.AdapterCoordinator#add(net.esper.adapter.Adapter)
		/// </summary>
		public virtual void Coordinate(InputAdapter inputAdapter)
		{
			if(inputAdapter == null)
			{
				throw new ArgumentException("AdapterSpec cannot be null");
			}

			if(!(inputAdapter is CoordinatedAdapter))
			{
				throw new ArgumentException("Cannot coordinate a Adapter of type " + inputAdapter.GetType());
			}
			CoordinatedAdapter adapter = (CoordinatedAdapter)inputAdapter;
			if(eventsFromAdapters.Values.Contains(adapter) || emptyAdapters.Contains(adapter))
			{
				return;
			}
			adapter.DisallowStateTransitions();
			adapter.EPService = epService;
			adapter.UsingEngineThread = usingEngineThread;
			adapter.ScheduleSlot = scheduleBucket.AllocateSlot();
			AddNewEvent(adapter);
		}

		/// <summary>
		/// Does nothing.
		/// </summary>
		protected override void Close()
		{
			// Do nothing
		}

		/// <summary>
		/// Replace the first member of eventsToSend with the next
		/// event returned by the read() method of the same Adapter that
		/// provided the first event.
		/// </summary>
		protected override void ReplaceFirstEventToSend()
		{
			log.Debug(".replaceFirstEventToSend");
			SendableEvent _event = eventsToSend.First;
			eventsToSend.Remove(_event);
			AddNewEvent(eventsFromAdapters.Fetch(_event));
			PollEmptyAdapters();
		}

		/// <summary>
		/// Reset all the changeable state of this ReadableAdapter, as if it were just created.
		/// </summary>
		protected override void Reset()
		{
			eventsFromAdapters.Clear();
			emptyAdapters.Clear();
		}

		private void AddNewEvent(CoordinatedAdapter adapter)
		{
			log.Debug(".addNewEvent eventsFromAdapters==" + eventsFromAdapters);
			SendableEvent _event = adapter.Read();
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
					LinkedList<SendableEvent> keyList = new LinkedList<SendableEvent>() ;
					
					foreach( KeyValuePair<SendableEvent,CoordinatedAdapter> entry in eventsFromAdapters )
					{
						if ( entry.Value == adapter )
						{
							keyList.AddFirst( entry.Key ) ;
						}
					}
					
					foreach( SendableEvent keyEvent in keyList )
					{
						eventsFromAdapters.Remove( keyEvent ) ;
					}
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
