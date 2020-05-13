public class Pieces {
    private Position position;
    private PieceType type;
    public Pieces(PieceType type,Position position){
        this.position =position;
        this.type = type;

    }
    @Override
    public String toString(){
        return this.type.toString();

    }

    public Position getPosition() {
        return position;
    }

    public PieceType getType() {
        return type;
    }
}
