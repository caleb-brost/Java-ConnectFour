import javax.swing.JOptionPane;
/**
 * Represents a human player to store the turn data.
 *
 * This class extends Player and overrides the getMove method to get the human player's move.
 *
 * @author Caleb Brost
 * @version 1.0
 * @since 2025
 */
public class HumanPlayer extends Player {
    /**
     * Constructor for the HumanPlayer class.
     */
    public HumanPlayer(JOptionPane frame) {
        super("Red", frame);
    }

    /**
     * This method overrides the getMove method in the Player class to get the human player's move.
     * 
     * @param board The current game board.
     * @return A Move object indicating the player's choice.
     */
    @Override
    public Move getMove(Board board) {
        while (true) {
            String input = JOptionPane.showInputDialog(frame, "Which column would you like to play in " + this.getName() + "?");
            
            // Check if the user clicked cancel
            if (input == null) {
                JOptionPane.showMessageDialog(frame, "Thanks for playing!");
                System.exit(0); // Exit the program
            }
    
            try {
                int column = Integer.parseInt(input); // Parse the column number
                if (column < 1 || column > board.getColumns()) {
                    JOptionPane.showMessageDialog(frame, "Invalid input! Please enter a column number between 1 and " + board.getColumns());
                    continue; // Prompt again
                }
                return new Move(column - 1); // Return a new Move object with the valid column (zero-based index)
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input! Please enter a valid column number.");
            }
        }
    }

    /**
     * Gets the player type
     * 
     * @return Returns 'R' to indicate that the player is a human player
     */
    @Override
    public char getPlayerType() {
        return 'R';
    }
}