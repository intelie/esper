/// <summary>***********************************************************************************
/// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
/// http://esper.codehaus.org                                                          *
/// ---------------------------------------------------------------------------------- *
/// The software in this package is published under the terms of the GPL license       *
/// a copy of which has been included with this distribution in the license.txt file.  *
/// ************************************************************************************
/// </summary>

using System;
using System.Collections.Generic;

using net.esper.compat;

namespace net.esper.client
{
    /// <summary>
    /// Configuration information for legacy event types.
    /// </summary>
    
    public class ConfigurationEventTypeLegacy
    {
        private AccessorStyleEnum accessorStyle;
        private CodeGenerationEnum codeGeneration;
        private List<LegacyFieldPropDesc> fieldProperties;
        private List<LegacyMethodPropDesc> methodProperties;

        /// <summary>
        /// Ctor.
        /// </summary>
        
        public ConfigurationEventTypeLegacy()
        {
            accessorStyle = AccessorStyleEnum.NATIVE;
            codeGeneration = CodeGenerationEnum.ENABLED;
            fieldProperties = new List<LegacyFieldPropDesc>();
            methodProperties = new List<LegacyMethodPropDesc>();
        }

        /// <summary>
        /// Accessor style defines the methods of a class that are automatically exposed via event property.
        /// </summary>

        public enum AccessorStyleEnum
        {
            /// <summary> Expose properties only, plus explicitly configured properties.</summary>
            NATIVE,
            /// <summary> Expose only the explicitly configured properties and public members as event properties.</summary>
            EXPLICIT,
            /// <summary> Expose all public properties and public members as event properties, plus explicitly configured properties.</summary>
            PUBLIC
        }

        /// <summary> Enum to control code generation.</summary>
        public enum CodeGenerationEnum
        {
            /// <summary> Enables code generation.</summary>
            ENABLED,
            /// <summary> Dispables code generation.</summary>
            DISABLED
        }

        /// <summary>
        /// Gets or sets the accessor style.
        /// </summary>
        /// <value>The accessor style.</value>

        public virtual AccessorStyleEnum AccessorStyle
        {
            get { return this.accessorStyle; }
            set { this.accessorStyle = value ; }
        }

        /// <summary>
        /// Gets or sets the code generation.  Thus controls whether or
        /// not the engine generates code for access to event property values.
        /// </summary>
        /// <value>The code generation.</value>

        public virtual CodeGenerationEnum CodeGeneration
        {
            get { return this.codeGeneration; }
            set { this.codeGeneration = value; }
        }

        /// <summary>
        /// Returns a list of descriptors specifying explicitly configured method names
        /// and their property name.
        /// </summary>
        /// <returns> list of explicit method-access descriptors
        /// </returns>

        public IList<LegacyMethodPropDesc> MethodProperties
        {
            get { return methodProperties; }
        }

        /// <summary> Returns a list of descriptors specifying explicitly configured field names
        /// and their property name.
        /// </summary>
        /// <returns> list of explicit field-access descriptors
        /// </returns>
        
        public IList<LegacyFieldPropDesc> FieldProperties
        {
            get { return fieldProperties; }
        }

        /// <summary>
        /// Adds the named event property backed by the named accessor method.
        /// The accessor method is expected to be a public method with no parameters
        /// for simple event properties, or with a single integer parameter for indexed
        /// event properties, or with a single String parameter for mapped event properties.
        /// </summary>
        /// <param name="name">is the event property name</param>
        /// <param name="accessorMethod">is the accessor method name.</param>
        
        public virtual void addMethodProperty(String name, String accessorMethod)
        {
        	methodProperties.Add(new LegacyMethodPropDesc(name, accessorMethod));
        }

        /// <summary>
        /// Adds the named event property backed by the named accessor field.
        /// </summary>
        /// <param name="name">is the event property name</param>
        /// <param name="accessorField">is the accessor field underlying the name</param>
        
        public virtual void addFieldProperty(String name, String accessorField)
        {
            fieldProperties.Add(new LegacyFieldPropDesc(name, accessorField));
        }

        /// <summary>
        /// Encapsulates information about an accessor field backing a named event property.
        /// </summary>

        public class LegacyFieldPropDesc
        {
            private String name;
            private String accessorFieldName;

            /// <summary> Returns the event property name.</summary>
            /// <returns> event property name
            /// </returns>
            virtual public String Name
            {
                get { return name; }
            }
            /// <summary> Returns the accessor field name.</summary>
            /// <returns> accessor field name
            /// </returns>
            virtual public String AccessorFieldName
            {
                get { return accessorFieldName; }
            }

            /// <summary> Ctor.</summary>
            /// <param name="name">is the event property name
            /// </param>
            /// <param name="accessorFieldName">is the accessor field name
            /// </param>
            public LegacyFieldPropDesc(String name, String accessorFieldName)
            {
                this.name = name;
                this.accessorFieldName = accessorFieldName;
            }
        }

        /// <summary>
        /// Encapsulates information about an accessor method backing a named event property.
        /// </summary>

        public class LegacyMethodPropDesc
        {
            private String name;
            private String accessorMethodName;

            /// <summary> Returns the event property name.</summary>
            /// <returns> event property name
            /// </returns>
            virtual public String Name
            {
                get { return name; }
            }

            /// <summary> Returns the accessor method name.</summary>
            /// <returns> accessor method name
            /// </returns>
            virtual public String AccessorMethodName
            {
                get { return accessorMethodName; }
            }

            /// <summary> Ctor.</summary>
            /// <param name="name">is the event method name
            /// </param>
            /// <param name="accessorMethodName">is the accessor method name
            /// </param>
            public LegacyMethodPropDesc(String name, String accessorMethodName)
            {
                this.name = name;
                this.accessorMethodName = accessorMethodName;
            }
        }
    }
}
