import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class StartWindow extends JFrame {

	private Font f;

	private Thread music;

	private boolean musicActive, scoresListActive;

	final JLabel scoresLabel;

	TreeSet<String> scoresSet;

	JFrame scoreFrame;

	public StartWindow() throws IOException, FontFormatException,
			InterruptedException {
		super("Start Menu");

		loadFont();

		music = new Thread(new Runnable() {
			public void run() {
				try {
					Sound.playStartMusic();

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

		});
		music.start();
		musicActive = true;
		String space = "&nbsp;&nbsp;&nbsp;&nbsp;";
		String name = "<html>I am in Love<br>" + space + "with the <br>"
				+ space + " Coca Cola<html>";

		JLabel title = new JLabel(name, SwingConstants.LEFT);
		title.setVerticalAlignment(SwingConstants.TOP);
		title.setFont(new Font("Loki Cola", Font.BOLD, 110));
		title.setForeground(Color.WHITE);

		Image image = Toolkit.getDefaultToolkit()
				.getImage("CokeMasterRace.jpg");
		ImagePanel imagePanel = new ImagePanel(image);
		imagePanel.setLayout(new GridLayout(2, 2));

		final JLabel startLabel = new JLabel("START");
		startLabel.setFont(new Font("Arial", Font.BOLD, 100));
		startLabel.setForeground(Color.WHITE);
		startLabel.setHorizontalAlignment(JLabel.CENTER);
		startLabel.setOpaque(false);
		startLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startLabel.setForeground(Color.RED);
			}

			public void mouseReleased(MouseEvent e) {
				startLabel.setForeground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent e) {
				try {
					music.interrupt();
					if (scoresListActive) {
						scoreFrame.setVisible(false);
						scoreFrame.dispose();
					}
					new UserWindow(musicActive);

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				setVisible(false);
				dispose();
			}
		});

		scoresLabel = new JLabel("SCORES");
		scoresLabel.setFont(new Font("Arial", Font.BOLD, 100));
		scoresLabel.setForeground(Color.WHITE);
		scoresLabel.setHorizontalAlignment(JLabel.CENTER);
		scoresLabel.setOpaque(false);
		scoresLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				scoresLabel.setForeground(Color.RED);
			}

			public void mouseReleased(MouseEvent e) {
				scoresLabel.setForeground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent e) {
				if (scoresListActive) {
					scoreFrame.setVisible(false);
					scoreFrame.dispose();
					scoresListActive = false;
				} else {
					try {
						loadScores();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					scoresListActive = true;
				}
			}
		});

		final JLabel musicLabel = new JLabel("MUSIC ON");
		musicLabel.setFont(new Font("Arial", Font.BOLD, 100));
		musicLabel.setForeground(Color.WHITE);
		musicLabel.setHorizontalAlignment(JLabel.CENTER);
		musicLabel.setOpaque(false);
		musicLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				musicLabel.setForeground(Color.RED);

			}

			public void mouseReleased(MouseEvent e) {
				musicLabel.setForeground(Color.WHITE);

			}

			public void mouseClicked(MouseEvent e) {
				if (musicActive) {
					music.interrupt();
					musicLabel.setText("MUSIC OFF");
					musicActive = false;
				} else {
					musicLabel.setText("MUSIC ON");
					music = new Thread(new Runnable() {
						public void run() {
							try {
								Sound.playStartMusic();

							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}
						}

					});
					music.start();
					musicActive = true;
				}
			}
		});

		final JLabel exitLabel = new JLabel("EXIT");
		exitLabel.setFont(new Font("Arial", Font.BOLD, 100));
		exitLabel.setForeground(Color.WHITE);
		exitLabel.setHorizontalAlignment(JLabel.CENTER);
		exitLabel.setOpaque(false);
		exitLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				exitLabel.setForeground(Color.RED);
			}

			public void mouseReleased(MouseEvent e) {
				exitLabel.setForeground(Color.WHITE);
			}

			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		JPanel startPanel = new JPanel(new GridLayout(4, 1));
		startPanel.setOpaque(false);
		startPanel.add(startLabel, 0);
		startPanel.add(scoresLabel, 1);
		startPanel.add(musicLabel, 2);
		startPanel.add(exitLabel, 3);

		JPanel empty = new JPanel();
		empty.setVisible(false);

		JPanel empty1 = new JPanel();
		empty1.setVisible(false);

		imagePanel.add(title);
		imagePanel.add(empty, 1);
		imagePanel.add(empty1, 2);
		imagePanel.add(startPanel);

		add(imagePanel);

		JComponent comp = (JComponent) getContentPane();
		comp.setBorder(new LineBorder(Color.GRAY, 2));

		setExtendedState(Frame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void loadScores() throws IOException {

		ScoreComparator compare = new ScoreComparator();
		scoresSet = new TreeSet<String>(Collections.reverseOrder(compare));

		BufferedReader reader = null;
		String line = null;
		reader = new BufferedReader(new FileReader("Scores.txt"));

		while ((line = reader.readLine()) != null) {

			scoresSet.add(line);
		}
		reader.close();

		ArrayList<String> list = new ArrayList<String>();
		Iterator<String> iter = scoresSet.iterator();
		while (iter.hasNext()) {

			list.add(iter.next());
		}

		ArrayList<String> scores = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();

		for (String str : list) {
			String tempScores = "";
			String tempNames = "";
			for (int x = 0; x < str.length(); x++) {
				char c = str.charAt(x);
				if (Character.isDigit(c)) {
					tempScores += c;
				} else {
					tempNames = str.substring(x);
					break;
				}
			}
			scores.add(tempScores);
			names.add(tempNames);
		}

		// JList<Object> scrollList = new JList<Object>(list.toArray());
		DefaultTableModel model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model.addColumn("Name", names.toArray());
		model.addColumn("Score", scores.toArray());

		JTable table = new JTable(model);
		table.setFont(new Font("Arial", Font.BOLD, 30));
		table.setRowHeight(35);
		table.setShowGrid(false);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		// renderer.setHorizontalAlignment(JLabel.CENTER);
		// scrollList.setCellRenderer(renderer);
		//
		// JScrollPane scroller = new JScrollPane(scrollList);
		// scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scoreFrame = new JFrame("Scores");
		scoreFrame.setSize(450, 750);
		scoreFrame.setLocationRelativeTo(null);
		scoreFrame.setUndecorated(true);
		scoreFrame.setVisible(true);
		scoreFrame.setAlwaysOnTop(true);
		scoreFrame.add(new JScrollPane(table));

	}

	private void loadFont() throws IOException, FontFormatException {
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File("Loki Cola.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			ge.registerFont(f);

		} catch (IOException | FontFormatException e) {
			System.out.println("Could not load font");
			e.printStackTrace();
		}

	}

}
