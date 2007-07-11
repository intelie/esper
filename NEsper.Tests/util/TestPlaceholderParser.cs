using System;
using System.Collections.Generic;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.util
{
    [TestFixture]
    public class TestPlaceholderParser
    {
        [Test]
        public void testParseValid()
        {
            Object[][] testdata = new Object[][]
            {
                new Object[]{ "a  a $${lib}", new Object[] {textF("a  a ${lib}") }},
                new Object[]{"a ${lib} b", new Object[] {textF("a "), paramF("lib"), textF(" b")}},
                new Object[]{"${lib} b", new Object[] {paramF("lib"), textF(" b")}},
                new Object[]{"a${lib}", new Object[] {textF("a"), paramF("lib")}},
                new Object[]{"$${lib}", new Object[] {textF("${lib}")}},
                new Object[]{"$${lib} c", new Object[] {textF("${lib} c")}},
                new Object[]{"a$${lib}", new Object[] {textF("a${lib}")}},
                new Object[]{"sometext ${a} text $${d} ${e} text", new Object[] {textF("sometext "), paramF("a"), textF(" text ${d} "), paramF("e"), textF(" text")}},
                new Object[]{"$${lib} c $${lib}", new Object[] {textF("${lib} c ${lib}")}},
                new Object[]{"$${lib}$${lib}", new Object[] {textF("${lib}${lib}")}},
                new Object[]{"${xxx}$${lib}", new Object[] {paramF("xxx"), textF("${lib}")}},
                new Object[]{"$${xxx}${lib}", new Object[] {textF("${xxx}"), paramF("lib")}},
                new Object[]{"${lib} ${lib}", new Object[] {paramF("lib"), textF(" "), paramF("lib")}},
                new Object[]{"${lib}${lib}", new Object[] {paramF("lib"), paramF("lib")}},
                new Object[]{"$${lib", new Object[] {textF("${lib")}},
                new Object[]{"lib}", new Object[] {textF("lib}")}}
            };

            for (int i = 0; i < testdata.Length; i++)
            {
                testParseValid(testdata[i]);
            }
        }

        public void testParseValid(Object[] inputAndResults)
        {
            String ParseString = (String)inputAndResults[0];
            Object[] expected = (Object[])inputAndResults[1];

            IList<PlaceholderParser.Fragment> result = PlaceholderParser.ParsePlaceholder(ParseString);

            Assert.AreEqual(expected.Length, result.Count,
                "Incorrect count for '" + ParseString + "'");

            for (int i = 0; i < expected.Length; i++)
            {
                Assert.AreEqual(expected[i], result[i],
                    "Incorrect value for '" + ParseString + "' at " + i);
            }
        }

        [Test]
        public void testParseInvalid()
        {
            tryParseInvalid("${lib");
            tryParseInvalid("${lib} ${aa");
        }

        private void tryParseInvalid(String parseString)
        {
            try
            {
                PlaceholderParser.ParsePlaceholder(parseString);
                Assert.Fail();
            }
            catch (PlaceholderParseException)
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
}