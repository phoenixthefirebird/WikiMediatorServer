package cpen221.mp3.wikimediator;

import java.util.LinkedHashMap;

public class loadTracker {
    /**
     * window the time window in secconds*/
    private int window;
    private final int DEFAULT_WINDOW = 30;

    private Integer maxLoad;
    //time in seconds to number of request
    private LinkedHashMap<Long, Integer> windowLog;

    private Integer max;

    public loadTracker(){
        this.window = DEFAULT_WINDOW;
    }

    public loadTracker(Integer max){
        this.window = DEFAULT_WINDOW;
        this.maxLoad = max;
    }

    public void increment(){

    }
}
