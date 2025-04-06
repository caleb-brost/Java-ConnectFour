/**
 * Represents a move made by a player on the board.
 *
 * This class stores the column where the player made the move.
 *
 * @author Caleb Brost
 * @version 1.0
 * @since 2025
 */
public class Move {
    private final int column;

    /**
     * Constructor for the Move class.
     *
     * @param column The column where the player made the move.
     */
    public Move(int column) {
        this.column = column; // Directly assign the column value
    }

    /**
     * Gets the column where the player made the move.
     *
     * @return The column where the player made the move.
     */
    public int getColumn() {
        return this.column; // Return the column value
    }
}