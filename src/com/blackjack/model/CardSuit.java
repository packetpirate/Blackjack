package com.blackjack.model;

public enum CardSuit {
	CLUBS(0), 
	DIAMONDS(1), 
	HEARTS(2), 
	SPADES(3);
	
	private int index;
	public int getIndex() { return this.index; }
	
	private CardSuit(int i) {
		this.index = i;
	}
}
