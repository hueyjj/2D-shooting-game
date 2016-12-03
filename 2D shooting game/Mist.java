public class Mist extends EnemySoda {

	public Mist(int x, int y) {
		super(x, y);
		initMist();
	}

	public void initMist() {
		loadImage("Mist.gif");
		setImageDimensions();
	}

}