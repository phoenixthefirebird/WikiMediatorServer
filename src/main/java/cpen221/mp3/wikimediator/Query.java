package cpen221.mp3.wikimediator;

public class Query {

    private final String type;
    private final String keywords;

    public Query(String type, String keywords){
        this.type = type;
        this.keywords = keywords;
    }

    public String getType() {
        return type;
    }

    public String getKeywords() {
        return keywords;
    }
}
