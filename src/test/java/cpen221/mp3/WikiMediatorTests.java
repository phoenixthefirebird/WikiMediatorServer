package cpen221.mp3;

import cpen221.mp3.wikimediator.*;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

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
        Timer timer = new Timer();
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

        System.out.println(wiki.peakLoad30s());
        wiki.closeWiki();
    }

    //wikimediator parameterized constructor testing
    @Test
    public void test7() {
        WikiMediator wiki = new WikiMediator(10, 30, "wikimediator.txt");
        wiki.closeWiki("wikimediator.txt");
    }

    //wikimediator parameterized constructor testing with filename
    @Test
    public void test8() {
        WikiMediator wiki = new WikiMediator("wikimediator.txt");
        wiki.closeWiki("wikimediator.txt");
    }

    //error opening a file testing
    @Test
    public void test9() throws IOException {
        WikiMediator wiki = new WikiMediator("wikimediator1.txt");
    }

    //error opening a file testing
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

//    @RunWith(ConcurrentJunitRunner.class)
//    @Concurrent(threads = 6)
//    public final class ATest {
//        WikiMediator wiki = new WikiMediator(1,1,"wikimediator.txt");
//
//        @Test public void test0() throws Throwable {
//            for(int i = 0; i < 20; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        @Test public void test1() throws Throwable {
//            for(int i = 0; i < 10; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        @Test public void test2() throws Throwable {
//            for(int i = 0; i < 9; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("engineering", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        @Test public void test3() throws Throwable {
//            for(int i = 0; i < 8; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("vancouver", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        @Test public void test4() throws Throwable {
//            for(int i = 0; i < 7; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("canada", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        @Test public void test5() throws Throwable {
//            for(int i = 0; i < 6; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("christmas", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        @Test public void test6() throws Throwable {
//            for(int i = 0; i < 5; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("tree", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        @Test public void test7() throws Throwable {
//            for(int i = 0; i < 4; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("snow", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        @Test public void test8() throws Throwable {
//            for(int i = 0; i < 3; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("frozen", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        @Test public void test9() throws Throwable {
//            for(int i = 0; i < 2; i++){
//                try {
//                    Thread.sleep(500);
//                    System.out.println(Arrays.toString(wiki.search("winter", 1).toArray()));
//                } catch (Exception InterruptedException) {
//                    return;
//                }
//            }
//        }
//        void printAndWait() throws Throwable {
//            int w = new Random().nextInt(1000);
//            System.out.println(String.format("[%s] %s %s %s", Thread.currentThread().getName(), getClass().getName(), new Throwable().getStackTrace()[1].getMethodName(), w));
//            Thread.sleep(w);
//        }
//    }
}
