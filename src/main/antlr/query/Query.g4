grammar Query;

// This puts "package poly;" for all generated Java files. .
@header {
package query;
}

// This adds code to the generated lexer and parser.
// DO NOT CHANGE THESE LINES
@members {
    // This method makes the lexer or parser stop running if it encounters
    // invalid input and throw a RuntimeException.
    public void reportErrorsAsExceptions() {
        //removeErrorListeners();

        addErrorListener(new ExceptionThrowingErrorListener());
    }

    private static class ExceptionThrowingErrorListener
                                              extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            throw new RuntimeException(msg);
        }
    }
}


/*
 * These are the lexical rules. They define the tokens used by the lexer.
 *  Antlr requires tokens to be CAPITALIZED, like START_ITALIC, and TEXT.
 */
GET : 'get';
ITEM : 'page'|'author'|'category';
WHERE : 'where';
SIMPLE_CONDITION : 'title is' + STRING | 'author is' + STRING | 'category is' + STRING;
STRING:  '\'' ( ~'\'' | '\'\'' )* '\'';
LPAREN : '(';
RPAREN : ')';
SORTED : 'asc'|'desc';
AND : 'and';
OR: 'or';
WHITESPACE : [ \t\r\n]+ -> skip ;


/*
 * These are the parser rules. They define the structures used by the parser.
 * Antlr requires grammar nonterminals to be lowercase,
 */
query : GET ITEM WHERE condition SORTED? EOF ;
condition : LPAREN condition AND condition RPAREN | LPAREN condition OR condition RPAREN | SIMPLE_CONDITION;
