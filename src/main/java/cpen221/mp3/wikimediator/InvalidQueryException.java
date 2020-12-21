package cpen221.mp3.wikimediator;

public class InvalidQueryException extends Exception{
    public InvalidQueryException(){
        super("This query is invalid!");
    }
}
