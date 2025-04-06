// import BoardShow;

import javax.swing.JOptionPane;

/**
 * Represents a simple connect four board with adding disc operations.
 *
 * <p>This class provides methods to add discs and check win conditions and the state of the board.
 *
 * @author Caleb Brost
 * @version 1.0
 * @since 2025
 */
public final class Board {
    private static final int DEFAULT_BOARD_SIZE = 6;
    
    Player[][] grid; 
    private int rows;
    private int cols;
    private int playedRowIndex;
    private int playedColIndex;
    private JOptionPane frame; 
    
    /**
    * Creates a new board with a defult size of 6 x 6 stored in a two demensional array and tracks the last played disc location.
    */
    public Board(int rows, int cols, JOptionPane frame) {
        setRows(rows); // width of grid
        setCols(cols); // height of grid
        this.grid = new Player[getRows()][getColumns()]; // grid to store discs
        this.playedRowIndex = 0; // tracks last played row
        this.playedColIndex = 0; // tracks last played column
        this.frame = frame;
    }

    /**
    * Creates a new board with a defult size of 6 x 6 stored in a two demensional array and tracks the last played disc location.
    */
    public Board() {
        setRows(DEFAULT_BOARD_SIZE); // width of grid
        setCols(DEFAULT_BOARD_SIZE); // height of grid
        this.grid = new Player[getRows()][getColumns()]; // grid to store discs
        this.playedRowIndex = 0; // tracks last played row
        this.playedColIndex = 0; // tracks last played column
    }

    /**
    * Gets the number of rows on the board.
    *
    * @return the number of rows on the board
    */
    public int getRows() {
        return this.rows;
    }

    /**
    * Gets the number of columns on the board.
    *
    * @return the number of columns on the board
    */
    public int getColumns() {
        return this.cols;
    }

    /**
    * Sets the number of rows on the board.
    *
    * @param rows the number of rows on the board
    */
    public void setRows(int rows) {
        this.rows = rows;   
    }

    /**
    * Sets the number of columns on the board.
    *
    * @param cols the number of columns on the board
    */
    public void setCols(int cols) {
        this.cols = cols;
}

    /**
    * Initializes each value in the board grid with 'o' chars.
    */
    public void initialize(){
        for (int i=0; i < this.rows; i++) {
            for (int j=0; j < this.cols; j++) {
                this.grid[i][j] = null;
            }
        }
    }

    /**
     * Prints out the formatted current state of the board to the console.
     */
    public void displayBoardASCII(){
        // Formatting output board
        System.out.print("#");
        for (int i = 0; i < (this.rows*2)+1; i++) {
            System.out.print("-");
        }
        System.out.print("#");
        for (Player[] row : this.grid) {
            System.out.print("\n| "); // Changed this
            for (Player element : row) {
                if (element == null) {
                    System.out.print("o ");
                } else {
                    System.out.print(element.getPlayerType() + " ");
                }
            }
            System.out.print("|");
        }
        System.out.print("\n#");
        for (int i = 0; i < (this.rows*2)+1; i++) {
            System.out.print("-");
        }
        System.out.print("#\n");
    }

    /**
     * Adds a disc into the board in a specificed column
     *
     * @param col The column index to add the disc into
     * @param player The player object that is playing the disc
     */
    public boolean makeMove(int col, Player player) {
        // Check if the column is valid
        if (col < 0 || col >= this.cols) {
            JOptionPane.showMessageDialog(frame, "Error: Column index out of bounds");
            return false;
        }
        // Check if there are any more spaces to play on the board or game quit
        if (this.tie()) {
            return false;
        }
    
        // Add disc to the board at the specified column
        for (int i = this.rows - 1; i >= 0; i--) { // Start from the bottom row
            if (this.grid[i][col] == null) { // Find the first empty spot
                this.grid[i][col] = player; // Place the disc
                this.playedRowIndex = i; // Update last played row
                this.playedColIndex = col; // Update last played column
                return true;
            }
        }
        // Column full
        JOptionPane.showMessageDialog(frame, "Column " + (col + 1) + " is full! Please try again\n");
        return false;
    }

    /**
     * Getter method to get the player at a specific location on the board
     *
     * @param row The row index to get the player from
     * @param col The col index to get the player from
     * @return The player at the specified location
     */
    public Player getPlayerAtLocation(int row, int col) {
        return this.grid[row][col];
    }
    
    /**
     * Setter method to update the last played row and column index trackers
     *
     * @param row The row index to be tracked
     * @param col The col index to be tracked
     */
    public void updatePlayed(int row, int col) {
        this.playedRowIndex = row;
        this.playedColIndex = col;
    }

    /**
     * Checks the condition of the board to see if a player won or to quit the game
     *
     * @param col The column index to see if player quit the game
     * @param player The player object that is playing the disc to output the correct winner
     * @return A boolean that states whether a player won or not
     */
    public Player win(int col, Player player) {
        // quit the game logic
        if (col == -1) {
            JOptionPane.showMessageDialog(frame, "You quit the game");
            JOptionPane.showMessageDialog(frame, "\nThanks for playing!");
            return null;

        // check winner logic
        } else if (
            Board.checkConnection(this.getPlayedRow()) ||
            Board.checkConnection(this.getPlayedCol()) ||
            Board.checkConnection(this.getPlayedLeftDiagonal()) ||
            Board.checkConnection(this.getPlayedRightDiagonal())
        ) {
            return player;
        }
        return null;
    }

    /**
     * This method gets the row of the board that the player last played in to check for four discs in a row
     * 
     * @return Returns the character array of the row that the player last played in
     */
    public Player[] getPlayedRow() {
        return this.grid[this.playedRowIndex];
    }

    /**
     * This method gets the column of the board that the player last played in to check for four discs in a row
     * 
     * @return Returns the character array of the column that the player last played in
     */
    public Player[] getPlayedCol() {
        Player[] colArray = new Player[this.rows];

        for (int i=0; i < this.rows; i++) {
            colArray[i] = this.grid[i][this.playedColIndex];
        }
        return colArray;
    }

    /**
     * This method gets the left diagonal of the board that the player last played in to check for four discs in a row
     * 
     * @return Returns the character array of the left diagonal that the player last played in
     */
    public Player[] getPlayedLeftDiagonal() {

        // Calculate the selected location to the diagonal side of the board
        int sub = Math.min(this.playedRowIndex, this.playedColIndex);
        int startY = this.playedRowIndex - sub;
        int startX = this.playedColIndex - sub;

        int size = this.rows - Math.max(startY, startX); // calculate the size of the array to store the diagonal elements

        Player[] result = new Player[size];

        int i = 0;

        // Iterate backwards through the diagonal and add the elemets into the return array
        while (startY <= this.rows-1 && startX <= this.cols-1) {
            result[i] = this.grid[startY][startX];
            startY++;
            startX++;
            i++;
        }
        return result;
    }

    /**
     * This method gets the right diagonal of the board that the player last played in to check for four discs in a row.
     * The right diagonal goes from bottom-left to top-right.
     * 
     * @return Returns the character array of the right diagonal that the player last played in
     */
    public Player[] getPlayedRightDiagonal() {
        // Find the top-right end of the diagonal
        int startY = this.playedRowIndex;
        int startX = this.playedColIndex;
        
        // Move to the bottom-left end of the diagonal
        while (startY < this.rows - 1 && startX > 0) {
            startY++;
            startX--;
        }
        
        // Calculate the size of the diagonal
        int size = Math.min(startY + 1, this.cols - startX);
        
        Player[] result = new Player[size];
        
        // Fill the result array with diagonal elements (bottom-left to top-right)
        for (int i = 0; i < size; i++) {
            result[i] = this.grid[startY - i][startX + i];
        }
        
        return result;
    }
    /**
     * This method checks an parameter array to count the number of discs in a row
     * 
     * @param array This is the array that holds the discs that potencially could have four in a row
     * @return Returns true if there are four of the same colour discs in a row
     */
    public static boolean checkConnection(Player[] array){

        int pattern = 1;

        // return short for optimization
        if (array.length < 4) return false; // It's impossible to have four in a row if there are less than four entries 

        // Loop through array
        for (int i=0; i < (array.length-1); i++) { 

            Player firstElement = array[i]; 
            Player secondElement = array[i+1];
            
            // take first and second element and increment counter if they are the same
            if (firstElement == secondElement && (firstElement != null)) {
                pattern++; // add to the pattern if they are the same
                if (pattern == 4) return true; // if there is row in a row
            }
            else pattern = 1; // set the pattern back to 1 since there was a symbol that did not match the pattern
        }
        return false; // otherwise there is not four in a row
    }

    /**
     * This method checks whether all the board is completly full of discs
     * 
     * @return Returns true if the top row is full of discs 
     */
    public boolean tie(){
        // checks if the first row in the grid is full of discs
        for (Player element : this.grid[0]) {
            if (element == null){
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks whether a column is full
     * 
     * @param column The column to check
     * @return Returns true if the column is full
     */
    public boolean isColumnFull(int column) {
        return this.grid[0][column] != null;
    }
}