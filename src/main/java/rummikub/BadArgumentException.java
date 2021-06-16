package rummikub;

public class BadArgumentException extends Exception {
    public BadArgumentException(String errorMessage){
        super(errorMessage);
    }
}
