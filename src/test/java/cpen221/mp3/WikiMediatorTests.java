package cpen221.mp3;

import cpen221.mp3.fsftbuffer.FSFTBuffer;
import cpen221.mp3.fsftbuffer.intBuffers;
import cpen221.mp3.wikimediator.*;
import org.junit.Test;
import java.util.*;

/** sequential tests */
public class WikiMediatorTests {
    //testing for wikiMediator's constructor and its search method
    @Test
    public void test1() {

        WikiMediator wiki = new WikiMediator();
        List<String> searchResult = new ArrayList<>();

        searchResult = wiki.search("wikipedia", 1);
        System.out.println(Arrays.toString(searchResult.toArray()));
    }

    //testing getpage method
//    @Test
//    public void test2() {
//
//        WikiMediator wiki = new WikiMediator();
//        String page = wiki.getPage("ubc");
//        System.out.println(page);
//    }

    //testing zeitgeist method
//    @Test
//    public void test3() {
//
//        WikiMediator wiki = new WikiMediator();
//        String page = wiki.getPage("ubc");
//        System.out.println(page);
//    }
}
