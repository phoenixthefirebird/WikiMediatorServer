package cpen221.mp3.fsftbuffer;

/**
 * this immutable class stores an Integer value
 */
public class intBuffers implements Bufferable {

    /**
     * AF:
     * represents an integer value to be stored in a buffer
     *
     * RI:
     * Integer is not null
     * Integer does not change after creation
     */
    private Integer value;

    public intBuffers(Integer value) {
        this.value = value;
    }


    /**
     * get the id of this bufferable
     * @return String representing the id of the instance
     * */
    @Override
    public String id() {
        return ((Integer)this.hashCode()).toString();
    }    
}
