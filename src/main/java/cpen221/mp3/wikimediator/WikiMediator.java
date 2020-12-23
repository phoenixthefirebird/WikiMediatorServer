package cpen221.mp3.wikimediator;

import cpen221.mp3.fsftbuffer.*;
import kotlin.Pair;
import org.fastily.jwiki.core.NS;
import org.fastily.jwiki.core.Wiki;

import java.io.File;
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
     * Wiki mediator service cache wikipedia pages to minimize network accesses
     * Wiki mediator service collect statistical information about requests
     * queryLog tracks queries used in methods
     * functionLog tracks Strings used in search and getPage requests with number of time used
     * maxRequest tracks number of times specific String is used
     * <p>
     * RI:
     * queryLog is not null
     * Pair in queryLog is Long, String pair
     * functionLog is not null
     * window //TODO: describe representation of window
     * Function log is not null
     */
    private final Wiki wiki = new Wiki.Builder().withDomain("en.wikipedia.org").build();
    private FSFTBuffer<WKBuffer> pageBuffer;
    private static ConcurrentHashMap<String, Integer> totalFrequency;
    private static List<Pair<Long,String>> queryLog;
    private static loadTracker functionLog;
    private final int WINDOW = 30000;


    /*
        You must implement the methods with the exact signatures
        as provided in the statement for this mini-project.

        You must add method signatures even for the methods that you
        do not plan to implement. You should provide skeleton implementation
        for those methods, and the skeleton implementation could return
        values like null.

     */

    public WikiMediator() {
        pageBuffer = new FSFTBuffer<>();
        if(queryLog == null){
            queryLog = new ArrayList<>();
        }
        if(totalFrequency == null){
            totalFrequency = new ConcurrentHashMap<>();
        }
        if(functionLog == null){
            File file = new File(".\\local\\backlog.txt");
            Scanner sc;
            try {
                sc = new Scanner(file);
            }catch (IOException e){
                System.err.println("cannot open file");
                sc = null;
            }
            if(sc != null){
                scanToIndex(sc);
            }
        }
    }

    public WikiMediator(int capacity, int timeout) {
        pageBuffer = new FSFTBuffer<>(capacity, timeout);
        if(queryLog == null){
            queryLog = new ArrayList<>();
        }
        if(totalFrequency == null){
            totalFrequency = new ConcurrentHashMap<>();
        }
        if(functionLog == null){
            File file = new File(".\\local\\backlog.txt");
            Scanner sc;
            try {
                sc = new Scanner(file);
            }catch (IOException e){
                System.err.println("cannot open file");
                sc = null;
            }
            if(sc != null){
                scanToIndex(sc);
            }
        }
    }

    public WikiMediator(int capacity, int timeout, String filename) throws IllegalArgumentException {
        pageBuffer = new FSFTBuffer<>(capacity, timeout);
        if(queryLog == null){
            queryLog = new ArrayList<>();
        }
        if(totalFrequency == null){
            totalFrequency = new ConcurrentHashMap<>();
        }
        if(functionLog == null){
            File file = new File(".\\local\\" +filename);
            Scanner sc;
            try {
                sc = new Scanner(file);
            }catch (IOException e){
                System.err.println("cannot open file");
                sc = null;
            }
            if(sc != null){
                scanToIndex(sc);
            }
        }

    }

    public WikiMediator(String filename) {
        pageBuffer = new FSFTBuffer<>();
        if(queryLog == null){
            queryLog = new ArrayList<>();
        }
        if(totalFrequency == null){
            totalFrequency = new ConcurrentHashMap<>();
        }
        if(functionLog == null){
            File file = new File(".\\local\\" +filename);
            Scanner sc;
            try {
                sc = new Scanner(file);
            }catch (IOException e){
                System.err.println("cannot open file");
                sc = null;
            }
            if(sc != null){
                scanToIndex(sc);
            }
        }
    }

    private void scanToIndex(Scanner sc){
        int max = 0;
        ArrayList<Integer> seed = new ArrayList<>();
        int stage = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if(line.compareTo("") == 0){
                break;
            }
            if(line.compareTo("totalFrequency") == 0 && stage != 0){
                throw new IllegalArgumentException();
            }else if (line.compareTo("totalFrequency") == 0){
                stage = 1;
                continue;
            }
            if(line.compareTo("queryLog") == 0 && stage != 1){
                throw new IllegalArgumentException();
            } else if(line.compareTo("queryLog") == 0){
                stage = 2;
                continue;
            }
            if(line.compareTo("functionLog") == 0 && stage != 2){
                throw new IllegalArgumentException();
            } else if(line.compareTo("functionLog") == 0){
                stage = 3;
                continue;
            }
            if(line.compareTo("startlog") == 0 && stage != 3){
                throw new IllegalArgumentException();
            }else if(line.compareTo("startlog") == 0){
                stage = 4;
                continue;
            }

            if(stage == 1){
                String[] arrStr = line.split(": ", 3);
                totalFrequency.put(arrStr[0],Integer.parseInt(arrStr[1]));
            }else if (stage == 2){
                String[] arrStr = line.split(": ", 3);
                queryLog.add(new Pair<>(Long.parseLong(arrStr[0]),arrStr[1]));
            }else if (stage == 3){
                String[] arrStr = line.split(": ", 3);
                max = Integer.parseInt(arrStr[1]);
            }else if (stage == 4){
                seed.add(Integer.parseInt(line));
            }
        }
        functionLog = new loadTracker(max,seed);
    }
    /**
     * Given a query, return up to limit page titles that match the query string
     * Also add timestamp data to the data log
     *
     * @param query the String to match the page titles with
     * @param limit the upward number of page titles to return
     * @return a list of page titles that match the query string
     */
    public List<String> search(String query, int limit) {
        trackWorkload();
        trackQuery(query);
        return wiki.search(query, limit, NS.MAIN);
    }

    /**
     * Given a pageTitle, return the text associated with the Wikipedia
     * page that matches pageTitle.
     *
     * @param pageTitle the page title for the page to search for matching text
     * @return a String representing the text of the wiki page with associated with the title
     */

    public String getPage(String pageTitle) {
        trackQuery(pageTitle);
        trackWorkload();
        String page;
        try {
            page = pageBuffer.get(pageTitle).getText();
        } catch (InvalidKeyException e) {
            page = wiki.getPageText(pageTitle);
            pageBuffer.put(new WKBuffer(pageTitle));
        }
        return page;
    }

    private void trackQuery(String query) {
        synchronized (queryLog){
            queryLog.add(new Pair<>(System.currentTimeMillis(), query));
        }
        synchronized (totalFrequency){
            if (totalFrequency.containsKey(query))
                totalFrequency.put(query, totalFrequency.get(query) + 1);
            else
                totalFrequency.put(query, 1);
        }
    }

    private void trackWorkload(){
        synchronized (functionLog){
            functionLog.increment();
        }
    }

    /**
     * Return the most common Strings used in search and getPage requests,
     * with items being sorted in non-increasing count order.
     * When many requests have been made, return only limit number of items.
     *
     * @param limit the maximum number of results in the return list
     * @return a list of limit number of the most common Strings sorted in non-increasing count order
     */
    public List<String> zeitgeist(int limit) {
        trackWorkload();
        synchronized (totalFrequency){
            return totalFrequency.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .map(Map.Entry::getKey)
                    .limit(limit)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Similar to zeitgeist(), but returns the most frequent requests made in the last 30 seconds.
     *
     * @param limit the number of results to return
     * @return a list of limit number of String sorted in non-increasing frequency of use
     * over the lst 30 seconds
     */
    public List<String> trending(int limit) {
        trackWorkload();
        synchronized (queryLog){
            queryLog = queryLog.stream()
                    .filter(x -> (x.getFirst() + WINDOW) >= System.currentTimeMillis())
                    .collect(Collectors.toList());
            Map<String, Long> map = queryLog.stream()
                    .collect(Collectors.groupingBy(Pair<Long, String>::getSecond,
                            (Collectors.counting())));
            return map.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .map(Map.Entry::getKey)
                    .limit(limit)
                    .collect(Collectors.toList());
        }
    }

    /**
     * @return the maximum number of requests seen in any 30-second window
     * The request count includes all requests made using the public API of WikiMediator
     */

    synchronized public int peakLoad30s() {
        trackWorkload();
        return functionLog.getMaxLoad();
    }

    /**
     * this function supports structural query over wiki
     * @param query the query input for the search
     * @return a list of relevant Strings for the search
     * throws InvalidQueryException for queries that are invalid (cannot be parsed).
     * TODO
     */
    synchronized public List<String> executeQuery(String query) throws InvalidQueryException {
        return null;
    }

    /**
     * TODO
     * @param query
     * @return
     */
    synchronized private boolean validateQuery(String query){
        return false;
    }

    /**
     * this function saves the states of the WikiMediator to an existing file,
     * overwrites the existing content if the file is not empty
     *
     * @param filename the complete .txt filename (with postfix) of the
     *                 file in the local directory to which the state of WikiMediator
     *                 will be saved to
     * @return true if the state is saved successfully, false otherwise
     */
    synchronized public boolean closeWiki(String filename) {
        try {
            FileWriter writer = new FileWriter(".\\local\\" + filename);
            writer.write("totalFrequency" + System.lineSeparator());
            for (String query : totalFrequency.keySet()) {
                writer.write(query + ": " + totalFrequency.get(query) + System.lineSeparator());
            }
            writer.write("queryLog" + System.lineSeparator());
            for(Pair<Long,String> i : queryLog){
                writer.write(i.getFirst() + ": " + i.getSecond() + System.lineSeparator());
            }
            writer.write("functionLog" + System.lineSeparator());
            functionLog.close(writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("There was error saving the state of the WikiMediator!");
            return false;
        }
        return true;
    }

    /**
     * this function saves the states of the WikiMediator to the default
     * backlog.txt in local directory,
     * overwrites the existing content if the file is not empty
     *
     * @return true if the state is saved successfully, false otherwise
     */
    synchronized public boolean closeWiki() {
        try {
            FileWriter writer = new FileWriter(".\\local\\backlog.txt");
            writer.write("totalFrequency" + System.lineSeparator());
            for (String query : totalFrequency.keySet()) {
                writer.write(query + ": " + totalFrequency.get(query) + System.lineSeparator());
            }
            writer.write("queryLog" + System.lineSeparator());
            for(Pair<Long,String> i : queryLog){
                writer.write(i.getFirst() + ": " + i.getSecond() + System.lineSeparator());
            }
            writer.write("functionLog" + System.lineSeparator());
            functionLog.close(writer);
            writer.close();
        } catch (IOException e) {
            System.err.println("There was error saving the state of the WikiMediator!");
            return false;
        }
        return true;
    }
}
