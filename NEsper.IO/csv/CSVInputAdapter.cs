using System;
using System.IO;
using System.Collections.Generic;
using System.Reflection;

using net.esper.adapter;
using net.esper.client;
using net.esper.compat;
using net.esper.core;
using net.esper.events;
using net.esper.util;

using org.apache.commons.logging;

namespace net.esper.adapter.csv
{
    /// <summary>An event Adapter that uses a CSV file for a source.</summary>

    public class CSVInputAdapter : AbstractCoordinatedAdapter, InputAdapter
    {
        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        private Int32? eventsPerSec;
        private CSVReader reader;
        private IDictionary<String, ConstructorInfo> propertyConstructors;
        private String[] propertyOrder;
        private CSVInputAdapterSpec adapterSpec;
        private IDictionary<String, Type> propertyTypes;
        private String eventTypeAlias;
        private long lastTimestamp = 0;
        private long totalDelay;
        internal bool atEOF = false;
        internal String[] firstRow;

        /// <summary>Ctor.</summary>
        /// <param name="epService">provides the engine runtime and services</param>
        /// <param name="spec">the parameters for this adapter</param>

        public CSVInputAdapter(EPServiceProvider epService, CSVInputAdapterSpec spec)
            : base(epService, spec.isUsingEngineThread())
        {
            adapterSpec = spec;
            eventTypeAlias = adapterSpec.EventTypeAlias;
            eventsPerSec = spec.EventsPerSec;

            if (epService != null)
            {
                finishInitialization(epService, spec);
            }
        }

        /// <summary>Ctor.</summary>
        /// <param name="epService">provides the engine runtime and services</param>
        /// <param name="adapterInputSource">the source of the CSV file</param>
        /// <param name="eventTypeAlias">the alias of the Map event to create from the CSV data</param>

        public CSVInputAdapter(EPServiceProvider epService, AdapterInputSource adapterInputSource, String eventTypeAlias)
            : this(epService, new CSVInputAdapterSpec(adapterInputSource, eventTypeAlias))
        {
            
        }

        /// <summary>Ctor for adapters that will be passed to an AdapterCoordinator.</summary>
        /// <param name="adapterSpec">contains parameters that specify the behavior of the input adapter</param>

        public CSVInputAdapter(CSVInputAdapterSpec adapterSpec)
            : this(null, adapterSpec)
        {
        }

        /// <summary>Ctor for adapters that will be passed to an AdapterCoordinator.</summary>
        /// <param name="adapterInputSource">the parameters for this adapter</param>
        /// <param name="eventTypeAlias">the event type alias name that the input adapter generates events for</param>

        public CSVInputAdapter(AdapterInputSource adapterInputSource, String eventTypeAlias)
        {
            this(null, adapterInputSource, eventTypeAlias);
        }

        public SendableEvent Read()
        {
            if (stateManager.State == AdapterState.DESTROYED || atEOF)
            {
                return null;
            }

            try
            {
                if (eventsToSend.isEmpty())
                {
                    return new SendableMapEvent(NewMapEvent(), eventTypeAlias, totalDelay, scheduleSlot);
                }
                else
                {
                    SendableEvent _event = eventsToSend.First;
                    eventsToSend.Remove(_event);
                    return _event;
                }
            }
            catch (EOFException e)
            {
                log.debug(".read reached end of CSV file");
                atEOF = true;
                if (stateManager.State == AdapterState.STARTED)
                {
                    stop();
                }
                else
                {
                    destroy();
                }
                return null;
            }
        }

        public override EPService EPService
        {
            set
            {
                base.EPService = value;
                FinishInitialization(value, adapterSpec);
            }
        }

        /// <summary>
        /// Close the CSVReader.
        /// </summary>
        
        protected void Close()
        {
            reader.Close();
        }

        /// <summary>
        /// Remove the first member of eventsToSend. If there isanother record in the CSV file, 
        /// insert the event createdfrom it into eventsToSend.
        /// </summary>

        protected void ReplaceFirstEventToSend()
        {
            eventsToSend.Remove(eventsToSend.First);
            SendableEvent _event = Read();
            if (_event != null)
            {
                eventsToSend.Add(_event);
            }
        }

        /// <summary>
        /// Reset all the changeable state of this ReadableAdapter, as if it were just created.
        /// </summary>

        protected void Reset()
        {
            lastTimestamp = 0;
            totalDelay = 0;
            atEOF = false;
            if (reader.IsResettable)
            {
                reader.Reset();
            }
        }

        private void FinishInitialization(EPServiceProvider epService, CSVInputAdapterSpec spec)
        {
            assertValidParameters(epService, spec);

            EPServiceProviderSPI spi = (EPServiceProviderSPI)epService;

            scheduleSlot = spi.getSchedulingService().allocateBucket().allocateSlot();

            reader = new CSVReader(spec.getAdapterInputSource());
            reader.setLooping(spec.isLooping());

            String[] firstRow = getFirstRow();

            EDictionary<String, Type> givenPropertyTypes = ConstructPropertyTypes(spec.getEventTypeAlias(), spec.getPropertyTypes(), spi.EventAdapterService);

            propertyOrder =
                spec.getPropertyOrder() != null ?
                spec.getPropertyOrder() :
                CSVPropertyOrderHelper.resolvePropertyOrder(firstRow, givenPropertyTypes);

            reader.IsUsingTitleRow = IsUsingTitleRow(firstRow, propertyOrder);
            if (!IsUsingTitleRow(firstRow, propertyOrder))
            {
                this.firstRow = firstRow;
            }

            propertyTypes = resolvePropertyTypes(givenPropertyTypes);
            if (givenPropertyTypes == null)
            {
                spi.EventAdapterService.addMapType(eventTypeAlias, propertyTypes);
            }

            this.propertyConstructors = createPropertyConstructors(propertyTypes);
        }

        private EDictionary<String, Object> NewMapEvent()
        {
            String[] row = firstRow != null ? firstRow : reader.GetNextRecord();
            firstRow = null;
            UpdateTotalDelay(row, reader.GetAndClearIsReset());
            return CreateMapFromRow(row);
        }

        private EDictionary<String, ConstructorInfo> CreatePropertyConstructors(IDictionary<String, Type> propertyTypes)
        {
            EDictionary<String, ConstructorInfo> constructors = new EHashDictionary<String, ConstructorInfo>();

            Type[] parameterTypes = new Type[] { typeof(String) };
            foreach (String property in propertyTypes.Keys)
            {
                log.debug(".CreatePropertyConstructors property==" + property + ", type==" + propertyTypes.get(property));
                FastClass fastClass = FastClass.create(TypeHelper.GetBoxedType(propertyTypes.get(property)));
                FastConstructor constructor = fastClass.getConstructor(parameterTypes);
                constructors.put(property, constructor);
            }
            return constructors;
        }

        private EDictionary<String, Object> CreateMapFromRow(String[] row)
        {
            EDictionary<String, Object> map = new EHashDictionary<String, Object>();

            int count = 0;

            try
            {
                foreach (String property in propertyOrder)
                {
                    // Skip properties that are in the title row but not
                    // part of the map to send
                    if (propertyTypes != null && !propertyTypes.containsKey(property))
                    {
                        count++;
                        continue;
                    }
                    Object[] parameters = new Object[] { row[count++] };
                    Object value = propertyConstructors.get(property).newInstance(parameters);
                    map[property] = value;
                }
            }
            catch (InvocationTargetException e)
            {
                throw new EPException(e);
            }
            return map;
        }

        private IDictionary<String, Type> ConstructPropertyTypes(
            String eventTypeAlias,
            EDictionary<String, Type> propertyTypesGiven,
            EventAdapterService eventAdapterService)
        {
            EDictionary<String, Type> propertyTypes = new EHashDictionary<String, Type>();
            EventType eventType = eventAdapterService.getExistsTypeByAlias(eventTypeAlias);
            if (eventType == null)
            {
                if (propertyTypesGiven != null)
                {
                    eventAdapterService.addMapType(eventTypeAlias, propertyTypesGiven);
                }
                return propertyTypesGiven;
            }
            if (!eventType.UnderlyingType.Equals(typeof(Map)))
            {
                throw new EPException("Alias " + eventTypeAlias + " does not correspond to a map event");
            }
            if (propertyTypesGiven != null && eventType.PropertyNames.Length != propertyTypesGiven.Count)
            {
                throw new EPException("Event type " + eventTypeAlias + " has already been declared with a different number of parameters");
            }
            foreach (String property in eventType.PropertyNames)
            {
                Type type = eventType.GetPropertyType(property);
                if (propertyTypesGiven != null && propertyTypesGiven.get(property) == null)
                {
                    throw new EPException("Event type " + eventTypeAlias + "has already been declared with different parameters");
                }
                if (propertyTypesGiven != null && !propertyTypesGiven.get(property).equals(type))
                {
                    throw new EPException("Event type " + eventTypeAlias + "has already been declared with a different type for property " + property);
                }
                propertyTypes.put(property, type);
            }
            return propertyTypes;
        }

        private void UpdateTotalDelay(String[] row, bool isFirstRow)
        {
            if (eventsPerSec != null)
            {
                int msecPerEvent = 1000 / eventsPerSec;
                totalDelay += msecPerEvent;
            }
            else if (adapterSpec.getTimestampColumn() != null)
            {
                Int64? timestamp = ResolveTimestamp(row);
                if (timestamp == null)
                {
                    throw new EPException("Couldn't resolve the timestamp for record " + Arrays.asList(row));
                }
                else if (timestamp < 0)
                {
                    throw new EPException("Encountered negative timestamp for CSV record : " + Arrays.asList(row));
                }
                else
                {
                    long timestampDifference = 0;
                    if (timestamp < lastTimestamp)
                    {
                        if (!isFirstRow)
                        {
                            throw new EPException("Subsequent timestamp " + timestamp + " is smaller than previous timestamp " + lastTimestamp);
                        }
                        else
                        {
                            timestampDifference = timestamp;
                        }
                    }
                    else
                    {
                        timestampDifference = timestamp - lastTimestamp;
                    }
                    lastTimestamp = timestamp;
                    totalDelay += timestampDifference;
                }
            }
        }

        private Int64? ResolveTimestamp(String[] row)
        {
            int timestampIndex = GetTimestampIndex(row);
            if (timestampIndex != -1)
            {
                return Int64.Parse(row[timestampIndex]);
            }
            else
            {
                return null;
            }
        }

        private int GetTimestampIndex(String[] row)
        {
            if (adapterSpec.TimestampColumn != null)
            {
                for (int i = 0; i < propertyOrder.Length; i++)
                {
                    if (propertyOrder[i].equals(adapterSpec.TimestampColumn))
                    {
                        return i;
                    }
                }
            }
            return -1;
        }

        private IDictionary<String, Type> ResolvePropertyTypes(IDictionary<String, Type> propertyTypes)
        {
            if (propertyTypes != null)
            {
                return propertyTypes;
            }

            IDictionary<String, Type> result = new EHashDictionary<String, Type>();
            foreach (String property in propertyOrder)
            {
                result[property] = typeof(string);
            }
            return result;
        }

        private bool IsUsingTitleRow(String[] firstRow, String[] propertyOrder)
        {
            if (firstRow == null)
            {
                return false;
            }
            ISet<String> firstRowSet = new EHashSet<String>(Arrays.asList(firstRow));
            ISet<String> propertyOrderSet = new EHashSet<String>(Arrays.asList(propertyOrder));
            return firstRowSet.equals(propertyOrderSet);
        }

        /// <summary>
        /// Gets the first row.
        /// </summary>
        /// <value>The first row.</value>
        
        private String[] FirstRow
        {
            get
            {
                String[] firstRow;
                try
                {
                    firstRow = reader.getNextRecord();
                }
                catch (EOFException e)
                {
                    atEOF = true;
                    firstRow = null;
                }
                return firstRow;
            }
        }

        private void AssertValidEventsPerSec(Int32? eventsPerSec)
        {
            if (eventsPerSec != null)
            {
                if (eventsPerSec < 1 || eventsPerSec > 1000)
                {
                    throw new IllegalArgumentException("Illegal value of eventsPerSec:" + eventsPerSec);
                }
            }
        }

        private void AssertValidParameters(EPServiceProvider epService, CSVInputAdapterSpec adapterSpec)
        {
            if (!(epService is EPServiceProviderSPI))
            {
                throw new IllegalArgumentException("Invalid type of EPServiceProvider");
            }

            if (adapterSpec.EventTypeAlias == null)
            {
                throw new NullPointerException("eventTypeAlias cannot be null");
            }

            if (adapterSpec.AdapterInputSource == null)
            {
                throw new NullPointerException("adapterInputSource cannot be null");
            }

            assertValidEventsPerSec(adapterSpec.EventsPerSec);

            if (adapterSpec.IsLooping && !adapterSpec.AdapterInputSource.IsResettable)
            {
                throw new EPException("Cannot loop on a non-resettable input source");
            }
        }
    }
}