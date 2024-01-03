import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
public class Tile extends JButton{
	private int x;
	private int y;
	private int count;
	boolean isMine;
	boolean isFirstClick = false;
	boolean isRevealed = false;
	Tile(int x, int y){
		this.x = x;
		this.y = y;

	}
	
	public int get_X() {
		return this.x;
	}
	public void set_X(int x) {
		this.x = x;
	}
	public int get_Y() {
		return this.y;
	}
	public void set_Y(int y) {
		this.y = y;	
	}
	
	public void reveal() {
		if(!this.isRevealed) {
			System.out.println("Revealing (" + this.get_X() + "," + this.get_Y() + ")");
			this.isRevealed = true;
			if(isMine)
				this.setBackground(Color.red);
			else {
				if(this.count == 0)
					this.setBackground(Color.gray);
				else {
					this.setBackground(Color.green);
					this.setText(count+"");
				}
				this.setForeground(Color.blue);
				this.setFont(new Font("Arial", Font.PLAIN, 16));
			}	
		}		
	}
	public int getMinesCount() {
		return count;
	}
	public void setMinesCount(int count) {
		this.count = count;
	}
}
