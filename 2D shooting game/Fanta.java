public class Fanta extends EnemySoda {



	public Fanta(int x, int y) {
		super(x, y);
		initFanta();
	}

	public void initFanta() {
		loadImage("Fanta.gif");
		setImageDimensions();
	}

}