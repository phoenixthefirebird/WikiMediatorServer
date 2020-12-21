package cpen221.mp3.wikimediator;

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

    public loadTracker(){
        this.window = DEFAULT_WINDOW;
        time = new Timer();
        ScheduledTask st = new ScheduledTask();
        time.schedule(st, 1000, 1000);
        currentLoad = 0;
        secondLoad = 0;
        log = new ArrayList<>();
    }

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
