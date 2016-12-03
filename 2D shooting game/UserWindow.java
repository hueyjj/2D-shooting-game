import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class UserWindow extends JFrame {

	private static JFrame frame;

	private static boolean menuActive = false;

	private static CardLayout cardLayout;

	private static JPanel cardPanel;

	private JPanel menu;

	private static Board board;

	private static boolean musicActive;

	private static String name = "";

	public UserWindow(boolean musicActive) throws InterruptedException {
		UserWindow.musicActive = musicActive;
		cardLayout = new CardLayout();
		initUI();
	}

	private void initUI() throws InterruptedException {
		frame = new JFrame("Defend of the Coca Cola Master Race");

		Image image = Toolkit.getDefaultToolkit()
				.getImage("CokeBackground.jpg");

		board = new Board(image, musicActive);
		board.setBorder(new LineBorder(Color.GRAY, 7));
		menu = makeMenuPanel();
		cardPanel = new JPanel();
		cardPanel.setLayout(cardLayout);
		cardPanel.add(board, "board");
		cardPanel.add(menu, "menu");

		initKeyBindings();

		frame.add(cardPanel);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static int getScore() {
		return board.getScore();
	}

	public boolean getGameOver() {
		return board.getGameOver();
	}

	public static void unPause() {
		board.start();
	}

	public static void pause() {
		board.stop();
	}

	public static void framePaint() {
		frame.validate();
		frame.repaint();
	}

	public static JPanel makeMenuPanel() {
		int fontSize = 100;
		final Color darkRed = new Color(204, 0, 0);

		final JLabel resume = new JLabel("Resume");
		resume.setFont(new Font("Serif", Font.BOLD, fontSize));
		resume.setForeground(darkRed);
		resume.setHorizontalAlignment(JLabel.CENTER);
		resume.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				resume.setForeground(Color.RED);
			}

			public void mouseReleased(MouseEvent e) {
				resume.setForeground(darkRed);
			}

			public void mouseClicked(MouseEvent e) {
				cardLayout.show(cardPanel, "board");
				board.requestFocusInWindow();
				menuActive = false;
				if (board.getGameOver() == false) {
					unPause();
				}
				framePaint();

			}
		});
		final JLabel restart = new JLabel("Restart");
		restart.setFont(new Font("Serif", Font.BOLD, fontSize));
		restart.setForeground(darkRed);
		restart.setHorizontalAlignment(JLabel.CENTER);
		restart.setHorizontalAlignment(JLabel.CENTER);
		restart.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				restart.setForeground(Color.RED);
			}

			public void mouseReleased(MouseEvent e) {
				restart.setForeground(darkRed);
			}

			public void mouseClicked(MouseEvent e) {

				if (musicActive) {
					board.stopMusic();
				}

				try {
					recordName();
					writeScores();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.setVisible(false);
				frame.dispose();
				try {
					new UserWindow(musicActive);

				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
				board.resetSpecialAtt();
				board.setEnemiesDead(0);
				board.setScore(0);
				board.resetLivesAndHealth();

			}
		});

		final JLabel backToMenu = new JLabel("Back to Menu");
		backToMenu.setFont(new Font("Serif", Font.BOLD, fontSize));
		backToMenu.setForeground(darkRed);
		backToMenu.setHorizontalAlignment(JLabel.CENTER);
		backToMenu.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				backToMenu.setForeground(Color.RED);
			}

			public void mouseReleased(MouseEvent e) {
				backToMenu.setForeground(darkRed);
			}

			public void mouseClicked(MouseEvent e) {
				try {
					if (musicActive) {
						board.stopMusic();
					}
					recordName();
					writeScores();
					board.resetSpecialAtt();
					board.setEnemiesDead(0);
					board.setScore(0);
					board.resetLivesAndHealth();

					new StartWindow();

				} catch (IOException | FontFormatException
						| InterruptedException e1) {
					e1.printStackTrace();
				}

				frame.setVisible(false);
				frame.dispose();

			}
		});

		final JLabel exit = new JLabel("Exit");
		exit.setFont(new Font("Serif", Font.BOLD, fontSize));
		exit.setForeground(darkRed);
		exit.setHorizontalAlignment(JLabel.CENTER);
		exit.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				exit.setForeground(Color.RED);
			}

			public void mouseReleased(MouseEvent e) {
				exit.setForeground(darkRed);
			}

			public void mouseClicked(MouseEvent e) {

				try {
					recordName();
					writeScores();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});

		JPanel menu = new JPanel();
		menu.setLayout(new GridLayout(4, 1));
		menu.setBackground(Color.WHITE);
		menu.setBorder(new LineBorder(Color.WHITE, 4));
		menu.add(resume, 0);
		menu.add(restart, 1);
		menu.add(backToMenu, 2);
		menu.add(exit, 3);

		return menu;
	}

	private static void recordName() {
		name = JOptionPane.showInputDialog("Enter a name to save your score!");

		if (name != null && name.length() > 0) {
			JOptionPane.showMessageDialog(frame, "Thank you for playing "
					+ name + '!');
		} else {
			JOptionPane.showMessageDialog(frame,
					"You definitely have name. Everyone has a name!");
			name = "I Don't Exist";
		}

	}

	private static void writeScores() throws IOException {

		BufferedWriter output = null;
		BufferedReader reader = null;
		String allScores = "";
		String line = null;
		String score = getScore() + "";
		try {

			reader = new BufferedReader(new FileReader("Scores.txt"));
			while ((line = reader.readLine()) != null) {
				allScores += line + "\n";
			}

			output = new BufferedWriter(new FileWriter("Scores.txt"));
			output.write(allScores + score + " " + name);

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (output != null)
				output.close();
		}
	}

	private void initKeyBindings() {
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);

		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(key, "escAction");
		frame.getRootPane().getActionMap()
				.put("escAction", new AbstractAction() {
					private static final long serialVersionUID = 1L;

					// use "esc" key to set menu panel visble/not visible
					public void actionPerformed(ActionEvent e) {
						// frame.add( menuPanel );
						if (menuActive == false) {
							// menuPanel.setVisible( true );
							cardLayout.show(cardPanel, "menu");
							menu.requestFocusInWindow();
							menuActive = true;
							pause();
						} else {
							// menuPanel.setVisible( false );
							cardLayout.show(cardPanel, "board");
							board.requestFocusInWindow();
							menuActive = false;
							if (board.getGameOver() == true) {

							} else {
								unPause();
							}

						}
						framePaint();
					}

				});
	}

}
