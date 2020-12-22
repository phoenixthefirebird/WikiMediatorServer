package cpen221.mp3;

import cpen221.mp3.wikimediator.*;
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
        List<String> searchResult = new ArrayList<>();

        searchResult = wiki.search("ubc", 1);
        System.out.println(Arrays.toString(searchResult.toArray()));
        searchResult = wiki.search("ubc", 2);
        System.out.println(Arrays.toString(searchResult.toArray()));
        wiki.closeWiki();
    }

    //testing getpage method
    @Test
    public void test2() {
        WikiMediator wiki = new WikiMediator();
        String page = wiki.getPage("ubc");
        System.out.println(page);
        wiki.closeWiki();
    }

    @Test
    public void test3() {
        WikiMediator wiki = new WikiMediator(10, 30);
        String page = wiki.getPage("ubc");
        System.out.println(page);
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
        System.out.println(Arrays.toString((wiki.zeitgeist(3)).toArray()));
        System.out.println(Arrays.toString((wiki.zeitgeist(2)).toArray()));
        wiki.closeWiki();
    }

    //testing trending method
    @Test
    public void test5() {
        WikiMediator wiki = new WikiMediator();
        Timer timer = new Timer();

        for(int i = 0; i < 30; i++){
            try {
                Thread.sleep(1000);
                System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
            } catch (Exception InterruptedException){
                return;
            }
        }

        System.out.println(Arrays.toString((wiki.trending(2)).toArray()));
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

    @RunWith(ConcurrentJunitRunner.class)
    @Concurrent(threads = 6)
    public final class ATest {
        WikiMediator wiki = new WikiMediator(1,1,"wikimediator.txt");

        @Test public void test0() throws Throwable {
            for(int i = 0; i < 20; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("ubc", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }
        @Test public void test1() throws Throwable {
            for(int i = 0; i < 10; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("computer", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }
        @Test public void test2() throws Throwable {
            for(int i = 0; i < 9; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("engineering", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }
        @Test public void test3() throws Throwable {
            for(int i = 0; i < 8; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("vancouver", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }
        @Test public void test4() throws Throwable {
            for(int i = 0; i < 7; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("canada", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }
        @Test public void test5() throws Throwable {
            for(int i = 0; i < 6; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("christmas", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }
        @Test public void test6() throws Throwable {
            for(int i = 0; i < 5; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("tree", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }
        @Test public void test7() throws Throwable {
            for(int i = 0; i < 4; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("snow", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }
        @Test public void test8() throws Throwable {
            for(int i = 0; i < 3; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("frozen", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }
        @Test public void test9() throws Throwable {
            for(int i = 0; i < 2; i++){
                try {
                    Thread.sleep(500);
                    System.out.println(Arrays.toString(wiki.search("winter", 1).toArray()));
                } catch (Exception InterruptedException){
                    return;
                }
            }
        }

//        void printAndWait() throws Throwable {
//            int w = new Random().nextInt(1000);
//            System.out.println(String.format("[%s] %s %s %s", Thread.currentThread().getName(), getClass().getName(), new Throwable().getStackTrace()[1].getMethodName(), w));
//            Thread.sleep(w);
//        }
    }
}
