import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;


public class Shield  extends GameObject{
	
	private int hitRow, hitCol;
	static final int S = 3;
	private int topY, bottomY, leftX, rightX;
	private final int ROWS = 15, COLS = 21;
	Block[][] blocks = new Block[ROWS][COLS];
	public Shield(int x, int y, int w, int h) {
		super(x, y, w, h, 0, 0, null);// don't really do anything with w and h...
		buildBlocks(x,y);
		
		trimBlocks();
		topY = y+(ROWS+1)*S;
		bottomY = y;
		leftX = x;
		rightX = x+(COLS+1)*S;
	}

	private void trimBlocks() {
		// trim away blocks to make the shield look better.
		//int cols = blocks[0].length;
		int safe = 5;
		for(int r = ROWS-1; safe < COLS; r--) {
			for(int c = 0; c<(COLS-safe)/2; c++) {
				blocks[r][c]=null;
				blocks[r][COLS-c-1]=null;
			}
			safe+=4;
		}
		int mid = COLS/2;
		for(int r = 0; r<5; r++) {
			for(int c=0; c<5; c++) {
				blocks[r][mid-c]=null;
				blocks[r][mid+c]=null;
			}
		}
		
	}

	private void buildBlocks(int x, int y) {
		// x is left most coord and y is bottom of shield
		Color col=null;
		for(int r = 0; r<ROWS; r++) {
			if(r%3==0 || col == null)
				col = GameObject.getRandColor();
			for(int c = 0; c<COLS; c++) {
				blocks[r][c]= new Block(x+S*c, y-S*r, S, S, col);
			}
		}
		
	}
	@Override
	public boolean collides(Collidable c) {
		hitRow = 0;
		for(Block[] arr:blocks) {
			hitCol = 0;
			for(Block b: arr) {
				if(b!=null && b.collides(c)) {
					return true;
				}
				hitCol++;
			}
			hitRow++;
		}
		
		return false;
		
	}
	public void draw(Graphics g) {
		for(Block[] row:blocks) {
			for(Block b: row) {
				if(b!=null)
					b.draw(g);
			}
		}
	}

	public void explode(int dir) {// negative if an alien bullet, positive if defender
		int numToRemove = rand.nextInt(3)+5;
		int r = hitRow;
		int c = hitCol;
		// so r,c should be at the top of a col.  remove this one and the two below
		// then remove the two that are diagonally below and if numToRemove >5 then remove
		// the ones diagonally below the block below r,c
		//System.out.println("Exploded "+r+" "+c);
		blocks[r][c]=null;
		r+=dir;
		if(isValid(r, c)) {
			//System.out.println("Exploded "+r+" "+c);
			blocks[r][c]=null;// r-1 to go lower...
			if(isValid(r, c-1))blocks[r][c-1]=null;
			if(isValid(r, c+1))blocks[r][c+1]=null;
		}
		r+=dir;
		if(isValid(r, c)) {
			//System.out.println("Exploded "+r+" "+c);
			blocks[r][c]=null;
		}
		r+=dir;
		if(dir>0 && isValid(r,c)) {
			//System.out.println("Exploded "+r+" "+c);
			blocks[r][c]=null;
		}
		
	}
	boolean isValid(int r, int c) {
		return r>=0&&r<ROWS && c >=0&& c<COLS;
	}


}
