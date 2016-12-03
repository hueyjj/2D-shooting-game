import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Soda {

	protected int x;

	protected int y;

	protected int width;

	protected int height;

	protected boolean vis;

	protected Image image;

	public Soda(int x, int y) {

		this.x = x;
		this.y = y;
		vis = true;
	}

	protected void loadImage(String imageName) {

		ImageIcon i = new ImageIcon(imageName);
		image = i.getImage();
	}

	protected void setImageDimensions() {

		width = image.getWidth(null);
		height = image.getHeight(null);
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isVisible() {
		return vis;
	}

	public void setVisible(Boolean visible) {
		vis = visible;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}