package cpen221.mp3;

import cpen221.mp3.wikimediator.InvalidQueryException;
import cpen221.mp3.wikimediator.QueryFactory;
import cpen221.mp3.wikimediator.WikiMediator;
import org.junit.Test;

import java.util.List;

public class Task5Test {


    @Test
    public void test1() throws InvalidQueryException {
        WikiMediator wiki = new WikiMediator();
        List<String> result = QueryFactory.evaluate("get page where category is 'Illinois State Senators' asc");
    }

    @Test
    public void test2() throws InvalidQueryException {
        WikiMediator wiki = new WikiMediator();
        List<String> result = QueryFactory.evaluate("get author where (title is 'Barack Obama' or category is 'Illinois State Senators')");
    }
}
