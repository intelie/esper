using System;
using System.Collections.Generic;

using antlr.collections;

using net.esper.compat;
using net.esper.eql.generated;
using net.esper.view;

namespace net.esper.eql.parse
{
    /// <summary> Builds a view specification from view AST nodes.</summary>
    public class ASTViewSpecHelper : EqlTokenTypes
    {
        /// <summary> Build a view specification from the AST node supplied.</summary>
        /// <param name="node">- parse node
        /// </param>
        /// <returns> view spec
        /// </returns>
        /// <throws>  ASTWalkException is thrown to indicate an error in node parsing </throws>
        public static ViewSpec buildSpec(AST node)
        {
            String objectNamespace = node.getFirstChild().getText();
            String objectName = node.getFirstChild().getNextSibling().getText();

            IList<Object> objectParams = new List<Object>();

            AST child = node.getFirstChild().getNextSibling().getNextSibling();
            while (child != null)
            {
                Object _object = ASTParameterHelper.makeParameter(child);
                objectParams.Add(_object);
                child = child.getNextSibling();
            }

            ViewSpec viewSpec = new ViewSpec(objectNamespace, objectName, objectParams);
            return viewSpec;
        }
    }
}