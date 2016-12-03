public class RootBeer extends EnemySoda {

	public RootBeer(int x, int y) {
		super(x, y);
		initRootBeer();
	}

	public void initRootBeer() {
		loadImage("RootBeer.gif");
		setImageDimensions();
	}

}
