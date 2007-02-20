using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.util
{
	/// <summary>
    /// Parser for strings with substitution parameters of the form ${parameter}.
    /// </summary>
	
    public class PlaceholderParser
	{
		/// <summary> Parses a string to find placeholders of format ${placeholder}.
		/// <p>
		/// Example: "My ${thing} is ${color}"
		/// <p>
		/// The example above parses into 4 fragements: a text fragment of value "My ",
		/// a parameter fragment "thing", a text fragement " is " and a parameter
		/// fragment "color".
		/// </summary>
		/// <param name="ParseString">is the string to parse
		/// </param>
		/// <returns> list of fragements that can be either text fragments or placeholder fragments
		/// </returns>
		/// <throws>  PlaceholderParseException if the string cannot be parsed to indicate syntax errors </throws>

        public static IList<Fragment> parsePlaceholder(String parseString)
        {
            List<Fragment> result = new List<Fragment>();
            int currOutputIndex = 0;
            int currSearchIndex = 0;

            while (true)
            {
                if (currSearchIndex == parseString.Length)
                {
                    break;
                }

                int startIndex = parseString.IndexOf("${", currSearchIndex);
                if (startIndex == -1)
                {
                    // no more parameters, add any remainder of string
                    if (currOutputIndex < parseString.Length)
                    {
                        String endString = parseString.Substring(currOutputIndex);
                        TextFragment textFragment = new TextFragment(endString);
                        result.Add(textFragment);
                    }
                    break;
                }
                // add text so far
                if (startIndex > 0)
                {
                    String textSoFar = parseString.Substring(currOutputIndex, startIndex - currOutputIndex);
                    if (textSoFar.Length != 0)
                    {
                        result.Add(new TextFragment(textSoFar));
                    }
                }
                // check if the parameter is escaped
                if ((startIndex > 0) && (parseString[startIndex - 1] == '$'))
                {
                    currOutputIndex = startIndex + 1;
                    currSearchIndex = startIndex + 1;
                    continue;
                }

                int endIndex = parseString.IndexOf("}", startIndex);
                if (endIndex == -1)
                {
                    throw new PlaceholderParseException("Syntax error in property: " + parseString.Substring(startIndex));
                }

                // add placeholder
                String between = parseString.Substring(startIndex + 2, endIndex - startIndex - 2);
                ParameterFragment parameterFragment = new ParameterFragment(between);
                result.Add(parameterFragment);
                currOutputIndex = endIndex + 1;
                currSearchIndex = endIndex;
            }

            // Combine adjacent text fragements
            ELinkedList<Fragment> fragments = new ELinkedList<Fragment>();
            fragments.Add(result[0]);
            for (int i = 1; i < result.Count; i++)
            {
                Fragment fragment = result[i];
                if (!(result[i] is TextFragment))
                {
                    fragments.Add(fragment);
                    continue;
                }
                if (!(fragments.Last is TextFragment))
                {
                    fragments.Add(fragment);
                    continue;
                }
                TextFragment textFragment = (TextFragment)fragments.Last;
                fragments.RemoveLast();
                fragments.Add(new TextFragment(textFragment.Value + fragment.Value));
            }

            return fragments;
        }
		
		/// <summary> Fragment is a parse result, a parse results in an ordered list of fragments.</summary>
		public abstract class Fragment
		{
			/// <summary> Returns the string text of the fragment.</summary>
			/// <returns> fragment string
			/// </returns>
			virtual public String Value
			{
				get
				{
					return value;
				}
				
			}
			/// <summary> Returns true to indicate this is a parameter and not a text fragment.</summary>
			/// <returns> true if parameter fragement, false if text fragment.
			/// </returns>
			public abstract bool IsParameter
			{
				get;
			}
			
			private String value;
			
			/// <summary> Ctor.</summary>
			/// <param name="value">is the fragment text
			/// </param>
			internal Fragment(String value)
			{
				this.value = value;
			}
		}
		
		/// <summary>
		/// Represents a piece of text in a parse string with placeholder values.
		/// </summary>
		
		public class TextFragment : Fragment
		{
			override public bool IsParameter
			{
				get
				{
					return false;
				}
				
			}
		
			/// <summary> Ctor.</summary>
			/// <param name="value">is the text
			/// </param>
			
			public TextFragment(String value):base(value)
			{
			}
			
			public override bool Equals(Object obj)
			{
				if (!(obj is TextFragment))
				{
					return false;
				}
				TextFragment other = (TextFragment) obj;
				return other.Value.Equals(this.Value);
			}
			
			public override String ToString()
			{
				return "text=" + Value;
			}

            public override int GetHashCode()
			{
				return base.GetHashCode();
			}
		}
		
		/// <summary>
		///  Represents a parameter in a parsed string of texts and parameters.
		/// </summary>
		
		public class ParameterFragment : Fragment
		{
			override public bool IsParameter
			{
				get
				{
					return true;
				}
				
			}
			/// <summary> Ctor.</summary>
			/// <param name="value">is the parameter name
			/// </param>
			public ParameterFragment(String value)
				: base(value)
			{
			}
			
			public  override bool Equals(Object obj)
			{
				if (!(obj is ParameterFragment))
				{
					return false;
				}
				ParameterFragment other = (ParameterFragment) obj;
				return other.Value.Equals(this.Value);
			}
			
			public override String ToString()
			{
				return "param=" + Value;
			}

            public override int GetHashCode()
			{
				return base.GetHashCode();
			}
		}
	}
}
