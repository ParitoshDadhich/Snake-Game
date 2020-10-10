package snake;

import javax.swing.*;
// we cannot add window ke andar window
// we can add window ke andar container;

public class Snake extends JFrame {
	Snake(){
		add(new Board());
		pack();
		// we use setLocationRelative to set frame at the centre;
		// in order to set the frame at other locations then use setLocation method;
		
		// to set a title of the game, we use setTitle() or else we can call super("title of the game")
		setLocationRelativeTo(null);  
		setTitle("Snake Game");
		setResizable(false);  // by setting setResizable false size of the window won't change;
	}
	
	public static void main(String args[]) {
		new Snake().setVisible(true);
	}
}
