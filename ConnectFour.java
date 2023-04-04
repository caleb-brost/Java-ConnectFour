import javax.swing.*;

public class ConnectFour
{
	private Player playerR;
	private Player playerY;
	private Player turn;
	private Board board;

	GUIBoard showBoard;
	
	public static void main(String[] args)
	{
		
		if(args.length < 2) new ConnectFour(6, 6);
		else
		{
			try
			{
				new ConnectFour(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
			}
			catch(Exception e) {System.out.println("Invalid arguments");}
		}
	}

	public ConnectFour(int rows, int columns)
	{
		playerR = new RandomPlayer('R');
		playerY = new RandomPlayer('Y');
		turn = playerR;
		board = new Board(rows, columns);

		// *** CREATE YOUR GUIBoard HERE ***



		while(board.win() == null && !board.tie())
		{
			Move move = turn.getMove(board);
			if(move == null) System.exit(0);
			if(board.makeMove(move.getColumn(), turn))
			{
				showBoard.updateBoard(board);

				if(turn == playerR) turn = playerY;
				else turn = playerR;
			}
		}


		if(board.win() == playerR) 
		{
			showBoard.setWinner(playerR);
			JOptionPane.showMessageDialog(null, "Player Red won", "Thanks for Playing", JOptionPane.INFORMATION_MESSAGE);
		}
		if(board.win() == playerY) 
		{
			showBoard.setWinner(playerY);
			JOptionPane.showMessageDialog(null, "Player Yellow won", "Thanks for Playing", JOptionPane.INFORMATION_MESSAGE);
		}
		if(board.tie()) 
			JOptionPane.showMessageDialog(null, "The game is a tie", "Thanks for Playing", JOptionPane.INFORMATION_MESSAGE);


	}
}
