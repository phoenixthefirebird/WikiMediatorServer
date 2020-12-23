package cpen221.mp3.wikimediator;

import cpen221.mp3.QueryBaseListener;
import cpen221.mp3.QueryLexer;
import cpen221.mp3.QueryParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.*;
import java.util.stream.Collectors;

public class QueryFactory{

    /**
     * @param string must contain a well-formed poly string
     * @return a List of String representing the result of a structured query
     */
    public static List<String> evaluate(String string) {
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
        System.out.println(tree.toStringTree(parser));

        // Finally, construct a Poly value by walking over the parse tree.
        ParseTreeWalker walker = new ParseTreeWalker();
        QueryListener_QueryCreator listener = new QueryListener_QueryCreator();
        walker.walk(listener, tree);

        if(!listener.valid()){
            return null;
        }

        return listener.evaluate();
    }


    private static class QueryListener_QueryCreator extends QueryBaseListener {
        Stack<Node> makingTree = new Stack<>();
        String item;
        boolean success = true;
        String sorted;
        private Node expression;

        /**
         * Exit a parse tree produced by {@link QueryParser#simple_condition}.
         *
         * @param ctx the parse tree
         */
        @Override
        public void exitSimple_condition(QueryParser.Simple_conditionContext ctx) {
            if(ctx.COND_TYPE()!= null){
                if(ctx.STRING() != null){
                    try{
                        makingTree.push(new OperandNode(ctx.COND_TYPE().getText(),ctx.STRING().getText()));
                    } catch (InvalidQueryException e){
                        success = true;
                    }
                }
            }else{
                success = true;
            }
        }

        /**
         * Exit a parse tree produced by {@link QueryParser#condition}.
         *
         * @param ctx the parse tree
         */
        @Override
        public void exitCondition(QueryParser.ConditionContext ctx) {
            if(ctx.LPAREN() != null){
                if(ctx.RPAREN() != null){
                    if(ctx.CONNECTIVE() != null){
                        if(ctx.condition() != null) {
                            if(ctx.CONNECTIVE().getText().compareTo("and") == 0){
                                AndNode and = new AndNode(makingTree.pop(),makingTree.pop());
                                makingTree.push(and);
                            }else if(ctx.CONNECTIVE().getText().compareTo("or") == 0){
                                OrNode or = new OrNode(makingTree.pop(),makingTree.pop());
                                makingTree.push(or);
                            }
                        }
                    }else if(ctx.condition() != null){
                        expression = makingTree.pop();
                    }
                }
            } else{
                success = true;
            }
        }


        /**
         * Exit a parse tree produced by {@link QueryParser#query}.
         *
         * @param ctx the parse tree
         */
        @Override
        public void exitQuery(QueryParser.QueryContext ctx) {
            if(ctx.GET() != null){
                if(ctx.ITEM() != null){
                    if(ctx.WHERE() != null){
                        if(ctx.condition() != null){
                            if(ctx.SORTED() != null){
                                sorted = ctx.SORTED().getText();
                            }
                            item = ctx.ITEM().getText();
                            expression = makingTree.pop();
                        }
                    }
                }
            }
        }

        public List<String> evaluate(){
            List<String> result;
            try{
                result = expression.evaluate(item);
            }catch (InvalidQueryException e){
                result = null;
            }
            if(sorted != null){
                if(sorted.compareTo("asc") == 0){
                    result = result.stream().sorted().collect(Collectors.toList());
                }else{
                    result = result.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                }
            }
            return result;
        }

        public boolean valid(){
            return success;
        }
    }



}