package cpen221.mp3.fsftbuffer;


public class intBuffers implements Bufferable {
    private Integer value;
    public intBuffers(Integer value) {
        this.value = value;
    }
    
    @Override
    public String id() {
        return ((Integer)this.hashCode()).toString();
    }    
}
