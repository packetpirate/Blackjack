/*****************************************************************
 * This class acts as the Model in my MVC design. It handles
 * the game logic and data manipulation. It communicates with
 * the Controller so that the data can be used to update the View.
 *****************************************************************/
package com.blackjack.model;

public class Game_Framework {
	private GameState state;
	public GameState getState() { return this.state; }
	
	private Deck deck;
	public Deck getDeck() { return this.deck; }
	
	private Hand playerHand;
	public Hand getPlayerHand() { return this.playerHand; }
	private Hand dealerHand;
	public Hand getDealerHand() { return this.dealerHand; }
	
	public Game_Framework() {
		this.deck = new Deck();
		this.playerHand = new Hand(true);
		this.dealerHand = new Hand(false);
		
		nextGame();
	}
	
	public void nextGame() {
		state = GameState.PLAYER_TURN;
		
		deck.shuffle();
		playerHand.discardHand();
		dealerHand.discardHand();
		dealerHand.setRevealed(false);
		
		/******************************************************************
		 * According to game rules, the player is dealt to first.
		 * This is because in the old west, if you dealt to yourself first,
		 * people would suspect you of cheating, and it could get you shot.
		 ******************************************************************/
		playerHand.addCard(deck.drawCard());
		dealerHand.addCard(deck.drawCard());
		playerHand.addCard(deck.drawCard());
		dealerHand.addCard(deck.drawCard());
	}
	
	public void hit(Hand hand) {
		// Draw a card from the deck and check to see if the player has busted.
		if(hand.getValue() < 21) hand.addCard(deck.drawCard());
		if(hand.getValue() > 21) {
			dealerHand.setRevealed(true);
			state = GameState.DEALER_WIN;
		}
	}
	
	public void stay() {
		// Now it's the dealer's turn. Reveal the hole card.
		state = GameState.DEALER_TURN;
		dealerHand.setRevealed(true);
		
		// The dealer now draws until his hand value is greater than or equal to 17.
		while(dealerHand.getValue() < 17) {
			dealerHand.addCard(deck.drawCard());
		}
		
		// Determine who wins the hand.
		if(dealerHand.getValue() > playerHand.getValue()) {
			if(dealerHand.getValue() <= 21) state = GameState.DEALER_WIN;
			else state = GameState.PLAYER_WIN;
		} else if(dealerHand.getValue() < playerHand.getValue()) {
			state = GameState.PLAYER_WIN;
		} else {
			state = GameState.PUSH;
		}
	}
}