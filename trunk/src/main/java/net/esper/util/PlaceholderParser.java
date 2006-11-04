package net.esper.util;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Select * from table where myVal =
 *  ${stream.field}
 *  ${field}
 *  ${stream.field.nested}
 *  ${stream.mapped('aa').indexed[1]}
 *
 *  List<String> segments;
 *  List<String> properties;
 */
public class PlaceholderParser
{
    public static List<Fragment> parsePlaceholder(String parseString) throws PlaceholderParseException
    {
        List<Fragment> result = new ArrayList<Fragment>();
        int currOutputIndex = 0;
        int currSearchIndex = 0;

        while(true)
        {
            if (currSearchIndex == parseString.length())
            {
                break;
            }

            int startIndex = parseString.indexOf("${", currSearchIndex);
            if (startIndex == -1)
            {
                // no more parameters, add any remainder of string
                if (currOutputIndex < parseString.length())
                {
                    String endString = parseString.substring(currOutputIndex, parseString.length());
                    TextFragment textFragment = new TextFragment(endString);
                    result.add(textFragment);
                }
                break;
            }
            // add text so far
            if (startIndex > 0)
            {
                String textSoFar = parseString.substring(currOutputIndex, startIndex);
                result.add(new TextFragment(textSoFar));
            }
            // check if the parameter is escaped
            if ((startIndex > 0) && (parseString.charAt(startIndex - 1) == '$'))
            {
                currOutputIndex = startIndex + 1;
                currSearchIndex = startIndex + 1;
                continue;
            }

            int endIndex = parseString.indexOf("}", startIndex);
            if (endIndex == -1)
            {
                throw new PlaceholderParseException("Syntax error in property: " + parseString.substring(startIndex, parseString.length()));
            }

            // add placeholder
            String between = parseString.substring(startIndex+2, endIndex);
            ParameterFragment parameterFragment = new ParameterFragment(between);
            result.add(parameterFragment);
            currOutputIndex = endIndex + 1;
            currSearchIndex = endIndex;
        }

        // Combine adjacent text fragements
        LinkedList<Fragment> fragments = new LinkedList<Fragment>();
        fragments.add(result.get(0));
        for (int i = 1; i < result.size(); i++)
        {
            Fragment fragment = result.get(i);
            if (!(result.get(i) instanceof TextFragment))
            {
                fragments.add(fragment);
                continue;
            }
            if (!(fragments.getLast() instanceof TextFragment))
            {
                fragments.add(fragment);
                continue;
            }
            TextFragment textFragment = (TextFragment) fragments.getLast();
            fragments.removeLast();
            fragments.add(new TextFragment(textFragment.getValue() + fragment.getValue()));
        }

        return fragments;
    }

    public static abstract class Fragment
    {
        private String value;

        protected Fragment(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }

    public static class TextFragment extends Fragment
    {
        public TextFragment(String value)
        {
            super(value);
        }

        public boolean equals(Object obj)
        {
            if (!(obj instanceof TextFragment))
            {
                return false;
            }
            TextFragment other = (TextFragment) obj;
            return other.getValue().equals(this.getValue());
        }

        public String toString()
        {
            return "text=" + getValue();
        }
    }

    public static class ParameterFragment extends Fragment
    {
        public ParameterFragment(String value)
        {
            super(value);
        }

        public boolean equals(Object obj)
        {
            if (!(obj instanceof ParameterFragment))
            {
                return false;
            }
            ParameterFragment other = (ParameterFragment) obj;
            return other.getValue().equals(this.getValue());
        }

        public String toString()
        {
            return "param=" + getValue();
        }
    }
}
