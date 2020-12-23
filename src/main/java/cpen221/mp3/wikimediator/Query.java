package cpen221.mp3.wikimediator;

public class Query {

    private final String item;
    private final String condition;
    private final String sorted;

    public Query(String type, String keywords){
        this.item = type;
        this.condition = keywords;
        this.sorted = null;
    }

    public Query(String type, String keywords, String sorted){
        this.item = type;
        this.condition = keywords;
        this.sorted = sorted;
    }

    public String getType() {
        return item;
    }

    public String getKeywords() {
        return condition;
    }

    public String getSorted(){
        return sorted;
    }
}
