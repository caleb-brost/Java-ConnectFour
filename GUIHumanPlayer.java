import javax.swing.JOptionPane;

/**
 * Represents a human player that interacts with the game through a graphical user interface.
 * This class extends the Player class and implements the getMove method to get user input
 * through the GUI rather than through text input.
 */
public class GUIHumanPlayer extends Player {
    private GUIBoard guiBoard;
    
    /**
     * Constructs a new GUIHumanPlayer with the specified JOptionPane.
     *
     * @param frame The JOptionPane used for displaying messages
     */
    public GUIHumanPlayer(JOptionPane frame) {
        super("Red", frame);
    }
    
    /**
     * Sets the GUIBoard associated with this player.
     * This establishes the connection between the player and the GUI.
     *
     * @param guiBoard The GUIBoard that this player will interact with
     */
    public void setGUIBoard(GUIBoard guiBoard) {
        this.guiBoard = guiBoard;
    }
    
    /**
     * Gets the player's move by waiting for them to click on a column in the GUI.
     * Uses wait/notify mechanism for efficient waiting.
     *
     * @param board The current game board
     * @return A Move object representing the player's chosen column, or null if the player quits
     */
    @Override
    public Move getMove(Board board) {
        if (guiBoard == null) {
            return null;
        }
        
        // Reset the GUI selection
        guiBoard.resetSelectedRowAndColumn();
        
        // Set the current player in the GUI
        guiBoard.setCurrentPlayer(this);
        
        // Set this player as the waiting player in the GUI
        guiBoard.setWaitingPlayer(this);
        
        // Get the lock object from the GUI board
        Object lock = guiBoard.getLock();
        
        // Wait for notification from the GUI
        synchronized (lock) {
            try {
                // Wait until notified by the GUI
                while (!guiBoard.isMoveSelected()) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        
        // Get the selected column
        int column = guiBoard.getSelectedColumn();
        
        // Reset the GUI title
        guiBoard.setCurrentPlayer(null);
        
        return new Move(column);
    }
    
    /**
     * Gets the player type character.
     *
     * @return 'R' to indicate that this is a red player
     */
    @Override
    public char getPlayerType() {
        return 'R';
    }
}