import javax.swing.JOptionPane;
/**
 * Represents a simple player to store the turn data.
 *
 * This class has methods to get the name and color of the player.
 *
 * @author Caleb Brost
 * @version 1.0
 * @since 2025
 */
public abstract class Player {
    private final String name;
    protected JOptionPane frame;

    /**
     * Constructor for the Player class.
     * @param name The name of the player
     */
    public Player(String name, JOptionPane frame) {
        this.name = name;
        this.frame = frame;
    }

    /**
     * Getter method to get the name of the player
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the player type.
     * @return 'R' for Red player or 'Y' for Yellow player.
     */
    public abstract char getPlayerType();

    /**
     * Given a board, the player indicates the square they would like returned via a Move object.
     * @param board The current game board.
     * @return A Move object indicating the player's choice.
     */
    public abstract Move getMove(Board board);
}