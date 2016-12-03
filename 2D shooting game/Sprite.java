public class Sprite extends EnemySoda {

	public Sprite(int x, int y) {
		super(x, y);
		initSprite();
	}

	public void initSprite() {
		loadImage("Sprite.gif");
		setImageDimensions();
	}
}
