import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.TimerTask;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class Board extends ImagePanel implements ActionListener {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private final double BOARD_WIDTH = screenSize.getWidth();

	private final double BOARD_HEIGHT = screenSize.getHeight();

	private final int DELAY = 10;

	private final int HEALTHRESET = 200;

	private final int LIVESRESET = 3;

	private int ENEMYDELAY = 2000;

	private final int dx = 10;

	private final int dy = dx;

	private Timer timer = new Timer(DELAY, this);

	private Timer enemyTimer;

	private ArrayList<EnemySoda> enemies;

	private static Coke coke;

	static int score, lives, health, enemiesDead = 0;

	private boolean gameOver;

	private int resetSpecialAtt = 3;

	private int specialAtt, bossToken = 0;

	private JLabel sodaScore, livesScore, title;

	private Thread music;

	private boolean musicActive;

	private Image frame1, frame2, frame3, frame4, frame5, frame6, frame7,
			frame8, frame9, frame10, frame11, frame12, frame13, frame14,
			frame15, frame16;

	private ArrayList<ImageFrame> frames;

	private ArrayList<ArrayList<ImageFrame>> arrFrames;

	private boolean boss = false;

	public Board(Image image, boolean musicActive) {
		super(image);
		this.musicActive = musicActive;
		initBoard();
	}

	private void initBoard() {
		loadCollisionImage();
		if (musicActive) {
			music = new Thread(new Runnable() {
				public void run() {
					try {
						Sound.playGameMusic();

					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}

			});
			music.start();
		}
		setFocusable(true);
		setDoubleBuffered(true);
		setLayout(new GridLayout(3, 1));
		lives = 3;
		health = HEALTHRESET;
		specialAtt = resetSpecialAtt;

		arrFrames = new ArrayList<ArrayList<ImageFrame>>();
		enemies = new ArrayList<EnemySoda>();
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// initShootThread();
				int mouseX = e.getX();
				int mouseY = e.getY();
				if (e.getButton() == MouseEvent.BUTTON1) {
					coke.fire(mouseX, mouseY);
				}
			}
		});
		enemyTimer = new Timer(ENEMYDELAY, new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				// every 10 dead enemies, decrease enemy spawn delay by .1
				// second; no lower than 1.1 second
				if (enemiesDead > 10) {
					if (ENEMYDELAY > 1100) {
						ENEMYDELAY = ENEMYDELAY - 100;
						enemyTimer.setDelay(ENEMYDELAY);
					}
					enemiesDead = 0;
					bossToken++;
				}
				// spawns boss every 10 dead enemies
				if (bossToken > 0) {
					bossToken--;
					boss = true;
					spawnEnemy(boss);
					boss = false;
				}
				spawnEnemy(false);

			}
		});

		coke = new Coke((int) BOARD_WIDTH / 2, (int) BOARD_HEIGHT / 2);

		title = new JLabel("Game Over!");
		title.setFont(new Font("Serif", Font.BOLD, 100));
		title.setBackground(Color.WHITE);
		title.setForeground(Color.RED);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBackground(Color.BLACK);
		title.setOpaque(true);
		title.setVisible(false);
		// gameOverTitle = new JPanel();
		// gameOverTitle.add(title);
		// gameOverTitle.setOpaque(true);
		// gameOverTitle.setVisible(false);

		sodaScore = new JLabel("Score: " + score);
		sodaScore.setFont(new Font("Serif", Font.BOLD, 50));
		sodaScore.setForeground(Color.WHITE);
		sodaScore.setVerticalAlignment(JLabel.TOP);

		livesScore = new JLabel("Lives: " + lives + " Health: " + health);
		livesScore.setFont(new Font("Serif", Font.BOLD, 50));
		livesScore.setForeground(Color.RED);
		livesScore.setVerticalAlignment(JLabel.BOTTOM);

		add(sodaScore);
		add(title);
		add(livesScore);

		setOpaque(false);
		initKeyBindings();
		enemyTimer.start();
		timer.start();

	}

	private void loadCollisionImage() {
		frame1 = Toolkit.getDefaultToolkit().getImage("Explosion/frame1.gif");
		frame2 = Toolkit.getDefaultToolkit().getImage("Explosion/frame2.gif");
		frame3 = Toolkit.getDefaultToolkit().getImage("Explosion/frame3.gif");
		frame4 = Toolkit.getDefaultToolkit().getImage("Explosion/frame4.gif");
		frame5 = Toolkit.getDefaultToolkit().getImage("Explosion/frame5.gif");
		frame6 = Toolkit.getDefaultToolkit().getImage("Explosion/frame6.gif");
		frame7 = Toolkit.getDefaultToolkit().getImage("Explosion/frame7.gif");
		frame8 = Toolkit.getDefaultToolkit().getImage("Explosion/frame8.gif");
		frame9 = Toolkit.getDefaultToolkit().getImage("Explosion/frame9.gif");
		frame10 = Toolkit.getDefaultToolkit().getImage("Explosion/frame10.gif");
		frame11 = Toolkit.getDefaultToolkit().getImage("Explosion/frame11.gif");
		frame12 = Toolkit.getDefaultToolkit().getImage("Explosion/frame12.gif");
		frame13 = Toolkit.getDefaultToolkit().getImage("Explosion/frame13.gif");
		frame14 = Toolkit.getDefaultToolkit().getImage("Explosion/frame14.gif");
		frame15 = Toolkit.getDefaultToolkit().getImage("Explosion/frame15.gif");
		frame16 = Toolkit.getDefaultToolkit().getImage("Explosion/frame16.gif");
	}

	public int getScore() {
		return score;
	}

	public void stopMusic() {
		music.interrupt();
	}

	public void stop() {
		enemyTimer.stop();
		timer.stop();
	}

	public void start() {
		enemyTimer.start();
		timer.start();
	}

	public void resetSpecialAtt() {
		specialAtt = resetSpecialAtt;
	}

	private void updateScore() {
		score++;
		sodaScore.setText("Score: " + score);
	}

	private void increaseScore(int addScore) {
		Board.score += addScore;
		sodaScore.setText("Score: " + score);

	}

	public void setScore(int score) {
		Board.score = score;
		sodaScore.setText("Score: " + score);
	}

	public void setEnemiesDead(int enemiesDead) {
		Board.enemiesDead = enemiesDead;
	}

	public void updateLivesAndHealth() {

		if (health == 0) {
			lives--;
			if (lives == 0) {
				endGame();
			} else {
				health = HEALTHRESET;
			}

		}

		livesScore.setText("Lives: " + lives + " Health: " + health);
	}

	public void decHealth() {
		health--;
	}

	public void decHealth(int decH) {
		health = health - decH;
	}

	public void decHealthCollision() {
		health = health - 10;
	}

	public void resetLivesAndHealth() {
		lives = LIVESRESET;
		health = HEALTHRESET;
		livesScore.setText("Lives: " + lives + " Health: " + health);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);
		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		if (coke.isVisible()) {

			g2d.drawImage(coke.getImage(), coke.getX(), coke.getY(), this);
		}
		ArrayList<Missile> ms = coke.getMissiles();

		for (Missile m : ms) {

			g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
		}

		for (EnemySoda enemy : enemies) {
			g2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
		}
		if (arrFrames.size() > 0) {
			for (ArrayList<ImageFrame> arr : arrFrames) {
				if (arr.size() > 0) {
					g2d.drawImage(arr.get(0).getImage(), arr.get(0).getX(), arr
							.get(0).getY(), this);
				}
			}
		}
	}

	private void spawnEnemy(boolean boss) {

		int randNum = (int) (Math.random() * 4 + 1);

		int randWidth = (int) (Math.random() * BOARD_WIDTH);
		int randHeight = (int) (Math.random() * BOARD_HEIGHT);

		EnemySoda enemySoda = randomEnemy();
		EnemySoda bigSoda = null;
		if (boss) {
			bigSoda = new BigPepsi(0, 0);
		}
		// spawn random enemy randomly from each of the screen's side borders
		switch (randNum) {
		case 1:
			enemySoda.setX(20);
			enemySoda.setY(randHeight);
			if (boss) {
				bigSoda.setX((int) BOARD_WIDTH);
				bigSoda.setY(randHeight);
			}
			break;
		// right border
		case 2:
			enemySoda.setX((int) BOARD_WIDTH);
			enemySoda.setY(randHeight);
			if (boss) {
				bigSoda.setX(20);
				bigSoda.setY(randHeight);

			}
			break;
		// bottom border
		case 3:
			enemySoda.setX(randWidth);
			enemySoda.setY((int) BOARD_HEIGHT);
			if (boss) {
				bigSoda.setX(randWidth);
				bigSoda.setY(20);
			}
			break;
		// top border
		case 4:
			enemySoda.setX(randWidth);
			enemySoda.setY(20);
			if (boss) {
				bigSoda.setX(randWidth);
				bigSoda.setY((int) BOARD_HEIGHT);

			}
			break;
		}
		if (boss) {
			enemies.add(bigSoda);
		}
		enemies.add(enemySoda);
	}

	public void actionPerformed(ActionEvent e) {
		updateMissiles();
		updateCoke();
		updateEnemies();
		updateCollisionFrames();
		checkCollision();
		repaint();
	}

	private EnemySoda randomEnemy() {
		int randSoda = (int) (Math.random() * 8 + 1);
		switch (randSoda) {
		case 1:
			return new Fanta(0, 0);
		case 2:
			return new Mist(0, 0);
		case 3:
			return new DrPepper(0, 0);
		case 4:
			return new MDew(0, 0);
		case 5:
			return new Sprite(0, 0);
		case 6:
			return new Pepsi(0, 0);
		case 7:
			return new SevenUP(0, 0);
		case 8:
			return new RootBeer(0, 0);
		}
		return null;
	}

	private void updateCollisionFrames() {

		ArrayList<ArrayList<ImageFrame>> tempArr = arrFrames;
		if (arrFrames.size() > 0) {
			for (ArrayList<ImageFrame> arr : arrFrames) {
				if (arr.size() > 0) {
					arr.remove(0);
				}
			}
		}
		// clean any empty arraylist from arrFrames
		for (int x = 0; x < tempArr.size(); x++) {
			if (tempArr.get(x).isEmpty()) {
				arrFrames.remove(x);
				x--;
			}
		}
	}

	private void updateEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			EnemySoda enemy = enemies.get(i);
			if (enemy.isVisible()) {
				enemy.setDestX(coke.getX());
				enemy.setDestY(coke.getY());
				enemy.calcVector();
				enemy.move();
			} else {
				enemies.remove(i);
			}
		}
	}

	private void updateMissiles() {

		ArrayList<Missile> ms = coke.getMissiles();

		for (int i = 0; i < ms.size(); i++) {

			Missile m = (Missile) ms.get(i);

			if (m.isVisible()) {
				m.move();
			} else {

				ms.remove(i);
			}
		}
	}

	private void updateCoke() {

		coke.move();
	}

	public void checkCollision() {

		for (EnemySoda enemySoda : enemies) {
			if (coke.getBounds().intersects(enemySoda.getBounds())) {
				addCollisionFrames(coke.getX() - 50, coke.getY() - 50);
				if (enemySoda.isBoss()) {
					decHealth(20);
				} else {
					decHealthCollision();
				}
				updateLivesAndHealth();
				enemiesDead++;
				enemySoda.setVisible(false);

			}
		}

		ArrayList<Missile> ms = coke.getMissiles();

		for (Missile m : ms) {
			for (EnemySoda enemy : enemies) {

				if (m.getBounds().intersects(enemy.getBounds())) {
					addCollisionFrames(m.getX() - 50, m.getY() - 50);
					enemy.decHealth();

					updateScore();

					m.setVisible(false);
					if (enemy.getHealth() == 0) {
						if (enemy.isBoss) {
							increaseScore(12);
						}
						enemiesDead++;
						enemy.setVisible(false);
						return;
					}
				}
			}
		}

	}

	private void destroyAllEnemies() {
		if (enemies.size() > 0)

			enemiesDead += enemies.size();
		for (EnemySoda soda : enemies) {
			addCollisionFrames(soda.getX() - 50, soda.getY() - 50);
			if (soda.isBoss()) {
				increaseScore(12);
			} else {
				increaseScore(3);
			}

			soda.setVisible(false);

		}
	}

	private void addCollisionFrames(int collideX, int collideY) {
		frames = new ArrayList<ImageFrame>();

		frames.add(new ImageFrame(frame1, collideX, collideY));
		frames.add(new ImageFrame(frame2, collideX, collideY));
		frames.add(new ImageFrame(frame3, collideX, collideY));
		frames.add(new ImageFrame(frame4, collideX, collideY));
		frames.add(new ImageFrame(frame5, collideX, collideY));
		frames.add(new ImageFrame(frame6, collideX, collideY));
		frames.add(new ImageFrame(frame7, collideX, collideY));
		frames.add(new ImageFrame(frame8, collideX, collideY));
		frames.add(new ImageFrame(frame9, collideX, collideY));
		frames.add(new ImageFrame(frame10, collideX, collideY));
		frames.add(new ImageFrame(frame11, collideX, collideY));
		frames.add(new ImageFrame(frame12, collideX, collideY));
		frames.add(new ImageFrame(frame13, collideX, collideY));
		frames.add(new ImageFrame(frame14, collideX, collideY));
		frames.add(new ImageFrame(frame15, collideX, collideY));
		frames.add(new ImageFrame(frame16, collideX, collideY));

		arrFrames.add(frames);
	}

	private void endGame() {

		gameOver = true;
		stop();
		// gameOverTitle.setVisible(true);
		title.setVisible(true);
		validate();
		repaint();

	}

	public boolean getGameOver() {
		return gameOver;
	}

	private void initKeyBindings() {
		InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actMap = getActionMap();

		KeyStroke leftRelease = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true);
		KeyStroke leftPress = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false);
		KeyStroke rightRelease = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true);
		KeyStroke rightPress = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false);
		KeyStroke upRelease = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true);
		KeyStroke upPress = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false);
		KeyStroke downRelease = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true);
		KeyStroke downPress = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false);
		KeyStroke spacePress = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0,
				false);

		inputMap.put(leftRelease, "leftRelease");
		inputMap.put(leftPress, "leftPress");
		inputMap.put(rightRelease, "rightRelease");
		inputMap.put(rightPress, "rightPress");
		inputMap.put(upRelease, "upRelease");
		inputMap.put(upPress, "upPress");
		inputMap.put(downRelease, "downRelease");
		inputMap.put(downPress, "downPress");
		inputMap.put(spacePress, "spacePress");

		actMap.put("leftPress", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				// craft.moveLeft( true );

				coke.setVelX(-dx);

				// craft.setVelX( -2 );
			}

		});
		actMap.put("leftRelease", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {

				// craft.moveLeft( false );
				if (coke.getVelX() == dx) {
					return;
				}
				coke.setVelX(0);

			}

		});
		actMap.put("rightPress", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				// craft.moveRight( true );

				coke.setVelX(dx);

				// craft.setVelX( 4 );
			}
		});
		actMap.put("rightRelease", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				// craft.moveRight( false );

				// stop interference when moving in an opposite direction
				if (coke.getVelX() == -dx) {
					return;
				}
				coke.setVelX(0);
			}
		});
		actMap.put("upPress", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				// craft.moveUp( true );
				coke.setVelY(-dy);
			}
		});
		actMap.put("upRelease", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				// craft.moveUp( false );

				if (coke.getVelY() == dy) {
					return;
				}
				coke.setVelY(0);

			}
		});

		actMap.put("downPress", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {

				// craft.moveDown( true );
				coke.setVelY(dy);
			}
		});
		actMap.put("downRelease", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {

				// craft.moveDown( false );
				if (coke.getVelY() == -dy) {
					return;
				}
				coke.setVelY(0);
			}
		});
		actMap.put("spacePress", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				if (specialAtt > 0) {
					specialAtt--;
					destroyAllEnemies();
				}
			}
		});
	}

	static class ImageFrame {
		Image im;

		int locX;

		int locY;

		public ImageFrame(Image im, int locX, int locY) {
			this.im = im;
			this.locX = locX;
			this.locY = locY;
		}

		public Image getImage() {
			return im;
		}

		public int getX() {
			return locX;
		}

		public int getY() {
			return locY;
		}

	}
}