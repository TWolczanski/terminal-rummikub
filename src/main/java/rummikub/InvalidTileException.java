package rummikub;

public class InvalidTileException extends Exception {
    public InvalidTileException(String errorMessage){
        super(errorMessage);
    }
}
