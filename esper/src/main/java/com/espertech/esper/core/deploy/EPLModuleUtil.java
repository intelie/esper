package com.espertech.esper.core.deploy;

import com.espertech.esper.antlr.NoCaseSensitiveStream;
import com.espertech.esper.client.deploy.ParseException;
import com.espertech.esper.epl.generated.EsperEPL2GrammarLexer;
import com.espertech.esper.epl.generated.EsperEPL2GrammarParser;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.Token;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class EPLModuleUtil
{
    private static Log log = LogFactory.getLog(EPLModuleUtil.class);

    public static ParseNode getModule(String segment, String resourceName) throws ParseException, IOException {
        CharStream input = new NoCaseSensitiveStream(new StringReader(segment));

        EsperEPL2GrammarLexer lex = new EsperEPL2GrammarLexer(input);
        CommonTokenStream tokenStream = new CommonTokenStream(lex);

        List tokens = tokenStream.getTokens();
        int beginIndex = 0;
        boolean isMeta = false;
        boolean isModule = false;
        boolean isUses = false;
        boolean isExpression = false;

        while (beginIndex < tokens.size()) {
            Token t = (Token) tokens.get(beginIndex);
            if ((t.getType() == EsperEPL2GrammarParser.WS) ||
                (t.getType() == EsperEPL2GrammarParser.SL_COMMENT) ||
                (t.getType() == EsperEPL2GrammarParser.ML_COMMENT)) {
                beginIndex++;
                continue;
            }
            String tokenText = t.getText().trim().toLowerCase();
            if (tokenText.equals("module")) {
                isModule = true; isMeta = true;
            }
            else if (tokenText.equals("uses")) {
                isUses = true; isMeta = true;
            }
            else if (tokenText.equals("import")) {
                isMeta = true;
            }
            else {
                isExpression = true;
                break;
            }
            beginIndex++;
            beginIndex++;   // skip space
            break;
        }

        if (isExpression) {
            return new ParseNodeExpression(segment);
        }
        if (!isMeta) {
            return new ParseNodeComment(segment);
        }

        // check meta tag (module, uses, import)
        StringWriter buffer = new StringWriter();
        for (int i = beginIndex; i < tokens.size(); i++)
        {
            Token t = (Token) tokens.get(i);
            if ((t.getType() != EsperEPL2GrammarParser.IDENT) &&
                (t.getType() != EsperEPL2GrammarParser.DOT) && 
                (t.getType() != EsperEPL2GrammarParser.STAR)) {
                throw getMessage(isModule, isUses, resourceName);
            }
            buffer.append(t.getText().trim());
        }

        String result = buffer.toString().trim();
        if (result.length() == 0) {
            throw getMessage(isModule, isUses, resourceName);
        }

        if (isModule) {
            return new ParseNodeModule(segment, result);
        }
        else if (isUses) {
            return new ParseNodeUses(segment, result);
        }
        return new ParseNodeImport(segment, result);
    }

    private static ParseException getMessage(boolean module, boolean uses, String resourceName)
    {
        String message = "Keyword '";
        if (module) {
            message += "module";
        }
        else if (uses) {
            message += "uses";
        }
        else {
            message += "import";
        }
        message += "' must be followed by a name or package name (set of names separated by dots) for resource '" + resourceName + "'";
        return new ParseException(message);
    }

    public static List<String> parse(String module) {

        CharStream input;
        try
        {
            input = new NoCaseSensitiveStream(new StringReader(module));
        }
        catch (IOException ex)
        {
            log.error("Exception reading model expression: " + ex.getMessage(), ex);
            return null;
        }

        EsperEPL2GrammarLexer lex = new EsperEPL2GrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);

        List<String> statements = new ArrayList<String>();
        StringWriter current = new StringWriter();
        for (Object token : tokens.getTokens()) // Call getTokens first before invoking tokens.size! ANTLR problem
        {
            Token t = (Token) token;
            if (t.getType() == EsperEPL2GrammarParser.SEMI) {
                if (current.toString().trim().length() > 0) {
                    statements.add(current.toString().trim());
                }
                current = new StringWriter();
            }
            else {
                current.append(t.getText());
            }
        }

        if (current.toString().trim().length() > 0) {
            statements.add(current.toString().trim());
        }
        return statements;
    }
}
