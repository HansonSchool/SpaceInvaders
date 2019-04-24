import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;


public class Block extends GameObject {

	Color c;
	public Block(int x, int y, int w, int h, Color col) {
		super(x, y, w, h, 0, 0, null);
		c = col;
		this.setMyRect(new Rectangle(x,y,w,h));
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		System.out.println("Drawing myself: "+this);
		g.fillRect(getX(), getY(), getW(), getH());
	}

}
