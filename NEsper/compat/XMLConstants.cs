using System;
using System.Xml;

namespace net.esper.compat
{
	public class XMLConstants
	{
        /// <summary>
        /// Reserved "xml" prefix
        /// </summary>

        public const string XML_NS_PREFIX = "xml";

        /// <summary>
        /// The namespace for the reserved "xml" prefix.
        /// The prefix xml is by definition bound to the namespace name http://www.w3.org/XML/1998/namespace.
        /// It MAY, but need not, be declared, and MUST NOT be bound to any other namespace name. Other prefixes
        /// MUST NOT be bound to this namespace name, and it MUST NOT be declared as the default namespace.
        /// </summary>

        public const string XML_NS_URI = "http://www.w3.org/XML/1998/namespace";

        /// <summary>
        /// Reserved "xmlns" prefix
        /// </summary>

        public const string XMLNS_ATTRIBUTE = "xmlns" ;

        /// <summary>
        /// The prefix xmlns is used only to declare namespace bindings and is by definition bound to the namespace
        /// name http://www.w3.org/2000/xmlns/. It MUST NOT be declared . Other prefixes MUST NOT be bound to this
        /// namespace name, and it MUST NOT be declared as the default namespace. Element names MUST NOT have the
        /// prefix xmlns.
        /// </summary>
        
        public const string XMLNS_ATTRIBUTE_NS_URI = "http://www.w3.org/2000/xmlns/";        
	}
}
