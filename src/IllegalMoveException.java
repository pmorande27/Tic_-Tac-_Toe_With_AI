public class IllegalMoveException extends GameException{
    public IllegalMoveException(){
        super();
    }
    public IllegalMoveException(String errorMessage){
        super(errorMessage);
    }
}
