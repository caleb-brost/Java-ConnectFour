import javax.swing.JOptionPane;
/**
 * Represents a the connect four game which contains the main game loop.
 *
 * <p>This class has the main game loop logic and instantiates the game objects.
 *
 * @author Caleb Brost
 * @version 1.0
 * @since 2025
 */
public class ConnectFour {

     /**
     * The main game loop that controls the game's execution.
     * <p>
     * This loop runs continuously during the game. The loop continues until the game is over or the user exits.
     *
     * @param args Command-line arguments passed to the program
     */
    public static void main(String[] args){
        JOptionPane frame = new JOptionPane("Connect Four");

        // Initialize board based on command-line arguments
        Board board;
        
        // Check the number of command-line arguments
        if (args.length == 2) {
            // Two arguments provided, parse them
            try {
                int rows = Integer.parseInt(args[0]);
                int cols = Integer.parseInt(args[1]);
    
                // Validate the input
                if (rows <= 0 || cols <= 0) {
                    JOptionPane.showMessageDialog(frame, "Error: rows and cols must be greater than zero");
                    System.exit(1);
                    return; // Exit the method
                }
    
                board = new Board(rows, cols, frame); // Initialize with specified size
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Error: Please provide valid integers for rows and columns.");
                System.exit(1);
                return; // Exit the method
            }
        } else if (args.length != 0) {
            // Incorrect number of arguments
            JOptionPane.showMessageDialog(frame, "Usage: java ConnectFour [rows] [cols]");
            System.exit(1);
            return; // Exit the method
        } else {
            // No arguments provided, use default constructor
            board = new Board(); // Default size
        }
    
        board.initialize(); // Fill the board

        // Create a GUIHumanPlayer instead of HumanPlayer
        GUIHumanPlayer humanPlayer = new GUIHumanPlayer(frame);
        RandomPlayer randomPlayer = new RandomPlayer(frame);

        // Replace the BoardShow with GUIBoard
        GUIBoard guiBoard = new GUIBoard(board, "Connect Four Game", humanPlayer, randomPlayer);


        // Set the GUIBoard in the GUIHumanPlayer
        humanPlayer.setGUIBoard(guiBoard);

        // Update the board display
        guiBoard.updateBoard(board);
        guiBoard.resetSelectedRowAndColumn();
        
        JOptionPane.showMessageDialog(frame, "\nWelcome to Connect Four!"); // Welcome message

        runGame(humanPlayer, randomPlayer, guiBoard, board, frame);
    }

    /**
     * Asks the player if they want to play again and handles the response.
     * If the player chooses to play again, creates a new game with a fresh board.
     * If the player chooses not to play again, exits the application.
     *
     * @param board The current game board
     * @param frame The JOptionPane for displaying messages
     */
    public static void playAgain(Board board, JOptionPane frame) {
        int response = JOptionPane.showConfirmDialog(frame, "Would you like to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            // Create a new board with the same dimensions
            Board newBoard = new Board(board.getRows(), board.getColumns(), frame);
            newBoard.initialize(); // Initialize the new board
            
            // Create new players and GUI
            GUIHumanPlayer humanPlayer = new GUIHumanPlayer(frame);
            RandomPlayer randomPlayer = new RandomPlayer(frame);
            GUIBoard guiBoard = new GUIBoard(newBoard, "Connect Four Game", humanPlayer, randomPlayer);
            
            // Set the GUIBoard in the GUIHumanPlayer
            humanPlayer.setGUIBoard(guiBoard);
            
            // Update the board display
            guiBoard.updateBoard(newBoard);
            
            // Start a new game
            runGame(humanPlayer, randomPlayer, guiBoard, newBoard, frame);
        }
        else {
            JOptionPane.showMessageDialog(frame, "Thanks for playing!");
            System.exit(0);
        }
    }

    /**
     * Runs the main game loop for Connect Four.
     * Alternates between human player and random player turns, checking for win or tie conditions after each move.
     * If a win or tie occurs, prompts the player to play again.
     *
     * @param humanPlayer The human player (red)
     * @param randomPlayer The computer player (yellow)
     * @param guiBoard The graphical user interface for the game board
     * @param board The game board
     * @param frame The JOptionPane for displaying messages
     */
    public static void runGame(GUIHumanPlayer humanPlayer, RandomPlayer randomPlayer, GUIBoard guiBoard, Board board, JOptionPane frame) {
      while (true) {
        // Human player turn
        Move move = humanPlayer.getMove(board); // Get move column index
        if (move == null) { // Check if the player wants to quit
            JOptionPane.showMessageDialog(frame, "Thanks for playing!");
            playAgain(board, frame);
            return; // Exit the method to avoid NullPointerException
        }
        
        // Proceed with the game logic only if move is not null
        board.makeMove(move.getColumn(), humanPlayer); // Play a piece
        guiBoard.updateBoard(board); // Display board
    
        if (board.win(move.getColumn(), humanPlayer) != null) {
            JOptionPane.showMessageDialog(frame, humanPlayer.getName() + " Won! \nThanks for playing!");
            playAgain(board, frame);
            guiBoard.resetSelectedRowAndColumn();
        }

        else if (board.tie()) {
            JOptionPane.showMessageDialog(frame, "It's a tie! \nThanks for playing!");
            playAgain(board, frame);
            guiBoard.resetSelectedRowAndColumn();
        }
    
        // Player 2 turn (Random Player)
        Move randomMove = randomPlayer.getMove(board); // Get move column index
        board.makeMove(randomMove.getColumn(), randomPlayer); // Play a piece
        guiBoard.updateBoard(board); // Display board
    
        if (board.win(randomMove.getColumn(), randomPlayer) != null) {
            JOptionPane.showMessageDialog(frame, randomPlayer.getName() + " Won! \nThanks for playing!");
            playAgain(board, frame);
            guiBoard.resetSelectedRowAndColumn();
        }

        else if (board.tie()) {
            JOptionPane.showMessageDialog(frame, "It's a tie! \nThanks for playing!");
            playAgain(board, frame);
            guiBoard.resetSelectedRowAndColumn();
        }
      }
    }
}