package cpen221.mp3.fsftbuffer;

import java.security.InvalidKeyException;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FSFTBuffer<T extends Bufferable> {
    //TODO: check over the documentation and implementation updates
    /**
     * AF:
     * buffer that retains a limited number of items for a
     * limited time.
     *
     * RI:
     * capacity is greater than 0
     * timeout value is greater than 0
     * bufferContents is not null
     * timers is not null
     * timeout is represented in miliseconds
     * the number of items in the buffer is never greater than the capacity
     * the contents of bufferContents and timers must correlate with each other
     * there are no two items with repeated id in the buffer
     * timeout items cannot be accessed again
     *
     * Thread-safety Arguments:
     * TODO: see the readings for details */

    /* the default buffer size is 32 objects */
    private static final int DSIZE = 32;

    /* the default timeout value is 3600s */
    private static final int DTIMEOUT = 3600;


    private int capacity;
    private int timeout;

    private ConcurrentHashMap<String, T> bufferContents;
    private ConcurrentHashMap<String, Long> timers;

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
        this.timeout = timeout * 1000;
        bufferContents = new ConcurrentHashMap<>();
        timers = new ConcurrentHashMap<>();
    }

    /**
     * Create a buffer with default capacity and timeout values.
     */
    public FSFTBuffer() {
        this(DSIZE, DTIMEOUT);
    }
    

    /**
     * Add a value to the buffer.
     * If the buffer is full then remove the least recently accessed
     * object to make room for the new object. This also removes all timeout items.
     * If there were repeated id in the buffer then throw out the old version before
     * inserting the new version.
     */
    synchronized public boolean put(T t) {
        String minID = clearStale();
        if(bufferContents.size() >= capacity) {
            bufferContents.remove(minID);
            timers.remove(minID);
        }
        bufferContents.put(t.id(),t);
        timers.put(t.id(),  timeout + System.currentTimeMillis());
        return true;
    }

    synchronized private String clearStale(){
        Long min = 2 * timeout + System.currentTimeMillis();
        String id = "something";
        ArrayList<String> stale = new ArrayList<>();
        for (String a : timers.keySet()) {
            if (timers.get(a) < min && timers.get(a) > System.currentTimeMillis()) {
                min = timers.get(a);
                id = a;
            }
            if(timers.get(a) > System.currentTimeMillis()){
                stale.add(a);
            }
        }
        for(String b: stale){
            timers.remove(b);
            bufferContents.remove(b);
        }
        return id;
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
        }else if(timers.get(id) - System.currentTimeMillis() < 0.00001){
            timers.remove(id);
            bufferContents.remove(id);
            throw new InvalidKeyException();
        }
        timers.put(id,  timeout + System.currentTimeMillis());
        return bufferContents.get(id);

    }

    /**
     * This method is used to mark an object as "not stale" so that its
     * timeout is delayed.
     *
     * @param id the identifier of the object to "touch"
     * @return true if successful and false if none of the items in the buffer has the
     * id or if the old object already timed out
     */
    synchronized public boolean touch(String id) {
        if(!timers.keySet().contains(id)) {
            return false;
        }else if(timers.get(id) - System.currentTimeMillis() < 0.00001){
            timers.remove(id);
            bufferContents.remove(id);
            return false;
        }  else {
            timers.put(id,  timeout + System.currentTimeMillis());
            return true;
        }
    }

    /**
     * Update an object in the buffer.
     * This method updates the content of the object and
     * also restart the countdown for timeout
     *
     * @param t the object to update
     * @return true if successful and false otherwise
     */
    synchronized public boolean update(T t) {
        if(!bufferContents.containsValue(t)) {
            return false;
        } else if(timers.get(t.id()) - System.currentTimeMillis() < 0.00001){
            timers.remove(t.id());
            bufferContents.remove(t.id());
            return false;
        } else {
            timers.put(t.id(), timeout + System.currentTimeMillis());
            bufferContents.put(t.id(),t);
            return true;
        }
    }


}
