package cpen221.mp3.wikimediator;

import org.fastily.jwiki.core.NS;
import org.fastily.jwiki.core.Wiki;
import org.fastily.jwiki.dwrap.Contrib;
import org.fastily.jwiki.dwrap.DataEntry;

import java.util.List;
import java.util.stream.Collectors;

public class OperandNode extends Node {

    static final int numChildren = 0;
    private final Wiki wiki;
    private final NS condition_type;
    private final String string;

    /**
     * Create a new OperandNode to hold and evaluate a simple condition in structured query
     * @param condition_type the type of condition to restrict the output of
     * @param string what the condition actually is
     * @throws InvalidQueryException with bad input
     */

    public OperandNode(String condition_type, String string) throws InvalidQueryException {
        super( );
        this.wiki = new Wiki.Builder().withDomain("en.wikipedia.org").build();
        if(condition_type.compareTo("title is")==0){
            this.condition_type = NS.MAIN;
        } else if(condition_type.compareTo("author is") == 0){
            this.condition_type = NS.USER;
        } else if(condition_type.compareTo("category is") == 0){
            this.condition_type = NS.CATEGORY;
        }else{
            throw new InvalidQueryException();
        }
        this.string = string;
    }

    /**
     * Evaluate the simple condition with JWiki methods
     * @return List of Strings representing the results
     * @throws InvalidQueryException with bad input
     */
    public List<String> evaluate(String item) throws InvalidQueryException {
        if(item.compareTo("page") == 0){
            return wiki.search(string,-1, condition_type);
        } else if (item.contains("author")){
            List<Contrib> result = wiki.getContribs(string,-1,false,false, condition_type);
            return result.stream().map(DataEntry::toString).collect(Collectors.toList());
        } else if(item.contains("category")){
            return wiki.getCategoryMembers(string,condition_type);
        }
        throw new InvalidQueryException();
    }

    /**
     * Return a character that represents the operation this node represents.
     *
     * @return ' ' for doesn't have an operation
     */
    public String getOpName( ) {
        return " ";
    }

}
