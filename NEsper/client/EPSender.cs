using net.esper.compat;

namespace net.esper.client
{
    /// <summary>
    /// Send a map containing event property values to the event stream processing runtime.
    /// Use the route method for sending events into the runtime from within UpdateListener code.
    /// </summary>
    /// <param name="mappedEvent">map that contains event property values. Keys are expected to be of type String while values
    /// can be of any type. Keys and values should match those declared via Configuration for the given eventTypeAlias. 
    /// </param>
    /// <throws>  EPException - when the processing of the event leads to an error </throws>
    public delegate void EPSender(IDataDictionary mappedEvent);
}
