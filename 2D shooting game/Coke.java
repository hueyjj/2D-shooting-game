import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Vector;

public class Coke extends Soda {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	double screenWidth = screenSize.getWidth();

	double screenHeight = screenSize.getHeight();

	private ArrayList<Missile> missiles;

	private int velX, velY;

	public Coke(int x, int y) {
		super(x, y);

		initCoke();
	}

	private void initCoke() {

		missiles = new ArrayList<Missile>();
		loadImage("Coke.gif");
		setImageDimensions();
	}

	public void move() {
		int newX = x + velX;
		int newY = y + velY;

		if (newX <= 1 || newY <= 1 || newX + width > screenWidth
				|| newY + height > screenHeight) {
			if (newY >= 1 && newY + height <= screenHeight) {
				y = newY;
			}
			if (newX >= 1 && newX + width <= screenWidth) {
				x = newX;
			}

			return;

		}

		x = newX;
		y = newY;

	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public int getVelX() {
		return velX;
	}

	public int getVelY() {
		return velY;
	}

	public ArrayList<Missile> getMissiles() {
		return missiles;
	}

	public void fire(int mouseX, int mouseY) {

		Missile m = new Missile(x, y);
		m.setDestX(mouseX);
		m.setDestY(mouseY);
		m.calcVector();
		missiles.add(m);
	}

}