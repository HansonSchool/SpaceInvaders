import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Bullet extends GameObject{

	Color c ;
	public static final int WIDTH = 3, HEIGHT = 5;
	public Bullet(int x, int y, int speed) {
		super(x,y,WIDTH, HEIGHT, 0, speed, null);
		//		try {
		//			setImg(ImageIO.read(getClass().getResourceAsStream("res/images/bullet.png")));
		//		} catch (IOException e) {
		//			System.out.println("problem opening image...");
		//			e.printStackTrace();
		c = new Color(getRandColor().getRGB()+Color.WHITE.getRGB()/2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(c);
		this.setMyRect(new Rectangle(getX(), getY(), getW(), getH()));
		g.fillRect(getX(), getY(), getW(), getH());
	}

}
