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
	
	private final int DOT_SIZE = 10; // FRAME 300*300 = 90000 / 100 = 900
	private final int ALL_DOTS = 900;  // maximum number of dots that can be present;
	private final int RANDOM_POSITION = 29;   // we set t
	// at the position of 29 it will cover max range; which is // 300 * 300;
	
	private int apple_x;  // this is the location of the apple at x cordinates
	private int apple_y;  // this is the location of a apple at y cordinates
	
	private final int x_cordinate[] = new int[ALL_DOTS];       // cordinates of all the dots matlab cordinates
	private final int y_cordinate[] = new int[ALL_DOTS];// cordinates head ke bhi hai and puri tail ke;
	
	// because snake is not moving in left direction initially;
	private boolean leftDirection = false;
	// because initial snake is moving in the right direction only initially;
	private boolean rightDirection = true;
	// because initially snake is not moving up and down;
	private boolean upDirection = false;
	private boolean downDirection = false;
	
	// initially inGame is true because we are in the game initially;
	private boolean inGame = true;   // when inGame will false then game over;
	
	private int dots;
	
	private Timer timer;   // we use timer class to set a small delay;
	
	Board(){
		
		// by using addKeyListner we can take controls over the keys we press;
		// so according to the functions of the keys snake will move;
		addKeyListener(new TAdapter()); 
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(300, 300));  // we set the dimension of our window by using setPreferredSize();
		
		// keylistner will only work if we set focous true on them
		setFocusable(true);
		loadImages();  // we can load Images by using LoadImages() method;
		initGame();    // game will initialise with initGame() function;
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
		
		// here snake will also start at the fix cordinates
		// that why we set x and y cordinates so that 
		// snake will always start at the same position;
		for(int i=0; i<dots; i++) {
			// x cordinate will vary jab starting me hamare pass 3 dots
			// first dot 50 and second wala first ke peche;
			x_cordinate[i] = 50 - i*DOT_SIZE;  // x[0] y[0] | x[1] y[1] | x[2] y[2]
			y_cordinate[i] = 50;				// head is at x[0] y[0]
		}
		locateApple();
		
		// we can add a slight delay in the movement of snake;
		// by using timer class;
		// we implement ActionListner Interface for timer
		// here this will work for actionListner interface and call actionPerformed method; 
		timer = new Timer(140, this);
		timer.start();  
		
		
	}
	
	// now my aim is to set the cordinates of apple
	// Coordinates of apple should not be at fixed position
	// Coordinates should be at random;
	
	public void locateApple() {
		int r = (int)(Math.random() * RANDOM_POSITION);
		// 50*10 = 500;
		// 1-20 // 200 // 300-200 = 100;
		// random function generates random function 0 and 1
		// we multiply it to RANDOM_POSITON TO GET A INTEGER value
		// and we are multiplying it with DOT_SIZE TO generate x cordinate;
		
		apple_x = (r * DOT_SIZE); 
		r = (int)(Math.random() * RANDOM_POSITION);
		apple_y = (r * DOT_SIZE);
	}

	public void checkApple() {
		// is function me mera ye target hoga ki
		// mujhe check karna hoga ki
		// apple and head tabhi milege jab head and apple k
		
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
			
			// here we are adding all the componets;
			for(int i=0; i<dots; i++) {
				//i am adding head and tails seperately beacuse head is 
				//of different color and tail is of different;
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
		// first check ye hoga ki agar head diwar(wall) se takragaya
		// to game over;
		//by applying these four check we have handled all cases for the collision 
		//with all four walls;
		if(y_cordinate[0] >= 300)
			y_cordinate[0] = (int)(Math.random() * RANDOM_POSITION) * DOT_SIZE;
		if(x_cordinate[0] >= 300)
			x_cordinate[0] = (int)(Math.random() * RANDOM_POSITION) * DOT_SIZE;
		if(x_cordinate[0] < 0)
			x_cordinate[0] = (int)(Math.random() * RANDOM_POSITION) * DOT_SIZE;
		if(y_cordinate[0] < 0)
			y_cordinate[0] = (int)(Math.random() * RANDOM_POSITION) * DOT_SIZE;
		
		//now lets take care a condition when snake collide with itself;
		// snake can only collide with itself only if its size is greater than 5;
		// because initially its size was 3 ;
		for(int i = dots; i>0; i--) {
			if(i>4 && x_cordinate[0] == x_cordinate[i] && y_cordinate[0] == y_cordinate[i])
				inGame = false;
		}
		
		// mujhe timer ko stop karna hoga jab inGame false ho jayega 
		// kyoki timer timer to chalta rahega and timer ko false hote hi 
		// stop hona hoga;
		if(!inGame)
			timer.stop(); 
	}
	
	
	public void move() {
		
		for(int i=dots; i>0; i--) {
			x_cordinate[i] = x_cordinate[i-1];
			y_cordinate[i] = y_cordinate[i-1];
		}
		
		// by applying first four conditions we moved head;
		// asume initial cordinate is 240;
		if(leftDirection) {
			x_cordinate[0] -= DOT_SIZE;
		}
		// 240-10// - size of dot
		
		if(rightDirection) {
			x_cordinate[0] += DOT_SIZE;
			// 240+10;
		}
		
		if(downDirection) {
			y_cordinate[0] += DOT_SIZE;
			// 240-10;
		}
		
		if(upDirection) {
			y_cordinate[0] -= DOT_SIZE;
			 //240+10;
		}
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(inGame) {
			// mujhe apple find karna hoga and dekhna hoga ki snake ka head 
			// apple se takraya hai ki nahi
			// uske liye main checkApple() ko call karuga check karne ke liya
				checkApple();
				 
				checkCollision();
				// checkCollision() se main check karuga ki collision kab hoga;
				move();
			}
			// we call repaint() method when we the look of the component changes;
			repaint();
		}
		
	
	private class TAdapter extends KeyAdapter{
		@Override
		// I am using this method to check which key is pressed;
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

