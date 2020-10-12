package snake;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Board extends JPanel implements ActionListener{
	private Image apple;
	private Image dot;
	private Image head;
	
	private final int DOT_SIZE = 10; 
	private final int ALL_DOTS = 900;  
	private final int RANDOM_POSITION = 29; 
	
	private int apple_x;  
	private int apple_y;  
	
	private final int x_cordinate[] = new int[ALL_DOTS];     
	private final int y_cordinate[] = new int[ALL_DOTS];
	
	
	private boolean leftDirection = false;
	
	private boolean rightDirection = true;
	
	private boolean upDirection = false;
	private boolean downDirection = false;
	
	
	private boolean inGame = true; 
	
	private int dots;
	
	private Timer timer; 
	
	Board(){
		
		addKeyListener(new TAdapter()); 
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(300, 300)); 
	
		setFocusable(true);
		loadImages();  
		initGame();  
	}
	
	public void loadImages() {
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snake/games/icons/apple.png"));
		apple = i1.getImage();
		ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snake/games/icons/dot.png"));
		dot = i2.getImage();
		ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snake/games/icons/head.png"));
		head = i3.getImage();
	}
	
	public void initGame() {
		dots = 3;    // size of the snake is 3;
		
		for(int i=0; i<dots; i++) {
			 
			x_cordinate[i] = 50 - i*DOT_SIZE; 
			y_cordinate[i] = 50;	
		}
		locateApple();
		
		timer = new Timer(140, this);
		timer.start();  
		
		
	}

	
	public void locateApple() {
		int r = (int)(Math.random() * RANDOM_POSITION);
		
		apple_x = (r * DOT_SIZE); 
		r = (int)(Math.random() * RANDOM_POSITION);
		apple_y = (r * DOT_SIZE);
	}

	public void checkApple() {
		if( x_cordinate[0] == apple_x && y_cordinate[0] == apple_y ) {
			dots++;
			locateApple();
			// locateApple() se new apple create hoga at random position;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		 
		super.paintComponent(g);
		
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(inGame) {
			g.drawImage(apple, apple_x, apple_y, this);
		
			for(int i=0; i<dots; i++) {
				if(i == 0) {
					g.drawImage(head, x_cordinate[i], y_cordinate[i], this);
				} else {
					g.drawImage(dot, x_cordinate[i], y_cordinate[i], this);
				}
			}
		
			Toolkit.getDefaultToolkit().sync();
		} else {
			gameOver(g);
		}
			
	}
	
	
	private void gameOver(Graphics g) {
		String msg = "Game Over";
		Font font = new Font("SAN_SERIF", Font.BOLD,14);
		FontMetrics metrics = getFontMetrics(font);
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg, (300-metrics.stringWidth(msg))/2, 300/2);
		
	}
	
	
	private void checkCollision() {
		if(y_cordinate[0] >= 300)
			y_cordinate[0] = (int)(Math.random() * RANDOM_POSITION) * DOT_SIZE;
		if(x_cordinate[0] >= 300)
			x_cordinate[0] = (int)(Math.random() * RANDOM_POSITION) * DOT_SIZE;
		if(x_cordinate[0] < 0)
			x_cordinate[0] = (int)(Math.random() * RANDOM_POSITION) * DOT_SIZE;
		if(y_cordinate[0] < 0)
			y_cordinate[0] = (int)(Math.random() * RANDOM_POSITION) * DOT_SIZE;
		
		for(int i = dots; i>0; i--) {
			if(i>4 && x_cordinate[0] == x_cordinate[i] && y_cordinate[0] == y_cordinate[i])
				inGame = false;
		}
		
		if(!inGame)
			timer.stop(); 
	}
	
	
	public void move() {
		
		for(int i=dots; i>0; i--) {
			x_cordinate[i] = x_cordinate[i-1];
			y_cordinate[i] = y_cordinate[i-1];
		}

		if(leftDirection) {
			x_cordinate[0] -= DOT_SIZE;
		}
		
		if(rightDirection) {
			x_cordinate[0] += DOT_SIZE;
		}
		
		if(downDirection) {
			y_cordinate[0] += DOT_SIZE;
		}
		
		if(upDirection) {
			y_cordinate[0] -= DOT_SIZE;
		}
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(inGame) {
				checkApple();
				 
				checkCollision();
				move();
		}
			repaint();
		}
		
	
	private class TAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			 
			if(key == KeyEvent.VK_LEFT && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if(key == KeyEvent.VK_RIGHT && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			
			if(key == KeyEvent.VK_UP && (!downDirection)) {
				leftDirection = false;
				upDirection = true;
				rightDirection = false;
			}
			
			if(key == KeyEvent.VK_DOWN && (!upDirection)) {
				leftDirection = false;
				downDirection = true;
				rightDirection = false;
			}
		}
		 
	}
	 
}

