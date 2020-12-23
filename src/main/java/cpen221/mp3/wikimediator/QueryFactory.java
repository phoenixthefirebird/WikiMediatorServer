package cpen221.mp3.wikimediator;

import cpen221.mp3.QueryBaseListener;
import cpen221.mp3.QueryLexer;
import cpen221.mp3.QueryParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.*;

public class QueryFactory{

    /**
     * @param string must contain a well-formed poly string
     * @return a Poly corresponding to the string in canonical form
     */
    public static QueryListener_QueryCreator parse(String string) {
        // Create a stream of tokens using the lexer.
        CharStream stream = new ANTLRInputStream(string);
        QueryLexer lexer = new QueryLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);

        // Feed the tokens into the parser.
        QueryParser parser = new QueryParser(tokens);
        parser.reportErrorsAsExceptions();

        // Generate the parse tree using the starter rule.
        ParseTree tree = parser.query(); // "root" is the starter rule.

        // debugging option #1: print the tree to the console
        System.err.println(tree.toStringTree(parser));

        // Finally, construct a Poly value by walking over the parse tree.
        ParseTreeWalker walker = new ParseTreeWalker();
        QueryListener_QueryCreator listener = new QueryListener_QueryCreator();
        walker.walk(listener, tree);

        // return the Document value that the listener created
        return listener; //TODO
    }


    private static class QueryListener_QueryCreator extends QueryBaseListener {
        List<SimpleCondition> conditions = new ArrayList<>();

        /**
         * Exit a parse tree produced by {@link QueryParser#simple_condition}.
         *
         * @param ctx the parse tree
         */
        @Override
        public void exitSimple_condition(QueryParser.Simple_conditionContext ctx) {
            //TODO
        }

        /**
         * Exit a parse tree produced by {@link QueryParser#condition}.
         *
         * @param ctx the parse tree
         */
        @Override
        public void exitCondition(QueryParser.ConditionContext ctx) {

        }


        /**
         * Exit a parse tree produced by {@link QueryParser#query}.
         *
         * @param ctx the parse tree
         */
        @Override
        public void exitQuery(QueryParser.QueryContext ctx) {
            // nothing to do
        }
    }

}