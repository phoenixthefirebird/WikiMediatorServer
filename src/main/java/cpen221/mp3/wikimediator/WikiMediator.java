package cpen221.mp3.wikimediator;

import cpen221.mp3.fsftbuffer.FSFTBuffer;
import org.fastily.jwiki.core.NS;
import org.fastily.jwiki.core.Wiki;

import java.util.*;

public class WikiMediator {
    Wiki wiki = new Wiki.Builder().withDomain("en.wikipedia.org").build();
//    FSFTBuffer buffer = new FSFTBuffer(int capacity, int timeout)<>;

    /* TODO: Implement this datatype

        You must implement the methods with the exact signatures
        as provided in the statement for this mini-project.

        You must add method signatures even for the methods that you
        do not plan to implement. You should provide skeleton implementation
        for those methods, and the skeleton implementation could return
        values like null.

     */
    public List<String> search(String query, int limit){
        List<String> searched = wiki.search(query, limit, NS.USER);
        return searched;
     }
   
    public  String getPage(String pageTitle){
        //need to use FSFTbuffer as LRU cache - get and put
        String page = wiki.getPageText(pageTitle);
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
