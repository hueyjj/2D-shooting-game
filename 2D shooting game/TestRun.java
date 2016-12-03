import java.awt.FontFormatException;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class TestRun {
	public static void main(String[] args) throws IOException,
			FontFormatException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new StartWindow();
				} catch (IOException | FontFormatException
						| InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

}
