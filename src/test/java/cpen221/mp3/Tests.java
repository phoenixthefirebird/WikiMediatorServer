package cpen221.mp3;

import cpen221.mp3.fsftbuffer.Bufferable;
import cpen221.mp3.fsftbuffer.FSFTBuffer;
import cpen221.mp3.fsftbuffer.intBuffers;
import org.junit.Test;
import java.util.*;
import java.util.concurrent.*;

/** sequential tests */
public class Tests {
    //testing for buffer's ability to remove the oldest content
    // and the put function
    @Test
    public void test() {

        Timer timer = new Timer();
        FSFTBuffer buffer= new FSFTBuffer(7,4);
        for(int i = 0; i < 10; i ++){
            buffer.put(new intBuffers(i));
            try {
                Thread.sleep(1000);
            } catch (Exception InterruptedException){
                return;
            }
        }
    }

    @Test
    public void testDefault() {

        Timer timer = new Timer();
        FSFTBuffer buffer= new FSFTBuffer();
        for(int i = 0; i < 10; i ++){
            buffer.put(new intBuffers(i));
            try {
                Thread.sleep(1000);
            } catch (Exception InterruptedException){
                return;
            }
        }
    }

    @Test
    public void test1() {

        Timer timer = new Timer();
        FSFTBuffer buffer= new FSFTBuffer(7,7);
        for(int i = 0; i < 20; i++){
            buffer.put(new intBuffers(i));
            try {
                Thread.sleep(7000);
            } catch (Exception InterruptedException){
                return;
            }
        }
    }

     @Test
    public void test5() {

        Timer timer = new Timer();
        FSFTBuffer buffer= new FSFTBuffer(4,5);
        for(int i = 0; i < 20; i++){
            buffer.put(new intBuffers(i));
            try {
                Thread.sleep(1000);
            } catch (Exception InterruptedException){
                return;
            }
        }
    }

    //testing the touch function
    @Test
    public void testTouch() {

        Timer timer = new Timer();
        FSFTBuffer buffer= new FSFTBuffer(5,7);
        intBuffers target = new intBuffers(1);
        buffer.put(target);

        for(int i = 0; i < 10; i++){
            try {
                Thread.sleep(1000);
                buffer.touch(target.id());
            } catch (Exception InterruptedException){
                return;
            }
        }
    }
    //testing the update function

    //testing
    @Test
    public void testUpdate() {

        Timer timer = new Timer();
        FSFTBuffer buffer= new FSFTBuffer(5,7);
        intBuffers target = new intBuffers(1);
        buffer.put(target);

        for(int i = 0; i < 10; i++){
            try {
                Thread.sleep(1000);
                buffer.update(target);
            } catch (Exception InterruptedException){
                return;
            }
        }
    }

    //testing the get function see if it gets the right thing and updates the timeout
    @Test
    public void testGet() {

        Timer timer = new Timer();
        FSFTBuffer buffer= new FSFTBuffer(5,7);
        intBuffers target = new intBuffers(1);
        buffer.put(target);

        for(int i = 0; i < 10; i++){
            try {
                Thread.sleep(1000);
               // System.out.println(buffer.get(target.id()));
            } catch (Exception InterruptedException){
                return;
            }
        }
    }



}
