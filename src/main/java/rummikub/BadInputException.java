package rummikub;

public class BadInputException extends Exception {
    public BadInputException(String errorMessage){
        super(errorMessage);
    }
}
