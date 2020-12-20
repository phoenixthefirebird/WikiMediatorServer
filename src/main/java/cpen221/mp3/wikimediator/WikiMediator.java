package cpen221.mp3.wikimediator;

import cpen221.mp3.fsftbuffer.*;
import org.fastily.jwiki.core.NS;
import org.fastily.jwiki.core.Wiki;
import java.io.FileWriter;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * this class enables basic page requests on Wikipedia
 */

public class WikiMediator {
    /**
     * AF:
     * TODO: write this
     * RI:
     *  TODO: write this
     */
    private Wiki wiki = new Wiki.Builder().withDomain("en.wikipedia.org").build();
    private FSFTBuffer pageBuffer;
    private ConcurrentHashMap<String, Integer> totalFrequency;


    /*
        You must implement the methods with the exact signatures
        as provided in the statement for this mini-project.

        You must add method signatures even for the methods that you
        do not plan to implement. You should provide skeleton implementation
        for those methods, and the skeleton implementation could return
        values like null.

     */

    public WikiMediator(){
        pageBuffer = new FSFTBuffer();
    }

    public WikiMediator(int capacity, int timeout){
        pageBuffer = new FSFTBuffer(capacity,timeout);
    }

    public WikiMediator(int capacity, int timeout, String filename){
        pageBuffer = new FSFTBuffer(capacity,timeout);
    }

    public WikiMediator(String filename){
        pageBuffer = new FSFTBuffer();
    }
    /**
     * Given a query, return up to limit page titles that match the query string
     * @param query, the String to match the page titles with
     * @param limit, the upward number of page titles to return
     * @return a list of page titles that match the query string
     * */
    public List<String> search(String query, int limit){
        List<String> searched = wiki.search(query, limit, NS.MAIN);
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
            page = ((WKBuffer) pageBuffer.get(pageTitle)).getText();
        }catch (InvalidKeyException e){
            page = wiki.getPageText(pageTitle);
            pageBuffer.put(new WKBuffer(pageTitle));
        }
        return page;
     }

     /**
      * Return the most common Strings used in search and getPage requests,
      * with items being sorted in non-decreasing count order.
      * When many requests have been made, return only limit number of items.
      * @param limit the maximum number of results in the return list
      * @return a list of most common Strings sorted in non-decreasing count order
      */
    public List<String> zeitgeist(int limit){
        return null;
     }

    public List<String> trending(int limit){
        return null;
    }

    public int peakLoad30s(){
        return -1;
    }

    public List<String> executeQuery(String query){
        return null;
    }

    /**
     * this function saves the states of the WikiMediator to an existing file,
     * overwrites the existing content if the file is not empty
     * @param filename the complete .txt filename (with postfix) of the
     *        file in the local directory to which the state of WikiMediator
     *        will be saved to
     * @return true if the state is saved successfully, false otherwise
     * */
    public boolean closeWiki(String filename){
        try {
            FileWriter writer = new FileWriter(".\\local\\" + filename);
            for (String query : totalFrequency.keySet()) {
                writer.write(query + ": " + totalFrequency.get(query) + System.lineSeparator());
            }
            writer.close();
        }catch (IOException e){
            System.err.println("There was error saving the state of the WikiMediator!");
            return false;
        }
        return true;
    }
}
