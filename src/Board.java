import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;


public class Board{
	private final int LEN = 15;
	private final int HEI = 15;
	private final int SIZE = 800;
	private boolean game = true;
	private boolean firstClick = true;
	private int mineCount = LEN * HEI / 8;

	Tile[][] board = new Tile[LEN][HEI];
	
	
	public Board() {
		JFrame frame = new JFrame("MineSweeper");
		frame.setSize(SIZE, SIZE);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new GridLayout(LEN, HEI));
				
		for (int x = 0; x < LEN; x++) {
			for (int y = 0; y < HEI; y++) {
				Tile t = new Tile(x, y);
				frame.add(t);
				t.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(firstClick) {
							t.isFirstClick = true;
							generateMine(mineCount);
							calcAllCounts();
							firstClick = !firstClick;
							if(t.getMinesCount() == 0) 
								dfs(t.get_X(),t.get_Y());
							else
								t.reveal();
						}
						else {
							if(game) {
								if(t.isMine) {
									revealAllMines();
									game = false;
									return;
								}
								if(t.getMinesCount() == 0) 
									dfs(t.get_X(),t.get_Y());
								else
									t.reveal();
							}
						}
						
					}
				});
				board[x][y] = t;
			}
		}

//		generateMine(mineCount);
//		calcAllCounts();
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public boolean isGame() {
		return game;
	}

	public void setGame(boolean game) {
		this.game = game;
	}

	public void generateMine(int num) {
		int count = 0;
		while (count < num) {
			int x = (int) (Math.random() * board.length);
			int y = (int) (Math.random() * board[0].length);
			while (board[x][y].isMine || board[x][y].isFirstClick) {
				x = (int) (Math.random() * board.length);
				y = (int) (Math.random() * board[0].length);
			}
			board[x][y].isMine = true;
			count++;
		}
		System.out.println("Generating mines...");
	}
	private void calcAllCounts() {
		
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				Tile t = board[x][y];
				for(int i = -1; i <= 1; i++) {
					for(int j = -1; j <= 1; j++) {
						if(i == 0 && j == 0)
							continue;
						if(x + i < 0 || y + j < 0 || x + i >= LEN || y + j >= HEI)
							continue;
						if(board[x+i][y+j].isMine) {
							t.setMinesCount(t.getMinesCount()+1);
						}
					}
				}
			}
		}
	}
	
	public void revealAllMines() {
		for(int x = 0; x < LEN; x++) {
			for (int y = 0; y < HEI; y++) {
				if(board[x][y].isMine)
					board[x][y].reveal();
	
			}
		}
	}
	
	public void dfs(int x, int y) {
		if(x < 0 || x >= LEN || y < 0 || y >= HEI)
			return;
		if(this.board[x][y].isRevealed)
			return;
		if(this.board[x][y].getMinesCount() == 0) {
			this.board[x][y].reveal();
			dfs(x-1,y-1);
			dfs(x-1,y);
			dfs(x-1,y+1);
			dfs(x,y-1);
			dfs(x,y+1);
			dfs(x+1,y-1);
			dfs(x+1,y);
			dfs(x+1,y+1);
		}
		this.board[x][y].reveal();
			
//		for(int i = -1; i <= 1; i++) {
//			for(int j = -1; j <= 1; j++) {
//				dfs(x+i,y+j);
//			}
//		}
		
			
	}
	


}
