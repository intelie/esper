package net.esper.adapter;

/**
 * A AdapterCoordinator coordinates several Adapters so that the events they 
 * send into the runtime engine arrive in some well-defined order, in
 * effect making the several Adapters into one large sending Adapter.
 */
public interface AdapterCoordinator extends Adapter
{
	/**
	 * Coordinate an Adapter.
	 * @param adapter - the Adapter to coordinate
	 */
	public void coordinate(Adapter adapter);
}