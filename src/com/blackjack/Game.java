/**********************************************************
 * Author: Darin Beaudreau
 * Last Modified: 03/15/2016
 * 
 * Description: This is a simple graphical Blackjack game 
 *  developed for my Software Engineering class at the
 *  University of Massachusetts in Lowell. The aim of the
 *  project was simply to create a Blackjack game that
 *  utilizes the MVC design pattern.
 *  
 * License: This project is distributed under the
 *  GNU Public License. You can find the terms of the
 *  license attached in the "LICENSE" file, or here:
 *  
 *  http://www.gnu.org/licenses/gpl-3.0.en.html
 *  
 * Remember kids... distribute your code free.
 * Free as in freedoms.
 * 
 *              .-=-==--==--.
 *      ..-=="  ,'o`)      `.
 *    ,'         `"'         \
 *   :  (                     `.__...._
 *   |                  )    /         `-=-.
 *   :       ,vv.-._   /    /               `---==-._
 *    \/\/\/VV ^ d88`;'    /                         `.
 *        ``  ^/d88P!'    /             ,              `._
 *           ^/    !'   ,.      ,      /                  "-,,__,,--'""""-.
 *          ^/    !'  ,'  \ . .(      (         _           )  ) ) ) ))_,-.\
 *         ^(__ ,!',"'   ;:+.:%:a.     \:.. . ,'          )  )  ) ) ,"'    '
 *         ',,,'','     /o:::":%:%a.    \:.:.:         .    )  ) _,'
 *          """'       ;':::'' `+%%%a._  \%:%|         ;.). _,-""
 *                 ,-='_.-'      ``:%::)  )%:|        /:._,"
 *                (/(/"           ," ,'_,'%%%:       (_,'
 *                               (  (//(`.___;        \
 *                                \     \    `         `
 *                                 `.    `.   `.        :
 *                                   \. . .\    : . . . :
 *                                    \. . .:    `.. . .:
 *                                     `..:.:\     \:...\
 *                                      ;:.:.;      ::...:
 *                                      ):%::       :::::;
 *                                  __,::%:(        :::::
 *                               ,;:%%%%%%%:        ;:%::
 *                                 ;,--""-.`\  ,=--':%:%:\
 *                                /"       "| /-".:%%%%%%%\
 *                                                ;,-"'`)%%)   (CJ)
 *                                               /"      "|
 **********************************************************/
package com.blackjack;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;

import com.blackjack.controller.Game_Controller;
import com.blackjack.model.Card;
import com.blackjack.model.Game_Framework;
import com.blackjack.view.Game_Canvas;

public class Game extends JApplet implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private boolean running;
	
	private Game_Canvas canvas;
	public Game_Canvas getCanvas() { return this.canvas; }
	private Game_Controller controller;
	private Game_Framework framework;

	public static void main(String[] args) {
		Game game = new Game();
		game.initializeComponents();
		
		JFrame frame = new JFrame("Blackjack");
		frame.add(game.getCanvas());
		
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		
		game.start();
	}
	
	private void initializeComponents() {
		running = false;
		
		// Initialize the card sprite sheet so the cards can be loaded fast.
		try {
			Card.CARD_SHEET = ImageIO.read(Game.class.getResource("/res/cards.png"));
		} catch(IOException io) {
			System.err.println("ERROR: Could not locate card spritesheet.");
			System.exit(1);
		}
		
		// Assuming the card sheet was loaded properly, load the card back image.
		if(Card.CARD_SHEET != null) {
			Card.BACK_IMAGE = Card.CARD_SHEET.getSubimage((Card.CARD_WIDTH * 2), (Card.CARD_HEIGHT * 4), 
														   Card.CARD_WIDTH, Card.CARD_HEIGHT);
		} else {
			System.err.println("ERROR: Card sprite sheet null. Could not load card back subimage.");
			System.exit(1);
		}
		
		canvas = new Game_Canvas();
		framework = new Game_Framework();
		controller = new Game_Controller(canvas, framework);
		controller.update();
	}
	
	public void start() {
		if(running) return;
		running = true;
		new Thread(this).start();
	}

	@Override
	public void run() {
		while(running) {
			canvas.repaint();
			
			try {
				Thread.sleep(20);
			} catch(InterruptedException ie) {
				System.out.println("Uh-oh! Spaghettios...");
				System.out.println("Exception during thread\'s nap time.");
			}
		}
		
		System.exit(0);
	}
}
