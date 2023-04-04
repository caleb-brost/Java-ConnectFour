public class GUIBoard 
{
	public void setTitle(String title) {}	

	public void defaultTitle() {}

	public int getRowSelected() { return -1;}

	public int getColumnSelected() { return -1;}

	public void resetSelectedRowAndColumn() {}

	public void setWinner(Player winner) {}

	public GUIBoard(Board board, String title, Player playerRed, Player playerYellow) {}

	public void updateBoard(Board board) {}


	public void setThickness(float thickness) {}

	public void setThickness(double thickness) {}	
}
