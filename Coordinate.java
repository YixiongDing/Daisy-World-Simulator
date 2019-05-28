/**
 * Coordinate representing x,y coordinates in the experiment.
 *
 * @author Shenglan Yu<shenglany1@student.unimelb.edu.au> - 808600
 * 		   Yixiong Ding - 671499
 *  	   Haohua Wu - 927081
 *
 */
public class Coordinate {
	
	private int x;
	private int y;
	
	public Coordinate(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	// getter
	public int getX() {
		return x;
	}
	
	// setter
	public void setX(int x) {
		this.x = x;
	}
	
	// getter
	public int getY() {
		return y;
	}
	
	// setter
	public void setY(int y) {
		this.y = y;
	}
	
	// overwrite equals method
	public boolean equals(Object other) {
		Coordinate cor = (Coordinate) other;
		if(cor.getX() == this.getX() && cor.getY() == this.getY()) {
			return true;
		}
		return false;
	}
	
	// overwrite hashing method
	public int hashCode() {
		return getX() * 100000000 + getY();
	}
	
	
}
