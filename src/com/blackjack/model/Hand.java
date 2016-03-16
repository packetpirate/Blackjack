package com.blackjack.model;

import java.util.ArrayList;

public class Hand {
	private boolean revealed;
	public boolean isRevealed() { return this.revealed; }
	public void setRevealed(boolean r) { this.revealed = r; }
	
	private ArrayList<Card> cards;
	public ArrayList<Card> getCards() { return this.cards; }
	
	public Hand(boolean _revealed_) {
		this.revealed = _revealed_;
		this.cards = new ArrayList<>();
	}
	
	public void discardHand() {
		this.cards.clear();
	}
	
	public void addCard(Card c) {
		this.cards.add(c);
	}
	
	public int getValue() {
		int value = 0;
		int aces = 0;
		for(Card c : this.cards) {
			if(c.getValue() == CardValue.ACE) aces++;
			value += c.getValue().getCardValue();
		}
		
		while((value > 21) && (aces > 0)) {
			value -= 10;
			aces--;
		}
		
		return value;
	}
}
