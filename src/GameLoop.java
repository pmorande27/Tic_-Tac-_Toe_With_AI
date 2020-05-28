import java.util.Scanner;

public class GameLoop {
    private PieceType turn;
    private Board board;
    Scanner scan;
    public GameLoop(int dimension){
        this.board = new Board(dimension);
        this.turn = PieceType.randomLetter();
        scan =new Scanner(System.in);
    }
    public void menu(){
        boolean onMenu = true;
        while(onMenu){

            try {

                onMenu = MoveOfPlayerAndAI();
                onMenu = CheckGameStatusAfterMove(onMenu);
                this.turn = switchTurn(turn);

            }
            catch (GameException e ){
                System.err.println(e.getMessage());
            }
        }
    }

    private boolean CheckGameStatusAfterMove(boolean onMenu) {
        if(board.checkTie(board.getPieces())){
        System.out.println("TIE");
        onMenu =false;
        }
        else if(board.checkWin(turn,board.getPieces())) {
            System.out.println(this.turn + " wins");
            onMenu = false;
        }
        return onMenu;
    }

    private boolean MoveOfPlayerAndAI() throws IllegalFigureException, IllegalNumberFormatException, IllegalPositionException, IllegalMoveException {
        if(turn.equals(Board.player)){
            System.out.println("Select one of the options: \n"+MenuOptions.MOVE.name()+"\n"+MenuOptions.EXIT.name());
            if(parseArgument(scan)) {
                board.displayBoard();
                System.out.println(this.turn + " moves");
                Position position = ParsePosition(scan);
                board.move(turn, position);

            }
            else{
                return false;
            }
        }
        else{
            board.move(turn,board.makeAIMove());
            board.displayBoard();
       }
        return true;
    }

    private Position ParsePosition(Scanner scan) throws IllegalNumberFormatException, IllegalPositionException {
       String option =  scan.nextLine();
       String[] numbers = option.split(" ");
       ErrorInParsePositionCheck(numbers,option);
       try{
           int x = Integer.parseInt(numbers[0]);
           int y = Integer.parseInt(numbers[1]);
           return new Position(x,y);
       }
       catch (NumberFormatException e){
           throw new IllegalNumberFormatException("The input: \""+option+"\", is not formed just by two numbers, please try again");
       }



    }
    private void ErrorInParsePositionCheck(String[] numbers, String original) throws IllegalPositionException {
        if(numbers.length!=2){
            String message = "Not valid position "+ original;
            throw new IllegalPositionException(message);
        }


    }


    private boolean parseArgument(Scanner scan) throws IllegalFigureException {
        String option = scan.next().toUpperCase();
        scan.nextLine();

        if(option.toUpperCase().equals(MenuOptions.MOVE.name())){
            return true;
        }
        else if((option.toUpperCase().equals(MenuOptions.EXIT.name()))){
            return false;
        }
        else{
            throw new IllegalFigureException("Not valid Menu Option");
        }
    }

    public PieceType switchTurn(PieceType turn){
        if (turn.name().equals("X")){
            return PieceType.O;
        }
        else{
            return PieceType.X;
        }

    }

}
