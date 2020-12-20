package cpen221.mp3.fsftbuffer;

/**
 * this immutable Bufferable class stores the query Strings used in WikiMediator
 */
public class QBuffer implements Bufferable{
    //TODO: check over the documentation and implementation
    /**
     * AF:
     * stores a String from query request
     *
     * RI:
     * query is not null
     * query never changes (immutable)
     * the id is unique from all other instances of QBuffer
     */
    private final String query;

    public QBuffer(String search){
        this.query = search;
    }

    /**
     * get the id of this QBuffer object
     * @return String representing the id of this object
     * */
    @Override
    public String id() {
        return query;
    }


    /**
     * get the stored query in String
     * @return a String representing the query
     * */
    public String getQuery() {
        return query;
    }
}
