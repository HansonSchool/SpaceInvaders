import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class Army {
	List<AlienShip> aliens = new ArrayList<AlienShip>();// list of aliens
	boolean movingLeft = false;
	//boolean movingDown = false;
	private int tBM , ticksSinceLast = 0;
	private int leftBorder = 50, rightBorder=950;
	private double ratioBetweenRows,ratioBetweenCols;
	private double startSize;
	private double speedUps = .8;// threshold to speed up
	private int numSpeedUps = 0;
	public Army(int x, int y, int rows, int cols, int SQ, int ticksBetweenMove) {
		ratioBetweenCols= SQ*9.0/5;
		ratioBetweenRows=SQ*8/5.0;
		int x1 = x, y1 = y;
		int pointVal = 50, type = 3;
		int decrease = pointVal/rows;
		startSize = rows*cols;
		for(int r = 0; r < rows; r++) {
			x1 = x;
			for(int c = 0; c < cols; c++) {
				aliens.add(new AlienShip(x1,y1,SQ,type	,pointVal ));
				x1+=ratioBetweenCols;
			}
			type = (rows-r+1)/3;
			pointVal -=decrease;
			y1+=ratioBetweenRows;
		}
		this.tBM = ticksBetweenMove;
	}

	public AlienShip kill(Bullet b) {
		for(AlienShip c:aliens)
			if(c != null && c.collides(b)) {
				return c;
			}
				
		return null;
	}

	public void remove(AlienShip as) {
		aliens.remove(as);
	}

	public void removeAll(List<GameObject> removeThese) {
		boolean success = aliens.removeAll(removeThese);
		if(success ) {// I have removed an alien
			if(aliens.size()<speedUps*startSize) {
				speedUp();
				speedUps-=.2;
			}
			else {// less than 20 % left
				if(aliens.size()/startSize<=.06) {
					while(this.numSpeedUps<10)
						speedUp();
				}
				else if(aliens.size()/startSize<=.09) {
					while(this.numSpeedUps<9)speedUp();
				}
				else if(aliens.size()/startSize<=.12) {
					while(this.numSpeedUps<8)speedUp();
				}
				else if(aliens.size()/startSize<=.155) {
					while(this.numSpeedUps<7)speedUp();
				}
				else if(aliens.size()/startSize<=.185) {
					while(this.numSpeedUps<6)speedUp();
				}
			}
		}
		
	}
	public void draw(Graphics g) {
		for(AlienShip as: aliens) {
			as.draw(g);
		}
	}

	public void move() {
		
		ticksSinceLast++;
		if(ticksSinceLast <= tBM) {
			return;
		}
		//System.out.println("Trying to move army :)");
		ticksSinceLast = 0;
		if(movingLeft && !canMoveLeft()||!movingLeft && !canMoveRight()) {
			moveDown();
			return;
		}
		for(AlienShip as:aliens) {
			as.move();
		}
		
	}

	private boolean canMoveRight() {
		for(AlienShip as:aliens) {
			if(as.getRightSide()> rightBorder)
				return false;
		}
		return true;
	}
	public void speedUp() {
		this.numSpeedUps++;
		int jump = 1;
		if(numSpeedUps > 5)jump++;
		if(numSpeedUps > 7)jump++;
		if(numSpeedUps > 9)jump++;
		//System.out.println("************ just sped up "+numSpeedUps+" times");
		for(AlienShip as: aliens) {
			int dx = as.getDX();
			if(dx>0)dx+=jump;
			else dx-=jump;
			as.setDX(dx);
		}
	}
	private void moveDown() {
		for(AlienShip as: aliens) {
			as.moveDown(ratioBetweenRows);
			as.setDX(as.getDX()*-1);
		}
		movingLeft = !movingLeft;
	}

	private boolean canMoveLeft() {
		for(AlienShip as:aliens) {
			if(as.getLeftSide()< leftBorder)
				return false;
		}
		return true;
	}

	public Bullet shoot() {
		AlienShip as = getRandShip();
		if(as != null)
			return as.fire();
		else
			return null;
	}

	private AlienShip getRandShip() {
		if(aliens.size()==0)return null;
		return this.aliens.get(GameObject.rand.nextInt(aliens.size()));
	}
	
}
