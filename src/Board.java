import java.util.ArrayList;

public class Board {
    public static PieceType player = PieceType.X;
    public static PieceType IA = PieceType.O;
    private int dimension;
    private ArrayList<Pieces> pieces = new ArrayList<>();

    public ArrayList<Pieces> getPieces() {
        return pieces;
    }

    private Position[][] listOfPositions;
    public Board(int dimension){
        this.dimension = dimension;
        this.listOfPositions = new Position[dimension][dimension];
        getPositions(dimension);
    /*
        Pieces a = new Pieces(this.IA,new Position(0,0));

        Pieces b = new Pieces(this.IA,new Position(1,1));
        Pieces c = new Pieces(this.player,new Position(2,0));
        Pieces d = new Pieces(this.player,new Position(2,1));
        pieces.add(a);
        pieces.add(b);
        pieces.add(c);
        pieces.add(d);


     */




    }

    private void getPositions(int dimension) {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                listOfPositions[i][j] = new Position(i,j);
            }
        }
    }
    public ArrayList<Pieces> Turn(PieceType type, Position newPosition,ArrayList<Pieces> pieces) throws IllegalMoveException {
        checkLegalMove(newPosition,listOfPositions);
        ArrayList<Pieces> newPosition2 =new ArrayList<>(pieces);
        boolean result = !isOccupiedPosition(newPosition,pieces);
        if (result){
            newPosition2.add(new Pieces(type,newPosition));
        }
        else{
            throw new IllegalMoveException("Not valid Move: that spot is already occupied");
        }
        return newPosition2;
    }
    public void move(PieceType type, Position position) throws IllegalMoveException {
        pieces = Turn(type,position,pieces);
    }

    private void checkLegalMove(Position newPosition, Position[][] listOfPositions) throws IllegalMoveException {
        for(int i = 0; i< dimension;i++){
            for(int j =0; j<dimension;j++){
                if(newPosition.equals(listOfPositions[i][j])){
                    return;
                }
            }
        }
        throw new IllegalMoveException("Not valid mode: not in Board");
    }

    private boolean isOccupiedPosition(Position newPosition,ArrayList<Pieces> pieces) {
        for (Pieces piece : pieces){
            if(piece.getPosition().equals(newPosition)) {
                return  true;
            }
        }
        return false;
    }
    public void displayBoard(){
        for(int i = 0; i<this.dimension;i++){
            for(int j =0; j<this.dimension;j++){
                Position position = listOfPositions[i][j];
                if(isOccupiedPosition(listOfPositions[i][j],this.pieces)){
                    determineTypeOfPiece(position);
                }
                else{
                    System.out.print("*");
                }
            }
            System.out.println();
        }


    }

    private void determineTypeOfPiece(Position position) {

        for(Pieces piece : pieces){
            if (piece.getPosition().equals(position)){
                System.out.print(piece.getType());
            }
        }
    }
    public boolean checkWin(PieceType winType ,ArrayList<Pieces> pieces){
        return (checkVertical(pieces,winType) || checkHorizontal(pieces,winType) || checkPrincipalDiagonal(winType,pieces) || checkSecondDiagonal(pieces,winType));

    }
    private boolean checkPrincipalDiagonal(PieceType winType,ArrayList<Pieces>pieces){
        Position initialPosition = listOfPositions[0][0];
        if (searchPosition(winType,initialPosition,pieces)){
            PieceType type = winType;
            for(int i = 0; i<dimension;i++){
                Position position = listOfPositions[i][i];
                if(!searchPosition(type,position,pieces)){
                    return false;
                }
            }
            return true;
        }
        return false;

    }
    private boolean checkSecondDiagonal(ArrayList<Pieces>pieces,PieceType winType){
        Position initialPosition = listOfPositions[dimension-1][0];
        if (searchPosition(winType,initialPosition,pieces)){
            PieceType type = winType;
            for(int i = 0; i<dimension;i++){
                Position position = listOfPositions[dimension-1-i][i];
                if(!searchPosition(type,position,pieces)){
                    return false;
                }
            }
            return true;
        }
        return false;

    }
    private boolean checkVertical(ArrayList<Pieces>pieces ,PieceType winType){
        PieceType type;
        boolean result = false;
        for(int i = 0; i<dimension; i++){
            if(result){
                break;
            }
            result = true;
            Position initialPosition = listOfPositions[0][i];
            if (searchPosition(winType,initialPosition,pieces)){
                type =winType;
                for(int j = 1; j<dimension;j++){
                    if (searchPosition(type, listOfPositions[j][i], pieces)) {
                        continue;
                    }
                    result= false;
                    break;
                }
            }
            else{
                result = false;
            }
        }
        return result;
    }

    private boolean checkHorizontal(ArrayList<Pieces>pieces,PieceType winType) {
        PieceType type;
        boolean result = false;
        for(int i = 0; i<dimension; i++){
            if(result){
                break;
            }
            result = true;
            Position initialPosition = listOfPositions[i][0];
            if (searchPosition(winType,initialPosition,pieces)){
                type =winType;
                for(int j = 1; j<dimension;j++){
                    if(!searchPosition(type,listOfPositions[i][j],pieces)){
                        result= false;
                        break;
                    }
                }
            }
            else{
                result = false;
            }
        }
        return result;
    }
    public boolean checkTie(ArrayList<Pieces> newPositions) {
        for (int i = 0; i<dimension;i++){
            for(int j = 0;j<dimension;j++){
                Position position = listOfPositions[i][j];
                if(!isOccupiedPosition(position,newPositions)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean searchPosition(PieceType type, Position position, ArrayList<Pieces> pieces){
        for (Pieces piece: pieces) {
            if (position.equals(piece.getPosition()) && type.equals(piece.getType())) {
                return true;
            }
        }
        return false;
    }

    public Position makeAIMove() throws IllegalMoveException {
        int bestScore = -1000000;
        Position bestMove = null;
        for(int i = 0; i< dimension;i++){
            for(int j = 0; j<dimension;j++){
                Position position = listOfPositions[i][j];
                ArrayList<Pieces> pieces2 = new ArrayList<>(pieces);
                if (!isOccupiedPosition(position,pieces2)){
                   ArrayList<Pieces> newPositions = Turn(IA,position,pieces2);
                   int score = minimax(newPositions,0,false);

                   if(score>bestScore){
                       bestScore = score;
                       bestMove = position;
                   }
                }
            }

        }

    return bestMove;
    }

    private int minimax(ArrayList<Pieces> newPositions, int depth, boolean isMax) throws IllegalMoveException {
        if(checkWin(IA,newPositions)){
            return 1;
        }
        else if(checkWin(player,newPositions)){
            return (-1);
        }
        else if(checkTie(newPositions)){
            return 0;
        }
        else if (isMax) {
            int bestScore = -1000000;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    Position position = listOfPositions[i][j];
                    if (!isOccupiedPosition(position, newPositions)) {
                        ArrayList<Pieces>positions = Turn(IA, position, newPositions);
                        int score = minimax(positions, depth+1, false);

                        positions.remove(new Pieces(IA,position));
                        if (score > bestScore) {
                            bestScore = score;
                        }
                    }
                }

            }
            return bestScore;
        }
        else {
            int bestScore = 1000000;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    Position position = listOfPositions[i][j];
                    if (!isOccupiedPosition(position, newPositions)) {
                        ArrayList<Pieces>positions = Turn(player, position, newPositions);
                        int score = minimax(positions, depth + 1, true);
                        positions.remove(new Pieces(player,position));
                        if (score < bestScore) {
                            bestScore = score;
                        }
                    }
                }
            }
            return bestScore;
        }
    }




}
