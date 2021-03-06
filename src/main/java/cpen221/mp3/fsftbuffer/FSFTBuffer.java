package cpen221.mp3.fsftbuffer;

import java.security.InvalidKeyException;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FSFTBuffer<T extends Bufferable> {

    /* the default buffer size is 32 objects */
    private static final int DSIZE = 32;

    /* the default timeout value is 3600s */
    private static final int DTIMEOUT = 3600;

    private Timer globalTimer;
    private Helper increments;

    private int capacity;
    private int timeout;
    private Integer absoluteTime;

    private ConcurrentHashMap<String, T> bufferContents;
    private ConcurrentHashMap<T, String> bufferReversed;
    private ConcurrentHashMap<String, Integer> timers;

    /**
     * Create a buffer with a fixed capacity and a timeout value.
     * Objects in the buffer that have not been refreshed within the
     * timeout period are removed from the cache.
     *
     * @param capacity the number of objects the buffer can hold
     * @param timeout  the duration, in seconds, an object should
     *                 be in the buffer before it times out
     */
    public FSFTBuffer(int capacity, int timeout) {
        this.capacity = capacity;
        this.timeout = timeout;
        bufferContents = new ConcurrentHashMap<>();
        bufferReversed = new ConcurrentHashMap<>();
        timers = new ConcurrentHashMap<>();
        absoluteTime = 0;
        globalTimer = new Timer();
        increments = new Helper();
        globalTimer.schedule(increments, 1000, 1000);
    }

    /**
     * Create a buffer with default capacity and timeout values.
     */
    public FSFTBuffer() {
        this(DSIZE, DTIMEOUT);
        bufferContents = new ConcurrentHashMap<>();
        bufferReversed = new ConcurrentHashMap<>();
        timers = new ConcurrentHashMap<>();
        absoluteTime = 0;
        globalTimer = new Timer();
        increments = new Helper();
        globalTimer.schedule(increments, 1000, 1000);
    }
    

    /**
     * Add a value to the buffer.
     * If the buffer is full then remove the least recently accessed
     * object to make room for the new object.
     */
    synchronized public boolean put(T t) {
        if(bufferContents.size() == capacity) {
            removeLast();
        }
        bufferContents.put(t.id(),t);
        bufferReversed.put(t,t.id());    
        timers.put(t.id(), absoluteTime + timeout);
        return true;
    }


    synchronized private void removeLast() {
        Integer min = timeout * 2 + absoluteTime;
        String id = "something";
        for (String a : timers.keySet()) {
            if (timers.get(a) < min) {
                min = timers.get(a);
                id = a;
            }
        }
        bufferReversed.remove(bufferContents.get(id));
        bufferContents.remove(id);
        timers.remove(id);
    }


    /**
     * @param id the identifier of the object to be retrieved
     * @return the object that matches the identifier from the
     * buffer
     * Throws InvalidKeyException when the content is not in the cache.
     *
     */
    synchronized public T get (String id) throws InvalidKeyException {
        if(!bufferContents.keySet().contains(id)){
            throw new InvalidKeyException();
        } else {
            timers.replace(id, timeout + absoluteTime);
            return bufferContents.get(id);
        }
    }

    /**
     * Update the last refresh time for the object with the provided id.
     * This method is used to mark an object as "not stale" so that its
     * timeout is delayed.
     *
     * @param id the identifier of the object to "touch"
     * @return true if successful and false otherwise
     */
    synchronized public boolean touch(String id) {
        if(!timers.keySet().contains(id)) {
            return false;
        } else {
            timers.replace(id, timeout + absoluteTime);
            return true;
        }
    }

    /**
     * Update an object in the buffer.
     * This method updates an object and acts like a "touch" to
     * renew the object in the cache.
     *
     * @param t the object to update
     * @return true if successful and false otherwise
     */
    synchronized public boolean update(T t) {
        if(!bufferContents.containsValue(t)) {
            return false;
        } else {
            timers.replace(bufferReversed.get(t), timeout + absoluteTime);
            return true;
        }
    }


    class Helper extends TimerTask {
        @Override
        synchronized public void run() {
            ++absoluteTime;
            Set <String> toBeRemoved = new HashSet<>();
            for(String a : timers.keySet()){
                if(timers.get(a) == absoluteTime){
                    toBeRemoved.add(a);
                    bufferReversed.remove(bufferContents.get(a));
                    bufferContents.remove(a);
                }
            }
            for(String id: toBeRemoved){
                timers.remove(id);
            }
        }
    }
}
