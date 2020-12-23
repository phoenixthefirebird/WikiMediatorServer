package cpen221.mp3;

import cpen221.mp3.wikimediator.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.*;

/** sequential tests */
public class WikiMediatorTests {
    //testing for wikiMediator's constructor and its search method
    @Test
    public void test1() {

        WikiMediator wiki = new WikiMediator();
        List<String> searchResult;
        List<String> expected = new ArrayList<>();
        expected.add("University of British Columbia");
        expected.add("UBC (disambiguation)");

        searchResult = wiki.search("ubc", 1);
        System.out.println(Arrays.toString(searchResult.toArray()));

        searchResult = wiki.search("ubc", 2);
        System.out.println(Arrays.toString(searchResult.toArray()));
        assertEquals(expected, searchResult);
        wiki.closeWiki();
    }

    //search method with parameterized constructor testing with filename
    @Test
    public void test1a() {
        WikiMediator wiki = new WikiMediator("wikimediator.txt");
        List<String> searchResult;
        List<String> expected = new ArrayList<>();
        expected.add("University of British Columbia");
        expected.add("UBC (disambiguation)");

        searchResult = wiki.search("ubc", 1);
        System.out.println(Arrays.toString(searchResult.toArray()));

        searchResult = wiki.search("ubc", 2);
        System.out.println(Arrays.toString(searchResult.toArray()));
        assertEquals(expected, searchResult);
        wiki.closeWiki("wikimediator.txt");
    }

    //testing getpage method with unparameterized constructor
    @Test
    public void test2() {
        WikiMediator wiki = new WikiMediator();
        String page = wiki.getPage("ubc");
        String expected = "#REDIRECT [[University of British Columbia]] {{R from other capitalisation}}";

        System.out.println(page);
        assertEquals(expected, page);
        wiki.closeWiki();
    }

    //getpage method with parameterized constructor testing
    @Test
    public void test2a() {
        WikiMediator wiki = new WikiMediator(10, 30, "wikimediator.txt");
        String page = wiki.getPage("ubc");
        String expected = "#REDIRECT [[University of British Columbia]] {{R from other capitalisation}}";

        System.out.println(page);
        assertEquals(expected, page);
        wiki.closeWiki("wikimediator.txt");
    }

    //testing getpage method with parameterized constructor
    //compare parameterized and unparameterized constructor results
    @Test
    public void test3() {
        WikiMediator wiki = new WikiMediator(10, 30);
        String page = wiki.getPage("ubc");
        System.out.println(page);

        WikiMediator expwiki = new WikiMediator();
        String expected = expwiki.getPage("ubc");
        assertEquals(expected, page);
        wiki.closeWiki();
    }

    //testing zeitgeist method
    @Test
    public void test4() {
        WikiMediator wiki = new WikiMediator();
        System.out.println(wiki.getPage("ubc"));
        System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
        System.out.println(Arrays.toString(wiki.search("ubc", 2).toArray()));

        System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
        System.out.println(Arrays.toString(wiki.search("computer", 2).toArray()));

        System.out.println(wiki.getPage("engineer"));
        List<String> zeitgeist3 = (wiki.zeitgeist(3));
        System.out.println(Arrays.toString(zeitgeist3.toArray()));
        List<String> zeitgeist2 = (wiki.zeitgeist(2));
        System.out.println(Arrays.toString(zeitgeist2.toArray()));

        List<String> expected3 = new ArrayList<>();
        expected3.add("ubc");
        expected3.add("computer");
        expected3.add("engineer");
        assertEquals(expected3, zeitgeist3);

        List<String> expected2 = new ArrayList<>();
        expected2.add("ubc");
        expected2.add("computer");
        assertEquals(expected2, zeitgeist2);

        wiki.closeWiki();
    }

    //testing trending method
    @Test
    public void test5() {
        WikiMediator wiki = new WikiMediator();
        Timer timer = new Timer();
        int flag = 0;

        for(int i = 0; i < 10; i++){
            try {
                Thread.sleep(1000);
                System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));

                if(flag == 0 || flag == 1) {
                    System.out.println(Arrays.toString(wiki.search("engineer", 1).toArray()));
                    flag++;
                }
                else if (flag == 2) {
                    System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
                    flag = 0;
                }
            } catch (Exception InterruptedException){
                return;
            }
        }

        List<String> trending = wiki.trending(3);
        System.out.println(Arrays.toString(trending.toArray()));

        List<String> expected = new ArrayList<>();
        expected.add("ubc");
        expected.add("engineer");
        expected.add("computer");
        assertEquals(expected, trending);
        wiki.closeWiki();
    }

    //testing trending method with smaller limit
    @Test
    public void test5a() {
        WikiMediator wiki = new WikiMediator();
        int flag = 0;

        for(int i = 0; i < 10; i++){
            try {
                Thread.sleep(1000);
                System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));

                if(flag == 0 || flag == 1) {
                    System.out.println(Arrays.toString(wiki.search("engineer", 1).toArray()));
                    flag++;
                }
                else if (flag == 2) {
                    System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
                    flag = 0;
                }
            } catch (Exception InterruptedException){
                return;
            }
        }

        List<String> trending2 = wiki.trending(2);
        System.out.println(Arrays.toString(trending2.toArray()));

        List<String> expected = new ArrayList<>();
        expected.add("ubc");
        expected.add("engineer");
        assertEquals(expected, trending2);
        wiki.closeWiki();
    }

    //testing peakLoad30s method
    @Test
    public void test6() {
        WikiMediator wiki = new WikiMediator();
        int flag = 0;

        for(int i = 0; i < 30; i++){
            try {
                Thread.sleep(1000);
                System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
                if(flag == 0) {
                    System.out.println(Arrays.toString(wiki.search("engineer", 1).toArray()));
                    flag = 1;
                }
                else {
                    System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
                    flag = 0;
                }
            } catch (Exception InterruptedException){
                return;
            }
        }
        int peak = wiki.peakLoad30s();
        System.out.println(peak);
        assertEquals(52, peak);
        wiki.closeWiki();
    }

    //testing peakLoad30s method with single element search - TODO: might be some errors in peakload30s?
    @Test
    public void test6a() {
        WikiMediator wiki = new WikiMediator();
        for(int i = 0; i < 30; i++){
            try {
                System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
                Thread.sleep(1000);
            } catch (Exception InterruptedException){
                return;
            }
        }

        int peak = wiki.peakLoad30s();
        System.out.println(peak);
        assertTrue(peak <= 30);
        wiki.closeWiki();
    }

    //error opening a file testing
    @Test
    public void test9() throws IOException {
        WikiMediator wiki = new WikiMediator("wikimediator1.txt");
    }

    //error opening a file testing with capacity and timeout parameter
    @Test
    public void test10() throws IOException {
        WikiMediator wiki = new WikiMediator(1,1,"wikimediator1.txt");
    }

    //error closing a file testing
    @Test
    public void test11() throws IOException {
        WikiMediator wiki = new WikiMediator("wikimediator1.txt");
        wiki.closeWiki("wikimediator2.txt");
    }

    //multiple thread testing
    @Test
    public void test12() {
        WikiMediator wiki = new WikiMediator();

            try {
                Thread t1 = new Thread();
                int flag = 0;
                for (int i = 0; i < 30; i++) {
                    try {
                        t1.wait(1000);
                        System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
                        if (flag == 0) {
                            System.out.println(Arrays.toString(wiki.search("engineer", 1).toArray()));
                            flag = 1;
                        } else {
                            System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
                            flag = 0;
                        }
                    } catch (Exception InterruptedException) {
                        return;
                    }
                }
            } catch (Exception InterruptedException) {
                return;
            }

        try {
            Thread t2 = new Thread();
            int flag = 0;
            for (int i = 0; i < 30; i++) {
                try {
                    t2.wait(1000);
                    System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
                    if (flag == 0) {
                        System.out.println(Arrays.toString(wiki.search("engineer", 1).toArray()));
                        flag = 1;
                    } else {
                        System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
                        flag = 0;
                    }
                } catch (Exception InterruptedException) {
                    return;
                }
            }
        } catch (Exception InterruptedException) {
            return;
        }

        try {
            Thread t3 = new Thread();
            int flag = 0;
            for (int i = 0; i < 30; i++) {
                try {
                    t3.wait(1000);
                    System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
                    if (flag == 0) {
                        System.out.println(Arrays.toString(wiki.search("engineer", 1).toArray()));
                        flag = 1;
                    } else {
                        System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
                        flag = 0;
                    }
                } catch (Exception InterruptedException) {
                    return;
                }
            }
        } catch (Exception InterruptedException) {
            return;
        }

        int peak = wiki.peakLoad30s();
        System.out.println(peak);
        assertEquals(52, peak);
        wiki.closeWiki();
    }
    //testing with diferent threads running at different intervals
    @Test
    public void test13() {
        WikiMediator wiki = new WikiMediator();

        try {
            Thread t1 = new Thread();
            int flag = 0;
            for (int i = 0; i < 30; i++) {
                try {
                    t1.wait(1000);
                    System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
                    if (flag == 0) {
                        System.out.println(Arrays.toString(wiki.search("engineer", 1).toArray()));
                        flag = 1;
                    } else {
                        System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
                        flag = 0;
                    }
                } catch (Exception InterruptedException) {
                    return;
                }
            }
        } catch (Exception InterruptedException) {
            return;
        }

        try {
            Thread t2 = new Thread();
            int flag = 0;
            for (int i = 0; i < 15; i++) {
                try {
                    t2.wait(1000);
                    System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
                    if (flag == 0) {
                        System.out.println(Arrays.toString(wiki.search("engineer", 1).toArray()));
                        flag = 1;
                    } else {
                        System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
                        flag = 0;
                    }
                } catch (Exception InterruptedException) {
                    return;
                }
            }
        } catch (Exception InterruptedException) {
            return;
        }

        try {
            Thread t3 = new Thread();
            int flag = 0;
            for (int i = 0; i < 20; i++) {
                try {
                    t3.wait(1000);
                    System.out.println(Arrays.toString(wiki.search("dog", 1).toArray()));
                    if (flag == 0) {
                        System.out.println(Arrays.toString(wiki.search("cat", 1).toArray()));
                        flag = 1;
                    } else {
                        System.out.println(Arrays.toString(wiki.search("animal", 1).toArray()));
                        flag = 0;
                    }
                } catch (Exception InterruptedException) {
                    return;
                }
            }
        } catch (Exception InterruptedException) {
            return;
        }

        int peak = wiki.peakLoad30s();
        System.out.println(peak);
        assertEquals(52, peak);
        wiki.closeWiki();
    }
}

