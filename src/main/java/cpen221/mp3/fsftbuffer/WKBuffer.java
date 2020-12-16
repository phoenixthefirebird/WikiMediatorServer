package cpen221.mp3.fsftbuffer;

import cpen221.mp3.fsftbuffer.FSFTBuffer;
import org.fastily.jwiki.core.NS;
import org.fastily.jwiki.core.Wiki;

import java.util.*;

/**
 * this class stores the title and text of a wikipage
 */

public class WKBuffer implements Bufferable{
    //TODO: check over the documentation and implementation
    /**
     * AF:
     * represents a wiki page with the title of the page and the text of the page
     *
     * RI:
     * title is not null
     * text is not null
     * wiki is not null
     * title is valid
     * title and text corresponds
     */

    private final String title;
    private final String text;
    private Wiki wiki = new Wiki.Builder().withDomain("en.wikipedia.org").build();

    public WKBuffer(String title){
        this.title = title;
        this.text = wiki.getPageText(title);
    }

    /**
     * get the id and page title of the wiki page in this bufferable
     * @return String representing the page title and id of the wiki page
     * */
    @Override
    public String id() {
        return title;
    }

    /**
     * get the text of the wiki page in this bufferable
     * @return the text of the wiki page in String
     * */
    public String getText() {
        return text;
    }
}
