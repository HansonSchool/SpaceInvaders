import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.JPanel;


public class GameObject implements Collidable{
	private Rectangle myRect;
	public static JPanel panel;
	private int x, y, w, h;
	private int dx = 0, dy = 0;
	private Image img;
	public static Random rand = new Random();
	
	public GameObject(int x, int y, int w, int h, int dx, int dy, Image img) {
		this.x = x;this.y = y; this.w = w; this.h = h; this.dx = dx; this.dy = dy;
		this.img = img;
	}
	public int getRightSide() {
		return x+w;
	}
	public int getLeftSide() {
		return x;
	}
	public int getBottom() {
		return y+h;
	}
	
	public boolean offScreen() {
		return (x<=-1*w ||x>=panel.getWidth()|| y <= -h || y >= panel.getHeight());
	}
	public void move() {
		x+=dx;
		y+=dy;
		//System.out.println("I am a "+this.getClass()+ " and I just moved to "+x+" , "+y);
	}
	public int getDX() {
		return dx;
	}
	public int getDY() {
		return dy;
	}
	public void setDX(int dx) {
		this.dx=dx;
	}
	public void setDY(int dy) {
		this.dy=dy;
	}
	public Rectangle getMyRect() {
		return myRect;
	}
	
	public void setMyRect(Rectangle myRect) {
		this.myRect = myRect;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	
	@Override
	public boolean collides(Collidable c) {
		// TODO Auto-generated method stub
		if(getRect() == null || c.getRect()== null)
			return false;
		return getRect().intersects(c.getRect());
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return myRect;
	}
	public void draw(Graphics g) {
		//	System.out.println("drawing ship at: ("+x+","+y+") and width: "+w+" height: "+h);
			myRect = new Rectangle(x,y,w,h);
			if(img == null) {
				g.setColor(Color.green);
				g.fillRect(x, y, w, h);
			}
			else {
				g.drawImage(img, x, y, w,h,null);
			}	
		}
	public static Color  getRandColor() {
		int r = rand.nextInt(256),
			g = rand.nextInt(256),
			b = rand.nextInt(256);
		return new Color(r,g,b);
		// TODO Auto-generated method stub
		
	}
	public String toString() {
		return this.getClass()+" at ( "+x+" , "+y+" )";
	}
}
