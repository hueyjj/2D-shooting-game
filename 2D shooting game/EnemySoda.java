import java.util.ArrayList;

public class EnemySoda extends Soda {

	ArrayList<Object> enemies;

	public double destX, destY, velX, velY, speed;

	protected double bossSpeed = 0;

	protected int health;

	protected boolean isBoss = false;

	public EnemySoda(int x, int y) {
		super(x, y);
		health = 3;
	}

	public void setDestX(int destX) {
		this.destX = destX;
	}

	public void setDestY(int destY) {
		this.destY = destY;
	}

	public void decHealth() {
		health--;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isBoss() {
		return isBoss;
	}

	public void setBoss() {
		isBoss = true;
	}

	public void calcVector() {
		speed = 50;
		double rad = Math.atan2((destX - x), (destY - y));
		if (isBoss) {

			velX = Math.sin(rad) * speed * .05 * bossSpeed;
			velY = Math.cos(rad) * speed * .05 * bossSpeed;
		} else {
			velX = Math.sin(rad) * speed * .05;
			velY = Math.cos(rad) * speed * .05;
		}
	}

	public void move() {
		x += velX;
		y += velY;
	}
}
