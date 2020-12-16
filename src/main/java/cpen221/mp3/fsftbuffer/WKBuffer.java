package cpen221.mp3.fsftbuffer;

import cpen221.mp3.fsftbuffer.FSFTBuffer;
import org.fastily.jwiki.core.NS;
import org.fastily.jwiki.core.Wiki;

import java.util.*;

/**
 * this class stores the title and text of a wikipage
 */

public class WKBuffer implements Bufferable{
    private String title;
    private String text;
    private Wiki wiki = new Wiki.Builder().withDomain("en.wikipedia.org").build();

    public WKBuffer(String title){
        this.title = title;
        this.text = wiki.getPageText(title);
    }

    @Override
    public String id() {
        return title;
    }
}
