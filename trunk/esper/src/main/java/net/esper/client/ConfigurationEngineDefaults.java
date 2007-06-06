package net.esper.client;

public class ConfigurationEngineDefaults
{
    private Threading threading;

    public ConfigurationEngineDefaults()
    {
        threading = new Threading();
    }

    public Threading getThreading()
    {
        return threading;
    }

    public class Threading
    {
        private boolean isListenerDispatchPreserveOrder;
        private long listenerDispatchTimeout;
        private boolean isInsertIntoDispatchPreserveOrder;

        public Threading()
        {
            listenerDispatchTimeout = 1000;
            isListenerDispatchPreserveOrder = true;
            isInsertIntoDispatchPreserveOrder = true;
        }

        public void setListenerDispatchPreserveOrder(boolean value)
        {
            isListenerDispatchPreserveOrder = value;
        }

        public void setListenerDispatchTimeout(long value)
        {
            listenerDispatchTimeout = value;
        }

        public void setInsertIntoDispatchPreserveOrder(boolean value)
        {
            isInsertIntoDispatchPreserveOrder = value;
        }

        public boolean isListenerDispatchPreserveOrder()
        {
            return isListenerDispatchPreserveOrder;
        }

        public long getListenerDispatchTimeout()
        {
            return listenerDispatchTimeout;
        }

        public boolean isInsertIntoDispatchPreserveOrder()
        {
            return isInsertIntoDispatchPreserveOrder;
        }
    }
}
