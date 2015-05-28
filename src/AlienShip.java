import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class AlienShip extends GameObject{
	public static final int S = 40;
	int pointValue;
	public AlienShip(int x, int y,int sq, int type, int pv) {
		super(x, y, sq, sq, 1, 0, null);
		this.pointValue= pv;
		setUpImage(type);
	}
	private void setUpImage(int type) {
		String path = "res/images/Alien"+type+".png";
		Image img = getImg();
		if(img != null)
			return;
		try {
			URL url = getClass().getResource(path);
		//	System.out.println(path + " "+ url);
			setImg(ImageIO.read(url));
		} catch (Exception e) {
			System.out.println("problem opening image...");
			e.printStackTrace();
		}
		
	}
	public void moveDown(double rbr) {
		this.setY((int) (getY()+rbr));
	}
	public Bullet fire() {
		int x = this.getX()+this.getW()/2,
			y = this.getBottom();
		return new Bullet(x, y, 2);
	}
	
}
