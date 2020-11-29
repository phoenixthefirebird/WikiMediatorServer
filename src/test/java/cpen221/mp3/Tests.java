package cpen221.mp3;

import cpen221.mp3.fsftbuffer.Bufferable;
import cpen221.mp3.fsftbuffer.FSFTBuffer;
import cpen221.mp3.fsftbuffer.intBuffers;
import org.junit.Test;
import java.util.*;
import java.util.concurrent.*;

public class Tests {
    /**testing sequentially*/

    //testing for buffer's ability to remove the oldest content
    @Test
    public void test() {

        Timer timer = new Timer();
        FSFTBuffer buffer= new FSFTBuffer(7,4);
        for(int i = 0; i < 10; i ++){
            buffer.put(new intBuffers(i));
            try {
                Thread.sleep(5000);
            } catch (Exception InterruptedException){
                return;
            }
        }
    }
    @Test
    public void test1() {

        Timer timer = new Timer();
        FSFTBuffer buffer= new FSFTBuffer(7,11);
        for(int i = 0; i < 20; i += 2){
            buffer.put(new intBuffers(i));
            try {
                Thread.sleep(5000);
            } catch (Exception InterruptedException){
                return;
            }
        }
    }


//        TimerTask add = new TimerTask() {
//            Integer i = 0;
//            @Override
//            public void run() {
//                buffer.put(new intBuffers(i++));
//            }
//        };
//
//        timer.schedule(add, 1000, 1000);

    }



}
