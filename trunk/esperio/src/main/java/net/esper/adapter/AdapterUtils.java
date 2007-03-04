package net.esper.adapter;

/**
 * Created for ESPER.
 */
public class AdapterUtils
{
  public static String EP_RUNTIME_NOT_FOUND_ERROR =
    "Esper engine cannot be null";
  public static String EP_SERVICE_NOT_FOUND_ERROR = "epService cannot be null";
  public static String EP_SERVICE_INVALID_TYPE_ERROR =
    "Invalid type of EPServiceProvider";
  public static String ADAPTER_START_ERROR =
    "Attempting to start an Adapter that hasn't had the epService provided";

  public static String JMS_ADAPTER_DEFAULT_EVENT_TYPE_ALIAS =
    "SpringJMSAdapterType";
}
