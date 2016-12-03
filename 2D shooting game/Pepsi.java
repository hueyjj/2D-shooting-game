public class Pepsi extends EnemySoda {

	public Pepsi(int x, int y) {
		super(x, y);
		initPepsi();
	}

	public void initPepsi() {
		loadImage("Pepsi.gif");
		setImageDimensions();
	}

}
