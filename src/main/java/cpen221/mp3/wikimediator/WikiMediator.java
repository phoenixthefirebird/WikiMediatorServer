package cpen221.mp3.wikimediator;

import cpen221.mp3.fsftbuffer.*;
import org.fastily.jwiki.core.NS;
import org.fastily.jwiki.core.Wiki;

import java.security.InvalidKeyException;
import java.util.*;

public class WikiMediator {
    private Wiki wiki = new Wiki.Builder().withDomain("en.wikipedia.org").build();
    private FSFTBuffer buffer;

    /*
        You must implement the methods with the exact signatures
        as provided in the statement for this mini-project.

        You must add method signatures even for the methods that you
        do not plan to implement. You should provide skeleton implementation
        for those methods, and the skeleton implementation could return
        values like null.

     */

    public WikiMediator(int capacity, int timeout){
        buffer = new FSFTBuffer(capacity,timeout);
    }
    /**
     * Given a query, return up to limit page titles that match the query string
     * @param query, the String to match the page titles with
     * @param limit, the upward number of page titles to return
     * @return a list of page titles that match the query string
     * */
    public List<String> search(String query, int limit){
        List<String> searched = wiki.search(query, limit, NS.USER); //not sure
        return searched;
     }

     /**
      * Given a pageTitle, return the text associated with the Wikipedia
      * page that matches pageTitle.
      * @param pageTitle, the page title for the page to search for matching text
      * @return a String representing the text of the wiki page with associated with the title
      * */
   
    public  String getPage(String pageTitle){
        String page;
        try{
            page = ((WKBuffer) buffer.get(pageTitle)).getText();
        }catch (InvalidKeyException e){
            page = wiki.getPageText(pageTitle);
            buffer.put(new WKBuffer(pageTitle));
        }
        return page;
     }

    public List<String> zeitgeist(int limit){
        return null;
     }

    public List<String> trending(int limit){
        return null;
    }

    public int peakLoad30s(){
        return -1;
    }

}
