import java.awt.*;
import javax.swing.*;

/**
 * Represents a graphical user interface for the Connect Four game board.
 * This class extends JFrame and displays the game board using a grid of buttons.
 * Players can interact with the board by clicking on columns to make their moves.
 */
public class GUIBoard extends JFrame {
    private final GameButton[][] buttons;
    private Board board;
    private int selectedColumn = -1;
    private boolean moveSelected = false;
    private Player currentPlayer;
    private final String defaultTitle;
    // Using a final object for synchronization instead of the non-final waitingPlayer field
    private final Object lock = new Object();
    private GUIHumanPlayer waitingPlayer = null;
    
    /**
     * Constructs a new GUIBoard with the specified board, title, and players.
     * Sets up the GUI components and initializes the game board display.
     *
     * @param board The game board to display
     * @param title The title for the game window
     * @param player1 The first player (typically human)
     * @param player2 The second player (typically computer)
     */
    public GUIBoard(Board board, String title, Player player1, Player player2) {
        super(title);
        this.board = board;
        this.defaultTitle = title;
        this.currentPlayer = player1;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        
        JPanel panel = new JPanel(new GridLayout(board.getRows(), board.getColumns()));
        buttons = new GameButton[board.getRows()][board.getColumns()];
        
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                buttons[i][j] = new GameButton(i, j);
                final int col = j;
                buttons[i][j].addActionListener(e -> {
                    if (!board.isColumnFull(col)) {
                        selectedColumn = col;
                        moveSelected = true;
                        
                        // Notify the waiting player
                        if (waitingPlayer != null) {
                            synchronized (lock) {
                                // Use the lock object for synchronization
                                lock.notify();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Column is full! Choose another column.");
                    }
                });
                panel.add(buttons[i][j]);
            }
        }
        
        add(panel);
        setVisible(true);
    }
    
    /**
     * Sets the title of the game window.
     *
     * @param title The new title for the window
     */
    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    /**
     * Resets the window title to the default title.
     */
    public void defaultTitle() {
        super.setTitle(defaultTitle);
    }

    /**
     * Gets the currently selected row.
     *
     * @return The index of the selected row, or -1 if no row is selected
     */
    public int getRowSelected() {
        return selectedColumn;
    }

    /**
     * Gets the currently selected column.
     *
     * @return The index of the selected column, or -1 if no column is selected
     */
    public int getSelectedColumn() {
        return selectedColumn;
    }

    /**
     * Updates the game board display based on the current state of the board.
     * Redraws all cells to reflect the current positions of player pieces.
     *
     * @param board The current game board to display
     */
    public void updateBoard(Board board) {
        this.board = board;
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                Player player = board.getPlayerAtLocation(i, j);
                if (player != null) {
                    if (player.getPlayerType() == 'R') {
                        buttons[i][j].setPlayer(player);
                    } else if (player.getPlayerType() == 'Y') {
                        buttons[i][j].setPlayer(player);
                    }
                } else {
                    buttons[i][j].setPlayer(null);
                }
            }
        }
        repaint();
        resetSelectedRowAndColumn();
    }
    
    /**
     * Resets the selected row and column to -1 and clears the move selection flag.
     */
    public void resetSelectedRowAndColumn() {
        selectedColumn = -1;
        moveSelected = false;
    }

    /**
     * Sets the window title to indicate that a player has won.
     *
     * @param winner The player who won the game
     */
    public void setWinner(Player winner) {
        setTitle(defaultTitle + " - " + winner.getName() + " Won!");
    }

    /**
     * Sets the window title to indicate that the game ended in a tie.
     */
    public void setTie() {
        setTitle(defaultTitle + " - It's a tie!");
    }

    /**
     * Sets the thickness of the borders around the game board cells.
     *
     * @param thickness The thickness of the borders as a float value
     */
    public void setThickness(float thickness) {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, (int) thickness));
            }
        }
    }

    /**
     * Sets the thickness of the borders around the game board cells.
     * This overloaded method accepts a double value for thickness.
     *
     * @param thickness The thickness of the borders as a double value
     */
    public void setThickness(double thickness) {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                buttons[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, (int) thickness));
            }
        }
    }
    
    /**
     * Checks if a move has been selected by the user.
     *
     * @return true if a move has been selected, false otherwise
     */
    public boolean isMoveSelected() {
        return moveSelected;
    }
    
    /**
     * Sets the current player and updates the window title to indicate whose turn it is.
     *
     * @param player The current player, or null to reset to the default title
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
        if (player != null) {
            setTitle(defaultTitle + " - " + currentPlayer.getName() + "'s Turn");
        } else {
            setTitle(defaultTitle);
        }
    }
    
    /**
     * Sets the player that is waiting for a move to be selected.
     * This player will be notified when a move is selected.
     *
     * @param player The player waiting for a move
     */
    public void setWaitingPlayer(GUIHumanPlayer player) {
        this.waitingPlayer = player;
    }
    
    /**
     * Gets the lock object used for synchronization.
     * 
     * @return The lock object
     */
    public Object getLock() {
        return lock;
    }
    
    /**
     * Inner class representing a button in the game board grid.
     * Extends JButton and overrides paintComponent to draw game pieces.
     */
    class GameButton extends JButton {
        private Player player;
        
        /**
         * Constructs a new GameButton.
         *
         * @param row The row index of this button
         * @param col The column index of this button
         */
        public GameButton(int row, int col) {
            this.player = null;
            setContentAreaFilled(false);
        }
        
        /**
         * Sets the player associated with this button.
         * This determines the color of the piece displayed on the button.
         *
         * @param player The player who has placed a piece at this position, or null if empty
         */
        public void setPlayer(Player player) {
            this.player = player;
        }
        
        /**
         * Overrides the paintComponent method to draw game pieces.
         * Draws a circle with the appropriate color based on the player.
         *
         * @param g The Graphics context used for painting
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            int diameter = Math.min(getWidth(), getHeight()) - 10;
            int x = (getWidth() - diameter) / 2;
            int y = (getHeight() - diameter) / 2;
            
            // Draw the circle
            g.setColor(Color.WHITE);
            g.fillOval(x, y, diameter, diameter);
            
            // If a player has played here, color the circle
            if (player != null) {
                if (player.getPlayerType() == 'R') {
                    g.setColor(Color.RED);
                } else if (player.getPlayerType() == 'Y') {
                    g.setColor(Color.YELLOW);
                }
                g.fillOval(x, y, diameter, diameter);
            }
            
            // Draw border
            g.setColor(Color.BLACK);
            g.drawOval(x, y, diameter, diameter);
        }
    }
}
