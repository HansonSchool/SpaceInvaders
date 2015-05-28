import java.awt.Rectangle;


public interface Collidable {
	public boolean collides(Collidable c);
	public Rectangle getRect();
}
