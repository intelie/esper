package net.esper.util;

import junit.framework.TestCase;

import java.util.List;

public class TestPlaceholderParser extends TestCase
{
    public void testParseValid() throws Exception
    {
        Object[][] testdata = new Object[][] {
          {"a  a $${lib}", new Object[] {textF("a  a ${lib}") }},
          {"a ${lib} b", new Object[] {textF("a "), paramF("lib"), textF(" b")}},
          {"${lib} b", new Object[] {paramF("lib"), textF(" b")}},
          {"a${lib}", new Object[] {textF("a"), paramF("lib")}},
          {"$${lib}", new Object[] {textF("${lib}")}},
          {"$${lib} c", new Object[] {textF("${lib} c")}},
          {"a$${lib}", new Object[] {textF("a${lib}")}},
          {"sometext ${a} text $${d} ${e} text",
                  new Object[] {textF("sometext "), paramF("a"), textF(" text ${d} "), paramF("e"), textF(" text")}}
            };

        for (int i = 0; i < testdata.length; i++)
        {
            testParseValid(testdata[i]);
        }
    }

    public void testParseValid(Object[] inputAndResults) throws Exception
    {
        String parseString = (String) inputAndResults[0];
        Object[] expected = (Object[]) inputAndResults[1];

        List<PlaceholderParser.Fragment> result = PlaceholderParser.parsePlaceholder(parseString);

        assertEquals("Incorrect count for '" + parseString + "'", expected.length, result.size());
        for (int i = 0; i < expected.length; i++)
        {
            assertEquals("Incorrect value for '" + parseString + "' at " + i, expected[i], result.get(i));
        }
    }

    public void testParseInvalid()
    {
        tryParseInvalid("${lib");
    }

    private void tryParseInvalid(String parseString)
    {
        try
        {
            PlaceholderParser.parsePlaceholder(parseString);
            fail();
        }
        catch (PlaceholderParseException ex)
        {
            // expected
        }
    }

    private PlaceholderParser.TextFragment textF(String text)
    {
        return new PlaceholderParser.TextFragment(text);
    }

    private PlaceholderParser.ParameterFragment paramF(String text)
    {
        return new PlaceholderParser.ParameterFragment(text);
    }
}
