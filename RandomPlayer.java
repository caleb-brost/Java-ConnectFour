import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 * RandomPlayer class that extends Player
 */
public class RandomPlayer extends Player {
    public RandomPlayer(JOptionPane frame) {
        super("Yellow", frame);
    }

    /**
     * Gets the move for the random player
     *
     * @param board The current game board
     * @return A Move object representing the player's move
     */
    @Override
    public Move getMove(Board board) {
        List<Integer> validColumns = new ArrayList<>();
        
        // Check for valid columns
        for (int col = 0; col < board.getColumns(); col++) {
            if (!board.isColumnFull(col)) { // Assuming you have a method to check if the column is full
                validColumns.add(col);
            }
        }

        // If there are no valid columns, handle accordingly (e.g., return null or throw an exception)
        if (validColumns.isEmpty()) {
            return null;
        }

        // Select a random valid column
        int randomIndex = (int) (Math.random() * validColumns.size());
        int selectedColumn = validColumns.get(randomIndex);
        
        return new Move(selectedColumn);
    }

    /**
     * Returns the player type.
     * @return 'Y' for Yellow player.
     */
    @Override
    public char getPlayerType() {
        return 'Y';
    }
}