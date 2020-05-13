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
        board.displayBoard();
        boolean onMenu = true;
        while(onMenu){

            try {

                if(turn.equals(Board.player)){
                    System.out.println("Select one of the options: \n"+MenuOptions.MOVE.name()+"\n"+MenuOptions.EXIT.name());
                    if(parseArgument(scan)) {

                        System.out.println(this.turn + " moves");
                        Position position = ParsePosition(scan);
                        board.move(turn, position);

                    }
                    else{
                        onMenu = false;
                    }
                }
                else{
                    board.move(turn,board.makeAIMove());
               }
            board.displayBoard();
            if(board.checkTie(board.getPieces())){
                System.out.println("TIE");
                onMenu =false;
            }
            else if(board.checkWin(turn,board.getPieces())) {
                    System.out.println(this.turn + " wins");
                    onMenu = false;
                }
            this.turn = switchTurn(turn);


            }
            catch (IllegalFigureException | IllegalMoveException |java.lang.NumberFormatException e ){
                System.err.println(e.getMessage());
            }

        }
    }

    private Position ParsePosition(Scanner scan) {
       String option =  scan.nextLine();
       String[] numbers = option.split(" ");
       int x = Integer.parseInt(numbers[0]);
        int y = Integer.parseInt(numbers[1]);
        return new Position(x,y);

    }


    private boolean parseArgument(Scanner scan) throws IllegalFigureException {
        String option = scan.next();
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
