/***********************************************************************
 * This class acts as the View in my MVC design. It displays the game
 * information relative to the user and communicates with the Controller
 * to get the information it needs to display.
 ***********************************************************************/
package com.blackjack.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.blackjack.model.Card;
import com.blackjack.model.GameState;
import com.blackjack.model.Hand;

public class Game_Canvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int W_WIDTH = 640;
	private static final int W_HEIGHT = 480;
	
	private BufferedImage image;
	
	private Button hitButton;
	public Button getHitButton() { return this.hitButton; }
	private Button stayButton;
	public Button getStayButton() { return this.stayButton; }
	private Button nextButton;
	public Button getNextButton() { return this.nextButton; }

	public Game_Canvas() {
		this.setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
		this.setBackground(Color.WHITE);
		
		this.image = new BufferedImage(Game_Canvas.W_WIDTH, Game_Canvas.W_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		int w = 80;
		int h = 40;
		int y = Game_Canvas.W_HEIGHT - (h + 20);
		{ // Initialize Hit Button
			int x = Game_Canvas.W_WIDTH - ((w * 2) + 30);
			this.hitButton = new Button("Hit", x, y, w, h, true);
		} // End Hit Button Init
		
		{ // Initialize Stay Button
			int x = Game_Canvas.W_WIDTH - (w + 20);
			this.stayButton = new Button("Stay", x, y, w, h, true);
		} // End Stay Button Init
		
		{ // Initialize Next Button
			int x = Game_Canvas.W_WIDTH - ((w * 3) + 40);
			this.nextButton = new Button("Next", x, y, w, h, false);
		} // End Next Button Init
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(this.image, 0, 0, null);
		g2d.dispose();
	}
	
	public void update(GameState state, Hand playerHand, Hand dealerHand) {
		Graphics2D g2d = image.createGraphics();
		
		// Erase the previous image.
		g2d.setColor(new Color(0, 153, 51));
		g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
		
		Font saved = g2d.getFont();
		
		// Draw the dealer's hand value at the top of the screen.
		{
			String scoreString = "Dealer Hand: " + ((dealerHand.isRevealed())?Integer.toString(dealerHand.getValue()):"?");
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Comic Sans MS", Font.PLAIN, 24)); // Don't judge me...
			g2d.drawString(scoreString, 20, 44);
		}
		
		// Draw the player's hand value at the bottom of the screen.
		{
			String scoreString = "Player Hand: " + playerHand.getValue();
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Comic Sans MS", Font.PLAIN, 24)); // Don't judge me...
			g2d.drawString(scoreString, 20, (Game_Canvas.W_HEIGHT - 20));
		}
		
		// Draw the Hit and Stay buttons.
		hitButton.draw(g2d);
		stayButton.draw(g2d);
		nextButton.draw(g2d);
		
		{ // Draw a status message depending on the game state.
			String message = "";
			if(state == GameState.PLAYER_WIN) {
				message = "Player wins!";
			} else if(state == GameState.DEALER_WIN) {
				message = "Dealer wins...";
			} else if(state == GameState.PUSH) {
				message = "It's a tie!";
			}
			
			Font font = new Font("Comic Sans MS", Font.BOLD, 24);
			FontMetrics metrics = g2d.getFontMetrics(font);
			
			int x = Game_Canvas.W_WIDTH - (metrics.stringWidth(message) + 20);
			int y = metrics.getHeight() + 10;
			
			g2d.setColor(Color.BLACK);
			g2d.setFont(font);
			g2d.drawString(message, x, y);
		} // End drawing status message.
		
		
		g2d.setFont(saved);
		
		// Offsets used to draw the player and dealer's hands.
		int dealerOffX = 20;
		int dealerOffY = 110;
		int dealerPadding = 5;
		int playerOffX = 20;
		int playerOffY = Game_Canvas.W_HEIGHT - (Card.CARD_HEIGHT + 110);
		int playerPadding = 5;
		
		// Draw the dealer's hand at the top of the screen.
		for(int i = 0; i < dealerHand.getCards().size(); i++) {
			Card c = dealerHand.getCards().get(i);
			int x = dealerOffX + (i * dealerPadding) + (i * Card.CARD_WIDTH);
			int y = dealerOffY;
			
			// If the dealer has not yet revealed their hand, only show the first card.
			// The other cards will show their backs.
			if(!dealerHand.isRevealed() && (i != 0)) g2d.drawImage(Card.BACK_IMAGE, x, y, null);
			else g2d.drawImage(c.getImage(), x, y, null);
		}
		
		// Draw the player's hand at the bottom of the screen.
		for(int i = 0; i < playerHand.getCards().size(); i++) {
			Card c = playerHand.getCards().get(i);
			int x = playerOffX + (i * playerPadding) + (i * Card.CARD_WIDTH);
			int y = playerOffY;
			g2d.drawImage(c.getImage(), x, y, null);
		}
		
		g2d.dispose();
	}
}
