import java.util.Scanner;

public class GameLoop {
    private PieceType turn;
    private Board board;
    private TypeOfGame typeOfGame;
    Scanner scan;
    public GameLoop(int dimension){
        this.board = new Board(dimension);
        this.turn = PieceType.randomLetter();
        scan =new Scanner(System.in);
    }
    public void menu(){
        boolean gameSelected = true;
        while (gameSelected){
            try {
                gameSelected = typeOfGame();
            }
            catch (IllegalNumberOfPlayersException | IllegalNumberFormatException e){
                System.err.println(e.getMessage());
            }

        }
        boolean onMenu = true;
        switch (typeOfGame){
            case OnePlayer:
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
                scan.close();
                break;
            case TwoPlayer:
                while (onMenu){
                    try {
                        onMenu = MakeMove();
                        board.displayBoard();
                        onMenu = CheckGameStatusAfterMove(onMenu);

                        this.turn = switchTurn(turn);


                    }
                    catch (GameException  e){
                        System.err.println(e.getMessage());
                    }
                }
                scan.close();
                break;
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
            return MakeMove();
        }
        else{
            board.move(turn,board.makeAIMove());
            board.displayBoard();
       }
        return true;
    }

    private boolean MakeMove() throws IllegalFigureException, IllegalNumberFormatException, IllegalPositionException, IllegalMoveException {
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
    private boolean typeOfGame() throws IllegalNumberFormatException, IllegalNumberOfPlayersException {
        System.out.println("Input the number of players: 1-2");
        String option =  scan.nextLine();
        try{
            int number = Integer.parseInt(option);
            switch (number){
                case 1:
                    typeOfGame = TypeOfGame.OnePlayer;
                    return false;
                case 2:
                    typeOfGame = TypeOfGame.TwoPlayer;
                    return false;
                default:
                    throw new IllegalNumberOfPlayersException(number+ " is not valid number of players, select 1 or 2");

            }
        }
        catch (NumberFormatException e){
            throw new IllegalNumberFormatException("The input: \""+option+"\", is not formed just by two numbers, please try again");
        }


    }

}
