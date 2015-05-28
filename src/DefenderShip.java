import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class DefenderShip extends GameObject{
	
	

	final public static int WIDTH = 75, HEIGHT = 20; 
//	int x,y;// upper left hand corner of ship
//	int w,h;// width and height of ship
	int moveState = 0;// 0 if stationary, neg=move left, pos = right
	int leftX, rightX;// smallest x-coord of ship, largest x-coord of ship
	private int lives;
	private int movesInARow = 0;
	private int maxBullets = 7;
	//private int dx = 0;
//	private Rectangle myBorder;
	
	public DefenderShip(int x, int y) {
		super(x, y, WIDTH, HEIGHT, 0, 0, null);
		setImg(setUpImage());
		// TODO Auto-generated constructor stub
	}
	public void setLocation(int defStartX, int defStartY) {
		setX(defStartX);
		setY(defStartY);
		setW(this.WIDTH);
		setH(this.HEIGHT);
		this.setMyRect(new Rectangle(getX(),getY(),getW(),getH()));
		
	}
	public Image setUpImage() {
		Image img = getImg();
		if(img != null)
			return img;
		try {
			String path = "res/images/Ship.png";
			URL imgUrl = getClass().getResource(path);
		//	System.out.println("URL: "+imgUrl);
			//setImg(new ImageIcon(imgUrl).getImage());
			setImg(ImageIO.read(imgUrl));
		} catch (Exception e) {
			System.out.println("problem opening image...");
			e.printStackTrace();
		}
		return getImg();
	}

	public void setLives(int startLives) {
		this.lives = startLives;
	}
	public int getLivesLeft() {
		return this.lives;
	}
	public void move() {
	/*
	 * Checks to see if already moving.  If so, then build up the dx a bit
	 * if moving left, then move a little faster left.  This basically accelerates
	 * the ship each clock cycle... like an ion drive, slow but steady accel :) 
	 */
		if(moveState!=0)
			movesInARow++;
		if(moveState<0) {
			setDX((movesInARow+1)/3*-1);
		}
		if(moveState>0) {
			setDX((movesInARow+1)/3);
		}
		super.move();
	}

	public void moveRight() {
		this.moveState = 100;// arbitrary positive #
	}

	public void moveLeft() {
		this.moveState= -2;// arbitrary neg
	}
	public void stop() {// set move state to 0, moves in a row to 0 and dx = 0;
		moveState = 0;
		movesInARow=0;
		setDX(0);
	}

	public int maxBullets() {
		
		return this.maxBullets;
	}

	public Bullet getBullet() {
		Bullet b = new Bullet(this.getX()+getW()/2, this.getY(), -2);
		return b;
	}
	public void shot() {
		this.lives--;
	}


}
