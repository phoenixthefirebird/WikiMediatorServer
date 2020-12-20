package cpen221.mp3.wikimediator;

import cpen221.mp3.fsftbuffer.*;
import kotlin.Pair;
import org.fastily.jwiki.core.NS;
import org.fastily.jwiki.core.Wiki;
import java.io.FileWriter;


import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
    private ArrayList<Pair> queryLog;
    private ConcurrentHashMap<Long, Integer> functionLog;
    private final int WINDOW = 30000;
    private Pair maxRequest;


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
        queryLog = new ArrayList<>();
        functionLog = new ConcurrentHashMap<>();
        maxRequest = new Pair(System.currentTimeMillis()/1000,0);
    }

    public WikiMediator(int capacity, int timeout){

        pageBuffer = new FSFTBuffer(capacity,timeout);
        queryLog = new ArrayList<>();
        functionLog = new ConcurrentHashMap<>();
        maxRequest = new Pair(System.currentTimeMillis()/1000,0);
    }

    public WikiMediator(int capacity, int timeout, String filename){
        //TODO: work on a scanner to scan a file for data
        pageBuffer = new FSFTBuffer(capacity,timeout);
        queryLog = new ArrayList<>();
        functionLog = new ConcurrentHashMap<>();
        maxRequest = new Pair(System.currentTimeMillis()/1000,0);
    }

    public WikiMediator(String filename){
        //TODO: work on a scanner to scan a file for data
        pageBuffer = new FSFTBuffer();
        queryLog = new ArrayList<>();
        functionLog = new ConcurrentHashMap<>();
        maxRequest = new Pair(System.currentTimeMillis()/1000,0);
    }
    /**
     * Given a query, return up to limit page titles that match the query string
     * Also add timestamp data to the data log
     * @param query, the String to match the page titles with
     * @param limit, the upward number of page titles to return
     * @return a list of page titles that match the query string
     * */
    synchronized public List<String> search(String query, int limit){

        queryLog.add(new Pair(System.currentTimeMillis(), query));
        if(!functionLog.keySet().contains(System.currentTimeMillis()/1000)){
            functionLog.put(System.currentTimeMillis()/1000, 1);
        }
        else {
            functionLog.put(System.currentTimeMillis()/1000, functionLog.get(System.currentTimeMillis()/1000) + 1);
        }

        if(totalFrequency.containsKey(query))
            totalFrequency.put(query, totalFrequency.get(query) + 1);
        else
            totalFrequency.put(query, 1);

        List<String> searched = wiki.search(query, limit, NS.MAIN);
        return searched;
     }

     /**
      * Given a pageTitle, return the text associated with the Wikipedia
      * page that matches pageTitle.
      * @param pageTitle, the page title for the page to search for matching text
      * @return a String representing the text of the wiki page with associated with the title
      * */

     synchronized public String getPage(String pageTitle){

        queryLog.add(new Pair(System.currentTimeMillis(), pageTitle));
         if(!functionLog.keySet().contains(System.currentTimeMillis()/1000)){
             functionLog.put(System.currentTimeMillis()/1000, 1);
         }
         else {
             functionLog.put(System.currentTimeMillis()/1000,
                     functionLog.get(System.currentTimeMillis()/1000) + 1);
         }

        if(totalFrequency.containsKey(pageTitle))
            totalFrequency.put(pageTitle, totalFrequency.get(pageTitle) + 1);
        else
            totalFrequency.put(pageTitle, 1);

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
      * with items being sorted in non-increasing count order.
      * When many requests have been made, return only limit number of items.
      * @param limit the maximum number of results in the return list
      * @return a list of limit number of the most common Strings sorted in non-increasing count order
      */
     synchronized public List<String> zeitgeist(int limit){
         if(!functionLog.keySet().contains(System.currentTimeMillis()/1000)){
             functionLog.put(System.currentTimeMillis()/1000, 1);
         }
         else {
             functionLog.put(System.currentTimeMillis()/1000, functionLog.get(System.currentTimeMillis()/1000) + 1);
         }
        List<String> commonStrings = totalFrequency.entrySet()
                .parallelStream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> e.getKey())
                .limit(limit)
                .collect(Collectors.toList());
        return commonStrings;
     }

    /**
     * Similar to zeitgeist(), but returns the most frequent requests made in the last 30 seconds.
     * @param limit the number of results to return
     * @return a list of limit number of String sorted in non-increasing frequency of use
     * over the lst 30 seconds
     */
    synchronized public List<String> trending(int limit){
        if(!functionLog.keySet().contains(System.currentTimeMillis()/1000)){
            functionLog.put(System.currentTimeMillis()/1000, 1);
        }
        else {
            functionLog.put(System.currentTimeMillis()/1000, functionLog.get(System.currentTimeMillis()/1000) + 1);
        }
        Map <String, Long> map = queryLog.stream()
                .filter(x -> (((Integer) x.getFirst()) + WINDOW) >= System.currentTimeMillis())
                .collect(Collectors.groupingBy(e -> (String) e.getSecond(),
                        (Collectors.counting())));
        List<String> result = map.entrySet()
                .parallelStream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> e.getKey())
                .limit(limit)
                .collect(Collectors.toList());
        return result;
    }

    /**
     * @return the maximum number of requests seen in any 30-second window
     * The request count includes all requests made using the public API of WikiMediator
     */

    synchronized public int peakLoad30s(){
        if(!functionLog.keySet().contains(System.currentTimeMillis()/1000)){
            functionLog.put(System.currentTimeMillis()/1000, 1);
        }
        else {
            functionLog.put(System.currentTimeMillis()/1000,
                    functionLog.get(System.currentTimeMillis()/1000) + 1);
        }

        //TODO: complete functionality, log the past maximum and its window start time in maxRequest
        return -1;
    }

    /**
     * this function saves the states of the WikiMediator to an existing file,
     * overwrites the existing content if the file is not empty
     * @param filename the complete .txt filename (with postfix) of the
     *        file in the local directory to which the state of WikiMediator
     *        will be saved to
     * @return true if the state is saved successfully, false otherwise
     * */
    synchronized public boolean closeWiki(String filename){
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
