public class IllegalPositionException extends GameException {
    public IllegalPositionException(){
        super();
    }
    public IllegalPositionException(String errorMessage){
        super(errorMessage);
    }
}