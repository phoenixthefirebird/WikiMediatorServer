package cpen221.mp3;

import cpen221.mp3.wikimediator.InvalidQueryException;
import cpen221.mp3.wikimediator.QueryFactory;
import cpen221.mp3.wikimediator.WikiMediator;
import static org.junit.Assert.*;

import org.fastily.jwiki.core.Wiki;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task5Test {


    @Test
    public void test1() throws InvalidQueryException{
        WikiMediator wiki = new WikiMediator();
        List<String> result = QueryFactory.evaluate("get page where category is 'Illinois State Senators' asc");
        List<String> expected = new ArrayList<>();
        expected.add("Category:Illinois state senator stubs");
        expected.add("Category:Illinois state senators");
        expected.add("Category:Madison Senators players");
        expected.add("Category:Presidents of the Illinois Senate");
        expected.add("Category:United States senators from Illinois");
        assertEquals(expected, result);
    }

    @Test
    public void test2() throws InvalidQueryException {
        WikiMediator wiki = new WikiMediator();
        Wiki wiki1 = new Wiki.Builder().withDomain("en.wikipedia.org").build();
        String author = wiki1.getPageCreator("Barack Obama");
        List<String> result = QueryFactory.evaluate("get author where (title is 'Barack Obama' or category is 'Illinois State Senators')");
        System.out.println(Arrays.toString(result.toArray()));
    }

    @Test
    public void test3() throws InvalidQueryException {
        WikiMediator wiki = new WikiMediator();
        List<String> result = QueryFactory.evaluate("get category where (author is 'CLCStudent' and (title is 'Barack Obama' or title is 'Naomi Klein'))");
        System.out.println(Arrays.toString(result.toArray()));
    }

    @Test
    public void test4() throws InvalidQueryException {
        WikiMediator wiki = new WikiMediator();
        List<String> result = QueryFactory.evaluate("get page where (author is 'AndrewOne' and author is 'Sylas')");
        System.out.println(Arrays.toString(result.toArray()));
    }

}
