/**
 * Public class used to define the pieces of the board,
 * they can be either "X" or "O", each piece stores all the information regarding
 * its position.
 */
public class Pieces {
    /**
     * Class variable used to store the position of the piece
     */
    private Position position;
    /**
     * Class variable used to store the Type of the piece
     */
    private PieceType type;

    /**
     * Constructor fo the class used to create a new Piece in the board.
     * @param type type of the new Piece.
     * @param position position of the new Piece in the board.
     */
    public Pieces(PieceType type,Position position){
        this.position =position;
        this.type = type;

    }

    /**
     * Public method used to represent a Piece in the board.
     * @return the type of the Piece either "X" or "0".
     */
    @Override
    public String toString(){
        return this.type.toString();

    }

    /**
     * Public method used to get the position of a given Piece.
     * @return the position of the Piece.
     */

    public Position getPosition() {
        return position;
    }
    /**
     * Public method used to get the Type of a given Piece.
     * @return the Type of the Piece.
     */
    public PieceType getType() {
        return type;
    }
}
