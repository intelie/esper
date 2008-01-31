package com.espertech.esper.adapter;

import java.util.Comparator;

/** 
 * A comparator that orders SendableEvents first on sendTime, and
 * then on schedule slot.
 */
public class SendableEventComparator implements Comparator<SendableEvent>
{
	public int compare(SendableEvent one, SendableEvent two)
	{
		if(one.getSendTime() < two.getSendTime())
		{
			return -1;
		}
		else if(one.getSendTime() > two.getSendTime())
		{
			return 1;
		}
		else
		{
			return one.getScheduleSlot().compareTo(two.getScheduleSlot());
		}
	}
}
