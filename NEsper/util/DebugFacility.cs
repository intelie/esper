using System;
using System.Configuration;

using antlr;
using antlr.collections;

namespace net.esper.util
{
	/// <summary>
	///  Utility class for enabling certain debugging using system properties.
	/// </summary>
	
	public class DebugFacility
	{
		private const String PROPERTY_ENABLED_AST_DUMP = "ENABLE_AST_DUMP";
		
		/// <summary> Dump the AST node to system.out.</summary>
		/// <param name="ast">to dump
		/// </param>
		
		public static void DumpAST(AST ast)
		{
			if ( ConfigurationManager.AppSettings[PROPERTY_ENABLED_AST_DUMP] != null)
			{
				(new DumpASTVisitor()).visit(ast);
			}
		}
	}
}
