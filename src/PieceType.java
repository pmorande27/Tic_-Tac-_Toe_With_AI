import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PieceType {
    X,
    O;


    private static final List<PieceType> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static PieceType randomLetter()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
