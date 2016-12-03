import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;

public class Missile extends Soda {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private final double BOARD_WIDTH = screenSize.getWidth();

	private final double BOARD_HEIGHT = screenSize.getHeight();

	private int destX, destY;

	private double velX, velY, speed;

	public Missile(int x, int y) {
		super(x, y);

		initMissile();
	}

	private void initMissile() {

		loadImage("CocaColaCap.gif");
		setImageDimensions();

	}

	public void setDestX(int destX) {
		this.destX = destX;
	}

	public void setDestY(int destY) {
		this.destY = destY;
	}

	public void calcVector() {
		speed = 420;
		double rad = Math.atan2((destX - x), (destY - y));

		velX = Math.sin(rad) * speed * .07;
		velY = Math.cos(rad) * speed * .07;
	}

	public void move() {

		// x += MISSILE_SPEED;

		x += velX;
		y += velY;
		// System.out.println(x + ", " + y);
		// System.out.println(velX + ", " + velY);
		if (x > BOARD_WIDTH || y > BOARD_HEIGHT) {
			vis = false;
		}
	}
}