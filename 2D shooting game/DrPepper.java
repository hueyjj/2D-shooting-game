public class DrPepper extends EnemySoda {

	public DrPepper(int x, int y) {
		super(x, y);
		initDrPepper();
	}

	public void initDrPepper() {
		loadImage("DrPepper.gif");
		setImageDimensions();
	}
}
