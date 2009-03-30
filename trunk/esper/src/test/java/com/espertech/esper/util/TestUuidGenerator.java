package com.espertech.esper.util;

import junit.framework.TestCase;

import java.util.UUID;

public class TestUuidGenerator extends TestCase
{
    public void testGenerate()
    {
        String uuid = UuidGenerator.generate();
        System.out.println(uuid + " length " + uuid.length());

        String newuuid = UUID.randomUUID().toString();
        System.out.println(newuuid + " length " + newuuid.length());
    }
}
