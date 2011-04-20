package com.espertech.esper.example.runtimeconfig;

import com.espertech.esper.client.*;
import com.espertech.esper.client.time.CurrentTimeEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestRuntimeConfigMain extends TestCase
{
    public void testRuntimeConfig()
    {
        RuntimeConfigMain main = new RuntimeConfigMain();
        main.runExample();
    }
}

