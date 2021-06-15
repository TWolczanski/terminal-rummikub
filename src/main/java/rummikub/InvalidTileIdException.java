package rummikub;

public class InvalidTileIdException extends Exception {
    public InvalidTileIdException(String errorMessage){
        super(errorMessage);
    }
}
