/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio;

import java.util.Map;

/**
 * Sender that sends without a threadpool.
 */
public class DirectSender extends AbstractSender {

	public void sendEvent(AbstractSendableEvent event, Object beanToSend) {
		runtime.sendEvent(beanToSend);
	}

	public void sendEvent(AbstractSendableEvent event, Map mapToSend, String eventTypeName) {
		runtime.sendEvent(mapToSend, eventTypeName);
	}

	public void onFinish() {
		// do nothing
	}
}
