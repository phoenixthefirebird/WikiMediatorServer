package cpen221.mp3.wikimediator;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class loadTracker {
    /**
     * AF:
     * an object holding the peak load within any 30s window over the lifetime of
     * a mediator, accurate to seconds
     *
     * RI:
     * window is greater than 0
     * log is not null
     * maxLoad is greater than 0
     * currentLoad is greater than 0
     * secondLoad is greater than 0
     * timer is not null
     * currentLoad is equal to the sum of the numbers currently in log
     * secondLoad only holds the number of request within a second
     * maxLoad always holds the maximum number of requests at the end of every second since
     *   the instantiation of a loadTracker
     * log is never longer than the size of the window
     *
     * Thread-safe argument:
     * This class is thread-safe because all methods are synchronized to prevent any interleaving from
     * happening.
     */

    private int window;
    private final int DEFAULT_WINDOW = 30;

    private ArrayList<Integer> log;
    private int maxLoad;
    private int currentLoad;
    private int secondLoad;

    private Timer time;

    public loadTracker(int max, ArrayList<Integer> previousLog){
        this.window = DEFAULT_WINDOW;
        this.maxLoad = max;
        time = new Timer();
        ScheduledTask st = new ScheduledTask();
        time.schedule(st, 1000, 1000);
        log = previousLog;
        currentLoad = previousLog.stream().reduce(0, Integer::sum);
        secondLoad = 0;
    }

    /**
     * this function increments the number of loads within the current second
     * effect: increment the number of loads tallied for the current second
     */
    synchronized public void increment(){
        secondLoad++;
    }

    /**
     * This function returns the maxLoad within the 30s window from the instantiation of the
     * instance till the moment the function is called
     * @return the maximum number of requests within any 30s window
     */

    synchronized public int getMaxLoad(){
        return Math.max(currentLoad + secondLoad - log.get(0), maxLoad);
    }

    /**
     * this function writes the data in loadTracker class to the end of a file
     * @param writer the Writer object to enable write toe the correct file
     */

    synchronized public void close(Writer writer){
        if(log.size() >= window){
            currentLoad -= log.remove(0);
        }
        log.add(secondLoad);
        currentLoad += secondLoad;
        secondLoad = 0;
        if(currentLoad > maxLoad){
            maxLoad = currentLoad;
        }
        try{
            writer.write("maxLoad: " + maxLoad + System.lineSeparator());
            writer.write("startlog" + System.lineSeparator());
            for(Integer i : log){
                writer.write(i + System.lineSeparator());
            }
        }catch (IOException e){
            System.err.println("there is a problem writing in the maxLoad data");
        }

    }

    public class ScheduledTask extends TimerTask {
        public void run() {
            synchronized (log){
                synchronized ((Integer) secondLoad){
                    synchronized ((Integer) currentLoad){
                        synchronized (((Integer) maxLoad)){
                            if(log.size() >= window){
                                currentLoad -= log.remove(0);
                            }
                            log.add(secondLoad);
                            currentLoad += secondLoad;
                            secondLoad = 0;
                            if(currentLoad > maxLoad){
                                maxLoad = currentLoad;
                            }
                        }
                    }
                }
            }

        }
    }
}
